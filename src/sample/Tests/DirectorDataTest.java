import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sample.datamodel.Director;
import sample.datamodel.DirectorData;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests the DirectorData Class
 */
class DirectorDataTest {

    private static DirectorData directorData;

    @BeforeAll
    static void setup(){
        directorData = DirectorData.getInstance();
    }

    /**
     * tests whether instance returns correct type of object and that the object is not null.
     */
    @Test
    void getInstance() {
        DirectorData directorData = DirectorData.getInstance();
        assertEquals("sample.datamodel.DirectorData", directorData.getClass().getName());
        assertNotEquals(null, directorData);
    }

    /**
     * tests whether directors are loaded correctly from file. Testing with two directors and their ids that are part of the file.
     */
    @Test
    void loadDirectors() {
        directorData.loadDirectors();
        Director testdirector = new Director(27975, "Georges Méliès");
        Director testdirector2 = new Director(29161, "Peter Jackson");
        assertEquals(testdirector2.getName(), directorData.getDirectorByID(29161).getName());

    }

    /**
     * first loading directors and then trying to get two directors by id that are part of the file.
     */
    @Test
    void getDirectorByID() {
        directorData.loadDirectors();
        Director testdirector = new Director(27975, "Georges Méliès");
        Director testdirector2 = new Director(29161, "Peter Jackson");
        assertEquals(testdirector.getName(), directorData.getDirectorByID(27975).getName());
        assertEquals(testdirector2.getName(), directorData.getDirectorByID(29161).getName());
    }
}