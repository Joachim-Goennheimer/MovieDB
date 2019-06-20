package sample;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.datamodel.*;

public class Controller {

    @FXML
    private TableView<Movie> movieDisplay;

    @FXML
    private TextField filterInput;

    private UserData userData;

    public void initialize(){


//        1. Load Data Organisation Hashmaps --> see connections between different data types. This is necessary to later load the data types as
//           they often include several of the other type
//        2. Load Directors, Actors and Users as they only need the key of the movies and not the actual name
//        3. Load the movies as they need all the explicit data (names of directors, actors etc.) and therefore must utilize the Hashmaps of the
//           DataOrganisation Class.


        DataOrganisation.load_MovieID_DirectorID_Map();
        DataOrganisation.load_MovieID_ActorID_Map();


        DirectorData.loadDirectors();
        ActorData.loadActors();
        MovieData.loadMovies();

//        MovieData.loadDirectors();

//        gets moviedata and puts it into table


//        Transform Observable List into filtered List

        FilteredList<Movie> movies = new FilteredList<>(MovieData.getMovies(), p -> true);

        filterInput.textProperty().addListener((observable, oldValue, newValue) -> {
            movies.setPredicate(movie -> {
                // If filter text is empty, display all movies.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

//                Compare Movie title and Directorname
                String lowerCaseFilter = newValue.toLowerCase();

                if (movie.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (movie.getDirectorNames().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (movie.getActorNames().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        });

//        need to wrap Filtered list into Sorted list because filtered list cannot be sorted --> would loose this functionality
        SortedList<Movie> sortedmovies = new SortedList<>(movies);
        sortedmovies.comparatorProperty().bind(movieDisplay.comparatorProperty());
        movieDisplay.setItems(movies);
    }


}
