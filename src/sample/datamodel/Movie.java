package sample.datamodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Movie {

    private SimpleIntegerProperty movieID = new SimpleIntegerProperty();
    private SimpleStringProperty title = new SimpleStringProperty();
    private SimpleStringProperty plotDescription = new SimpleStringProperty();
    private List<String> genres = new ArrayList<>();
    private SimpleStringProperty genreNames = new SimpleStringProperty();

    private Date releaseDate = new Date();
    private SimpleDateFormat inputFormater = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat outputFormater = new SimpleDateFormat("yyyy.MM.dd");
    private SimpleStringProperty releaseDateString = new SimpleStringProperty();

    private SimpleDoubleProperty imdbRating = new SimpleDoubleProperty();
    private SimpleDoubleProperty currentUserRating = new SimpleDoubleProperty();
    private SimpleStringProperty currentUserRatingString = new SimpleStringProperty();
    private SimpleIntegerProperty numbImdbRatings = new SimpleIntegerProperty();
    private SimpleIntegerProperty numbUserRatings = new SimpleIntegerProperty();

    private SimpleDoubleProperty generalRating = new SimpleDoubleProperty();

//    might not be needed later on
    private List<Director> directors = new ArrayList();
    private SimpleStringProperty directorNames = new SimpleStringProperty();
//    might not be needed later on
    private ObservableList<Actor> actors = FXCollections.observableArrayList();
    private SimpleStringProperty actorNames = new SimpleStringProperty();





    public void addDirector(Director director){
        this.directors.add(director);

//        Puts director names into String property to display in Tableview

        if (this.directorNames.get() == null){
            this.directorNames.set(director.getName());
        }
        else {
            this.directorNames.set(directorNames.get() + ", " + director.getName());
        }

    }

    public void addActor(Actor actor){
        this.actors.add(actor);

//        Puts actor names into String property to display in Tableview

        if (this.actorNames.get() == null){
            this.actorNames.set(actor.getName());
        }
        else {
            this.actorNames.set(actorNames.get() + ", " + actor.getName());
        }

    }

    public void addGenres(List<String> genres){

        this.genres.addAll(genres);

        for (String genre: genres){
            if (this.genreNames.get() == null){
                this.genreNames.set(genre);
            }
            else if (!this.genreNames.get().contains(genre)){
                this.genreNames.set(this.genreNames.getValue() + ", " + genre);
            }
        }


    }




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

    public void setDirectorNames(String directors){
        this.directorNames.set(directors);
    }

    public void setActorNames(String actors){
        this.actorNames.set(actors);
    }

    public String getDirectorNames(){
        return directorNames.get();
    }

    public String getActorNames(){
        return actorNames.get();
    }

    public String getGenreNames(){
        return genreNames.get();
    }

    public List<String> getGenres(){ return genres; }

    public String getPlotDescription() {
        return plotDescription.get();
    }

    public SimpleStringProperty plotDescriptionProperty() {
        return plotDescription;
    }

    public void setPlotDescription(String plotDescription) {

        if (plotDescription.equals("")){
            plotDescription = "n/a";
        }

        this.plotDescription.set(plotDescription);
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getReleaseDateString(){
        return releaseDateString.get();
    }

    public void setReleaseDate(String releaseDateString) {

        try {
            this.releaseDate = inputFormater.parse(releaseDateString);
            String formattedDate = outputFormater.format(releaseDate);
            this.releaseDateString.set(formattedDate);
        } catch (ParseException e){
            this.releaseDate = null;
            this.releaseDateString.set("n/a");
        }

    }

    public double getImdbRating() {
        return imdbRating.get();
    }

    public SimpleDoubleProperty imdbRatingProperty() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating.set(imdbRating);
    }



    public int getNumbImdbRatings() {
        return numbImdbRatings.get();
    }

    public SimpleIntegerProperty numbImdbRatingsProperty() {
        return numbImdbRatings;
    }

    public void setNumbImdbRatings(int numbImdbRatings) {
        this.numbImdbRatings.set(numbImdbRatings);
    }

    public double getCurrentUserRating() {
        return currentUserRating.get();
    }

//    public SimpleDoubleProperty currentUserRatingProperty() {
//        System.out.println("currentUserRatingProperty invoked");
//        return currentUserRating;
//    }

    public void setCurrentUserRating(double currentUserRating) {
//        this method does some validation based on what response code it receives.
//        -1: no information in data
//        -2: user has tried to enter a non-numerical value

        this.currentUserRating.set(currentUserRating);

        if (currentUserRating == -1){
            this.currentUserRatingString.set("n/a");
        }
        else if (currentUserRating == -2){
            if (currentUserRatingString.get().equals("Please enter a number")){
                this.currentUserRatingString.set("YOU HAVE TO ENTER A NUMBER");
            }
            else {
                this.currentUserRatingString.set("Please enter a number");

            }
        }
        else {
            this.currentUserRatingString.set(String.valueOf(currentUserRating));

        }

    }

    public String getCurrentUserRatingString() {
        return currentUserRatingString.get();
    }

    public SimpleStringProperty currentUserRatingStringProperty() {
//        System.out.println("currentUserRatingStringProperty invoked");
        return currentUserRatingString;
    }

    public void setCurrentUserRatingString(String currentUserRatingString) {
//        System.out.println("setCurrentUserRatingString invoked");

        this.currentUserRatingString.set(currentUserRatingString);
    }

    public double getGeneralRating() {
        return generalRating.get();
    }

    public SimpleDoubleProperty generalRatingProperty() {
        return generalRating;
    }

    public void setGeneralRating(double generalRating) {
        this.generalRating.set(generalRating);
    }

    @Override
    public String toString() {
        return title.get();
    }
}
