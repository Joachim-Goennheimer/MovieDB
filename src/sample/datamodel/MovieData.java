package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MovieData {

    private static final String MOVIE_FILE = "movieproject.db";

    private static ObservableList<Movie> movies = FXCollections.observableArrayList();


    public static void loadMovies(){
//        method that loads the movieData from the db-file

        Map<Integer, List<Integer>> MovieID_DirectorID_Map = DataOrganisation.getMovieID_DirectorID_Map();
        Map<Integer, List<Integer>> MovieID_ActorID_Map = DataOrganisation.getMovieID_ActorID_Map();


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

                        Movie movie = new Movie();
                        movie.setMovieID(movieID);
                        movie.setTitle(inputData[1]);
                        movie.setPlotDescription(inputData[2]);
                        movie.setReleaseDate(inputData[4]);


//                        getting directors

                        try {
                            for (Integer directorID: MovieID_DirectorID_Map.get(movieID)){

                                Director director = DirectorData.getDirectorByID(directorID);
                                movie.addDirector(director);
                            }

                        } catch (Exception e){
                            movie.setDirectorNames("n/a");
//                            System.out.println("No director for: " + movie.getTitle());

                        }

//                        getting actors
                        try {
                            for (Integer actorID: MovieID_ActorID_Map.get(movieID)){

                                Actor actor = ActorData.getActorByID(actorID);
                                movie.addActor(actor);
                            }

                        } catch (Exception e){
                            movie.setActorNames("n/a");
//                            System.out.println("No actor for: " + movie.getTitle());

                        }

                        movies.add(movie);
//                        System.out.println(movie.getReleaseDate().toLocalizedPattern());

                    }

                }
                else {
                    if (input.contains("New_Entity: \"movie_id\",\"movie_title\"")){
                        System.out.println(input);
                        loadMovies = true;
                    }

                }


            }

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

//    public static void loadDirectors(){
//
//        DirectorData directorData = new DirectorData();
//        directorData.loadDirectors();
//        directorData.loadCorrespondingMovies();
//
//        HashMap<Integer, Director> directorMovieMap = directorData.getDirectorId_DirectorMap();
//
//        Set<Integer> directorIDs = directorMovieMap.keySet();
//
//        for (Integer directorID: directorIDs) {
//
//
//            System.out.println("Director Name: " + directorMovieMap.get(directorID).getName());
//            System.out.println("Movies: " + directorMovieMap.get(directorID).getDirectedMoviesIDs());
//            System.out.println("Director ID: " + directorID);
//
//        }
//
//
//    }

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
}
