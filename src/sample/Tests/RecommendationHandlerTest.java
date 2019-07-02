import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.InteractiveModeLoader;
import sample.datamodel.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationHandlerTest {

    @BeforeEach
    void reset(){
        MovieData.reset();
        DataOrganisation.reset();
    }

    @Test
    void loadRecommendations() {

        InteractiveModeLoader.loadDataInteractiveMode();
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        registeredUserData.loginUser("testuser", "TestPasswort123");
        MovieData movieData = MovieData.getInstance();
        movieData.loadMovies(registeredUserData.getCurrentlyLoggedIn());

        RecommendationHandler.loadRecommendations();

        List<Movie> recommendations = RecommendationHandler.getRecommendations();

        assertFalse(recommendations.isEmpty());

    }

    @Test
    void addRecommendations() {

        Set<Integer> recommendationIDs = new HashSet<>();
        recommendationIDs.add(3890);
        recommendationIDs.add(4414);
        recommendationIDs.add(5045);

        InteractiveModeLoader.loadDataInteractiveMode();

        MovieData movieData = MovieData.getInstance();
        movieData.loadMovies();
        RecommendationHandler.addRecommendations(recommendationIDs);

        List<Movie> recommendations = RecommendationHandler.getRecommendations();

        boolean movie1InList = false;
        boolean movie2InList = false;
        boolean movie3InList = false;

        for (Movie movie: recommendations){
            if (movie.getMovieID() == 3890){
                movie1InList = true;
            }
            else if (movie.getMovieID() == 4414){
                movie2InList = true;
            }
            else if (movie.getMovieID() == 5045){
                movie3InList = true;
            }
        }

        assertTrue(movie1InList && movie2InList && movie3InList);


    }

    @Test
    void getRecommendations() {
//        is tested sufficiently in in loadRecommendations() and addRecommendations().
    }
}