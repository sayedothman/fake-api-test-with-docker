package testing.Books;

import constants.HttpStatusCode;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import model.authors.Authors;
import model.books.Books;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Get All Books Tests")
public class GetAllBooksTests extends BaseBookApiTest {

    private List<Books> books;
    private Response response;

    @BeforeMethod
    @Description("Send GET request to fetch all authors before running tests")
    public void setupFetchAllBooks() {
        Allure.step("Sending GET request to fetch all authors");
        response = booksHelper.getAllBooks();
        books = response.jsonPath().getList("", Books.class);
    }

    @Test(description = "Test getting all books returns 200")
    @Description("Ensure GET /Books returns HTTP 200 OK")
    public void testGetAllBooks_ShouldReturn200() {
        Allure.step("Asserting response status code is 200 OK");
        assertThat(response.getStatusCode(), is(HttpStatusCode.OK.getCode()));
    }

    @Test(description = "Test book list is not empty after fetching all books")
    @Description("Ensure the returned book list from GET /Books is not empty")
    public void testGetAllBooks_ShouldReturnNonEmptyList() {
        Allure.step("Asserting that the book list is not empty");
        assertThat(books, is(not(empty())));
    }

    @Test(description = "Test every book in the book list has a valid ID after fetching all books")
    @Description("Ensure each book returned from GET /Books has a valid (greater than zero) ID")
    public void testEachBook_ShouldHaveValidId() {
        Allure.step("Asserting that each book has an ID greater than zero");
        assertThat(books, everyItem(hasProperty("id", greaterThan(0))));
    }

    @Test(description = "Test every book in the book list has a non-empty title after fetching all books")
    @Description("Ensure each book returned from GET /Books has a non-empty, non-null title")
    public void testEachBook_ShouldHaveNonEmptyTitle() {
        Allure.step("Asserting that each book has a non-null and non-empty title");
        assertThat(books, everyItem(hasProperty("title", not(blankOrNullString()))));
    }

    @Test(description = "Test every book in the book list has a publish date after fetching all books")
    @Description("Ensure each book returned from GET /Books has a publish date field")
    public void testEachBook_ShouldHavePublishDate() {
        Allure.step("Asserting that each book has a non-null publish date");
        assertThat(books, everyItem(hasProperty("publishDate", notNullValue())));
    }
}
