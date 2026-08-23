package com.highonline.rag.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    private static final String SYSTEM_PROMPT = """
            You are an intelligent tour recommendation assistant for an online
            experiences marketplace (similar to GetYourGuide).

            You will receive, in the user message, a set of available tours retrieved
            from the catalog and the customer's purchase history. Work ONLY with the
            tours given to you there — never invent tours that are not listed.

            Infer the customer's interests and travel profile from their purchases and
            history, recommend tours that fit by location and interest to compose a
            coherent itinerary, and never recommend a tour the customer already bought.
            Keep the answer concise and friendly, explaining why each tour fits.
            """;

    @Bean
    public ChatClient recommendationChatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem(SYSTEM_PROMPT)
                .build();
    }
}
