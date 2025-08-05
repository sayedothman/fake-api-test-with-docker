package testing.Authors;

import constants.ErrorMessages;
import constants.HttpStatusCode;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import model.common.ErrorResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.AuthorsDataGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

@Epic("Delete Author By ID Tests")
public class DeleteAuthorByIdTests extends BaseAuthorApiTest {


    @Test(description = "Test deleting author with a valid ID returns 200 and author is deleted")
    @Description("Ensure DELETE /Authors/{id} with existing ID returns 200 and author is deleted")
    public void testDeleteAuthorByValidId_ShouldSucceed() {
        int validAuthorId = validAuthor.getId();

        Allure.step("Deleting author with ID: " + validAuthorId);
        Response deleteResponse = authorsHelper.deleteAuthor(validAuthorId);

        Allure.step("Asserting DELETE status is 200 or 204");
        assertThat(deleteResponse.statusCode(), is(HttpStatusCode.OK.getCode()));

        Allure.step("Verifying author cannot be retrieved after deletion");
        Response getResponse = authorsHelper.getAuthorById(validAuthorId);
        assertEquals(getResponse.statusCode(), HttpStatusCode.NOT_FOUND.getCode());
    }

    @DataProvider(name = "nonExistentAuthorIds")
    public Object[][] nonExistentAuthorIds() {
        return new Object[][]{
                {999999, "Non-existent ID: 999999"},
                {0, "Zero ID"},
                {-10, "Negative ID: -10"}
        };
    }

    @Test(dataProvider = "nonExistentAuthorIds", description = "Test deleting a non-existent author ID returns 404 Not Found")
    @Description("DELETE /Authors/{id} on non-existent IDs returns 404 Not Found")
    public void testDeleteNonExistentAuthor_ShouldReturn404(int authorId, String testName) {
        Allure.step("Executing test: " + testName);
        Response response = authorsHelper.deleteAuthor(authorId);

        Allure.step("Asserting status code is 404 Not Found");
        assertThat(response.statusCode(), is(HttpStatusCode.NOT_FOUND.getCode()));
    }

    @DataProvider(name = "invalidAuthorIdFormats")
    public Object[][] invalidAuthorIdFormats() {
        return new Object[][]{
                {"**", "Special characters: **"},
                {"abc", "Alphabetic ID: abc"},
                {null, "Null ID"}
        };
    }

    @Test(dataProvider = "invalidAuthorIdFormats", description = "Test deleting with invalid ID format returns 400 Bad Request")
    @Description("DELETE /Authors/{id} with invalid ID formats returns 400 Bad Request with validation error")
    public void testDeleteWithInvalidIdFormat_ShouldReturn400(String authorId, String testName) {
        Allure.step("Attempting DELETE with invalid ID: " + testName);
        Response response = authorsHelper.deleteAuthor(authorId);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        Allure.step("Asserting status code is 400 Bad Request");
        assertThat(response.statusCode(), is(HttpStatusCode.BAD_REQUEST.getCode()));

        Allure.step("Validating error message for key 'id'");
        assertThat(errorResponse.getErrors().get("id").get(0), matchesPattern(ErrorMessages.VALUE_NOT_VALID));
    }

    @Test(description = "Test deleting author without specifying ID returns 405 Method Not Allowed")
    @Description("DELETE /Authors without a specified ID returns 405 Method Not Allowed")
    public void testDeleteAuthorWithoutId_ShouldReturn405() {
        Allure.step("Sending DELETE request to /Authors without ID");
        Response response = authorsHelper.deleteAuthor();

        Allure.step("Asserting status code is 405 Method Not Allowed");
        assertThat(response.statusCode(), is(HttpStatusCode.METHOD_NOT_ALLOWED.getCode()));
    }
}
