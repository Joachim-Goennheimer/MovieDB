package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.datamodel.MovieData;
import sample.datamodel.RegisteredUserData;

import java.io.IOException;


public class LoginController {

    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;

    @FXML
    private Label loginResponseLabel;

    @FXML
    private TextField registerUserName;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private PasswordField registerPasswordCheck;

    @FXML
    private Label registerUserLabel;





    public void login(Event event){

        String loginResponse = RegisteredUserData.getInstance().loginUser(userName.getText(), password.getText());

        if (loginResponse.equals("Login Successful")){
            loadMainWindow(event);
        }
        else if (loginResponse.equals("Invalid Username or Password")){
            loginResponseLabel.setText(loginResponse);
        }
    }

    public void registerUser(){

        if (!registerPassword.getText().equals(registerPasswordCheck.getText())){
            System.out.println("Password entered: " + registerPassword.getText());
            System.out.println("Password verified: " + registerPasswordCheck.getText());
            registerUserLabel.setText("Password and Password Check did not match");
        }
        else {
            String registerResponse = RegisteredUserData.getInstance().registerNewUser(registerUserName.getText(), registerPassword.getText());
            registerUserLabel.setText(registerResponse);
        }

    }




    public void loadMainWindow(Event event){

//        loading movies here because need logged in user first in order to retrieve user ratings

        MovieData.getInstance().loadMovies(RegisteredUserData.getInstance().getCurrentlyLoggedIn());

        Stage primaryStage = new Stage();

        try {
//            closes Login Window
            ((Node)(event.getSource())).getScene().getWindow().hide();
//            Opens Main Window
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            primaryStage.setTitle("Movie DB");
            primaryStage.setScene(new Scene(root, 1600, 800));
            primaryStage.show();

        } catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}
