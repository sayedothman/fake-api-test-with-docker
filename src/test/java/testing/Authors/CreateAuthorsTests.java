package testing.Authors;

import constants.ErrorMessages;
import constants.ErrorKey;
import constants.HttpStatusCode;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import model.authors.Authors;
import model.authors.AuthorsInvalidRequest;
import model.common.ErrorResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.AuthorsDataGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

@Epic("Create Authors Tests")
public class CreateAuthorsTests extends BaseAuthorApiTest {

    @Test(description = "Test creating author with valid data returns 200 and correct author data")
    @Description("Ensure POST /Authors with valid payload returns 200 OK and author fields match the request")
    public void testCreateAuthorWithValidPayload_ShouldReturn200AndMatchRequest() {
        Allure.step("Generating a random author payload");
        Authors authorRequest = AuthorsDataGenerator.generateRandomAuthor();

        Allure.step("Sending POST request to create author");
        Response response = authorsHelper.createAuthor(authorRequest);
        Authors authorResponse = response.as(Authors.class);

        Allure.step("Asserting status code is 200 OK");
        assertEquals(response.statusCode(), HttpStatusCode.OK.getCode());

        Allure.step("Verifying first name matches request");
        assertThat(authorResponse.getFirstName(), equalTo(authorRequest.getFirstName()));

        Allure.step("Verifying last name matches request");
        assertThat(authorResponse.getLastName(), equalTo(authorRequest.getLastName()));

        Allure.step("Verifying book ID matches request");
        assertThat(authorResponse.getIdBook(), equalTo(authorRequest.getIdBook()));

        Allure.step("Verifying author ID is greater than zero");
        assertThat(authorResponse.getId(), greaterThan(0));
    }

    @DataProvider(name = "invalidAuthorPayloads")
    public Object[][] invalidAuthorPayloads() {
        AuthorsInvalidRequest baseRequest = AuthorsDataGenerator.baseInvalid();

        return new Object[][]{
                {"Invalid ID format", baseRequest.toBuilder().id("asd").build(), ErrorKey.BODY_ID, ErrorMessages.CAN_NOT_CONVERT_TO_INT},
                {"Invalid bookID format", baseRequest.toBuilder().idBook("abc").build(), ErrorKey.BOOK_ID, ErrorMessages.CAN_NOT_CONVERT_TO_INT},
                {"Invalid firstName format", baseRequest.toBuilder().firstName(123).build(), ErrorKey.FIRST_NAME, ErrorMessages.CAN_NOT_CONVERT_TO_STRING},
                {"Invalid lastName format", baseRequest.toBuilder().lastName(456).build(), ErrorKey.LAST_NAME, ErrorMessages.CAN_NOT_CONVERT_TO_STRING}
        };
    }

    @Test(dataProvider = "invalidAuthorPayloads", description = "Test creating author with invalid fields returns 400 with validation errors")
    @Description("Ensure POST /Authors with invalid field types returns 400 Bad Request and proper error messages")
    public void testCreateAuthorWithInvalidFields_ShouldReturn400WithErrors(String testName, AuthorsInvalidRequest invalidRequest, String errorKey, String expectedRegex) {
        Allure.step("Running test case: " + testName);
        Response response = authorsHelper.createAuthorInvalid(invalidRequest);

        Allure.step("Asserting status code is 400 Bad Request");
        assertEquals(response.statusCode(), HttpStatusCode.BAD_REQUEST.getCode());

        Allure.step("Verifying error response contains validation error for field: " + errorKey);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        assertThat(errorResponse.getErrors().get(errorKey).get(0), matchesPattern(expectedRegex));
    }
}