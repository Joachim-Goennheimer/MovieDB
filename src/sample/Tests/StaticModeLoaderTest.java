import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sample.StaticModeLoader;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests the static mode of the program.
 * As the tested methods are private static methods that cannot easily be accessed from another class, the
 * test logic is implemented in the StaticModeLoader Class itself and the StaticModeLoaderTest Class only determines the
 * input, calls the methods and then verifies whether the tests were run successfully.
 */
class StaticModeLoaderTest {


    private static String[] userArgs;

    /**
     * setting up the user parameters that were entered in the test scenario.
     */
    @BeforeAll
    static void setup(){
        userArgs = new String[2];
        userArgs[0] = "--film=lord of the rings, Matrix";
        userArgs[1] = "genre=adventure";
    }

    @Test
    void processUserArguments() {
        assertTrue(StaticModeLoader.testProcessUserArguments(userArgs));
    }

    @Test
    void checkInvalidInput(){

        String[] invalidArgs = new String[5];
        invalidArgs[0] = "tralala";
        invalidArgs[1] = "flim=fjfj";
        invalidArgs[2] = "geenre=Action";
        invalidArgs[3] = "25";
        invalidArgs[4] = "Another--limitInvalidArgument";

        assertTrue(StaticModeLoader.testForInvalidInput(invalidArgs));
    }

    @Test
    void preFilter(){
        assertTrue(StaticModeLoader.testPreFilter(userArgs));
    }

    @Test
    void postFilter(){
        assertTrue(StaticModeLoader.testPostFilter(userArgs));
    }
}