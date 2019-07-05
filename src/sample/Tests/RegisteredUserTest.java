import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.datamodel.RegisteredUser;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests the RegisteredUser Class. Tests should be self-explanatory.
 */
class RegisteredUserTest {

    private RegisteredUser testuser;

    @BeforeEach
    void setUp() {
        testuser = new RegisteredUser();
        testuser.setUserName("Leon");
        testuser.setPassword("Leon12345");
        testuser.addRating(2.5, 1015);
        testuser.addRating(5.0, 1017015);
    }

    @Test
    void addRating_validInput() {
        testuser.addRating(3.5, 101025);
        assertEquals(3.5, testuser.getRating(101025));

    }

    /**
     * tests whether invalid input for a rating is processed correctly. If invalid input was entered the rating will be
     * set to -1.
     */
    @Test
    void addRating_InvalidInput() {
        testuser.addRating(-2.0, 1015);
        assertEquals(-1, testuser.getRating(1015));
    }

    @Test
    void getRatings() {
        Map<Integer, Double> testmap = new HashMap<>();
        testmap.put(1015, 2.5);
        testmap.put(1017015, 5.0);
        assertEquals(testmap, testuser.getRatings());
    }

    @Test
    void getRating() {
        assertEquals(5.0, testuser.getRating(1017015) );
    }

    @Test
    void setUserName() {
        testuser.setUserName("Janik");
        assertEquals("Janik", testuser.getUserName());
    }

    @Test
    void getUserName() {
        assertEquals("Leon", testuser.getUserName());
    }

    @Test
    void checkPassword_correctInput() {
        assertTrue(testuser.checkPassword("Leon12345"));
    }

    @Test
    void checkPassword_falseInput() {
        assertFalse(testuser.checkPassword("blablabla"));
    }

    @Test
    void setPassword() {
        testuser.setPassword("ThisIsAStrongPassword171712345");
        assertTrue(testuser.checkPassword("ThisIsAStrongPassword171712345"));
    }


}