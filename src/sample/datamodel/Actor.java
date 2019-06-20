package sample.datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Actor {

    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleIntegerProperty actorID = new SimpleIntegerProperty();
    private ObservableList<Movie> playedInMovies = FXCollections.observableArrayList();
    private List<Integer> playedInMoviesIDs = new ArrayList<>();

    public Actor(int id, String name) {
        this.actorID.set(id);
        this.name.set(name);
    }

    public void addMovieID(int movieID) {
        this.playedInMoviesIDs.add(movieID);
//        System.out.println("Added MovieID " + movieID + " for director " + this.name);
    }


    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getActorID() {
        return actorID.get();
    }

    public SimpleIntegerProperty actorIDProperty() {
        return actorID;
    }

    public ObservableList<Movie> getPlayedInMovies() {
        return playedInMovies;
    }

    public List<Integer> getPlayedInMoviesIDs() {
        return playedInMoviesIDs;
    }

    @Override
    public String toString() {
        return this.name.getValue();
    }
}
