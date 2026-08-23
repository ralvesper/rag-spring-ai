package com.highonline.rag.model;

import java.util.List;
import java.util.UUID;

public record ProductCreatedDto(
        UUID productId,
        String name,
        String description,
        String location,
        String category,
        double price,
        String currency,
        List<String> tags
) {
}
