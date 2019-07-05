package sample.datamodel;

import java.io.*;
import java.util.*;

/**
 * Singleton class that stores the data of a of all Registered Users into a Map for later access and in interactive mode
 * it holds information which user is currently logged in.
 * This class is used for handling the log in as well as the registration process of users. It also does some validation
 * and is responsible for saving and loading the data of Registered Users into a binary file.
 */
public class RegisteredUserData {
//    Class that manages the data of Registered Users and the Register as well as the Login process.

    private static RegisteredUserData instance = new RegisteredUserData();

    private static final String REGISTERED_USERS_FILE = "userRegister.dat";

    private static RegisteredUser currentlyLoggedIn;

    private Map<String, RegisteredUser> registeredUsersMap = new HashMap<>();

    /**
     * Private constructor because class implements the singleton pattern.
     */
    private RegisteredUserData(){}

    /**
     * Returns the single instance of the class.
     * @return Returns instance of RegisteredUserData class.
     */
    public static RegisteredUserData getInstance(){
        return instance;
    }


    /**
     * Adds a rating to the user that is currently logged in.
     * @param movieID The ID of the movie to which a rating should be added.
     * @param rating The rating the user wants to give the movie.
     */
    public void addRating(Integer movieID, Double rating) {

        currentlyLoggedIn.addRating(rating, movieID);

    }

    /**
     * Processes the information a user has entered into the login form. Checks whether login was successful or not.
     * @param username The name the user has entered in the username field of the UI.
     * @param password The password the user has entered in the password field of the UI.
     * @return Returns a message that indicates whether the login was successful or not.
     */
    public String loginUser(String username, String password) {

        String responseMessage = "Ups something went wrong";

//        Could also check separately to give more detailed message but in this way it is more secure.
        if (!registeredUsersMap.keySet().contains(username) || !registeredUsersMap.get(username).checkPassword(password)) {
            responseMessage = "Invalid Username or Password";

        } else {

            currentlyLoggedIn = registeredUsersMap.get(username);
            responseMessage = "Login Successful";

        }
        return responseMessage;

    }


    /**
     * Processes the information a user has entered into the registration form. Checks whether registration was successful
     * or not.
     * @param username The name the user has entered in the username field of the UI.
     * @param password The password the user has entered into both passwords fields of the UI.
     * @return Returns a message that indicates whether the registration was successful or not.
     */
    public String registerNewUser(String username, String password) {

        String responseMessage = "Ups something went wrong";
        String passwordMessage = validateRegisterPassword(password);

        if (!validateRegisterUserName(username)) {
            responseMessage = "Username already taken";
        } else if (!passwordMessage.equals("Password valid")) {
            responseMessage = passwordMessage;
        } else {
            RegisteredUser user = new RegisteredUser();
            user.setUserName(username);
            user.setPassword(password);
            registeredUsersMap.put(username, user);
            responseMessage = "Successfully registered";

        }

        return responseMessage;

    }

    /**
     * Checks whether a username is already taken either by a Registered or by a Nonregistered User.
     * Is called by the registerNewUser() method.
     * @param username The name the user has entered in the username field of the UI.
     * @return True if the username is not yet taken - False if the username is already taken.
     */
    private boolean validateRegisterUserName(String username) {

        boolean userNameIsValid = true;

        if (registeredUsersMap.keySet().contains(username)) {
            userNameIsValid = false;
        }

        if (!NonRegisteredUserData.getInstance().validateNameForRegistration(username)) {
            userNameIsValid = false;
        }

        return userNameIsValid;
    }

    /**
     * Checks whether the password requirements are fulfilled. Additional requirements can be added later.
     * @param password The password the user has entered into both passwords fields of the UI.
     * @return Returns a message that indicates whether the chosen password fulfills the requirements.
     */
    private String validateRegisterPassword(String password) {

        String passwordResponseMessage = "Password valid";

        if (password.length() < 7) {
            passwordResponseMessage = "Your Password must have at least 7 letters";
        }

        return passwordResponseMessage;

    }

    /**
     * Method that loads all the Registered User objects from the REGISTERED_USERS_FILE and puts them into a map.
     * The Map maps the user object to the name of the user
     */
    public void loadRegisteredUsers() {

        try (ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(REGISTERED_USERS_FILE)))) {

            boolean fileEnd = false;

            while (!fileEnd) {
                try {
                    RegisteredUser loadingUser = (RegisteredUser) inputStream.readObject();

                    registeredUsersMap.put(loadingUser.getUserName(), loadingUser);


                } catch (EOFException e) {
                    fileEnd = true;
                } catch (ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Method that saves all the Registered User objects to the REGISTERED_USERS_FILE from the registeredUsersMap
     */
    public void saveRegisteredUsers() {

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(REGISTERED_USERS_FILE)))) {

            for (RegisteredUser user : registeredUsersMap.values()) {
                outputStream.writeObject(user);
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public RegisteredUser getCurrentlyLoggedIn() {
        return currentlyLoggedIn;
    }

    public String getCurrentUserName() {
        return currentlyLoggedIn.getUserName();
    }

    public List<RegisteredUser> getRegisteredUsersList() {

        List<RegisteredUser> userList = new ArrayList<>(registeredUsersMap.values());
        return userList;
    }

    /**
     * Method is used for removing the dummy user in StaticMode and for testing purposes.
     * Could later also be utilized to implement a functionality for a user to delete his account.
     * @param username The name of the user that should be removed.
     */
    public void removeUser(String username){


        String removename = "";
        for (String user: registeredUsersMap.keySet()){
            if (username.equals(user)){
                removename = user;
            }
        }
        if (!removename.equals("")){
            registeredUsersMap.remove(removename);
        }
    }

}
