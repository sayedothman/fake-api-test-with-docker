package utils;

import com.github.javafaker.Faker;
import model.books.Books;
import model.books.BooksInvalidRequest;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Random;

/**
 * Utility class for generating random book data.
 */
public class BooksDataGenerator {

    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static Books generateRandomBook() {
        return new Books(
                random.nextInt(1000),
                faker.book().title(),
                faker.lorem().sentence(10),
                random.nextInt(1000),
                faker.lorem().paragraph(),
                generatePastDate()
        );
    }

    private static String generatePastDate() {
        int yearsBack = random.nextInt(2) + 1;
        OffsetDateTime date = OffsetDateTime.now(ZoneOffset.UTC).minusYears(yearsBack);
        return date.toString();
    }

    public static BooksInvalidRequest baseInvalid() {
        Books valid = BooksDataGenerator.generateRandomBook();
        return BooksInvalidRequest.builder()
                .id(valid.getId())
                .title(valid.getTitle())
                .description(valid.getDescription())
                .pageCount(valid.getPageCount())
                .excerpt(valid.getExcerpt())
                .publishDate(valid.getPublishDate())
                .build();
    }
}
