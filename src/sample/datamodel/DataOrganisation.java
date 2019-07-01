package sample.datamodel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataOrganisation {
//    Class that loads data into Hashmaps before the movie data is loaded.
//    This data is needed prior to loading movies because it is included into the movie objects.

    private static final String MOVIE_FILE = "movieproject.db";

    private static Map<Integer, List<Integer>> MovieID_DirectorID_Map = new HashMap<>();
    private static Map<Integer, List<Integer>> MovieID_ActorID_Map = new HashMap<>();
    private static Map<Integer, List<String>> MovieID_GenreMap = new HashMap<>();


    public static void load_MovieID_DirectorID_Map() {
//        method that loads data that describes which director has directed which movie.

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

    public static void load_MovieID_ActorID_Map() {
//        method that loads data that describes which actor has played in which movie.
//        same logic as in load_MovieID_DirectorID_Map

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

    public static void load_MovieID_GenreMap() {
//        method that loads data that describes to which genres a movie is attributed to.
//        same logic as in load_MovieID_DirectorID_Map

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


    public static Map<Integer, List<Integer>> getMovieID_DirectorID_Map() {
        return MovieID_DirectorID_Map;
    }

    public static Map<Integer, List<Integer>> getMovieID_ActorID_Map() {
        return MovieID_ActorID_Map;
    }

    public static Map<Integer, List<String>> getMovieID_GenreMap() {
        return MovieID_GenreMap;
    }
}
