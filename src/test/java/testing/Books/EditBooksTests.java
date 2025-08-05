package testing.Books;

import constants.ErrorKey;
import constants.ErrorMessages;
import constants.HttpStatusCode;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import model.books.BooksInvalidRequest;
import model.books.Books;
import model.common.ErrorResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.BooksDataGenerator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

@Epic("Edit Books Tests")
public class EditBooksTests extends BaseBookApiTest {

    private final int validBookId = 50;

    @Test(description = "Test updating a book with valid data returns 200 and updates correctly")
    @Description("Ensure that a valid update request to PUT /Books/{id} returns HTTP 200 OK and fields are correctly updated")
    public void testUpdateBook_ValidData_ShouldReturnUpdatedBook() {
        Books updateRequest = BooksDataGenerator.generateRandomBook();

        Allure.step("Sending PUT request to update book with ID: " + validBookId);
        Response response = booksHelper.updateBook(validBookId, updateRequest);
        Books updatedBook = response.as(Books.class);

        Allure.step("Asserting response status code is 200 OK");
        assertEquals(response.statusCode(), HttpStatusCode.OK.getCode());

        Allure.step("Validating updated title matches the request");
        assertThat(updatedBook.getTitle(), equalTo(updateRequest.getTitle()));

        Allure.step("Validating updated description matches the request");
        assertThat(updatedBook.getDescription(), equalTo(updateRequest.getDescription()));

        Allure.step("Validating updated page count matches the request");
        assertThat(updatedBook.getPageCount(), equalTo(updateRequest.getPageCount()));

        Allure.step("Validating updated excerpt matches the request");
        assertThat(updatedBook.getExcerpt(), equalTo(updateRequest.getExcerpt()));

        Allure.step("Validating updated publish date matches the request");
        Instant actual = Instant.parse(updatedBook.getPublishDate()).truncatedTo(ChronoUnit.MILLIS);
        Instant expected = Instant.parse(updateRequest.getPublishDate()).truncatedTo(ChronoUnit.MILLIS);

        assertThat(actual, equalTo(expected));

    }

    @DataProvider(name = "invalidUpdatePayloads")
    public Object[][] invalidUpdatePayloads() {
        BooksInvalidRequest baseRequest = BooksDataGenerator.baseInvalid();

        return new Object[][]{
                { "Invalid ID format", baseRequest.toBuilder().id("bad_id").build(), ErrorKey.BODY_ID, ErrorMessages.CAN_NOT_CONVERT_TO_INT },
                { "Invalid title format", baseRequest.toBuilder().title(123).build(), ErrorKey.TITLE, ErrorMessages.CAN_NOT_CONVERT_TO_STRING },
                { "Invalid description format", baseRequest.toBuilder().description(true).build(), ErrorKey.DESCRIPTION, ErrorMessages.CAN_NOT_CONVERT_TO_STRING },
                { "Invalid pageCount format", baseRequest.toBuilder().pageCount("pages").build(), ErrorKey.PAGE_COUNT, ErrorMessages.CAN_NOT_CONVERT_TO_INT },
                { "Invalid excerpt format", baseRequest.toBuilder().excerpt(999).build(), ErrorKey.EXCERPT, ErrorMessages.CAN_NOT_CONVERT_TO_STRING },
                { "Invalid publishDate format", baseRequest.toBuilder().publishDate(false).build(), ErrorKey.PUBLISH_DATE, ErrorMessages.CAN_NOT_CONVERT_TO_DATE }
        };
    }

    @Test(dataProvider = "invalidUpdatePayloads", description = "Test updating a book with invalid data returns 400 with validation errors")
    @Description("Ensure that PUT /Books/{id} with invalid fields returns HTTP 400 Bad Request and relevant validation errors")
    public void testUpdateBook_InvalidData_ShouldReturn400(String testName, BooksInvalidRequest invalidRequest, String errorKey, String expectedRegex) {
        Allure.step("Running invalid update test case: " + testName);
        Response response = booksHelper.updateBookInvalid(validBookId, invalidRequest);

        Allure.step("Asserting response status code is 400 Bad Request");
        assertEquals(response.statusCode(), HttpStatusCode.BAD_REQUEST.getCode());

        Allure.step("Validating error response contains expected validation error");
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertThat(errorResponse.getErrors().get(errorKey).get(0), matchesPattern(expectedRegex));
    }
}
