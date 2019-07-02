package sample.datamodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Movie class represents a move.
 * It contains all the information that is available in the movieproject.db file including the directors, actors and
 * genres of the movie. The fields utilize the Simple...Property classes which makes databinding in JavaFX convenient.
 */
public class Movie {
//    Class that describes a movie object. The data about directors/actors/ratings is also consolidated here.

    private SimpleIntegerProperty movieID = new SimpleIntegerProperty();
    private SimpleStringProperty title = new SimpleStringProperty();

    //    Attributes used for displaying and formatting the release date.
    private Date releaseDate = new Date();
    private SimpleDateFormat inputFormater = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat outputFormater = new SimpleDateFormat("yyyy.MM.dd");
    private SimpleStringProperty releaseDateString = new SimpleStringProperty();

    private List<String> genres = new ArrayList<>();
    private SimpleStringProperty genreNames = new SimpleStringProperty();

    //    Rating of the user who is currently logged in. This is displayed in the interactive mode
    private SimpleDoubleProperty currentUserRating = new SimpleDoubleProperty();
    private SimpleStringProperty currentUserRatingString = new SimpleStringProperty();

    private SimpleDoubleProperty imdbRating = new SimpleDoubleProperty();
    private SimpleIntegerProperty numbImdbRatings = new SimpleIntegerProperty();

    private SimpleStringProperty plotDescription = new SimpleStringProperty();

    private List<Director> directors = new ArrayList();
    private SimpleStringProperty directorNames = new SimpleStringProperty();

    private ObservableList<Actor> actors = FXCollections.observableArrayList();
    private SimpleStringProperty actorNames = new SimpleStringProperty();

    /**
     * This method adds a director to the movie object. Depending on whether it is the first director added or not the
     * directorNames property which displays the directors in the GUI has to be updated differently.
     * This method is called from the MovieData class which is responsible for loading all the movie objects.
     * @param director The director object that should be added to the movie object.
     */
    public void addDirector(Director director) {
        this.directors.add(director);

//        Puts director names into String property to display in Tableview
        if (this.directorNames.get() == null) {
//            If this is the first director who gets added to the movie object
            this.directorNames.set(director.getName());
        } else {
//            If there are already directors added to the movie object
            this.directorNames.set(directorNames.get() + ", " + director.getName());
        }
    }

    /**
     * This method adds an actor to the movie object. Depending on whether it is the first actor added or not the
     * actorNames property which displays the actors in the GUI has to be updated differently.
     * This method is called from the MovieData class which is responsible for loading all the movie objects.
     * @param actor The actor object that should be added to the movie object.
     */
    public void addActor(Actor actor) {
//        works in same way as addDirector method
        this.actors.add(actor);

//        Puts actor names into String property to display in Tableview
        if (this.actorNames.get() == null) {
            this.actorNames.set(actor.getName());
        } else {
            this.actorNames.set(actorNames.get() + ", " + actor.getName());
        }
    }

    /**
     * This method adds the genres to the movie object.
     * This method is called from the MovieData class which is responsible for loading all the movie objects.
     * @param genres A List that contains all the genres that can be attributed to the movie object.
     */
    public void addGenres(List<String> genres) {
//        works in same way as addDirector method
        this.genres.addAll(genres);

        for (String genre : genres) {
            if (this.genreNames.get() == null) {
                this.genreNames.set(genre);
            } else if (!this.genreNames.get().contains(genre)) {
                this.genreNames.set(this.genreNames.getValue() + ", " + genre);
            }
        }
    }

    public void setMovieID(int movieID) {
        this.movieID.set(movieID);
    }

    public int getMovieID() {
        return movieID.get();
    }

    public SimpleIntegerProperty movieIDProperty() {
        return movieID;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }


    public void setDirectorNames(String directors) {
        this.directorNames.set(directors);
    }

    public String getDirectorNames() {
        return directorNames.get();
    }

