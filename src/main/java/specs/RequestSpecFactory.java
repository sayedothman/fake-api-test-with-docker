package specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Collections;
import java.util.Map;

public class RequestSpecFactory {

    /**
     * Builds a dynamic RequestSpecification.
     *
     * @param baseUri     The base URI of the API.
     * @param headers     Optional request headers, may be null.
     * @param contentType Content type of the request.
     * @param body        Optional request body: if contentType is URLENC, and body is Map, treated as form params.
     *                    Otherwise treated as raw body (POJO, String, etc).
     * @return Configured RequestSpecification.
     */
    public static RequestSpecification build(String baseUri,
                                             Map<String, String> headers,
                                             ContentType contentType,
                                             Object body) {

        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(contentType)
                .addHeaders(headers != null ? headers : Collections.emptyMap())
                .addFilter(new AllureRestAssured())
                .log(io.restassured.filter.log.LogDetail.ALL);

        // Handle form params differently
        if (body != null) {
            if (contentType == ContentType.URLENC && body instanceof Map) {
                builder.addFormParams((Map<String, String>) body);
            } else {
                builder.setBody(body);
            }
        }

        return builder.build();
    }
}
