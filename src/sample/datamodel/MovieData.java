package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Singleton class stores the data of all the movies in an observable list. This class is used to display the movies
 * to the user in the GUI. It provides 2 methods for loading the movies into the program.
 */
public class MovieData {
//    Class that organises the data of all the movies. It provides 2 methods for loading the movies into the program.
//    1. loadMovies method is used for static mode where no user is logged in and therefore the ratings of an individual user
//    don't have to be included into the data
//    2. loadMovies method is used for interactive mode where ratings of the individual user that is logged in must be included
//    into the moviedata in order to show the saved ratings.
//    Apart from that both methods are identical.

    private static MovieData instance = new MovieData();

    private static final String MOVIE_FILE = "movieproject.db";

    //    This Observable list is the main component for the moviedata. It will be displayed in the JavaFx GUI.
    private ObservableList<Movie> movies = FXCollections.observableArrayList();
    private HashMap<Integer, Double> imdbRatings = new HashMap<>();

    /**
     * Private constructor because class implements the singleton pattern.
     */
    private MovieData(){

    }

    /**
     * returns the single instance of the class.
     * @return Returns instance of MovieData class.
     */
    public static MovieData getInstance(){
        return instance;
    }

    /**
     * resets the single instance of the DataOrganisation class.
     * This method is only used for testing purposes and is not called in the main program.
     */
    public static void reset(){
        instance = new MovieData();
    }


