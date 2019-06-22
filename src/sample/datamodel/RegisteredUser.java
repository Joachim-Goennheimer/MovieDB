package sample.datamodel;

import java.io.Serializable;

public class RegisteredUser extends User implements Serializable {


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
