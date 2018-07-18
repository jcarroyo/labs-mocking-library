import com.jcarroyo.labs.mocking.data.Book;
import com.jcarroyo.labs.mocking.data.IBookRepo;
import com.jcarroyo.labs.mocking.logic.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class BooksTest {
    @Test
    public void testJUnit(){
        Assertions.assertTrue(true);
    }

    private BookLogic bookLogic;

    @BeforeEach
    public void BeforeEach() throws Exception{

    }

    @AfterEach
    public void AfterEach(){

    }

    @Test
    public void registerBook_DuplicatedBook_ThrowException()
    {
        try{
            IBookRepo bookRepo = Mockito.mock(IBookRepo.class);
            when(bookRepo.lookForDuplicated(anyString())).thenReturn(true);
            BookLogic bookLogic = new BookLogic(bookRepo);
            Assertions.assertThrows(BookLogic.DuplicatedBookException.class,
                    () -> {
                        bookLogic.registerBook("Libro duplicado", 15.00f);
                    });
        }
        catch (Exception ex){
            ex.printStackTrace();
            Assertions.fail("Error", ex);
        }
    }

    @Test
    public void printBooks_EmptyLibrary_ThrowException(){
        try{
            IBookRepo bookRepo = Mockito.mock(IBookRepo.class);
            when(bookRepo.getAllBooks()).thenReturn(null);
            BookLogic bookLogic = new BookLogic(bookRepo);
            Assertions.assertThrows(BookLogic.LibraryEmptyException.class,
                    () -> {
                        bookLogic.printBooks();
                    });
        }
        catch (Exception ex){
            ex.printStackTrace();
            Assertions.fail("Fallo", ex);
        }
    }

    @Test
    public void registerBook_ApplyFormat_Correct(){
        try{
            IBookRepo bookRepo = Mockito.mock(IBookRepo.class);
            when(bookRepo.lookForDuplicated(anyString())).thenReturn(false);
            //http://static.javadoc.io/org.mockito/mockito-core/2.19.1/org/mockito/Mockito.html#doNothing--
            BookLogic bookLogic = new BookLogic(bookRepo);
            Book result = bookLogic.registerBook("Libros Fantásticos", 16.34f);
            Assertions.assertEquals("Libros Fantásticos", result.getName());
            Assertions.assertEquals("libros fantasticos", result.getFlatName());
            Assertions.assertEquals(16f, result.getPrice());
        }
        catch (Exception ex){
            ex.printStackTrace();
            Assertions.fail("Fallo", ex);
        }
    }

    @Test
    public void printBooks_AvailableBooks_AuditAction_IsCalled(){
        try{
            IBookRepo bookRepo = Mockito.mock(IBookRepo.class);
            when(bookRepo.getAllBooks()).thenReturn(new ArrayList<Book>());
            BookLogic bookLogic = new BookLogic(bookRepo);
            bookLogic.printBooks();
            verify(bookRepo, times(1)).auditAction();
        }
        catch (Exception ex){
            ex.printStackTrace();
            Assertions.fail("Fallo", ex);
        }
    }
}
