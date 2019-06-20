package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserData {

    private ObservableList<User> users;

    public UserData(){

        users = FXCollections.observableArrayList();
    }

    public void addUser(User user){

        if (!users.contains(user)){
            users.add(user);
        }

    }
}
