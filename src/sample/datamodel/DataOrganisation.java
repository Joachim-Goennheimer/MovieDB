package sample.datamodel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton class that holds information about the directors, actors and genres of each movie.
 * The information is stored in three Hashmaps that have movieIDs as keys and Lists of the corresponding
 * directors, actors and genres respectively.
 */
public class DataOrganisation {
//    Class that loads data into Hashmaps before the movie data is loaded.
//    This data is needed prior to loading movies because it is included into the movie objects.

    private static DataOrganisation instance = new DataOrganisation();

    private static final String MOVIE_FILE = "movieproject.db";

    private Map<Integer, List<Integer>> MovieID_DirectorID_Map = new HashMap<>();
    private Map<Integer, List<Integer>> MovieID_ActorID_Map = new HashMap<>();
    private Map<Integer, List<String>> MovieID_GenreMap = new HashMap<>();

    /**
     * Private constructor because class implements the singleton pattern.
     */
    private DataOrganisation(){}

    /**
     * returns the single instance of the class.
     * @return Returns instance of DataOrganisation class.
     */
    public static DataOrganisation getInstance(){
        return instance;
    }

    /**
     * resets the single instance of the DataOrganisation class.
     * This method is only used for testing purposes and is not called in the main program.
     */
    public static void reset(){
        instance = new DataOrganisation();
    }

    /**
     * Method that loads data into the MovieID_DirectorID_Map from the MOVIE_FILE file.
     * The Map maps all directors that have directed a particular movie.
     */
    public void load_MovieID_DirectorID_Map() {

        try (BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))) {
            String input;
            boolean loadData = false;

            while ((input = inputReader.readLine()) != null) {

                if (loadData) {

                    if (input.contains("New_Entity")) {
                        loadData = false;
                    } else {

                        String[] inputData = input.split("\",\"");
                        int directorID = Integer.parseInt(inputData[0].replace("\"", ""));
                        int movieID = Integer.parseInt(inputData[1].replace("\"", ""));

                        if (MovieID_DirectorID_Map.containsKey(movieID)) {
                            MovieID_DirectorID_Map.get(movieID).add(directorID);
                        } else {
                            List<Integer> directorIDs = new ArrayList<>();
                            directorIDs.add(directorID);
                            MovieID_DirectorID_Map.put(movieID, directorIDs);
                        }
                    }
                }
//                Checks for beginning of movie/director data.
                else {
                    if (input.contains("New_Entity: \"director_id\",\"movie_id\"")) {
                        loadData = true;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * Method that loads data into the MovieID_ActorID_Map from the MOVIE_FILE file.
     * The Map maps all actors that have played in a particular movie.
     */
    public void load_MovieID_ActorID_Map() {

        try (BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))) {
            String input;
            boolean loadData = false;

            while ((input = inputReader.readLine()) != null) {

                if (loadData) {

                    if (input.contains("New_Entity")) {
                        loadData = false;
                    } else {

                        String[] inputData = input.split("\",\"");
                        int actorID = Integer.parseInt(inputData[0].replace("\"", ""));
                        int movieID = Integer.parseInt(inputData[1].replace("\"", ""));

                        if (MovieID_ActorID_Map.containsKey(movieID)) {
                            MovieID_ActorID_Map.get(movieID).add(actorID);
                        } else {
                            List<Integer> actorIDs = new ArrayList<>();
                            actorIDs.add(actorID);
                            MovieID_ActorID_Map.put(movieID, actorIDs);
                        }
                    }
                } else {
                    if (input.contains("New_Entity: \"actor_id\",\"movie_id\"")) {
                        loadData = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that loads data into the MovieID_Genre_Map from the MOVIE_FILE file.
     * The Map maps all genres that are attributed to a certain movie.
     */
    public void load_MovieID_GenreMap() {

        try (BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))) {
            String input;
            boolean loadData = false;

            while ((input = inputReader.readLine()) != null) {

                if (loadData) {

                    if (input.contains("New_Entity")) {
                        loadData = false;
                    } else {

                        String[] inputData = input.split("\",\"");
                        int movieID = Integer.parseInt(inputData[0].replace("\"", ""));
                        String genre = inputData[3];

                        if (MovieID_GenreMap.containsKey(movieID)) {
                            MovieID_GenreMap.get(movieID).add(genre);
                        } else {
                            List<String> Genres = new ArrayList<>();
                            if (genre.contains("no genres")) {
                                genre = "n/a";
                            }
                            Genres.add(genre);
                            MovieID_GenreMap.put(movieID, Genres);
                        }
                    }
                } else {
                    if (input.contains("New_Entity: \"movie_id\",\"movie_title\"")) {
                        loadData = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return Returns a Map that maps the directors that have directed a movie to the corresponding movieID.
     */
    public Map<Integer, List<Integer>> getMovieID_DirectorID_Map() {
        return MovieID_DirectorID_Map;
    }

    /**
     *
     * @return Returns a Map that maps the actors that have played in a movie to the corresponding movieID.
     */
    public Map<Integer, List<Integer>> getMovieID_ActorID_Map() {
        return MovieID_ActorID_Map;
    }

    /**
     *
     * @return Returns a Map that maps the genres that are attributed to a movie with a certain movieID .
     */
    public Map<Integer, List<String>> getMovieID_GenreMap() {
        return MovieID_GenreMap;
    }
}
