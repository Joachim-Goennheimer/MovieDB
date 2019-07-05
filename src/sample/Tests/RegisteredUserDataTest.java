import org.junit.jupiter.api.Test;
import sample.InteractiveModeLoader;
import sample.datamodel.Movie;
import sample.datamodel.MovieData;
import sample.datamodel.RegisteredUser;
import sample.datamodel.RegisteredUserData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegisteredUserDataTest {

    /**
     * tests whether instance returns correct type of object.
     */
    @Test
    void getInstance() {
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        assertEquals("sample.datamodel.RegisteredUserData", registeredUserData.getClass().getName());
    }

    /**
     * tests whether addRating successfully adds the rating for a movie to the user that is currently logged in.
     */
    @Test
    void addRating() {
        InteractiveModeLoader.loadDataInteractiveMode();
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        if (registeredUserData.loginUser("testuser", "TestPasswort123").equals("Invalid Username or Password")){
            registeredUserData.registerNewUser("testuser", "TestPasswort123");
            registeredUserData.loginUser("testuser", "TestPasswort123");
        }

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

    /**
     * tests whether the login works for a user that is registered.
     */
    @Test
    void loginUser_validInput() {

        InteractiveModeLoader.loadDataInteractiveMode();
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
//        registeredUserData.removeUser("testuser");
        registeredUserData.registerNewUser("testuser2", "TestPasswort123");
        assertEquals("Login Successful", registeredUserData.loginUser("testuser2", "TestPasswort123"));
        registeredUserData.removeUser("testuser2");

    }

    /**
     * tests whether the login does not work for a user that is not yet registered.
     */
    @Test
    void loginUser_invalidInput() {
        InteractiveModeLoader.loadDataInteractiveMode();
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        assertEquals("Invalid Username or Password", registeredUserData.loginUser("notAUser", "NotAPassword"));

    }

    /**
     * tests whether the registration of a new user works if he enters a valid username and password.
     */
    @Test
    void registerNewUser_validInput() {
        InteractiveModeLoader.loadDataInteractiveMode();
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        registeredUserData.removeUser("testuser2");
        assertEquals("Successfully registered", registeredUserData.registerNewUser("testuser2", "TestPasswort123"));
        registeredUserData.removeUser("testuser2");
    }

    /**
     * tests whether the registration of a new user does not work if enters an invalid username or and invalid password.
     */
    @Test
    void registerNewUser_invalidInput() {
        InteractiveModeLoader.loadDataInteractiveMode();
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        registeredUserData.registerNewUser("testuser2", "TestPasswort456");
        assertEquals("Username already taken", registeredUserData.registerNewUser("testuser2", "TestPasswort123"));
        assertEquals("Your Password must have at least 7 letters", registeredUserData.registerNewUser("testuser3", "test"));
    }

    /**
     * tests whether RegisteredUsers are correctly loaded from the file.
     */
    @Test
    void loadRegisteredUsers() {
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        registeredUserData.loadRegisteredUsers();
        List<RegisteredUser> users = registeredUserData.getRegisteredUsersList();
//        Could also test that list is not empty. However, if no user registered yet test will fail even though method works correctly.
        assertEquals("java.util.ArrayList", users.getClass().getName());
    }

    /**
     * tests whether RegisteredUsers are correctly saved to the file by
     * 1. removing user
     * 2. register user
     * 3. saving user
     * 4. loading user
     * 5. Checking whether user is in RegisteredUserData.
     */
    @Test
    void saveRegisteredUsers() {
        RegisteredUserData registeredUserData = RegisteredUserData.getInstance();
        registeredUserData.loadRegisteredUsers();
        registeredUserData.removeUser("testuser5");
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

}