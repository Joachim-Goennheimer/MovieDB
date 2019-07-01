package sample.datamodel;

import java.io.Serializable;

public class RegisteredUser extends User implements Serializable {
//    Class that describes a Registered User. A registered user has filled in the registration form, can log in
//    and can see as well as alter his movie ratings in the interactive mode.

    private String password;


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

    public String getPassword() {
        return password;
    }
}
