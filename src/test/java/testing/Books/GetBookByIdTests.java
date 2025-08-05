package testing.Books;

import constants.ErrorMessages;
import constants.HttpStatusCode;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import model.books.Books;
import model.common.ErrorResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Get Book By ID Tests")
public class GetBookByIdTests extends BaseBookApiTest {

    private final int validBookId = 10;

    @Test(description = "Test getting book by valid ID returns 200 OK and book data")
    @Description("Ensure GET /Books/{id} with a valid ID returns 200 OK and book data")
    public void testGetBookB_ValidId_ShouldReturnBookDetails() {
        Allure.step("Fetching book with ID: " + validBookId);
        Response response = booksHelper.getBookById(validBookId);
        Books book = response.as(Books.class);

        Allure.step("Checking status code is 200 OK");
        assertThat(response.statusCode(), is(HttpStatusCode.OK.getCode()));

        Allure.step("Validating returned book ID");
        assertThat(book.getId(), equalTo(validBookId));

        Allure.step("Checking title is not empty");
        assertThat(book.getTitle(), not(is(emptyOrNullString())));

        Allure.step("Checking publish date exists");
        assertThat(book.getPublishDate(), notNullValue());
    }

    @DataProvider(name = "nonExistentBookIds")
    public Object[][] nonExistentBookIds() {
        return new Object[][]{
                {99999, "Large ID"},
                {0, "Zero ID"},
                {-1, "Negative ID"}
        };
    }

    @Test(dataProvider = "nonExistentBookIds", description = "Test getting book by non-existent ID returns 404")
    @Description("Ensure non-existent but valid IDs return 404 Not Found")
    public void testGetBook_NonExistentId_ShouldReturn404(int bookId, String testName) {
        Allure.step("Running test case: " + testName);
        Response response = booksHelper.getBookById(bookId);
        ErrorResponse error = response.as(ErrorResponse.class);

        Allure.step("Checking status code is 404");
        assertThat(response.statusCode(), equalTo(HttpStatusCode.NOT_FOUND.getCode()));

        Allure.step("Checking error title is 'Not Found'");
        assertThat(error.getTitle(), equalTo("Not Found"));
    }

    @DataProvider(name = "invalidBookIdFormats")
    public Object[][] invalidBookIdFormats() {
        return new Object[][]{
                {"**", "Asterisks"},
                {"abc", "Alphabetic"},
                {" ", "Empty Space"}
        };
    }

    @Test(dataProvider = "invalidBookIdFormats", description = "Test getting book by invalid ID format returns 400")
    @Description("Ensure GET /Books/{id} with invalid formats returns 400 and relevant validation errors")
    public void testGetBook_InvalidIdFormat_ShouldReturn400(String bookId, String testName) {
        Allure.step("Running test case: " + testName);
        Response response = booksHelper.getBookById(bookId);
        ErrorResponse error = response.as(ErrorResponse.class);

        Allure.step("Checking status code is 400");
        assertThat(response.statusCode(), is(HttpStatusCode.BAD_REQUEST.getCode()));

        Allure.step("Validating error response contains key: id");
        assertThat(error.getErrors(), hasKey("id"));
        assertThat(error.getErrors().get("id").get(0), matchesPattern(ErrorMessages.VALUE_NOT_VALID));
    }
}
