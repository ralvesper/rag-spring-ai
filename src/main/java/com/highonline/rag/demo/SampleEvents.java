package com.highonline.rag.demo;

import com.highonline.rag.model.OrderCreatedDto;
import com.highonline.rag.model.ProductCreatedDto;

import java.util.List;
import java.util.UUID;


public final class SampleEvents {

    // Fixed UUIDs so the demo is reproducible and easy to follow during the talk.
    private static final UUID SAMPLE_CUSTOMER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID SAMPLE_ORDER_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");

    private SampleEvents() {
    }

    /**
     * Sample tour catalog, as if emitted by the Products service. Holds 21 tours
     * across three cities (Rome, Paris and London) so the location constraint in
     * the recommendation step has enough same-region candidates to compose an
     * itinerary.
     */
    public static List<ProductCreatedDto> catalog() {
        return List.of(
                // ===== ROME, ITALY =====
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000001"),
                        "Colosseum Skip-the-Line Guided Tour",
                        "Walk through the ancient arena with an expert guide and skip the long entrance lines. Includes the Roman Forum and Palatine Hill.",
                        "Rome, Italy",
                        "History & Culture",
                        59.90, "EUR",
                        List.of("history", "guided", "skip-the-line", "ancient-rome")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000002"),
                        "Vatican Museums & Sistine Chapel Tour",
                        "Discover Renaissance masterpieces and Michelangelo's ceiling with a small-group art historian guide.",
                        "Rome, Italy",
                        "History & Culture",
                        69.00, "EUR",
                        List.of("history", "art", "guided", "museum")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000003"),
                        "Roman Forum & Palatine Hill Walking Tour",
                        "Stroll through the heart of ancient Rome with a guide and discover the ruins where emperors once lived.",
                        "Rome, Italy",
                        "History & Culture",
                        39.00, "EUR",
                        List.of("history", "guided", "ancient-rome", "walking")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000004"),
                        "Trastevere Food Tour by Night",
                        "Taste authentic Roman cuisine, street food and local wine while wandering the charming alleys of Trastevere.",
                        "Rome, Italy",
                        "Food & Cruises",
                        72.00, "EUR",
                        List.of("food", "wine", "evening", "walking")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000005"),
                        "Borghese Gallery Skip-the-Line Tour",
                        "Admire Bernini sculptures and Caravaggio paintings in the elegant Borghese Gallery with a small group.",
                        "Rome, Italy",
                        "History & Culture",
                        54.00, "EUR",
                        List.of("art", "museum", "guided", "skip-the-line")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000006"),
                        "Pantheon & Piazza Navona Guided Walk",
                        "Explore Rome's best-preserved monument and its baroque squares and fountains on a relaxed guided walk.",
                        "Rome, Italy",
                        "Landmarks",
                        29.00, "EUR",
                        List.of("history", "landmark", "guided", "walking")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000007"),
                        "Catacombs of Rome Underground Tour",
                        "Descend into the ancient underground burial tunnels with a guide and uncover early Christian history.",
                        "Rome, Italy",
                        "Adventure & Nature",
                        48.00, "EUR",
                        List.of("history", "underground", "guided", "adventure")),

                // ===== PARIS, FRANCE =====
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000008"),
                        "Eiffel Tower Summit Access by Elevator",
                        "Enjoy priority access to the very top of the Eiffel Tower and panoramic views over Paris.",
                        "Paris, France",
                        "Landmarks",
                        45.50, "EUR",
                        List.of("landmark", "views", "skip-the-line")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000009"),
                        "Seine River Evening Dinner Cruise",
                        "A romantic dinner cruise along the Seine with French cuisine and illuminated monuments.",
                        "Paris, France",
                        "Food & Cruises",
                        89.00, "EUR",
                        List.of("cruise", "food", "romantic", "evening")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000010"),
                        "Louvre Museum Highlights Guided Tour",
                        "Skip the line and discover the Mona Lisa, Venus de Milo and other masterpieces with an art historian.",
                        "Paris, France",
                        "History & Culture",
                        65.00, "EUR",
                        List.of("art", "museum", "guided", "skip-the-line")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000011"),
                        "Montmartre & Sacré-Cœur Walking Tour",
                        "Wander the artistic hilltop neighbourhood, visit the basilica and enjoy sweeping views over the city.",
                        "Paris, France",
                        "History & Culture",
                        32.00, "EUR",
                        List.of("history", "art", "walking", "views")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000012"),
                        "Palace of Versailles Day Trip",
                        "Visit the opulent royal palace and its gardens on a guided day trip from central Paris.",
                        "Paris, France",
                        "History & Culture",
                        99.00, "EUR",
                        List.of("history", "palace", "guided", "gardens")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000013"),
                        "Musée d'Orsay Skip-the-Line Tour",
                        "Explore the world's finest Impressionist collection, from Monet to Van Gogh, with a small group.",
                        "Paris, France",
                        "History & Culture",
                        52.00, "EUR",
                        List.of("art", "museum", "guided", "skip-the-line")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000014"),
                        "Latin Quarter Food & Wine Tasting",
                        "Sample French cheeses, pastries and wine while exploring the lively Latin Quarter with a local guide.",
                        "Paris, France",
                        "Food & Cruises",
                        78.00, "EUR",
                        List.of("food", "wine", "walking", "local")),

