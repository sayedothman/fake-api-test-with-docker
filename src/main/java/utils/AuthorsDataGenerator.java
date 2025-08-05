package utils;

import com.github.javafaker.Faker;
import model.authors.Authors;
import model.authors.AuthorsInvalidRequest;

import java.util.Random;

/**
 * Utility class for generating random author data.
 */
public class AuthorsDataGenerator {

    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static Authors generateRandomAuthor() {
        return Authors.builder()
                .id(random.nextInt(1000))
                .idBook(random.nextInt(1000))
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .build();
    }

    public static AuthorsInvalidRequest baseInvalid() {
        Authors valid = generateRandomAuthor();
        return AuthorsInvalidRequest.builder()
                .id(valid.getId())
                .idBook(valid.getIdBook())
                .firstName(valid.getFirstName())
                .lastName(valid.getLastName())
                .build();
    }
}
