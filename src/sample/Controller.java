package sample;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.datamodel.*;

public class Controller {

    @FXML
    private TableView<Movie> movieDisplay;

    @FXML
    private TextField filterInput;

    @FXML
    private Label currentUser;

    private NonRegisteredUserData nonRegisteredUserData;

    public void initialize(){

//        Display current Users username

        currentUser.setText(RegisteredUserData.getCurrentUserName());
//        currentUser.setText("Max Mustermann");


//        1. Load Data Organisation Hashmaps --> see connections between different data types. This is necessary to later load the data types as
//           they often include several of the other type
//        2. Load Directors, Actors and Users as they only need the key of the movies and not the actual name
//        3. Load the movies as they need all the explicit data (names of directors, actors etc.) and therefore must utilize the Hashmaps of the
//           DataOrganisation Class.

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

                String lowerCaseFilter = newValue.toLowerCase();

                if (movie.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches movie title
                }
                else if (movie.getDirectorNames().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches director name.
                }
                else if (movie.getActorNames().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (movie.getGenreNames().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false; // Does not match.
            });
        });

//        need to wrap Filtered list into Sorted list because filtered list cannot be sorted --> would loose this functionality
        SortedList<Movie> sortedMovies = new SortedList<>(movies);
        sortedMovies.comparatorProperty().bind(movieDisplay.comparatorProperty());
        movieDisplay.setItems(sortedMovies);
    }


}
