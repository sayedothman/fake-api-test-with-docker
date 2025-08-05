package helpers.books;

import clients.ApiClient;
import constants.ApiConstants;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import model.books.Books;
import model.books.BooksInvalidRequest;
import java.util.HashMap;
import java.util.Map;

public class BooksHelper {

    private final ApiClient client;

    public BooksHelper() {
        this.client = new ApiClient(ApiConstants.BASE_URL);
    }

    /**
     * Sends a request to create a new book.
     *
     * @param request The book data to create.
     * @return The API response.
     */
    @Step("Create a new book")
    public Response createBook(Books request) {
        return client.sendRequest(
                Method.POST,
                ApiConstants.Endpoints.BOOKS,
                null,
                request,
                ContentType.JSON,
                null
        );
    }

    /**
     * Sends a request to create a book with invalid data.
     *
     * @param invalidRequest The invalid book data.
     * @return The API response.
     */
    @Step("Create a new book with invalid payload")
    public Response createBookInvalid(BooksInvalidRequest invalidRequest) {
        return client.sendRequest(
                Method.POST,
                ApiConstants.Endpoints.BOOKS,
                null,
                invalidRequest,
                ContentType.JSON,
                null
        );
    }

    /**
     * Retrieves all books from the API.
     *
     * @return The API response containing all books.
     */
    @Step("Get all books")
    public Response getAllBooks() {
        return client.sendRequest(
                Method.GET,
                ApiConstants.Endpoints.BOOKS,
                null,
                null,
                ContentType.JSON,
                null
        );
    }

    /**
     * Retrieves a book by its numeric ID.
     *
     * @param id The book's ID.
     * @return The API response containing the book details.
     */
    @Step("Get book by ID: {id}")
    public Response getBookById(int id) {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("id", id);
        return client.sendRequest(
                Method.GET,
                ApiConstants.Endpoints.BOOKS + "/{id}",
                null,
                null,
                ContentType.JSON,
                pathParams
        );
    }

    /**
     * Retrieves a book by its string ID.
     *
     * @param id The book's ID as a string.
     * @return The API response containing the book details.
     */
    @Step("Get book by ID (String): {id}")
    public Response getBookById(String id) {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("id", id);
        return client.sendRequest(
                Method.GET,
                ApiConstants.Endpoints.BOOKS + "/{id}",
                null,
                null,
                ContentType.JSON,
                pathParams
        );
    }

    /**
     * Updates an existing book by ID with new data.
     *
     * @param id The book's ID.
     * @param updatedData The updated book information.
     * @return The API response.
     */
    @Step("Update book with ID: {id}")
    public Response updateBook(int id, Books updatedData) {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("id", id);
        return client.sendRequest(
                Method.PUT,
                ApiConstants.Endpoints.BOOKS + "/{id}",
                null,
                updatedData,
                ContentType.JSON,
                pathParams
        );
    }

    /**
     * Attempts to update a book with invalid data.
     *
     * @param id The book's ID.
     * @param invalidRequest The invalid update data.
     * @return The API response.
     */
    public Response updateBookInvalid(int id, BooksInvalidRequest invalidRequest) {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("id", id);
        return client.sendRequest(
                Method.PUT,
                ApiConstants.Endpoints.BOOKS + "/{id}",
                null,
                invalidRequest,
                ContentType.JSON,
                pathParams
        );
    }

    /**
     * Deletes a book by its numeric ID.
     *
     * @param id The book's ID.
     * @return The API response.
     */
    @Step("Delete book with ID: {id}")
    public Response deleteBook(int id) {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("id", id);
        return client.sendRequest(
                Method.DELETE,
                ApiConstants.Endpoints.BOOKS + "/{id}",
                null,
                null,
                ContentType.JSON,
                pathParams
        );
    }

    /**
     * Sends a request to delete a book without specifying an ID.
     *
     * @return The API response.
     */
    @Step("Delete book without ID")
    public Response deleteBook() {
        return client.sendRequest(
                Method.DELETE,
                ApiConstants.Endpoints.BOOKS,
                null,
                null,
                ContentType.JSON,
                null
        );
    }

    /**
     * Deletes a book by its string ID.
     *
     * @param id The book's ID as a string.
     * @return The API response.
     */
    @Step("Delete book with string ID: {id}")
    public Response deleteBook(String id) {
        return client.sendRequest(
                Method.DELETE,
                ApiConstants.Endpoints.BOOKS + "/" + id,
                null,
                null,
                ContentType.JSON,
                null
        );
    }
}
