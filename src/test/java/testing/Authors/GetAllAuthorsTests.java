package testing.Authors;

import constants.HttpStatusCode;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import model.authors.Authors;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Get All Authors Tests")
public class GetAllAuthorsTests extends BaseAuthorApiTest {

    private List<Authors> authors;
    private Response response;

    @BeforeMethod
    @Description("Send GET request to fetch all authors before running tests")
    public void setupFetchAllAuthors() {
        Allure.step("Sending GET request to fetch all authors");
        response = authorsHelper.getAllAuthors();
        authors = response.jsonPath().getList("", Authors.class);
    }

    @Test(description = "Test getting all Authors returns HTTP 200 status code")
    @Description("Verify that the GET request to /Authors returns HTTP 200 OK status")
    public void testGetAllAuthors_ShouldReturnStatus200() {
        Allure.step("Asserting response status code is 200");
        assertThat(response.getStatusCode(), is(HttpStatusCode.OK.getCode()));
    }

    @Test(description = "Test author list is not empty after fetching all authors")
    @Description("Verify that the list of authors returned by GET /Authors is not empty")
    public void testGetAllAuthors_ShouldReturnNonEmptyAuthorList() {
        Allure.step("Asserting that author list is not empty");
        assertThat(authors, is(not(empty())));
    }

    @Test(description = "Test each author in the authors list has valid positive ID after fetching all authors")
    @Description("Validate that every author in the retrieved list has an ID greater than zero")
    public void testGetAllAuthors_EachAuthorHasValidId() {
        Allure.step("Asserting that each author has a valid ID");
        assertThat(authors, everyItem(hasProperty("id", greaterThan(0))));
    }

    @Test(description = "Test each author in the authors list has valid positive book ID after fetching all authors")
    @Description("Validate that every author has a bookID greater than zero")
    public void testGetAllAuthors_EachAuthorHasValidBookId() {
        Allure.step("Asserting that each author has a valid bookID");
        assertThat(authors, everyItem(hasProperty("idBook", greaterThan(0))));
    }

    @Test(description = "Test each author in the authors list has a non-empty first name after fetching all authors")
    @Description("Check that every author's first name is neither null nor blank")
    public void testGetAllAuthors_EachAuthorHasNonEmptyFirstName() {
        Allure.step("Asserting that each author has a non-empty first name");
        assertThat(authors, everyItem(hasProperty("firstName", not(blankOrNullString()))));
    }

    @Test(description = "Test each author in the authors list has a non-empty last name after fetching all authors")
    @Description("Check that every author's last name is neither null nor blank")
    public void testGetAllAuthors_EachAuthorHasNonEmptyLastName() {
        Allure.step("Asserting that each author has a non-empty last name");
        assertThat(authors, everyItem(hasProperty("lastName", not(blankOrNullString()))));
    }
}
