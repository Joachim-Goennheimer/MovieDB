package sample.datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * The Director class represents a director.
 */

public class Director {

    private SimpleIntegerProperty directorID = new SimpleIntegerProperty();
    private SimpleStringProperty name = new SimpleStringProperty();

//    attribute not used. Included in beginning because thought it might be useful for filtering movies in static mode. Maybe useful
//    later if program gets modified.
//    private SimpleListProperty<Movie> directedMovies = new SimpleListProperty<>();
//    private List<Integer> directedMoviesIDs = new ArrayList<>();

    /**
     * Constructs a director object.
     * @param id The ID a director has in the movieproject.db file.
     * @param name The name of the director.
     */
    public Director(int id, String name) {
        this.directorID.set(id);
        this.name.set(name);
    }


    /**
     * @return Returns name of the director
     */
    public String getName() {
        return name.get();
    }


    @Override
    public String toString() {
        return this.name.getValue();
    }
}
