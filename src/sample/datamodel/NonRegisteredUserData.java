package sample.datamodel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NonRegisteredUserData {

    private static final String MOVIE_FILE = "movieproject.db";


    private static List<NonRegisteredUser> nonRegisteredUsers = new ArrayList<>();



    public static void loadUsers(){

        Set<String> verifyDuplicateSet = new HashSet<>();


        try(BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))){
            String input;
            boolean loadUsers = false;
            String lastUserName = "";
            NonRegisteredUser nonRegisteredUser = new NonRegisteredUser();
//            System.out.println(nonRegisteredUser.getUserName());

            while ((input = inputReader.readLine()) != null){

                if (loadUsers){

                    if (input.contains("New_Entity")){
                        loadUsers = false;
                    }
                    else {

                        String[] inputData = input.split("\",\"");
                        String currentUserName = inputData[0].replace("\"", "");
                        Double rating = Double.parseDouble(inputData[1].replace("\"", ""));
                        Integer movieID = Integer.parseInt(inputData[2].replace("\"", ""));



                        if (!nonRegisteredUser.getUserName().equals(currentUserName)){

//                            check for first empty nonRegisteredUser. Don't want to add that fellow
                            if (!nonRegisteredUser.getUserName().equals("")) {
                                nonRegisteredUsers.add(nonRegisteredUser);
                            }
                            nonRegisteredUser = new NonRegisteredUser();
                            nonRegisteredUser.setUserName(currentUserName);
                        }
                        nonRegisteredUser.addRating(rating, movieID);

                    }

                }
                else {
                    if (input.contains("New_Entity: \"user_name\",\"rating\"")){
//                        System.out.println(input);
                        loadUsers = true;
                    }

                }

            }
//            necessary in order to also load the last nonRegisteredUser in the list
            nonRegisteredUsers.add(nonRegisteredUser);


        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public static List<NonRegisteredUser> getNonRegisteredUsers() {
        return nonRegisteredUsers;
    }
}
