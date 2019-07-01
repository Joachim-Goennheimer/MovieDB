package sample.datamodel;

import java.io.*;
import java.util.*;

public class RegisteredUserData {
//    Class that manages the data of Registered Users and the Register as well as the Login process.

    private static final String REGISTERED_USERS_FILE = "userRegister.dat";

    private static RegisteredUser currentlyLoggedIn;

    private static Map<String, RegisteredUser> registeredUsersMap = new HashMap<>();


    public static void addRating(Integer movieID, Double rating) {
//        method that adds a rating to the user that is currently logged in.

        currentlyLoggedIn.addRating(rating, movieID);

    }

    public static String loginUser(String username, String password) {
//        method that checks whether the login was successful or not.

        String responseMessage = "Ups something went wrong";

//        Could also check separately to give more detailed message but in this way it is more secure.
        if (!registeredUsersMap.keySet().contains(username) || !registeredUsersMap.get(username).getPassword().equals(password)) {
            responseMessage = "Invalid Username or Password";

        } else {

            currentlyLoggedIn = registeredUsersMap.get(username);
            responseMessage = "Login Successful";

        }
        return responseMessage;

    }


    public static String registerNewUser(String username, String password) {
//        method that checks whether registration process was successful or not.

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

    private static boolean validateRegisterUserName(String username) {
//        Checks whether a username is already taken.

        boolean userNameIsValid = true;

        if (registeredUsersMap.keySet().contains(username)) {
            userNameIsValid = false;
        }

        if (!NonRegisteredUserData.validateNameForRegistration(username)) {
            userNameIsValid = false;
        }

        return userNameIsValid;
    }

    private static String validateRegisterPassword(String password) {
//        Checks whether the password requirements are fulfilled. Additional requirements can be added later.

        String passwordResponseMessage = "Password valid";

        if (password.length() < 7) {
            passwordResponseMessage = "Your Password must have at least 7 letters";
        }

        return passwordResponseMessage;

    }


    public static void loadRegisteredUsers() {
//        Method that loads all registered users from the binary file.

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

    public static void saveRegisteredUsers() {
//        method that saves all registered users and their ratings to the binary file.

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(REGISTERED_USERS_FILE)))) {

            for (RegisteredUser user : registeredUsersMap.values()) {
                outputStream.writeObject(user);
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static RegisteredUser getCurrentlyLoggedIn() {
        return currentlyLoggedIn;
    }

    public static String getCurrentUserName() {
        return currentlyLoggedIn.getUserName();
    }

    public static List<RegisteredUser> getRegisteredUsersList() {

        List<RegisteredUser> userList = new ArrayList<>(registeredUsersMap.values());
        return userList;
    }

}
