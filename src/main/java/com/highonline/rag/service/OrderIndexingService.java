package com.highonline.rag.service;

import com.highonline.rag.model.OrderCreatedDto;
import com.highonline.rag.repository.VectorStoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class OrderIndexingService {

    private static final Logger log = LoggerFactory.getLogger(OrderIndexingService.class);

    private final VectorStoreRepository vectorStoreRepository;

    public OrderIndexingService(VectorStoreRepository vectorStoreRepository) {
        this.vectorStoreRepository = vectorStoreRepository;
    }

    public void index(OrderCreatedDto orderCreatedDto) {
        Document document = Document.builder()
                .id(orderCreatedDto.orderId().toString())
                .text(toEmbeddableText(orderCreatedDto))
                .metadata(Map.of(
                        VectorStoreRepository.METADATA_TYPE, VectorStoreRepository.TYPE_ORDER_HISTORY,
                        VectorStoreRepository.METADATA_CUSTOMER_ID, orderCreatedDto.customerId().toString(),
                        "orderId", orderCreatedDto.orderId().toString()))
                .build();

        vectorStoreRepository.save(document);
        log.info("[INDEXING] Saved order {} as purchase history for customer {}",
                orderCreatedDto.orderId(), orderCreatedDto.customerId());
    }

    private String toEmbeddableText(OrderCreatedDto orderCreatedDto) {
        return "Customer %s purchased the activity \"%s\" in %s."
                .formatted(orderCreatedDto.customerId(), orderCreatedDto.orderName(), orderCreatedDto.orderLocation());
    }
}
