import org.junit.jupiter.api.Test;
import sample.datamodel.NonRegisteredUser;
import sample.datamodel.NonRegisteredUserData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NonRegisteredUserDataTest {

    @Test
    void getInstance() {
        NonRegisteredUserData nonRegisteredUserData = NonRegisteredUserData.getInstance();
        assertEquals("sample.datamodel.NonRegisteredUserData", nonRegisteredUserData.getClass().getName());
    }

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

    @Test
    void validateNameForRegistration_validUserName() {
        NonRegisteredUserData nonRegisteredUserData = NonRegisteredUserData.getInstance();
        nonRegisteredUserData.loadUsers();
        assertTrue(nonRegisteredUserData.validateNameForRegistration("Max Mustermann"));
    }

    @Test
    void validateNameForRegistration_invalidUserName() {
        NonRegisteredUserData nonRegisteredUserData = NonRegisteredUserData.getInstance();
        nonRegisteredUserData.loadUsers();
        assertFalse(nonRegisteredUserData.validateNameForRegistration("Angela Thompson"));
    }

    @Test
    void getNonRegisteredUsers() {
        NonRegisteredUserData nonRegisteredUserData = NonRegisteredUserData.getInstance();
        nonRegisteredUserData.loadUsers();
        assertEquals("java.util.ArrayList", nonRegisteredUserData.getNonRegisteredUsers().getClass().getName());
        assertFalse(nonRegisteredUserData.getNonRegisteredUsers().isEmpty());
    }
}