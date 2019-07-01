package sample.datamodel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NonRegisteredUserData {
//    Class that holds the data of all the users from the db file.

    private static final String MOVIE_FILE = "movieproject.db";

    private static List<NonRegisteredUser> nonRegisteredUsers = new ArrayList<>();

    public static void loadUsers() {
//        method that loads all nonRegistered users and their ratings.
//        logic might be a bit confusing to understand at first but it was the most elegant solution that I could come up with.

        try (BufferedReader inputReader = new BufferedReader(new FileReader(MOVIE_FILE))) {
            String input;
            boolean loadUsers = false;
            NonRegisteredUser nonRegisteredUser = new NonRegisteredUser();

            while ((input = inputReader.readLine()) != null) {

                if (loadUsers) {

                    if (input.contains("New_Entity")) {
                        loadUsers = false;
                    } else {

                        String[] inputData = input.split("\",\"");
                        String currentUserName = inputData[0].replace("\"", "");
                        Double rating = Double.parseDouble(inputData[1].replace("\"", ""));
                        Integer movieID = Integer.parseInt(inputData[2].replace("\"", ""));

                        if (!nonRegisteredUser.getUserName().equals(currentUserName)) {

//                            check for first empty nonRegisteredUser. Don't want to add that fellow
                            if (!nonRegisteredUser.getUserName().equals("")) {
//                                here the user from the last loop will be added before the next user object is created
                                nonRegisteredUsers.add(nonRegisteredUser);
                            }
                            nonRegisteredUser = new NonRegisteredUser();
                            nonRegisteredUser.setUserName(currentUserName);
                        }
                        nonRegisteredUser.addRating(rating, movieID);

                    }

                } else {
                    if (input.contains("New_Entity: \"user_name\",\"rating\"")) {
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

    public static boolean validateNameForRegistration(String username){
        boolean response = true;

        for (NonRegisteredUser user: nonRegisteredUsers){
            if (user.getUserName().toLowerCase().equals(username.toLowerCase())) {
                response = false;
            }
        }
        return response;
    }

    public static List<NonRegisteredUser> getNonRegisteredUsers() {
        return nonRegisteredUsers;
    }
}
