import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.datamodel.Director;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class that tests the Director Class. Tests should be self-explanatory.
 */
class DirectorTest {

    private Director testdirector;

    @BeforeEach
    void setup(){
        testdirector = new Director(31415, "Peter Jackson");
    }
    @Test
    void getName() {

        assertEquals("Peter Jackson", testdirector.getName());
    }


    @Test
    void toString1() {
        assertEquals("Peter Jackson", testdirector.getName());
    }
}