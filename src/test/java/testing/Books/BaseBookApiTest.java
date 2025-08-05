package testing.Books;

import helpers.books.BooksHelper;
import model.books.Books;
import org.testng.annotations.BeforeClass;
import utils.AuthorsDataGenerator;
import utils.BooksDataGenerator;

public class BaseBookApiTest {
    protected BooksHelper booksHelper;
    protected Books validBook;


    @BeforeClass
    public void setup() {
        booksHelper = new BooksHelper();
        validBook = BooksDataGenerator.generateRandomBook();
        booksHelper.createBook(validBook);
    }
}

