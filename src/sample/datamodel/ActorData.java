package sample.datamodel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Singleton class that stores the data of an actor into a Hashmap for later access.
 */
public class ActorData {
//    Class that loads and organises the data of the directors.

    private static ActorData instance = new ActorData();

    private static final String MOVIE_FILE = "movieproject.db";

    private HashMap<Integer, Actor> ActorID_ActorMap = new HashMap<>();

    /**
     * Private constructor because class implements the singleton pattern.
     */
    private ActorData(){}

    /**
     * returns the single instance of the class.
     * @return Returns instance of ActorData class.
     */
    public static ActorData getInstance(){
        return instance;
    }

    /**
     * Method that loads all Actor objects from the movieproject.db file and stores them in the ActorID_ActorMap.
     * The Map takes the actorID as a key and the corresponding Actor object as value.
     */
    public void loadActors() {
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

    /**
     * Method that returns an actor object for a given actorID after validating whether the actor exists.
     * @param actorID Takes the actorID of the actor that should be returned as an argument.
     * @return Returns Actor object.
     */
    public Actor getActorByID(Integer actorID) {
//        method that returns the actor object for a given actorID.
//        Called by MovieData class when loading the movies to set the actorsproperty of each movie.

        if (ActorID_ActorMap.containsKey(actorID)) {
            return ActorID_ActorMap.get(actorID);
        } else {
            return null;
        }
    }
}