    public void setActorNames(String actors) {
        this.actorNames.set(actors);
    }

    public String getActorNames() {
        return actorNames.get();
    }

    public String getGenreNames() {
        return genreNames.get();
    }

    public List<String> getGenres() {
        return genres;
    }

    /**
     * Sets the plot description of the movie object. If no plot description is available in the movieproject.db file
     * it will set the description to n/a.
     * @param plotDescription A description of the plot of the movie.
     */
    public void setPlotDescription(String plotDescription) {

        if (plotDescription.equals("")) {
            plotDescription = "n/a";
        }

        this.plotDescription.set(plotDescription);
    }

    public String getPlotDescription() {
        return plotDescription.get();
    }

    public SimpleStringProperty plotDescriptionProperty() {
        return plotDescription;
    }


    /**
     * Sets the release date of the movie object. The inputFormatter is responsible for parsing the date from the
     * movieproject.db file, the outputFormatter decides in which format the date will be displayed in the GUI.
     * If the input String cannot be parsed it will set the release date to n/a.
     * @param releaseDateString The release date of the movie in String format.
     */
    public void setReleaseDate(String releaseDateString) {

        try {
            this.releaseDate = inputFormater.parse(releaseDateString);
            String formattedDate = outputFormater.format(releaseDate);
            this.releaseDateString.set(formattedDate);
        } catch (ParseException e) {
            this.releaseDate = null;
            this.releaseDateString.set("n/a");
        }
    }

//    public Date getReleaseDate() {
//        return releaseDate;
//    }

    public String getReleaseDateString() {
        return releaseDateString.get();
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating.set(imdbRating);
    }

    public double getImdbRating() {
        return imdbRating.get();
    }

    public SimpleDoubleProperty imdbRatingProperty() {
        return imdbRating;
    }

    public void setNumbImdbRatings(int numbImdbRatings) {
        this.numbImdbRatings.set(numbImdbRatings);
    }

    public int getNumbImdbRatings() {
        return numbImdbRatings.get();
    }

    public SimpleIntegerProperty numbImdbRatingsProperty() {
        return numbImdbRatings;
    }

    /**
     * Sets the rating that the user currently logged in has given the movie.
     * 1. Method is called when the program loads in interactive mode from the MovieData class in order to retrieve the
     * ratings that were saved for the user.
     * 2. Method is called when the user enters a new rating in the GUI in order to update the rating that is displayed.
     *
     * @param currentUserRating The rating that the current user has given the movie or a numerical code if there is no
     *                          information in the data or the user has entered invalid input.
     */
    public void setCurrentUserRating(double currentUserRating) {
//        this method does some validation based on what response code it receives.
//        -1: no information in data
//        -2: user has tried to enter a non-numerical value

        this.currentUserRating.set(currentUserRating);

        if (currentUserRating == -1) {
            this.currentUserRatingString.set("n/a");
        } else if (currentUserRating == -2) {
            if (currentUserRatingString.get().equals("Please enter a number")) {
                this.currentUserRatingString.set("YOU HAVE TO ENTER A NUMBER");
            } else {
                this.currentUserRatingString.set("Please enter a number");
            }
        } else {
            this.currentUserRatingString.set(String.valueOf(currentUserRating));
        }
    }

    public double getCurrentUserRating() {
        return currentUserRating.get();
    }

//    public SimpleDoubleProperty currentUserRatingProperty() {
//        System.out.println("currentUserRatingProperty invoked");
//        return currentUserRating;
//    }

//    public void setCurrentUserRatingString(String currentUserRatingString) {
//        this.currentUserRatingString.set(currentUserRatingString);
//    }

    public String getCurrentUserRatingString() {
        return currentUserRatingString.get();
    }

    public SimpleStringProperty currentUserRatingStringProperty() {
        return currentUserRatingString;
    }

    @Override
    public String toString() {
        return title.get();
    }
}
