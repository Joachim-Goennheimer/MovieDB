package sample.datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * The Actor class represents an actor
 */
public class Actor {

    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleIntegerProperty actorID = new SimpleIntegerProperty();

//    attribute not used. Included in beginning because thought it might be useful for filtering movies in static mode. Maybe useful
//    later if program gets modified.
//    private ObservableList<Movie> playedInMovies = FXCollections.observableArrayList();
//    private List<Integer> playedInMoviesIDs = new ArrayList<>();

    /**
     * Constructs an Actor object
     * @param id The id the actor has in the movieproject.db file.
     * @param name The name of the actor.
     */
    public Actor(int id, String name) {
        this.actorID.set(id);
        this.name.set(name);
    }


    /**
     * @return Returns name of the actor.
     */
    public String getName() {
        return name.get();
    }

    /**
     * @return Returns ID of the actor.
     */
    public int getActorID() {
        return actorID.get();
    }

    @Override
    public String toString() {
        return this.name.getValue();
    }
}