                // ===== LONDON, UNITED KINGDOM =====
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000015"),
                        "Tower of London & Crown Jewels Tour",
                        "Explore the historic fortress with a Beefeater guide and admire the dazzling Crown Jewels.",
                        "London, United Kingdom",
                        "History & Culture",
                        58.00, "GBP",
                        List.of("history", "guided", "landmark", "royal")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000016"),
                        "London Eye Fast-Track Experience",
                        "Skip the queue and enjoy panoramic views over the Thames and the city from the iconic observation wheel.",
                        "London, United Kingdom",
                        "Landmarks",
                        40.00, "GBP",
                        List.of("landmark", "views", "skip-the-line")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000017"),
                        "British Museum Guided Highlights Tour",
                        "Discover the Rosetta Stone, Egyptian mummies and world treasures with an expert guide.",
                        "London, United Kingdom",
                        "History & Culture",
                        47.00, "GBP",
                        List.of("history", "art", "museum", "guided")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000018"),
                        "Westminster Abbey & Big Ben Walking Tour",
                        "Walk past Westminster's landmarks and learn the stories behind royal coronations and Big Ben.",
                        "London, United Kingdom",
                        "History & Culture",
                        35.00, "GBP",
                        List.of("history", "landmark", "guided", "walking")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000019"),
                        "Thames River Sightseeing Cruise",
                        "Glide past Tower Bridge, the Houses of Parliament and the city skyline on a relaxing river cruise.",
                        "London, United Kingdom",
                        "Food & Cruises",
                        30.00, "GBP",
                        List.of("cruise", "views", "landmark", "relax")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000020"),
                        "Harry Potter Warner Bros. Studio Tour",
                        "Step onto the original film sets and discover the magic behind the Harry Potter movies.",
                        "London, United Kingdom",
                        "Adventure & Nature",
                        110.00, "GBP",
                        List.of("family", "movies", "guided", "experience")),
                new ProductCreatedDto(
                        UUID.fromString("00000000-0000-0000-0000-000000000021"),
                        "Borough Market Food Tasting Tour",
                        "Sample artisan cheeses, street food and local treats at London's most famous food market with a guide.",
                        "London, United Kingdom",
                        "Food & Cruises",
                        62.00, "GBP",
                        List.of("food", "market", "walking", "local")));
    }

    /**
     * A fixed order event: the customer bought a history/art tour in Rome. The
     * agent must infer the travel profile from this purchase alone and recommend
     * tours that fit by interest and located in the same region to compose an itinerary.
     */
    public static OrderCreatedDto sampleOrder() {
        return new OrderCreatedDto(
                SAMPLE_ORDER_ID,
                SAMPLE_CUSTOMER_ID,
                "Colosseum Skip-the-Line Guided Tour",
                "Rome, Italy");
    }
}
