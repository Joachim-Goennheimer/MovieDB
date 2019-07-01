package sample.datamodel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DirectorData {
//    Class that loads and organises the data of the directors.

    private static final String MOVIE_FILE = "movieproject.db";

    private static HashMap<Integer, Director> DirectorId_DirectorMap = new HashMap<>();


    public static void loadDirectors() {
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

    public static Director getDirectorByID(Integer directorID) {
//        method that returns the director object for a given directorID.
//        Called by MovieData class when loading the movies to set the directorsproperty of each movie.

        if (DirectorId_DirectorMap.containsKey(directorID)) {
            return DirectorId_DirectorMap.get(directorID);
        } else {
            return null;
        }
    }
}
