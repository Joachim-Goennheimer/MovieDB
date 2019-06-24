package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MovieData {

    private static final String MOVIE_FILE = "movieproject.db";

    private static ObservableList<Movie> movies = FXCollections.observableArrayList();
    private static HashMap<Integer, Double> imdbRatings = new HashMap<>();


    public static void loadMovies(){
//        method that loads the movieData from the db-file

        Map<Integer, List<Integer>> MovieID_DirectorID_Map = DataOrganisation.getMovieID_DirectorID_Map();
        Map<Integer, List<Integer>> MovieID_ActorID_Map = DataOrganisation.getMovieID_ActorID_Map();
        Map<Integer, List<String>> MovieID_GenreIDMap = DataOrganisation.getMovieID_GenreMap();
        Set<Integer> verifyDuplicateSet = new HashSet<>();

        try(BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))){
            String input;
            boolean loadMovies = false;

            while ((input = inputReader.readLine()) != null){

                if (loadMovies){

                    if (input.contains("New_Entity")){
                        loadMovies = false;
                    }
                    else {

                        String[] inputData = input.split("\",\"");

                        int movieID = Integer.parseInt(inputData[0].replace("\"", ""));

//                        checks whether movie is already loaded.
                        if (!verifyDuplicateSet.contains(movieID)) {

                            Movie movie = new Movie();
                            movie.setMovieID(movieID);
                            movie.setTitle(inputData[1]);
                            movie.setPlotDescription(inputData[2]);
                            movie.setReleaseDate(inputData[4]);

                            try {

                                movie.setNumbImdbRatings(Integer.parseInt(inputData[5]));

                            } catch (NumberFormatException e){
                                movie.setNumbImdbRatings(0);
                            }

                            try {

                                double rating = Double.parseDouble(inputData[6].replace("\"", ""));
                                movie.setImdbRating(rating);

                                imdbRatings.put(movieID, rating);

                            } catch (NumberFormatException e){
                                movie.setImdbRating(0.0);
                            }


//                        getting directors

                            try {
                                for (Integer directorID : MovieID_DirectorID_Map.get(movieID)) {

                                    Director director = DirectorData.getDirectorByID(directorID);
                                    movie.addDirector(director);
                                }

                            } catch (Exception e) {
                                movie.setDirectorNames("n/a");
//                            System.out.println("No director for: " + movie.getTitle());

                            }

//                        getting actors
                            try {
                                for (Integer actorID : MovieID_ActorID_Map.get(movieID)) {

                                    Actor actor = ActorData.getActorByID(actorID);
                                    movie.addActor(actor);
                                }

                            } catch (Exception e) {
                                movie.setActorNames("n/a");
//                            System.out.println("No actor for: " + movie.getTitle());

                            }

//                        getting genres

                            try {

                                List<String> genres = MovieID_GenreIDMap.get(movieID);
                                movie.addGenres(genres);


                            } catch (Exception e) {
                                System.out.println("No error should happen here");
//                            System.out.println("No actor for: " + movie.getTitle());

                            }

                            verifyDuplicateSet.add(movieID);
                            movies.add(movie);
//                        System.out.println(movie.getReleaseDate().toLocalizedPattern());

                        }
                    }

                }
                else {
                    if (input.contains("New_Entity: \"movie_id\",\"movie_title\"")){
//                        System.out.println(input);
                        loadMovies = true;
                    }

                }


            }

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

//    Overloading loadMovies method for Interactive Mode in order to include current Users ratings
    public static void loadMovies(RegisteredUser currentUser){
//        method that loads the movieData from the db-file

        Map<Integer, List<Integer>> MovieID_DirectorID_Map = DataOrganisation.getMovieID_DirectorID_Map();
        Map<Integer, List<Integer>> MovieID_ActorID_Map = DataOrganisation.getMovieID_ActorID_Map();
        Map<Integer, List<String>> MovieID_GenreIDMap = DataOrganisation.getMovieID_GenreMap();
        Set<Integer> verifyDuplicateSet = new HashSet<>();

        try(BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))){
            String input;
            boolean loadMovies = false;

            while ((input = inputReader.readLine()) != null){

                if (loadMovies){

                    if (input.contains("New_Entity")){
                        loadMovies = false;
                    }
                    else {

                        String[] inputData = input.split("\",\"");

                        int movieID = Integer.parseInt(inputData[0].replace("\"", ""));

//                        checks whether movie is already loaded.
                        if (!verifyDuplicateSet.contains(movieID)) {

                            Movie movie = new Movie();
                            movie.setMovieID(movieID);
                            movie.setTitle(inputData[1]);
                            movie.setPlotDescription(inputData[2]);
                            movie.setReleaseDate(inputData[4]);

                            double currentUserRating = currentUser.getRating(movieID);
                            movie.setCurrentUserRating(currentUserRating);

                            try {

                                movie.setNumbImdbRatings(Integer.parseInt(inputData[5]));

                            } catch (NumberFormatException e){
                                movie.setNumbImdbRatings(0);
                            }

                            try {

                                double rating = Double.parseDouble(inputData[6].replace("\"", ""));
                                movie.setImdbRating(rating);

                                imdbRatings.put(movieID, rating);

                            } catch (NumberFormatException e){
                                movie.setImdbRating(0.0);
                            }


//                        getting directors

                            try {
                                for (Integer directorID : MovieID_DirectorID_Map.get(movieID)) {

                                    Director director = DirectorData.getDirectorByID(directorID);
                                    movie.addDirector(director);
                                }

                            } catch (Exception e) {
                                movie.setDirectorNames("n/a");
//                            System.out.println("No director for: " + movie.getTitle());

                            }

//                        getting actors
                            try {
                                for (Integer actorID : MovieID_ActorID_Map.get(movieID)) {

                                    Actor actor = ActorData.getActorByID(actorID);
                                    movie.addActor(actor);
                                }

                            } catch (Exception e) {
                                movie.setActorNames("n/a");
//                            System.out.println("No actor for: " + movie.getTitle());

                            }

//                        getting genres

                            try {

                                List<String> genres = MovieID_GenreIDMap.get(movieID);
                                movie.addGenres(genres);


                            } catch (Exception e) {
                                System.out.println("No error should happen here");
//                            System.out.println("No actor for: " + movie.getTitle());

                            }

                            verifyDuplicateSet.add(movieID);
                            movies.add(movie);
//                        System.out.println(movie.getReleaseDate().toLocalizedPattern());

                        }
                    }

                }
                else {
                    if (input.contains("New_Entity: \"movie_id\",\"movie_title\"")){
//                        System.out.println(input);
                        loadMovies = true;
                    }

                }


            }

        } catch (IOException e) {
            e.printStackTrace();

        }

    }


    public void save() throws IOException{

        try(FileWriter localFile = new FileWriter("testData2.txt")){
            for (Movie movie: movies){
                localFile.write("Movie Title: " + movie.getTitle() + " Movie ID: " + movie.getMovieID() + "\n");
            }
        }

//        FileWriter localFile = null;
//
//        try {
//            localFile = new FileWriter("testData.txt");
//            for (Movie movie: movies){
//                localFile.write("Movie Title: " + movie.getTitle() + " Movie ID: " + movie.getMovieID() + "\n");
//            }
//        } catch (IOException e){
//            System.out.println("In catch block");
//            e.printStackTrace();
//        } finally {
//            System.out.println("In finally block");
//
//            try {
//                if (localFile != null){
//                    localFile.close();
//                }
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//        }

    }

    public static ObservableList<Movie> getMovies() {
//        method that returns all the movie objects
        return movies;
    }

    public static ObservableList<Movie> getMoviesByID(Set<Integer> movieIDs){
        ObservableList<Movie> requestedMovies = FXCollections.observableArrayList();
        for (Movie movie: movies){
            if (movieIDs.contains(movie.getMovieID())){
                requestedMovies.add(movie);
            }
        }
        return requestedMovies;
    }

    public static HashMap<Integer, Double> getImdbRatings() {
        return imdbRatings;
    }
}
