package sample.datamodel;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;

public class Actor {

    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleListProperty<Movie> directedMovies = new SimpleListProperty<>();

    public Actor(SimpleStringProperty name) {
        this.name = name;
    }
}
