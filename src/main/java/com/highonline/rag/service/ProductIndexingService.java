package com.highonline.rag.service;

import com.highonline.rag.model.ProductCreatedDto;
import com.highonline.rag.repository.VectorStoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductIndexingService {

    private static final Logger log = LoggerFactory.getLogger(ProductIndexingService.class);

    private final VectorStoreRepository vectorStoreRepository;

    public ProductIndexingService(VectorStoreRepository vectorStoreRepository) {
        this.vectorStoreRepository = vectorStoreRepository;
    }

    public void index(ProductCreatedDto productCreatedDto) {
        Document document = Document.builder()
                .id(productCreatedDto.productId().toString())
                .text(toEmbeddableText(productCreatedDto))
                .metadata(Map.of(
                        VectorStoreRepository.METADATA_TYPE, VectorStoreRepository.TYPE_PRODUCT,
                        "productId", productCreatedDto.productId().toString(),
                        "name", productCreatedDto.name(),
                        "location", productCreatedDto.location(),
                        "category", productCreatedDto.category(),
                        "price", productCreatedDto.price(),
                        "currency", productCreatedDto.currency()))
                .build();

        vectorStoreRepository.save(document);
        log.info("[INDEXING] Indexed product '{}' ({}) into the vector store", productCreatedDto.name(), productCreatedDto.productId());
    }

    /**
     * Builds a natural-language representation of the tour. This is the text that
     * gets embedded, so it should read like something a customer might search for.
     */
    private String toEmbeddableText(ProductCreatedDto productCreatedDto) {
        return """
                %s in %s.
                Category: %s.
                %s
                Tags: %s.
                Price: %.2f %s.
                """.formatted(
                productCreatedDto.name(),
                productCreatedDto.location(),
                productCreatedDto.category(),
                productCreatedDto.description(),
                String.join(", ", productCreatedDto.tags()),
                productCreatedDto.price(),
                productCreatedDto.currency());
    }
}
