package sample.datamodel;

import java.io.Serializable;

/**
 * The RegisteredUser class represents a user that is registered. A registered user has filled in the registration form,
 * can log in and can see as well as alter his movie ratings in the interactive mode.
 */
public class RegisteredUser extends User implements Serializable {

    private String password;

    /**
     * Method that is called when a user tries to log in in order to verify whether he entered the correct password.
     * @param enteredPassword The password the user entered in the log in form.
     * @return Returns true if he entered the correct password - Returns false if he did not enter the correct password.
     */
    public boolean checkPassword(String enteredPassword){
        boolean passwordIsCorrect = false;

        if (enteredPassword.equals(this.password)){
            passwordIsCorrect = true;
        }
        return passwordIsCorrect;

    }



    public void setPassword(String password) {
        this.password = password;
    }

}
