import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.datamodel.Actor;
import sample.datamodel.Director;
import sample.datamodel.Movie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests the Movie Class. Tests should be self-explanatory.
 */
class MovieTest {

    private Movie testmovie;

    @BeforeEach
    void setUp() {
        testmovie = new Movie();
        testmovie.setMovieID(10125);
        testmovie.setTitle("Lord of the Rings");
        testmovie.setReleaseDate("2001-12-19");
        List<String> genres = new ArrayList<>();
        genres.add("Fantasy");
        genres.add("Action");
        testmovie.addGenres(genres);
        testmovie.setCurrentUserRating(5.0);
        testmovie.setImdbRating(9.9);
        testmovie.setNumbImdbRatings(10000);
        testmovie.setPlotDescription("A great movie");
        Director director1 = new Director(10, "Peter Jackson");
        testmovie.addDirector(director1);
        Actor actor1 = new Actor(101025, "Orlando Bloom");
        Actor actor2 = new Actor(101026, "Elijah Wood");
        testmovie.addActor(actor1);
        testmovie.addActor(actor2);

    }

    @Test
    void addDirector() {
        Director director = new Director(1212515, "Andy Serkis");
        testmovie.addDirector(director);
        assertEquals("Peter Jackson, Andy Serkis", testmovie.getDirectorNames());
    }

    @Test
    void addActor() {
        Actor actor3 = new Actor(3737125, "Sean Astin");
        testmovie.addActor(actor3);
        assertEquals("Orlando Bloom, Elijah Wood, Sean Astin", testmovie.getActorNames());
    }

    @Test
    void addGenres() {
        List<String> genres = new ArrayList<>();
        genres.add("Adventure");
        genres.add("BeingExciting");
        testmovie.addGenres(genres);
        assertEquals("Fantasy, Action, Adventure, BeingExciting", testmovie.getGenreNames());
    }

    @Test
    void setMovieID() {
        testmovie.setMovieID(10);
        assertEquals(10, testmovie.getMovieID());
    }

    @Test
    void getMovieID() {
        assertEquals(10125, testmovie.getMovieID());
    }

    @Test
    void movieIDProperty() {
        assertEquals(10125, testmovie.movieIDProperty().get());
    }

    @Test
    void setTitle() {
        testmovie.setTitle("The two towers");
        assertEquals("The two towers", testmovie.getTitle());
    }

    @Test
    void getTitle() {
        assertEquals("Lord of the Rings", testmovie.getTitle());
    }

    @Test
    void titleProperty() {
        assertEquals("Lord of the Rings", testmovie.titleProperty().get());
    }

    @Test
    void setDirectorNames() {
        testmovie.setDirectorNames("n/a");
        assertEquals("n/a", testmovie.getDirectorNames());
    }

    @Test
    void getDirectorNames() {
        assertEquals("Peter Jackson", testmovie.getDirectorNames());
    }

    @Test
    void setActorNames() {
        testmovie.setActorNames("n/a");
        assertEquals("n/a", testmovie.getActorNames());
    }

    @Test
    void getActorNames() {
        assertEquals("Orlando Bloom, Elijah Wood", testmovie.getActorNames());
    }

    @Test
    void getGenreNames() {
        assertEquals("Fantasy, Action", testmovie.getGenreNames());
    }

    @Test
    void getGenres() {
        List<String> genres = new ArrayList<>();
        genres.add("Fantasy");
        genres.add("Action");

        assertEquals(genres, testmovie.getGenres());
    }

    @Test
    void setPlotDescription() {
        testmovie.setPlotDescription("I really like this movie");
        assertEquals("I really like this movie", testmovie.getPlotDescription());
    }

    @Test
    void getPlotDescription() {
        assertEquals("A great movie", testmovie.getPlotDescription());
    }

    @Test
    void plotDescriptionProperty() {
        assertEquals("A great movie", testmovie.plotDescriptionProperty().get());

    }

    @Test
    void setReleaseDate_validInput() {
        testmovie.setReleaseDate("2002-12-18");
        assertEquals("2002.12.18", testmovie.getReleaseDateString());
    }

    @Test
    void setReleaseDate_invalidInput() {
        testmovie.setReleaseDate("20d0-0df-15");
        assertEquals("n/a", testmovie.getReleaseDateString());
    }


    @Test
    void getReleaseDateString() {
        assertEquals("2001.12.19", testmovie.getReleaseDateString());
    }

    @Test
    void setImdbRating() {
        testmovie.setImdbRating(9.8);
        assertEquals(9.8, testmovie.getImdbRating());
    }

    @Test
    void getImdbRating() {
        assertEquals(9.9, testmovie.getImdbRating());
    }

    @Test
    void imdbRatingProperty() {
        assertEquals(9.9, testmovie.imdbRatingProperty().get());
    }

    @Test
    void setNumbImdbRatings() {
        testmovie.setNumbImdbRatings(20000);
        assertEquals(20000, testmovie.getNumbImdbRatings());
    }

    @Test
    void getNumbImdbRatings() {
        assertEquals(10000, testmovie.getNumbImdbRatings());
    }

    @Test
    void numbImdbRatingsProperty() {
        assertEquals(10000, testmovie.numbImdbRatingsProperty().get());
    }

    @Test
    void setCurrentUserRating_validInput() {
        testmovie.setCurrentUserRating(4.9);
        assertEquals(4.9, testmovie.getCurrentUserRating());
    }

    @Test
    void setCurrentUserRating_invalidInput() {
        testmovie.setCurrentUserRating(-2);
        assertEquals(-2, testmovie.getCurrentUserRating());
        assertEquals("Please enter a number", testmovie.getCurrentUserRatingString());

        testmovie.setCurrentUserRating(-2);
        assertEquals(-2, testmovie.getCurrentUserRating());
        assertEquals("YOU HAVE TO ENTER A NUMBER", testmovie.getCurrentUserRatingString());
    }

    @Test
    void setCurrentUserRating_noData() {
        testmovie.setCurrentUserRating(-1);
        assertEquals(-1, testmovie.getCurrentUserRating());
        assertEquals("n/a", testmovie.getCurrentUserRatingString());
    }


    @Test
    void getCurrentUserRating() {
        assertEquals(5.0, testmovie.getCurrentUserRating());
    }

    @Test
    void getCurrentUserRatingString() {
        assertEquals("5.0", testmovie.getCurrentUserRatingString());

    }

    @Test
    void currentUserRatingStringProperty() {
        assertEquals("5.0", testmovie.currentUserRatingStringProperty().get());

    }

    @Test
    void toString1() {
        assertEquals(testmovie.getTitle(), testmovie.toString());
    }
}