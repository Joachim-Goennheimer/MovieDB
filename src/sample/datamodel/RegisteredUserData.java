package sample.datamodel;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisteredUserData {

    private static final String REGISTERED_USERS_FILE = "userRegister.dat";

    private static User currentlyLoggedIn;

    private static Map<String, RegisteredUser> registeredUsersMap = new HashMap<>();



    public static void dummyData(){

        RegisteredUser user = new RegisteredUser();

        user.setUserName("Joachim");
        user.setPassword("123");
        user.addRating(8.5, 20);

        registeredUsersMap.put(user.getUserName(), user);

        user = new RegisteredUser();

        user.setUserName("Leon");
        user.setPassword("456");
        user.addRating(5.5, 25);

        registeredUsersMap.put(user.getUserName(), user);

        user = new RegisteredUser();

        user.setUserName("Janik");
        user.setPassword("789");
        user.addRating(4.5, 35);

        registeredUsersMap.put(user.getUserName(), user);


    }

    public static String registerNewUser(String username, String password){

        String responseMessage = "Successfully registered";

        String passwordMessage = validatePassword(password);


        if (!validateUserName(username)){
            responseMessage = "Username already taken";
        }
        else if (!passwordMessage.equals("Password valid")){
            responseMessage = passwordMessage;
        }
        else {
            RegisteredUser user = new RegisteredUser();
            user.setUserName(username);
            user.setPassword(password);
            registeredUsersMap.put(username, user);
        }

        return responseMessage;

    }

    private static boolean validateUserName(String username){

//        to do: implement check for nonRegistered usernames

        boolean userNameIsValid = true;

        if (registeredUsersMap.keySet().contains(username)){
            userNameIsValid = false;
        }

        return userNameIsValid;
    }

    private static String validatePassword(String password){
        String passwordResponseMessage = "Password valid";


        if (password.length() < 7){
            passwordResponseMessage = "Your Password must have at least 7 letters";
        }

        return passwordResponseMessage;

    }




    public static void loadRegisteredUsers(){


        try (ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(REGISTERED_USERS_FILE)))){

            boolean fileEnd = false;

            while (!fileEnd){
                try {
                    RegisteredUser loadingUser = (RegisteredUser)inputStream.readObject();

                    System.out.println("Loading user: " + loadingUser.getUserName() + " Password: " + loadingUser.getPassword());
                    loadingUser.printRatings();

                    registeredUsersMap.put(loadingUser.getUserName(), loadingUser);


                } catch (EOFException e){
                    fileEnd = true;
                } catch (ClassNotFoundException e){
                    System.out.println(e.getMessage());
                }
            }


        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    public static void saveRegisteredUsers(){

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(REGISTERED_USERS_FILE)))){

            for (RegisteredUser user: registeredUsersMap.values()){

                System.out.println("Saving: " + user.getUserName() + " Password: " + user.getPassword());
                outputStream.writeObject(user);
            }


        } catch (IOException e){
            System.out.println(e.getMessage());
        }

    }


    public static void printRegisteredUsers(){

        for (RegisteredUser user: registeredUsersMap.values()){
            System.out.println("User: " + user.getUserName() + " Password " + user.getPassword());
        }

    }

}
