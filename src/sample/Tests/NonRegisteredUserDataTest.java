import org.junit.jupiter.api.Test;
import sample.datamodel.NonRegisteredUser;
import sample.datamodel.NonRegisteredUserData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests the NonRegisteredUserData Class.
 */
class NonRegisteredUserDataTest {

    /**
     * tests whether instance returns correct type of object.
     */
    @Test
    void getInstance() {
        NonRegisteredUserData nonRegisteredUserData = NonRegisteredUserData.getInstance();
        assertEquals("sample.datamodel.NonRegisteredUserData", nonRegisteredUserData.getClass().getName());
    }

    /**
     * tests whether NonRegisteredUsers are correctly loaded from the file.
     */
    @Test
    void loadUsers() {
        NonRegisteredUserData nonRegisteredUserData = NonRegisteredUserData.getInstance();
        nonRegisteredUserData.loadUsers();
        List<NonRegisteredUser> testusers = nonRegisteredUserData.getNonRegisteredUsers();

        boolean isInList = false;
        for (NonRegisteredUser user: testusers){

            if (user.getUserName().equals("Angela Thompson")){
                isInList = true;
            }
        }
        assertTrue(isInList);

    }

    /**
     * tests whether user name that is not part of the NonRegistered Users is validated as available.
     */
    @Test
    void validateNameForRegistration_validUserName() {
        NonRegisteredUserData nonRegisteredUserData = NonRegisteredUserData.getInstance();
        nonRegisteredUserData.loadUsers();
        assertTrue(nonRegisteredUserData.validateNameForRegistration("Max Mustermann"));
    }

    /**
     * tests whether user name that is part of the NonRegistered Users is validated as unavailable.
     */
    @Test
    void validateNameForRegistration_invalidUserName() {
        NonRegisteredUserData nonRegisteredUserData = NonRegisteredUserData.getInstance();
        nonRegisteredUserData.loadUsers();
        assertFalse(nonRegisteredUserData.validateNameForRegistration("Angela Thompson"));
    }

    /**
     * tests whether getter returns the correct datatype.
     */
    @Test
    void getNonRegisteredUsers() {
        NonRegisteredUserData nonRegisteredUserData = NonRegisteredUserData.getInstance();
        nonRegisteredUserData.loadUsers();
        assertEquals("java.util.ArrayList", nonRegisteredUserData.getNonRegisteredUsers().getClass().getName());
        assertFalse(nonRegisteredUserData.getNonRegisteredUsers().isEmpty());
    }
}