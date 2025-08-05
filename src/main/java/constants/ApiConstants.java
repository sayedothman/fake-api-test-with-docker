package constants;


import utils.ConfigUtil;

public final class ApiConstants {

    private ApiConstants() {
    }

    // Base URI
    public static final String BASE_URL = ConfigUtil.getBaseUrl();

    // Endpoints
    public static final class Endpoints {
        private Endpoints() {}

        public static final String BOOKS = "Books";
        public static final String AUTHORS = "Authors";
    }
}
