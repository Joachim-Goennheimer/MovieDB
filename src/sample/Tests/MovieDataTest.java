import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.DataLoader;
import sample.InteractiveModeLoader;
import sample.datamodel.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MovieDataTest {

    private static RegisteredUserData registeredUserData;
    private static RegisteredUser currentlyLoggedIn;
    private static MovieData movieData;

    @BeforeAll
    static void setup(){

    }

    @BeforeEach
    void setup2(){

        MovieData.reset();

        DataOrganisation.reset();

    }

    @Test
    void getInstance() {
        MovieData movieData = MovieData.getInstance();
        assertTrue(movieData.getClass().getName().equals("sample.datamodel.MovieData"));
        assertNotEquals(null, movieData);
    }

    @Test
    void loadMovies_staticMode() {

        DataLoader.loadBaseData();
        MovieData movieData = MovieData.getInstance();
        movieData.loadMovies();
        Set<Integer> getMoviesSet = new HashSet<>();
        getMoviesSet.add(5045);
        getMoviesSet.add(2081);
        List<Movie> movies = movieData.getMoviesByID(getMoviesSet);
        for (Movie movie: movies){
            if (movie.getMovieID() == 5045){
                assertEquals("Lord of the Rings: The Return of the King, The", movie.getTitle());
                assertEquals("2003.12.17", movie.getReleaseDateString());
                assertEquals("Peter Jackson", movie.getDirectorNames());
                assertEquals("David Aston, Sean Astin, Ali Astin, Noel Appleby", movie.getActorNames());
                assertEquals("Adventure, Fantasy, Drama, Action", movie.getGenreNames());
                assertEquals(8.9, movie.getImdbRating());
            }
            else {
                assertEquals("Matrix, The", movie.getTitle());
                assertEquals("1999.03.31", movie.getReleaseDateString());
                assertEquals("Andy Wachowski, Lana Wachowski", movie.getDirectorNames());
                assertEquals("Laurence Fishburne, Carrie-Anne Moss, Keanu Reeves, Hugo Weaving", movie.getActorNames());
                assertEquals("Action, Thriller, Sci-Fi", movie.getGenreNames());
                assertEquals(8.7, movie.getImdbRating());
            }
        }

    }

    @Test
    void loadMovies_interactiveMode() {

        InteractiveModeLoader.loadDataInteractiveMode();
        registeredUserData = RegisteredUserData.getInstance();
        registeredUserData.loginUser("testuser", "TestPasswort123");
        currentlyLoggedIn = registeredUserData.getCurrentlyLoggedIn();

        MovieData movieData = MovieData.getInstance();
        movieData.loadMovies(currentlyLoggedIn);
        Set<Integer> getMoviesSet = new HashSet<>();
        getMoviesSet.add(5045);
        getMoviesSet.add(2081);
        List<Movie> movies = movieData.getMoviesByID(getMoviesSet);
        for (Movie movie: movies){
            if (movie.getMovieID() == 5045){
                assertEquals("Lord of the Rings: The Return of the King, The", movie.getTitle());
                assertEquals("2003.12.17", movie.getReleaseDateString());
                assertEquals("Peter Jackson", movie.getDirectorNames());
                assertEquals("David Aston, Sean Astin, Ali Astin, Noel Appleby", movie.getActorNames());
                assertEquals("Adventure, Fantasy, Drama, Action", movie.getGenreNames());
                assertEquals(8.9, movie.getImdbRating());
                assertEquals(5.0, movie.getCurrentUserRating());
                assertEquals("5.0", movie.getCurrentUserRatingString());
            }
            else {
                assertEquals("Matrix, The", movie.getTitle());
                assertEquals("1999.03.31", movie.getReleaseDateString());
                assertEquals("Andy Wachowski, Lana Wachowski", movie.getDirectorNames());
                assertEquals("Laurence Fishburne, Carrie-Anne Moss, Keanu Reeves, Hugo Weaving", movie.getActorNames());
                assertEquals("Action, Thriller, Sci-Fi", movie.getGenreNames());
                assertEquals(8.7, movie.getImdbRating());
                assertEquals(5.0, movie.getCurrentUserRating());
                assertEquals("5.0", movie.getCurrentUserRatingString());
            }
        }
    }

    @Test
    void getMovies() {

        DataLoader.loadBaseData();
        MovieData movieData = MovieData.getInstance();
        movieData.loadMovies();
        assertTrue(movieData.getMovies().getClass().getName().equals("com.sun.javafx.collections.ObservableListWrapper"));
        assertFalse(movieData.getMovies().isEmpty());
    }

    @Test
    void getMoviesByID() {

        DataLoader.loadBaseData();
        MovieData movieData = MovieData.getInstance();
        movieData.loadMovies();
        Set<Integer> getMoviesSet = new HashSet<>();
        getMoviesSet.add(5045);
        getMoviesSet.add(2081);
        List<Movie> movies = movieData.getMoviesByID(getMoviesSet);
        for (Movie movie: movies){
            if (movie.getMovieID() == 5045){
                assertEquals("Lord of the Rings: The Return of the King, The", movie.getTitle());
                assertEquals("2003.12.17", movie.getReleaseDateString());
                assertEquals("Peter Jackson", movie.getDirectorNames());
                assertEquals("David Aston, Sean Astin, Ali Astin, Noel Appleby", movie.getActorNames());
                assertEquals("Adventure, Fantasy, Drama, Action", movie.getGenreNames());
                assertEquals(8.9, movie.getImdbRating());
            }
            else {
                assertEquals("Matrix, The", movie.getTitle());
                assertEquals("1999.03.31", movie.getReleaseDateString());
                assertEquals("Andy Wachowski, Lana Wachowski", movie.getDirectorNames());
                assertEquals("Laurence Fishburne, Carrie-Anne Moss, Keanu Reeves, Hugo Weaving", movie.getActorNames());
                assertEquals("Action, Thriller, Sci-Fi", movie.getGenreNames());
                assertEquals(8.7, movie.getImdbRating());
            }
        }

    }

    @Test
    void getImdbRatings() {

        InteractiveModeLoader.loadDataInteractiveMode();
        registeredUserData = RegisteredUserData.getInstance();
        registeredUserData.loginUser("testuser", "TestPasswort123");
        currentlyLoggedIn = registeredUserData.getCurrentlyLoggedIn();

        MovieData movieData = MovieData.getInstance();
        movieData.loadMovies(currentlyLoggedIn);
        Map<Integer, Double> ratings = movieData.getImdbRatings();
        assertEquals(8.5, ratings.get(2879));
        assertEquals(8.9, ratings.get(5045));
        assertEquals(8.7, ratings.get(2081));
    }
}