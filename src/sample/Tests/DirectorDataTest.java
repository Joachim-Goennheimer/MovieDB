import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sample.datamodel.Director;
import sample.datamodel.DirectorData;

import static org.junit.jupiter.api.Assertions.*;

class DirectorDataTest {

    private static DirectorData directorData;

    @BeforeAll
    static void setup(){
        directorData = DirectorData.getInstance();
    }

    @Test
    void getInstance() {
        DirectorData directorData = DirectorData.getInstance();
        assertEquals("sample.datamodel.DirectorData", directorData.getClass().getName());
        assertNotEquals(null, directorData);
    }

    @Test
    void loadDirectors() {
        directorData.loadDirectors();
        Director testdirector = new Director(27975, "Georges Méliès");
        Director testdirector2 = new Director(29161, "Peter Jackson");
        assertEquals(testdirector2.getName(), directorData.getDirectorByID(29161).getName());

    }

    @Test
    void getDirectorByID() {
        directorData.loadDirectors();
        Director testdirector = new Director(27975, "Georges Méliès");
        Director testdirector2 = new Director(29161, "Peter Jackson");
        assertEquals(testdirector2.getName(), directorData.getDirectorByID(29161).getName());
    }
}