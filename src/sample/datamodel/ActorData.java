package sample.datamodel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ActorData {
//    Class that loads and organises the data of the directors.

    private static final String MOVIE_FILE = "movieproject.db";

    private static HashMap<Integer, Actor> ActorID_ActorMap = new HashMap<>();


    public static void loadActors() {
//        works the same as loadDirectors() in DirectorData
        try (BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))) {
            String input;
            boolean loadActors = false;

            while ((input = inputReader.readLine()) != null) {

                if (loadActors) {

                    if (input.contains("New_Entity")) {
                        loadActors = false;
                    } else {
                        String[] inputData = input.split("\",\"");
                        int actorID = Integer.parseInt(inputData[0].replace("\"", ""));
                        Actor actor = new Actor(actorID, inputData[1].replace("\"", "").trim());
                        ActorID_ActorMap.put(actorID, actor);
                    }
                } else {
                    if (input.contains("New_Entity: \"actor_id\",\"actor_name\"")) {
                        loadActors = true;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void loadCorrespondingMovies(){
////        method that is never used. Written in the beginning because thought it might be useful for filtering movies
////        in static mode based on actor.
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
//                        String[] inputData = input.split("\",\"");
//                        int actorID = Integer.parseInt(inputData[0].replace("\"", ""));
//                        int movieID = Integer.parseInt(inputData[1].replace("\"", ""));
//                        ActorID_ActorMap.get(actorID).addMovieID(movieID);
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

    public static Actor getActorByID(Integer actorID) {
//        method that returns the actor object for a given actorID.
//        Called by MovieData class when loading the movies to set the actorsproperty of each movie.

        if (ActorID_ActorMap.containsKey(actorID)) {
            return ActorID_ActorMap.get(actorID);
        } else {
            return null;
        }
    }
}
