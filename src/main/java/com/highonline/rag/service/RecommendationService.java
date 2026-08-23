package com.highonline.rag.service;

import com.highonline.rag.model.OrderCreatedDto;
import com.highonline.rag.repository.VectorStoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RecommendationService {

    private static final Logger log = LoggerFactory.getLogger(RecommendationService.class);
    private static final int TOP_K = 4;

    private final ChatClient chatClient;
    private final VectorStoreRepository vectorStoreRepository;

    public RecommendationService(ChatClient recommendationChatClient,
                                 VectorStoreRepository vectorStoreRepository) {
        this.chatClient = recommendationChatClient;
        this.vectorStoreRepository = vectorStoreRepository;
    }

    public String recommendFor(OrderCreatedDto orderCreatedDto) {
        String searchQuery = buildSearchQuery(orderCreatedDto);

        // ===== PHASE 1: RETRIEVAL =====
        // Embed the query and fetch from pgvector both the available tours and this
        // customer's purchase history (vector similarity search).
        log.info("[RETRIEVAL] Searching pgvector for available tours and history of customer {}",
                orderCreatedDto.customerId());
        List<Document> availableTours = vectorStoreRepository.findSimilarProducts(searchQuery, TOP_K);
        List<Document> purchaseHistory = vectorStoreRepository.findCustomerHistory(
                orderCreatedDto.customerId(), searchQuery, TOP_K);
        log.info("[RETRIEVAL] Retrieved {} candidate tours and {} history entries",
                availableTours.size(), purchaseHistory.size());

        // ===== PHASE 2: AUGMENTATION =====
        // Inject the retrieved context into the prompt that will be sent to the LLM.
        String augmentedPrompt = buildAugmentedPrompt(orderCreatedDto, availableTours, purchaseHistory);
        log.info("[AUGMENTATION] Built augmented prompt with the retrieved context");

        // ===== PHASE 3: GENERATION =====
        // The LLM infers the travel profile and produces the grounded recommendation.
        log.info("[GENERATION] Asking the LLM to generate recommendations for order {}", orderCreatedDto.orderId());
        return chatClient.prompt()
                .user(augmentedPrompt)
                .call()
                .content();
    }

    /** Natural-language text used to embed and search the vector store. */
    private String buildSearchQuery(OrderCreatedDto orderCreatedDto) {
        return "Tours related to: %s in %s".formatted(orderCreatedDto.orderName(), orderCreatedDto.orderLocation());
    }

    private String buildAugmentedPrompt(OrderCreatedDto orderCreatedDto,
                                        List<Document> availableTours,
                                        List<Document> purchaseHistory) {
        return """
                The customer just purchased the activity "%s" in %s.

                == AVAILABLE TOURS (retrieved from the catalog) ==
                %s

                == CUSTOMER PURCHASE HISTORY (previously bought) ==
                %s

                Infer the customer's interests and travel profile from the purchased activity and history above.
                Recommend up to 3 tours chosen ONLY from the AVAILABLE TOURS section that best fit that
                profile, by both interest and location, so that together they compose a coherent itinerary.
                Recommend ONLY tours located in the same city or region as %s.
                Do NOT recommend any tour the customer has already purchased (avoid repetition).
                Keep the whole response to 5 lines maximum: be short, explanatory and to the point.
                Write the final recommendation in Brazilian Portuguese.
                """.formatted(
                orderCreatedDto.orderName(),
                orderCreatedDto.orderLocation(),
                renderContext(availableTours),
                purchaseHistory.isEmpty() ? "No previous purchases." : renderContext(purchaseHistory),
                orderCreatedDto.orderLocation());
    }

    private String renderContext(List<Document> documents) {
        return documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n---\n"));
    }
}
