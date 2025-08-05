package testing.Authors;

import constants.ErrorKey;
import constants.ErrorMessages;
import constants.HttpStatusCode;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import model.authors.Authors;
import model.authors.AuthorsInvalidRequest;
import model.common.ErrorResponse;
import org.testng.ITest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.AuthorsDataGenerator;

import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

@Epic("Edit Authors Tests")
public class EditAuthorsTests extends BaseAuthorApiTest {

    private final int validAuthorId = 50;

    @Test(testName = "Update author with valid data should succeed", description = "Test successful updating author with valid data")
    @Description("Ensure that updating an existing author with valid data via PUT /Authors/{id} returns 200 OK and updates the author's information accurately.")
    public void testUpdateAuthor_ValidData_ShouldSucceed() {
        Authors updateRequest = AuthorsDataGenerator.generateRandomAuthor();

        Allure.step("Sending PUT request to update author ID: " + validAuthorId);
        Response response = authorsHelper.updateAuthor(validAuthorId, updateRequest);
        Authors authorResponse = response.as(Authors.class);

        Allure.step("Assert that the response status code is 200 OK");
        assertEquals(response.statusCode(), HttpStatusCode.OK.getCode());

        Allure.step("Verify that firstName is updated correctly");
        assertThat(authorResponse.getFirstName(), equalTo(updateRequest.getFirstName()));

        Allure.step("Verify that lastName is updated correctly");
        assertThat(authorResponse.getLastName(), equalTo(updateRequest.getLastName()));

        Allure.step("Verify that book ID is updated correctly");
        assertThat(authorResponse.getIdBook(), equalTo(updateRequest.getIdBook()));
    }

    @DataProvider(name = "invalidUpdatePayloads")
    public Object[][] invalidUpdatePayloads(Method method) {
        AuthorsInvalidRequest baseRequest = AuthorsDataGenerator.baseInvalid();

        return new Object[][]{{"Invalid ID format", baseRequest.toBuilder().id("bad_id").build(), ErrorKey.BODY_ID, ErrorMessages.CAN_NOT_CONVERT_TO_INT}, {"Invalid bookID format", baseRequest.toBuilder().idBook("not_a_number").build(), ErrorKey.BOOK_ID, ErrorMessages.CAN_NOT_CONVERT_TO_INT}, {"Invalid firstName format", baseRequest.toBuilder().firstName(123).build(), ErrorKey.FIRST_NAME, ErrorMessages.CAN_NOT_CONVERT_TO_STRING}, {"Invalid lastName format", baseRequest.toBuilder().lastName(true).build(), ErrorKey.LAST_NAME, ErrorMessages.CAN_NOT_CONVERT_TO_STRING}};
    }

    @Test(dataProvider = "invalidUpdatePayloads", description = "Test updating author with invalid data returns 400")
    @Description("Ensure that updating an author with invalid field types via PUT /Authors/{id} returns a 400 Bad Request and includes the appropriate validation error message.")
    public void testUpdateAuthor_InvalidPayload_ShouldReturn400(String caseName, AuthorsInvalidRequest invalidRequest, String errorKey, String expectedRegex) {
        Allure.step("Executing test case: " + caseName);
        Response response = authorsHelper.updateAuthorInvalid(validAuthorId, invalidRequest);

        Allure.step("Assert that the response status code is 400 Bad Request");
        assertEquals(response.statusCode(), HttpStatusCode.BAD_REQUEST.getCode());

        Allure.step("Validate that the correct error message is returned for: " + errorKey);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertThat(errorResponse.getErrors().get(errorKey).get(0), matchesPattern(expectedRegex));
    }
}