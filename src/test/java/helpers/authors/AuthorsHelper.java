package helpers.authors;

import clients.ApiClient;
import constants.ApiConstants;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import model.authors.Authors;
import model.authors.AuthorsInvalidRequest;

import java.util.HashMap;
import java.util.Map;

public class AuthorsHelper {

    private final ApiClient client;

    public AuthorsHelper() {
        this.client = new ApiClient(ApiConstants.BASE_URL);
    }

    /**
     * Sends a request to create a new author.
     *
     * @param request The author data to create.
     * @return The API response.
     */
    @Step("Create a new author")
    public Response createAuthor(Authors request) {
        return client.sendRequest(
                Method.POST,
                ApiConstants.Endpoints.AUTHORS,
                null,
                request,
                ContentType.JSON,
                null
        );
    }

    /**
     * Sends a request to create an author with invalid data.
     *
     * @param invalidRequest The invalid author data.
     * @return The API response.
     */
    @Step("Create a new author with invalid payload")
    public Response createAuthorInvalid(AuthorsInvalidRequest invalidRequest) {
        return client.sendRequest(
                Method.POST,
                ApiConstants.Endpoints.AUTHORS,
                null,
                invalidRequest,
                ContentType.JSON,
                null
        );
    }

    /**
     * Retrieves all authors from the API.
     *
     * @return The API response containing all authors.
     */
    @Step("Get all authors")
    public Response getAllAuthors() {
        return client.sendRequest(
                Method.GET,
                ApiConstants.Endpoints.AUTHORS,
                null,
                null,
                ContentType.JSON,
                null
        );
    }

    /**
     * Retrieves an author by numeric ID.
     *
     * @param id The author's ID.
     * @return The API response containing the author details.
     */
    @Step("Get author by ID: {id}")
    public Response getAuthorById(int id) {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("id", id);
        return client.sendRequest(
                Method.GET,
                ApiConstants.Endpoints.AUTHORS + "/{id}",
                null,
                null,
                ContentType.JSON,
                pathParams
        );
    }

    /**
     * Retrieves an author by string ID.
     *
     * @param id The author's ID as a string.
     * @return The API response containing the author details.
     */
    @Step("Get author by ID (String): {id}")
    public Response getAuthorById(String id) {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("id", id);
        return client.sendRequest(
                Method.GET,
                ApiConstants.Endpoints.AUTHORS + "/{id}",
                null,
                null,
                ContentType.JSON,
                pathParams
        );
    }

    /**
     * Updates an existing author by ID with new data.
     *
     * @param id The author's ID.
     * @param updatedData The updated author information.
     * @return The API response.
     */
    @Step("Update author with ID: {id}")
    public Response updateAuthor(int id, Authors updatedData) {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("id", id);
        return client.sendRequest(
                Method.PUT,
                ApiConstants.Endpoints.AUTHORS + "/{id}",
                null,
                updatedData,
                ContentType.JSON,
                pathParams
        );
    }

    /**
     * Attempts to update an author with invalid data.
     *
     * @param id The author's ID.
     * @param invalidRequest The invalid update data.
     * @return The API response.
     */
    public Response updateAuthorInvalid(int id, AuthorsInvalidRequest invalidRequest) {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("id", id);
        return client.sendRequest(
                Method.PUT,
                ApiConstants.Endpoints.AUTHORS + "/{id}",
                null,
                invalidRequest,
                ContentType.JSON,
                pathParams
        );
    }

    /**
     * Deletes an author by numeric ID.
     *
     * @param id The author's ID.
     * @return The API response.
     */
    @Step("Delete author with ID: {id}")
    public Response deleteAuthor(int id) {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("id", id);
        return client.sendRequest(
                Method.DELETE,
                ApiConstants.Endpoints.AUTHORS + "/{id}",
                null,
                null,
                ContentType.JSON,
                pathParams
        );
    }

    /**
     * Sends a request to delete authors without specifying an ID.
     *
     * @return The API response.
     */
    @Step("Delete author without ID")
    public Response deleteAuthor() {
        return client.sendRequest(
                Method.DELETE,
                ApiConstants.Endpoints.AUTHORS,
                null,
                null,
                ContentType.JSON,
                null
        );
    }

    /**
     * Deletes an author by string ID.
     *
     * @param id The author's ID as a string.
     * @return The API response.
     */
    @Step("Delete author with string ID: {id}")
    public Response deleteAuthor(String id) {
        return client.sendRequest(
                Method.DELETE,
                ApiConstants.Endpoints.AUTHORS + "/" + id,
                null,
                null,
                ContentType.JSON,
                null
        );
    }
}
