package constants;

/**
 * Keys used in validation error responses from the /Books API.
 * These typically match the JSON path format in the response `errors` map.
 */
public class ErrorKey {
    public static final String BODY_ID = "$.id";
    public static final String TITLE = "$.title";
    public static final String DESCRIPTION = "$.description";
    public static final String PAGE_COUNT = "$.pageCount";
    public static final String EXCERPT = "$.excerpt";
    public static final String PUBLISH_DATE = "$.publishDate";
    public static final String BOOK_ID = "$.idBook";
    public static final String FIRST_NAME =  "$.firstName";
    public static final String LAST_NAME = "$.lastName";

}
