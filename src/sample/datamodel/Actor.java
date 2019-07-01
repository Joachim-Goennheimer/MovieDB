package sample.datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Actor {
//    Class which describes an actor

    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleIntegerProperty actorID = new SimpleIntegerProperty();

//    attribute not used. Included in beginning because thought it might be useful for filtering movies in static mode. Maybe useful
//    later if program gets modified.
//    private ObservableList<Movie> playedInMovies = FXCollections.observableArrayList();
//    private List<Integer> playedInMoviesIDs = new ArrayList<>();

    public Actor(int id, String name) {
        this.actorID.set(id);
        this.name.set(name);
    }


    public String getName() {
        return name.get();
    }

    public int getActorID() {
        return actorID.get();
    }

    @Override
    public String toString() {
        return this.name.getValue();
    }
}
