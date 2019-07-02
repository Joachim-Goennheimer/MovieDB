package sample.datamodel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Singleton class that stores the data of a director into a Hashmap for later access.
 */
public class DirectorData {
//    Class that loads and organises the data of the directors.

    private static DirectorData instance = new DirectorData();

    private static final String MOVIE_FILE = "movieproject.db";

    private HashMap<Integer, Director> DirectorId_DirectorMap = new HashMap<>();

    /**
     * Private constructor because class implements the singleton pattern.
     */
    private DirectorData(){}

    /**
     * returns the single instance of the class.
     * @return Returns instance of DirectorData class.
     */
    public static DirectorData getInstance(){
        return instance;
    }

    /**
     * Method that loads all Director objects from the movieproject.db file and stores them in the DirectorID_DirectorMap.
     * The Map takes the directorID as a key and the corresponding Director object as value.
     */
    public void loadDirectors() {
        try (BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))) {
            String input;
            boolean loadDirectors = false;

            while ((input = inputReader.readLine()) != null) {

                if (loadDirectors) {

                    if (input.contains("New_Entity")) {
                        loadDirectors = false;
                    } else {

                        String[] inputData = input.split("\",\"");
                        int directorID = Integer.parseInt(inputData[0].replace("\"", ""));
                        Director director = new Director(directorID, inputData[1].replace("\"", "").trim());
                        DirectorId_DirectorMap.put(directorID, director);
                    }
                }
//                Checks for beginning of director data.
                else {
                    if (input.contains("New_Entity: \"director_id\",\"director_name\"")) {
                        loadDirectors = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void loadCorrespondingMovies(){
////        method that is never used. Written in the beginning because thought it might be useful for filtering movies
////        in static mode based on director.
//
//        try(BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))){
//
//            String input;
//            boolean loadCorrespondingMovies = false;
//
//            while ((input = inputReader.readLine()) != null){
//
//                if (loadCorrespondingMovies){
//
//                    if(input.contains("New_Entity")){
//                        loadCorrespondingMovies = false;
//                    }
//                    else {
//
//                        String[] inputData = input.split("\",\"");
//                        int directorID = Integer.parseInt(inputData[0].replace("\"", ""));
//                        int movieID = Integer.parseInt(inputData[1].replace("\"", ""));
//                        DirectorId_DirectorMap.get(directorID).addMovieID(movieID);
//                    }
//                }
//                else {
//                    if (input.contains("New_Entity: \"director_id\",\"movie_id\"")){
//                        loadCorrespondingMovies = true;
//                    }
//                }
//            }
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//    }

    /**
     * Method that returns an director object for a given directorID after validating whether the director exists.
     * @param directorID Takes the directorID of the director that should be returned as an argument.
     * @return Returns Director object.
     */
    public Director getDirectorByID(Integer directorID) {
//        method that returns the director object for a given directorID.
//        Called by MovieData class when loading the movies to set the directorsproperty of each movie.

        if (DirectorId_DirectorMap.containsKey(directorID)) {
            return DirectorId_DirectorMap.get(directorID);
        } else {
            return null;
        }
    }
}
