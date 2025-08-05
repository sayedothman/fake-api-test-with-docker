package constants;

public enum HttpStatusCode {
    OK(200),
    CREATED(201),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CONFLICT(409),
    UNSUPPORTED_MEDIA_TYPE(415),
    INTERNAL_SERVER_ERROR(500),
    METHOD_NOT_ALLOWED(405);

    private final int code;

    HttpStatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
