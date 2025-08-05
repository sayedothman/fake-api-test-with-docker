package testing.Books;

import constants.ErrorMessages;
import constants.HttpStatusCode;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import model.common.ErrorResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

@Epic("Delete Book By ID Tests")
public class DeleteBookByIdTests extends BaseBookApiTest {

    @Test(description = "Test deleting a book with valid ID returns 200 and book is deleted")
    @Description("Ensure DELETE /Books/{id} with a valid existing ID returns 200 OK and the book is no longer retrievable")
    public void testDeleteBook_ValidId_ShouldDeleteBookSuccessfully() {
        int bookIdToDelete = validBook.getId();

        Allure.step("Deleting book with ID: " + bookIdToDelete);
        Response deleteResponse = booksHelper.deleteBook(bookIdToDelete);

        Allure.step("Asserting DELETE response status code is 200 OK or 204 No Content");
        assertThat(deleteResponse.statusCode(), is(HttpStatusCode.OK.getCode()));

        Allure.step("Verifying that the deleted book is no longer retrievable");
        Response getResponse = booksHelper.getBookById(bookIdToDelete);
        assertEquals(getResponse.statusCode(), HttpStatusCode.NOT_FOUND.getCode());
    }

    @DataProvider(name = "nonExistentBookIds")
    public Object[][] nonExistentBookIds() {
        return new Object[][]{
                {999999, "Delete non-existent book ID: 999999"},
                {0, "Delete with zero ID"},
                {-10, "Delete with negative ID: -10"}
        };
    }

    @Test(dataProvider = "nonExistentBookIds", description = "Test deleting a book with non-existent ID returns 404 Not Found")
    @Description("Ensure DELETE /Books/{id} on non-existent but valid IDs returns 404 Not Found")
    public void testDeleteBook_NonExistentId_ShouldReturn404(int bookId, String testName) {
        Allure.step("Running test case: " + testName);
        Response response = booksHelper.deleteBook(bookId);

        Allure.step("Asserting response status code is 404 Not Found");
        assertThat(response.statusCode(), is(HttpStatusCode.NOT_FOUND.getCode()));
    }

    @DataProvider(name = "invalidBookIdFormats")
    public Object[][] invalidBookIdFormats() {
        return new Object[][]{
                {"**", "Invalid ID format: **"},
                {"abc", "Invalid ID format: abc"},
                {null, "Invalid ID format: null value"}
        };
    }

    @Test(dataProvider = "invalidBookIdFormats", description = "Test deleting a book with invalid ID format returns 400 Bad Request")
    @Description("Ensure DELETE /Books/{id} with invalid ID formats returns 400 Bad Request and proper validation errors")
    public void testDeleteBook_InvalidIdFormat_ShouldReturn400(String bookId, String testName) {
        Allure.step("Attempting DELETE with invalid ID: " + testName);
        Response response = booksHelper.deleteBook(bookId);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        Allure.step("Asserting response status code is 400 Bad Request");
        assertThat(response.statusCode(), is(HttpStatusCode.BAD_REQUEST.getCode()));

        Allure.step("Validating error response contains ID validation error");
        assertThat(errorResponse.getErrors().get("id").get(0), matchesPattern(ErrorMessages.VALUE_NOT_VALID));
    }

    @Test(description = "Test deleting a Book without ID returns 405 Method Not Allowed")
    @Description("Ensure DELETE request to /Books endpoint without a book ID path parameter returns 405 Method Not Allowed")
    public void testDeleteBook_MissingId_ShouldReturn405() {
        Allure.step("Sending DELETE request to /Books without specifying book ID");
        Response response = booksHelper.deleteBook();

        Allure.step("Asserting response status code is 405 Method Not Allowed");
        assertThat(response.statusCode(), is(HttpStatusCode.METHOD_NOT_ALLOWED.getCode()));
    }
}