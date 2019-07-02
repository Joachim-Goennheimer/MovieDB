import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.datamodel.DataOrganisation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataOrganisationTest {

    @BeforeEach
    void setup(){
        DataOrganisation.reset();
    }


    @Test
    void getInstance() {
        DataOrganisation dataOrganisation = DataOrganisation.getInstance();
        assertNotEquals(null, dataOrganisation);
    }

    @Test
    void load_MovieID_DirectorID_Map() {
        DataOrganisation dataOrganisation = DataOrganisation.getInstance();
        dataOrganisation.load_MovieID_DirectorID_Map();
        Map<Integer, List<Integer>> testmap = dataOrganisation.getMovieID_DirectorID_Map();
        List<Integer> directorIDS = new ArrayList<>();
        directorIDS.add(30191);
        directorIDS.add(30192);
        assertEquals(directorIDS, testmap.get(2081));
    }

    @Test
    void load_MovieID_ActorID_Map() {
        DataOrganisation dataOrganisation = DataOrganisation.getInstance();
        dataOrganisation.load_MovieID_ActorID_Map();
        Map<Integer, List<Integer>> testmap = dataOrganisation.getMovieID_ActorID_Map();
        List<Integer> actorIDs = new ArrayList<>();
        actorIDs.add(16689);
        actorIDs.add(20027);
        actorIDs.add(16315);
        actorIDs.add(18255);
        assertEquals(actorIDs, testmap.get(2081));
    }

    @Test
    void load_MovieID_GenreMap() {
        DataOrganisation dataOrganisation = DataOrganisation.getInstance();
        dataOrganisation.load_MovieID_GenreMap();
        Map<Integer, List<String>> testmap = dataOrganisation.getMovieID_GenreMap();
        List<String> genres = new ArrayList<>();
        genres.add("Action");
        genres.add("Thriller");
        genres.add("Sci-Fi");
        assertEquals(genres, testmap.get(2081));
    }

    @Test
    void getMovieID_DirectorID_Map() {
        DataOrganisation dataOrganisation = DataOrganisation.getInstance();
        dataOrganisation.load_MovieID_DirectorID_Map();
        assertTrue(dataOrganisation.getMovieID_DirectorID_Map().get(2081).contains(30191));
    }

    @Test
    void getMovieID_ActorID_Map() {
        DataOrganisation dataOrganisation = DataOrganisation.getInstance();
        dataOrganisation.load_MovieID_ActorID_Map();
        assertTrue(dataOrganisation.getMovieID_ActorID_Map().get(2081).contains(16689));
    }

    @Test
    void getMovieID_GenreMap() {
        DataOrganisation dataOrganisation = DataOrganisation.getInstance();
        dataOrganisation.load_MovieID_GenreMap();
        assertTrue(dataOrganisation.getMovieID_GenreMap().get(2081).contains("Action"));
    }
}