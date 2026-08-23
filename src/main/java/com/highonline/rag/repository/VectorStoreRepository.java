package com.highonline.rag.repository;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class VectorStoreRepository {

    public static final String METADATA_TYPE = "type";
    public static final String METADATA_CUSTOMER_ID = "customerId";
    public static final String TYPE_PRODUCT = "PRODUCT";
    public static final String TYPE_ORDER_HISTORY = "ORDER_HISTORY";

    private final VectorStore vectorStore;

    public VectorStoreRepository(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    /** Stores (or replaces, by document id) a document in the vector store. */
    public void save(Document document) {
        vectorStore.add(List.of(document));
    }

    /** RETRIEVAL: available tours (catalog) most similar to the query. */
    public List<Document> findSimilarProducts(String query, int topK) {
        String filter = "%s == '%s'".formatted(METADATA_TYPE, TYPE_PRODUCT);
        return search(query, topK, filter);
    }

    /** RETRIEVAL: the given customer's previously purchased tours (history). */
    public List<Document> findCustomerHistory(UUID customerId, String query, int topK) {
        String filter = "%s == '%s' && %s == '%s'".formatted(
                METADATA_TYPE, TYPE_ORDER_HISTORY, METADATA_CUSTOMER_ID, customerId);
        return search(query, topK, filter);
    }

    private List<Document> search(String query, int topK, String filterExpression) {
        return vectorStore.similaritySearch(SearchRequest.builder()
                .query(query)
                .topK(topK)
                .filterExpression(filterExpression)
                .build());
    }
}
