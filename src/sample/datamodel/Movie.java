package sample.datamodel;

import javafx.beans.property.*;

import java.text.SimpleDateFormat;

public class Movie {

    private SimpleIntegerProperty movieID = new SimpleIntegerProperty();
    private SimpleStringProperty title = new SimpleStringProperty();
    private SimpleListProperty genres = new SimpleListProperty();
    private SimpleDateFormat releaseDate = new SimpleDateFormat();
    private SimpleStringProperty description = new SimpleStringProperty();
    private SimpleDoubleProperty imdbRating = new SimpleDoubleProperty();
    private SimpleDoubleProperty userRating = new SimpleDoubleProperty();
    private SimpleIntegerProperty numbImdbRatings = new SimpleIntegerProperty();
    private SimpleIntegerProperty numbUserRatings = new SimpleIntegerProperty();

    private SimpleDoubleProperty generalRating = new SimpleDoubleProperty();

    private SimpleListProperty<Director> directors = new SimpleListProperty<>();
    private SimpleListProperty<Actor> actors = new SimpleListProperty<>();


    public int getMovieID() {
        return movieID.get();
    }

    public SimpleIntegerProperty movieIDProperty() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID.set(movieID);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }
}
