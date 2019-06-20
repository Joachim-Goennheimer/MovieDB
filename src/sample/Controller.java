package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import sample.datamodel.Movie;
import sample.datamodel.MovieData;
import sample.datamodel.UserData;

public class Controller {

    @FXML
    private TableView<Movie> movieDisplay;

    private MovieData moviedata;
    private UserData userData;

    public void initialize(){


//        1. Load Data Organisation Hashmaps --> see connections between different data types. This is necessary to later load the data types as
//           they often include several of the other type
//        2. Load Directors, Actors and Users as they only need the key of the movies and not the actual name
//        3. Load the movies as they need all the explicit data (names of directors, actors etc.) and therefore must utilize the Hashmaps of the
//           DataOrganisationClass.

        moviedata = new MovieData();
        moviedata.loadMovies();
        moviedata.loadDirectors();

//        gets moviedata and puts it into table
        movieDisplay.setItems(moviedata.getMovies());
    }
}
