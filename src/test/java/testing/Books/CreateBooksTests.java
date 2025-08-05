package testing.Books;

import constants.ErrorMessages;
import constants.ErrorKey;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

@Epic("Create Books Tests")
public class CreateBooksTests extends BaseBookApiTest {

    @Test(description = "Test creating a book with valid data returns 200 OK and correct book data")
    @Description("Ensure POST /Books with a valid book payload returns 200 OK and the created book fields match the request")
    public void testCreateBook_ValidPayload_ShouldCreateBookSuccessfully() {
        Allure.step("Generating valid random book payload");
        Books booksRequest = BooksDataGenerator.generateRandomBook();

        Allure.step("Sending POST request to create book");
        Response response = booksHelper.createBook(booksRequest);
        Books books = response.as(Books.class);

        Allure.step("Asserting response status code is 200 OK");
        assertEquals(response.statusCode(), HttpStatusCode.OK.getCode());

        Allure.step("Validating created book fields match the request");
        assertThat(books.getTitle(), equalTo(booksRequest.getTitle()));
        assertThat(books.getDescription(), equalTo(booksRequest.getDescription()));
        assertThat(books.getPageCount(), equalTo(booksRequest.getPageCount()));
        assertThat(books.getExcerpt(), equalTo(booksRequest.getExcerpt()));
        assertThat(books.getPublishDate(), notNullValue());
    }

    @DataProvider(name = "invalidBookPayloads")
    public Object[][] invalidBookPayloads() {
        BooksInvalidRequest baseRequest = BooksDataGenerator.baseInvalid();

        return new Object[][]{
                { "Invalid ID format", baseRequest.toBuilder().id("asd").build(), ErrorKey.BODY_ID, ErrorMessages.CAN_NOT_CONVERT_TO_INT },
                { "Invalid title format", baseRequest.toBuilder().title(123).build(), ErrorKey.TITLE, ErrorMessages.CAN_NOT_CONVERT_TO_STRING },
                { "Invalid description format", baseRequest.toBuilder().description(456).build(), ErrorKey.DESCRIPTION, ErrorMessages.CAN_NOT_CONVERT_TO_STRING },
                { "Invalid pageCount format", baseRequest.toBuilder().pageCount("d").build(), ErrorKey.PAGE_COUNT, ErrorMessages.CAN_NOT_CONVERT_TO_INT },
                { "Invalid excerpt format", baseRequest.toBuilder().excerpt(1).build(), ErrorKey.EXCERPT, ErrorMessages.CAN_NOT_CONVERT_TO_STRING },
                { "Invalid publishDate format", baseRequest.toBuilder().publishDate(1).build(), ErrorKey.PUBLISH_DATE, ErrorMessages.CAN_NOT_CONVERT_TO_DATE }
        };
    }

    @Test(dataProvider = "invalidBookPayloads", description = "Test creating a book with invalid data returns 400 Bad Request with validation errors")
    @Description("Ensure POST /Books with invalid book fields returns 400 Bad Request and appropriate validation error messages")
    public void testCreateBook_InvalidPayload_ShouldReturn400(String testName, BooksInvalidRequest invalidRequest, String errorKey, String expectedRegex) {
        Allure.step("Executing invalid payload test case: " + testName);
        Response response = booksHelper.createBookInvalid(invalidRequest);

        Allure.step("Asserting response status code is 400 Bad Request");
        assertEquals(response.statusCode(), HttpStatusCode.BAD_REQUEST.getCode());

        Allure.step("Validating error response contains expected validation error for key: " + errorKey);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertThat(errorResponse.getErrors().get(errorKey).get(0), matchesPattern(expectedRegex));
    }
}
