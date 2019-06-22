package sample.datamodel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DirectorData {

    private static final String MOVIE_FILE = "movieproject.db";


    private static HashMap<Integer, Director> DirectorId_DirectorMap = new HashMap<>();


    public static void loadDirectors(){
        try(BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))){
            String input;
            boolean loadDirectors = false;

            while ((input = inputReader.readLine()) != null){

                if (loadDirectors){

                    if (input.contains("New_Entity")){
                        loadDirectors = false;
                    }
                    else {

                        String[] inputData = input.split("\",\"");

                        int directorID = Integer.parseInt(inputData[0].replace("\"", ""));

                        Director director = new Director(directorID, inputData[1].replace("\"", "").trim());
                        DirectorId_DirectorMap.put(directorID, director);

                    }

                }
                else {
                    if (input.contains("New_Entity: \"director_id\",\"director_name\"")){
//                        System.out.println(input);
                        loadDirectors = true;
                    }

                }


            }

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public static void loadCorrespondingMovies(){

        try(BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))){

            String input;
            boolean loadCorrespondingMovies = false;

            while ((input = inputReader.readLine()) != null){

                if (loadCorrespondingMovies){

                    if(input.contains("New_Entity")){
                        loadCorrespondingMovies = false;
                    }
                    else {

                        String[] inputData = input.split("\",\"");

                        int directorID = Integer.parseInt(inputData[0].replace("\"", ""));
                        int movieID = Integer.parseInt(inputData[1].replace("\"", ""));

                        DirectorId_DirectorMap.get(directorID).addMovieID(movieID);

                    }
                }
                else {
                    if (input.contains("New_Entity: \"director_id\",\"movie_id\"")){
//                        System.out.println(input);
                        loadCorrespondingMovies = true;
                    }

                }




            }

        } catch (IOException e){
            e.printStackTrace();
        }


    }

    public static Director getDirectorByID(Integer directorID){

//        System.out.println("retriving director with ID: " + directorID);
        if (DirectorId_DirectorMap.containsKey(directorID)){
            return DirectorId_DirectorMap.get(directorID);
        }
        else {
            return null;
        }

    }

    public static HashMap<Integer, Director> getDirectorId_DirectorMap() {
        return DirectorId_DirectorMap;
    }


}
