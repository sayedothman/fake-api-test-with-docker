package constants;

/**
 * Centralized regex patterns for known error message responses
 * to be used in test assertions with matchesPattern().
 */
public class ErrorMessages {

    public static final String CAN_NOT_CONVERT_TO_INT =
            "The JSON value could not be converted to System\\.Int32\\. .*";

    public static final String CAN_NOT_CONVERT_TO_DATE =
            "The JSON value could not be converted to System\\.DateTime\\. .*";

    public static final String CAN_NOT_CONVERT_TO_STRING =
            "The JSON value could not be converted to System\\.String\\. .*";

    public static final String VALUE_NOT_VALID =
            "The value '.*' is (not valid|invalid)\\.";

    public static final String MALFORMED_JSON =
            "'/' is invalid after a value\\. Expected either ',', '}', or ']'\\. Path: \\$ .*";

    private ErrorMessages() {
    }
}
