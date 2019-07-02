import org.junit.jupiter.api.Test;
import sample.InteractiveModeLoader;
import sample.datamodel.Movie;
import sample.datamodel.MovieData;
import sample.datamodel.RegisteredUser;
import sample.datamodel.RegisteredUserData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegisteredUserDataTest {

    @Test
    void getInstance() {
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        assertEquals("sample.datamodel.RegisteredUserData", registeredUserData.getClass().getName());
    }

    @Test
    void addRating() {
        InteractiveModeLoader.loadDataInteractiveMode();
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        registeredUserData.loginUser("testuser", "TestPasswort123");
        System.out.println(registeredUserData.getCurrentlyLoggedIn());
        registeredUserData.addRating(3052, 5.0);
        MovieData movieData = MovieData.getInstance();
        movieData.loadMovies(registeredUserData.getCurrentlyLoggedIn());
        List<Movie> movies = movieData.getMovies();
        boolean checked = false;
        for (Movie movie: movies){
            if (movie.getMovieID() == 3052){
                checked = true;
                assertEquals(5.0, movie.getCurrentUserRating());
            }
        }
        assertTrue(checked);

    }

    @Test
    void loginUser_validInput() {

        InteractiveModeLoader.loadDataInteractiveMode();
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        assertEquals("Login Successful", registeredUserData.loginUser("testuser", "TestPasswort123"));

    }

    @Test
    void loginUser_invalidInput() {
        InteractiveModeLoader.loadDataInteractiveMode();
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        assertEquals("Invalid Username or Password", registeredUserData.loginUser("notAUser", "NotAPassword"));

    }

    @Test
    void registerNewUser_validInput() {
        InteractiveModeLoader.loadDataInteractiveMode();
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        registeredUserData.deleteUser("testuser2");
        assertEquals("Successfully registered", registeredUserData.registerNewUser("testuser2", "TestPasswort456"));
        registeredUserData.deleteUser("testuser2");
    }

    @Test
    void registerNewUser_invalidInput() {
        InteractiveModeLoader.loadDataInteractiveMode();
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        assertEquals("Username already taken", registeredUserData.registerNewUser("testuser", "TestPasswort456"));
        assertEquals("Your Password must have at least 7 letters", registeredUserData.registerNewUser("testuser3", "test"));
    }

    @Test
    void loadRegisteredUsers() {
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        registeredUserData.loadRegisteredUsers();
        List<RegisteredUser> users = registeredUserData.getRegisteredUsersList();
//        Could also test that list is not empty. However, if no user registered yet test will fail even though method works correctly.
        assertEquals("java.util.ArrayList", users.getClass().getName());
    }

    @Test
    void saveRegisteredUsers() {
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        registeredUserData.loadRegisteredUsers();
        registeredUserData.registerNewUser("testuser5", "TestPasswort12345");
        registeredUserData.saveRegisteredUsers();
        registeredUserData.loadRegisteredUsers();
        List<RegisteredUser> users = registeredUserData.getRegisteredUsersList();
        boolean isInList = false;
        for (RegisteredUser user: users){
            if (user.getUserName().equals("testuser5")){
                isInList = true;
            }
        }
        assertTrue(isInList);

    }

    @Test
    void getCurrentlyLoggedIn() {
    }

    @Test
    void getCurrentUserName() {
    }

    @Test
    void getRegisteredUsersList() {
    }
}