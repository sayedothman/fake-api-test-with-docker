package testing.Authors;

import helpers.authors.AuthorsHelper;
import model.authors.Authors;
import org.testng.annotations.BeforeClass;
import utils.AuthorsDataGenerator;

public class BaseAuthorApiTest {
    protected AuthorsHelper authorsHelper;
    protected Authors validAuthor;


    @BeforeClass
    public void setup() {
        authorsHelper = new AuthorsHelper();
        validAuthor = AuthorsDataGenerator.generateRandomAuthor();
        authorsHelper.createAuthor(validAuthor);
    }
}

