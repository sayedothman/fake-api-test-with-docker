package testing.Authors;

import constants.ErrorMessages;
import constants.HttpStatusCode;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import model.authors.Authors;
import model.common.ErrorResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Get Author By ID Tests")
public class GetAuthorByIdTests extends BaseAuthorApiTest {

    private int validAuthorId = 10;

    @Test(description = "Test getting authors with valid ID returns 200 and correct author data")
    @Description("Verify that fetching an author by a valid ID returns 200 OK and the author data matches the requested ID")
    public void testGetAuthorById_ValidId_ShouldReturnAuthorSuccessfully() {
        Allure.step("Fetching author with valid ID: " + validAuthorId);
        Response response = authorsHelper.getAuthorById(validAuthorId);
        Authors author = response.as(Authors.class);

        Allure.step("Asserting HTTP status code is 200");
        assertThat(response.statusCode(), is(HttpStatusCode.OK.getCode()));

        Allure.step("Asserting returned author ID matches requested ID");
        assertThat(author.getId(), equalTo(validAuthorId));

        Allure.step("Asserting author book ID is valid (greater than 0)");
        assertThat(author.getIdBook(), greaterThan(0));

        Allure.step("Asserting first name is neither null nor empty");
        assertThat(author.getFirstName(), not(is(emptyOrNullString())));

        Allure.step("Asserting last name is neither null nor empty");
        assertThat(author.getLastName(), not(is(emptyOrNullString())));
    }

    @DataProvider(name = "nonExistentAuthorIds")
    public Object[][] nonExistentAuthorIds() {
        return new Object[][]{
                {99999, "Non-existent large author ID: 99999"},
                {0, "Non-existent zero author ID: 0"},
                {-1, "Non-existent negative author ID: -1"}
        };
    }

    @Test(dataProvider = "nonExistentAuthorIds", description = "Test getting authors with non-existent ID returns 404 Not Found")
    @Description("Verify that requesting authors with valid but non-existent IDs returns 404 Not Found with appropriate error response")
    public void testGetAuthorById_NonExistentId_ShouldReturn404(int authorId, String testName) {
        Allure.step("Executing test case: " + testName);
        Response response = authorsHelper.getAuthorById(authorId);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        Allure.step("Asserting status code is 404 Not Found for author ID: " + authorId);
        assertThat(response.statusCode(), equalTo(HttpStatusCode.NOT_FOUND.getCode()));

        Allure.step("Asserting error response title is 'Not Found'");
        assertThat(errorResponse.getTitle(), equalTo("Not Found"));
    }

    @DataProvider(name = "invalidAuthorIdFormats")
    public Object[][] invalidAuthorIdFormats() {
        return new Object[][]{
                {"**", "Invalid ID format: **"},
                {"abc", "Invalid ID format: abc"},
                {" ", "Invalid ID format: empty space"}
        };
    }

    @Test(dataProvider = "invalidAuthorIdFormats", description = "Test getting authors with invalid ID formats returns 400 Bad Request")
    @Description("Verify that GET /Authors/{id} with invalid ID formats returns 400 Bad Request and validation error details")
    public void testGetAuthorById_InvalidIdFormat_ShouldReturn400(String authorId, String testName) {
        Allure.step("Testing invalid ID format: " + testName);
        Response response = authorsHelper.getAuthorById(authorId);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        Allure.step("Asserting HTTP status code is 400 Bad Request");
        assertThat(response.statusCode(), is(HttpStatusCode.BAD_REQUEST.getCode()));

        Allure.step("Validating response contains validation error for 'id'");
        assertThat(errorResponse.getErrors(), hasKey("id"));
        assertThat(errorResponse.getErrors().get("id").get(0), matchesPattern(ErrorMessages.VALUE_NOT_VALID));
    }
}
