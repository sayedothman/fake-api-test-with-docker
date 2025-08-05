package clients;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import specs.RequestSpecFactory;

import java.util.Map;

public class ApiClient {

    private final String baseURI;

    public ApiClient(String baseURI) {
        this.baseURI = baseURI;
    }

    /**
     * Sends an API request with full customization.
     *
     * @param method      HTTP method (GET, POST, PUT, DELETE, etc.)
     * @param path        API endpoint (e.g., "/books/{id}")
     * @param headers     Optional headers (nullable)
     * @param body        Optional body (nullable)
     * @param contentType Content type (e.g., JSON)
     * @param pathParams  Optional path parameters to fill in {placeholders} (nullable)
     * @return API Response
     */
    @Step("Send {method} request to {baseURI}{path}")
    public Response sendRequest(Method method,
                                String path,
                                Map<String, String> headers,
                                Object body,
                                ContentType contentType,
                                Map<String, ?> pathParams) {

        RequestSpecification spec = RequestSpecFactory.build(baseURI, headers, contentType, body);

        if (pathParams != null && !pathParams.isEmpty()) {
            spec.pathParams(pathParams);
        }

        Response response = RestAssured
                .given()
                .spec(spec)
                .request(method, path);

        response.then().log().all();
        return response;
    }
}
