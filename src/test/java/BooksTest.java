import com.jcarroyo.labs.mocking.data.Book;
import com.jcarroyo.labs.mocking.logic.BookLogic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BooksTest {
    @Test
    public void testJUnit(){
        Assertions.assertTrue(true);
    }

    private BookLogic bookLogic;

    @BeforeEach
    public void BeforeEach() throws Exception{
        bookLogic = new BookLogic();
    }

    @AfterEach
    public void AfterEach(){
        bookLogic.closeLibraryRepo();
    }

    @Test
    public void b()
    {
        try{
            Book book = bookLogic.registerBook("Fulanito", 15.5f);
            Assertions.assertEquals(book.getName(), "Fulanito");
            Assertions.assertEquals(book.getFlatName(), "fulanito");
            Assertions.assertEquals(book.getPrice(), 15f);
        }
        catch (Exception ex){
            ex.printStackTrace();
            Assertions.fail("Error", ex);
        }
    }


}
