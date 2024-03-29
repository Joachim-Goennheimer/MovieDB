package sample.datamodel;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class User implements Serializable {
//    Class that represents a User. Inherited by NonRegisteredUser as well as RegisteredUser Class.

    private String userName = "";
    private Map<Integer, Double> ratings = new HashMap<>();

    private static final long serialVersionUID = 45L;


    public void addRating(Double rating, Integer movieID){

        if (rating == -2.00 && ratings.keySet().contains(movieID)){
//            -2 will be passed if a registered user entered invalid input. As a consequence a rating that previously existed will be deleted.
            ratings.remove(movieID);
        }
        else {
            ratings.put(movieID, rating);
        }
    }

    public Map<Integer, Double> getRatings() {
        return ratings;
    }

    public double getRating(Integer movieID){
        double returnValue = -1;

        if (movieID != null){
            if (ratings.keySet().contains(movieID)){
                returnValue = ratings.get(movieID);
            }

        }
        return returnValue;

    }

    public void setUserName(String userName) {

        this.userName = userName;

    }

    public String getUserName() {
        return userName;
    }

//    Used for debugging purposes.
//    public void printRatings(){
//
//        System.out.println("*****************************************************");
//        System.out.println("User: " + userName + " has the following ratings: ");
//
//        for (Integer key: ratings.keySet()){
//            System.out.println("MovieID: " + key + " Rating: " + ratings.get(key));
//        }
//        System.out.println("*****************************************************");
//
//
//    }
}
