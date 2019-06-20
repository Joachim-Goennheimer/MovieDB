package sample.datamodel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataOrganisation {

    private static final String MOVIE_FILE = "movieproject.db";


    private static Map<Integer, List<Integer>> DirectorID_MovieID_Map = new HashMap<>();
    private static Map<Integer, List<Integer>> MovieID_DirectorID_Map = new HashMap<>();
    private static Map<Integer, List<Integer>> ActorID_MovieID_Map = new HashMap<>();
    private static Map<Integer, List<Integer>> MovieID_ActorID_Map = new HashMap<>();


    public static void load_DirectorID_MovieID_Map(){

    }

    public static void load_MovieID_DirectorID_Map(){

        try(BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))){
            String input;
            boolean loadData = false;

            while ((input = inputReader.readLine()) != null){

                if (loadData){

                    if (input.contains("New_Entity")){
                        loadData = false;
                    }
                    else {

                        String[] inputData = input.split("\",\"");

                        int directorID = Integer.parseInt(inputData[0].replace("\"", ""));
                        int movieID = Integer.parseInt(inputData[1].replace("\"", ""));

                        if (MovieID_DirectorID_Map.containsKey(movieID)) {
                            MovieID_DirectorID_Map.get(movieID).add(directorID);
//                            System.out.println("To movie " + movieID + " added director " + MovieID_DirectorID_Map.get(movieID));
                        }
                        else {
                            List<Integer> directorIDs = new ArrayList<>();
                            directorIDs.add(directorID);
                            MovieID_DirectorID_Map.put(movieID, directorIDs);
//                            System.out.println("For " + movieID + " added new List with director  " + MovieID_DirectorID_Map.get(movieID));

                        }

                    }

                }
                else {
                    if (input.contains("New_Entity: \"director_id\",\"movie_id\"")){
                        System.out.println(input);
                        loadData = true;
                    }

                }


            }

        } catch (IOException e) {
            e.printStackTrace();

        }

    }


    public static void load_ActorID_MovieID_Map(){

    }

    public static void load_MovieID_ActorID_Map(){

        try(BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))){
            String input;
            boolean loadData = false;

            while ((input = inputReader.readLine()) != null){

                if (loadData){

                    if (input.contains("New_Entity")){
                        loadData = false;
                    }
                    else {

                        String[] inputData = input.split("\",\"");

                        int directorID = Integer.parseInt(inputData[0].replace("\"", ""));
                        int movieID = Integer.parseInt(inputData[1].replace("\"", ""));

                        if (MovieID_ActorID_Map.containsKey(movieID)) {
                            MovieID_ActorID_Map.get(movieID).add(directorID);
//                            System.out.println("To movie " + movieID + " added actor " + MovieID_ActorID_Map.get(movieID));
                        }
                        else {
                            List<Integer> directorIDs = new ArrayList<>();
                            directorIDs.add(directorID);
                            MovieID_ActorID_Map.put(movieID, directorIDs);
//                            System.out.println("For " + movieID + " added new List with actor  " + MovieID_ActorID_Map.get(movieID));

                        }

                    }

                }
                else {
                    if (input.contains("New_Entity: \"actor_id\",\"movie_id\"")){
                        System.out.println(input);
                        loadData = true;
                    }

                }


            }

        } catch (IOException e) {
            e.printStackTrace();

        }


    }










    public static Map<Integer, List<Integer>> getDirectorID_MovieID_Map() {
        return DirectorID_MovieID_Map;
    }

    public static Map<Integer, List<Integer>> getMovieID_DirectorID_Map() {
        return MovieID_DirectorID_Map;
    }

    public static Map<Integer, List<Integer>> getActorID_MovieID_Map() {
        return ActorID_MovieID_Map;
    }

    public static Map<Integer, List<Integer>> getMovieID_ActorID_Map() {
        return MovieID_ActorID_Map;
    }
}
