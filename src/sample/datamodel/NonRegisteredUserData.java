package sample.datamodel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Singleton class that stores the data of a of all Nonregistered Users into a List for later access.
 * This class is later used in order to find (in addition to registered users) nonregistered users with similar movie
 * taste like the currently logged in user. With these similar users
 * the Recommendation Handler later determines the recommendations for the currently logged in user.
 */
public class NonRegisteredUserData {
//    Class that holds the data of all the users from the db file.

    private static NonRegisteredUserData instance = new NonRegisteredUserData();

    private static final String MOVIE_FILE = "movieproject.db";

    private List<NonRegisteredUser> nonRegisteredUsers = new ArrayList<>();

    /**
     * Private constructor because class implements the singleton pattern.
     */
    private NonRegisteredUserData(){}

    /**
     * returns the single instance of the class.
     * @return Returns instance of NonRegisteredUserData class.
     */
    public static NonRegisteredUserData getInstance(){
        return instance;
    }

    /**
     * Method that loads all Nonregistered user objects from the MOVIE_FILE file and stores them in the nonRegisteredUsers list.
     */
    public void loadUsers() {
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

    /**
     * Validates whether the name a user tried to enter in the registration form is already taken by a Nonregistered User.
     * In that case he will not be allowed to take the same name.
     * @param username The name the user tried to register as his username.
     * @return True if no Nonregistered User has the same name - False if a Nonregistered User has the same name.
     */
    public boolean validateNameForRegistration(String username){
        boolean response = true;

        for (NonRegisteredUser user: nonRegisteredUsers){
            if (user.getUserName().toLowerCase().equals(username.toLowerCase())) {
                response = false;
            }
        }
        return response;
    }

    public List<NonRegisteredUser> getNonRegisteredUsers() {
        return nonRegisteredUsers;
    }
}
