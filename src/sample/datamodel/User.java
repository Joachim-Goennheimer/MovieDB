package sample.datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {

    private SimpleIntegerProperty userID = new SimpleIntegerProperty();
    private SimpleStringProperty userName = new SimpleStringProperty("");
    private SimpleMapProperty<Movie, Integer> ratings = new SimpleMapProperty<>();
    private SimpleListProperty<Movie> recommendations = new SimpleListProperty<>();

}