    /**
     * Method that is used to load the data of all the movies into the program. Also loads the directors, actors and genres
     * by using the corresponding Hashmaps from the DataOrganisation class that have been loaded before.
     * This method is only used in the static mode where no user is logged in and therefore the ratings of an individual user
     * don't have to be included into the data
     */
    public void loadMovies() {
//        method that loads the movieData from the db-file in static mode.

//        Getting data about directors, actors and genres first because the information has to be included for every movie.
        DataOrganisation dataOrganisation = DataOrganisation.getInstance();
        Map<Integer, List<Integer>> MovieID_DirectorID_Map = dataOrganisation.getMovieID_DirectorID_Map();
        Map<Integer, List<Integer>> MovieID_ActorID_Map = dataOrganisation.getMovieID_ActorID_Map();
        Map<Integer, List<String>> MovieID_GenreIDMap = dataOrganisation.getMovieID_GenreMap();
//        used to verify that movies are only included once into the List.
        Set<Integer> verifyDuplicateSet = new HashSet<>();

        try (BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))) {
            String input;
            boolean loadMovies = false;

            while ((input = inputReader.readLine()) != null) {

                if (loadMovies) {

                    if (input.contains("New_Entity")) {
                        loadMovies = false;
                    } else {

                        String[] inputData = input.split("\",\"");
                        int movieID = Integer.parseInt(inputData[0].replace("\"", ""));

//                        checks whether movie is already loaded.
                        if (!verifyDuplicateSet.contains(movieID)) {

//                            Setting attributes of Movie object.
                            Movie movie = new Movie();
                            movie.setMovieID(movieID);
                            movie.setTitle(inputData[1]);
                            movie.setPlotDescription(inputData[2]);
                            movie.setReleaseDate(inputData[4]);

//                            If no data is given for the number of Imdb Rating sets it to 0
                            try {
                                movie.setNumbImdbRatings(Integer.parseInt(inputData[5]));
                            } catch (NumberFormatException e) {
                                movie.setNumbImdbRatings(0);
                            }

                            try {
                                double rating = Double.parseDouble(inputData[6].replace("\"", ""));
                                movie.setImdbRating(rating);
                            } catch (NumberFormatException e) {
                                movie.setImdbRating(0.0);
                            }

//                        getting directors from previously loaded directorMap
                            try {
                                for (Integer directorID : MovieID_DirectorID_Map.get(movieID)) {
                                    Director director = DirectorData.getInstance().getDirectorByID(directorID);
                                    movie.addDirector(director);
                                }
                            } catch (Exception e) {
                                movie.setDirectorNames("n/a");
                            }

//                        getting actors from previously loaded actorMap
                            try {
                                for (Integer actorID : MovieID_ActorID_Map.get(movieID)) {
                                    Actor actor = ActorData.getInstance().getActorByID(actorID);
                                    movie.addActor(actor);
                                }
                            } catch (Exception e) {
                                movie.setActorNames("n/a");
                            }

//                        getting genres from previously loaded genreMap
                            try {
                                List<String> genres = MovieID_GenreIDMap.get(movieID);
                                movie.addGenres(genres);
                            } catch (Exception e) {
                                System.out.println("No error should happen here");
                            }

//                            Adds movie to verifyDuplicateSet to ensure it is not added again.
                            verifyDuplicateSet.add(movieID);
//                            Adds movie to the Observable list that contains all the movie data.
                            movies.add(movie);
                        }
                    }
                }
//                Checks for beginning of movie data.
                else {
                    if (input.contains("New_Entity: \"movie_id\",\"movie_title\"")) {
                        loadMovies = true;
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that is used to load the data of all the movies into the program. Also loads the directors, actors and genres
     * by using the corresponding Hashmaps from the DataOrganisation class that have been loaded before.
     * This method is only used in the interactive mode where a user is logged in and therefore his user ratings have
     * to be included into the movie objects in order to display them in the GUI.
     * The method is mostly identical with the loadMovies() method. The only difference is that the ratings of the user
     * who is currently logged in are included.
     */
    public void loadMovies(RegisteredUser currentUser) {
//        method that loads the movieData from the db-file in interactive mode.
//        everything the same with loadmovies method above except for commented section.

        DataOrganisation dataOrganisation = DataOrganisation.getInstance();
        Map<Integer, List<Integer>> MovieID_DirectorID_Map = dataOrganisation.getMovieID_DirectorID_Map();
        Map<Integer, List<Integer>> MovieID_ActorID_Map = dataOrganisation.getMovieID_ActorID_Map();
        Map<Integer, List<String>> MovieID_GenreIDMap = dataOrganisation.getMovieID_GenreMap();
        Set<Integer> verifyDuplicateSet = new HashSet<>();

        try (BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))) {
            String input;
            boolean loadMovies = false;

            while ((input = inputReader.readLine()) != null) {

                if (loadMovies) {

                    if (input.contains("New_Entity")) {
                        loadMovies = false;
                    } else {

                        String[] inputData = input.split("\",\"");
                        int movieID = Integer.parseInt(inputData[0].replace("\"", ""));
                        if (!verifyDuplicateSet.contains(movieID)) {

                            Movie movie = new Movie();
                            movie.setMovieID(movieID);
                            movie.setTitle(inputData[1]);
                            movie.setPlotDescription(inputData[2]);
                            movie.setReleaseDate(inputData[4]);

//                            Only difference to loadMovies in static mode. Rating of current user is included into moviedata.
                            double currentUserRating = currentUser.getRating(movieID);
                            movie.setCurrentUserRating(currentUserRating);

                            try {
                                movie.setNumbImdbRatings(Integer.parseInt(inputData[5]));

                            } catch (NumberFormatException e) {
                                movie.setNumbImdbRatings(0);
                            }

                            try {
                                double rating = Double.parseDouble(inputData[6].replace("\"", ""));
                                movie.setImdbRating(rating);
                                imdbRatings.put(movieID, rating);

                            } catch (NumberFormatException e) {
                                movie.setImdbRating(0.0);
                            }

                            try {
                                for (Integer directorID : MovieID_DirectorID_Map.get(movieID)) {
                                    Director director = DirectorData.getInstance().getDirectorByID(directorID);
                                    movie.addDirector(director);
                                }
                            } catch (Exception e) {
                                movie.setDirectorNames("n/a");
                            }

                            try {
                                for (Integer actorID : MovieID_ActorID_Map.get(movieID)) {
                                    Actor actor = ActorData.getInstance().getActorByID(actorID);
                                    movie.addActor(actor);
                                }

                            } catch (Exception e) {
                                movie.setActorNames("n/a");
                            }

                            try {
                                List<String> genres = MovieID_GenreIDMap.get(movieID);
                                movie.addGenres(genres);
                            } catch (Exception e) {
                                System.out.println("No error should happen here");
                            }

                            verifyDuplicateSet.add(movieID);
                            movies.add(movie);
                        }
                    }

                } else {
                    if (input.contains("New_Entity: \"movie_id\",\"movie_title\"")) {
                        loadMovies = true;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Returns a List which includes the data of all the movies.
     */
    public ObservableList<Movie> getMovies() {
        return movies;
    }

    /**
     * Method that is used for the recommendations.
     * Gets called by RecommendationHandler.
     * @param movieIDs The movieIDs for which the movieobjects should be returned.
     * @return Returns all the movie with the corresponding movieIDs in an observable list to display them to the user.
     */
    public ObservableList<Movie> getMoviesByID(Set<Integer> movieIDs) {

        ObservableList<Movie> requestedMovies = FXCollections.observableArrayList();
        for (Movie movie : movies) {
            if (movieIDs.contains(movie.getMovieID())) {
                requestedMovies.add(movie);
            }
        }
        return requestedMovies;
    }

    /**
     * Method that is used for the recommendations. Used in RecommendationHandler to incorporate the Imdb ratings
     * of a movie into the calculation of the general rating that decides about recommendations.
     * @return Returns all ImdbRatings mapped to the corresponding movies.
     */
    public HashMap<Integer, Double> getImdbRatings() {

        return imdbRatings;
    }
}
