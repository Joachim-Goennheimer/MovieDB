package sample.datamodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Director {

    private SimpleIntegerProperty directorID = new SimpleIntegerProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleListProperty<Movie> directedMovies = new SimpleListProperty<>();
    private ObservableList<Integer> directedMoviesIDs = FXCollections.observableArrayList();

    public Director(int id, String name) {

        this.directorID.set(id);
        this.name.set(name);
    }

    @Override
    public String toString() {
        return this.name.getValue();
    }

    public int getDirectorID() {
        return directorID.get();
    }

    public SimpleIntegerProperty directorIDProperty() {
        return directorID;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }


    public ObservableList<Movie> getDirectedMovies() {
        return directedMovies.get();
    }

    public SimpleListProperty<Movie> directedMoviesProperty() {
        return directedMovies;
    }

    public void setDirectedMovies(ObservableList<Movie> directedMovies) {
        this.directedMovies.set(directedMovies);
    }

    public ObservableList<Integer> getDirectedMoviesIDs() {
        return directedMoviesIDs;
    }

    public ObservableList<Integer> directedMoviesIDsProperty() {
        return directedMoviesIDs;
    }

    public void addMovieID(int movieID) {
        this.directedMoviesIDs.add(movieID);
//        System.out.println("Added MovieID " + movieID + " for director " + this.name);
    }
}
