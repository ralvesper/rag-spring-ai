package com.highonline.rag.controller;

import com.highonline.rag.demo.SampleEvents;
import com.highonline.rag.model.OrderCreatedDto;
import com.highonline.rag.model.ProductCreatedDto;
import com.highonline.rag.service.OrderIndexingService;
import com.highonline.rag.service.ProductIndexingService;
import com.highonline.rag.service.RecommendationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class RecommendationDemoController {

    private final ProductIndexingService productIndexingService;
    private final RecommendationService recommendationService;
    private final OrderIndexingService orderIndexingService;

    public RecommendationDemoController(ProductIndexingService productIndexingService,
                                        RecommendationService recommendationService,
                                        OrderIndexingService orderIndexingService) {
        this.productIndexingService = productIndexingService;
        this.recommendationService = recommendationService;
        this.orderIndexingService = orderIndexingService;
    }

    @PostMapping("/index-catalog")
    public String indexCatalog() {
        List<ProductCreatedDto> catalog = SampleEvents.catalog();
        catalog.forEach(productIndexingService::index);
        return "Indexed " + catalog.size() + " tours into the vector store.";
    }

    @PostMapping("/products")
    public String indexProduct(@RequestBody ProductCreatedDto productCreatedDto) {
        productIndexingService.index(productCreatedDto);
        return "Indexed tour '" + productCreatedDto.name() + "'.";
    }

    @PostMapping("/orders/sample")
    public String recommendForSampleOrder() {
        return process(SampleEvents.sampleOrder());
    }

    @PostMapping("/orders")
    public String recommend(@RequestBody OrderCreatedDto orderCreatedDto) {
        return process(orderCreatedDto);
    }

    private String process(OrderCreatedDto orderCreatedDto) {
        orderIndexingService.index(orderCreatedDto);
        return recommendationService.recommendFor(orderCreatedDto);
    }
}
