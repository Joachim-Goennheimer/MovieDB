package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class RecommendationHandler {
//    Purpose of this class is to handle the logic for recommending movies based on the user preferences.

//    Idea: RegisteredUser has a number of votes given for movies. When he wants to receive recommendations the algorithm will
//    calculate a similarTaste value for each other user and rank the 15 most similar users in a List. Afterwards the other movies they liked
//    will be recommended provided the IMDB rating is above a certain level.


    private static ObservableList<Movie> recommendations = FXCollections.observableArrayList();


    public static void loadRecommendations(){

        Set<Integer> recommendationIDs = new TreeSet<>();

        RegisteredUser currentUser = RegisteredUserData.getCurrentlyLoggedIn();
        Map<Integer, Double> currentUserRatings = currentUser.getRatings();

        Map<Integer, Double> imdbRatings = MovieData.getImdbRatings();

        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(NonRegisteredUserData.getNonRegisteredUsers());
        allUsers.addAll(RegisteredUserData.getRegisteredUsersList());

        Map<Double, User> allUsersWithSimilarTasteMap = new TreeMap<>(Collections.reverseOrder());
        Map<Double, User> limitedUsersWithSimilarTasteMap= new TreeMap<>(Collections.reverseOrder());
//        Because oneself is always included in the similarUsers + 1 to actually have 15 new users to compare from.
        int similarUsersLimit = 15 + 1;
        int similarUsersLimitCounter = 0;
        Double generalRating;



        for (User otherUser: allUsers){

            double similarityValue = 0;
            int sameMoviesWatched = 0;


//            going through all the movies the other user has watched
                for (Integer movieID: otherUser.getRatings().keySet()){

//                checking whether the current user has also watched the movie
                    if (currentUserRatings.keySet().contains(movieID)){

                        double currentUserRating = currentUserRatings.get(movieID);
                        double otherUserRating = otherUser.getRating(movieID);

                        if (currentUserRating == otherUserRating){
                            similarityValue += 2;
                        }
                        else if ((currentUserRating - otherUserRating) <= 0.5 || (otherUserRating - currentUserRating) <= 0.5){
                            similarityValue += 1.5;
                        }
                        else if ((currentUserRating - otherUserRating) <= 1.0 || (otherUserRating - currentUserRating) <= 1.0){
                            similarityValue += 1;
                        }
                        else if ((currentUserRating - otherUserRating) <= 1.5 || (otherUserRating - currentUserRating) <= 1.5){
                            similarityValue += 0.5;
                        }
                        else {
//                        have watched same movie but rated very differently
                            similarityValue -= 1;
                        }

                        sameMoviesWatched ++;
                    }

                }

//            accounts for amount of movies watched. First divides by moviesWatched to see average and correct for bias if too many
//            of the same movies were watched. Then adds the amount of movies watched to correct for validity of comparison

                if (sameMoviesWatched != 0){
                    similarityValue = (similarityValue/sameMoviesWatched) + ((double)sameMoviesWatched)/5;

                    similarityValue = similarityValue*100;
                    similarityValue = Math.round(similarityValue);
                    similarityValue = similarityValue /100;

                    if ((int)similarityValue >= 2){

//                necessary to ensure that all keys are unique
                        boolean addedToMap = false;
                        while (!addedToMap){

                            if (allUsersWithSimilarTasteMap.containsKey(similarityValue)){
                                similarityValue += 0.001;
                                similarityValue = similarityValue*1000;
                                similarityValue = Math.round(similarityValue);
                                similarityValue = similarityValue /1000;

                            }
                            else {
                                System.out.println("Adding: " + otherUser.getUserName() + " SimliarTasteValue: " + similarityValue);
                                allUsersWithSimilarTasteMap.put(similarityValue, otherUser);
                                addedToMap = true;

                            }
                        }

                    }
                }

            }



        for (User user: allUsersWithSimilarTasteMap.values()){
            System.out.println("User: " + user.getUserName() + " Similarity Value: ");
        }

//        deletes all but the most similar users from the Map


        for (Double key: allUsersWithSimilarTasteMap.keySet()){

            if (similarUsersLimitCounter < similarUsersLimit){
                similarUsersLimitCounter++;
                System.out.println("Preserving: " + allUsersWithSimilarTasteMap.get(key).getUserName() + " with Rating: " + key);
                limitedUsersWithSimilarTasteMap.put(key, allUsersWithSimilarTasteMap.get(key));
            }
            else {
                System.out.println("Deleting: " + allUsersWithSimilarTasteMap.get(key).getUserName() + " with Rating: " + key);
            }


        }

        for (Double key: limitedUsersWithSimilarTasteMap.keySet()){


            int bestMovieID = -1;
            double bestMovieRating = 0.0;
            int secondBestMovieID = -1;
            double secondBestMovieRating = 0.0;

            User user = limitedUsersWithSimilarTasteMap.get(key);
            Map<Integer, Double> currentlyComparedUserNotYetWatchedMap = new TreeMap<>(Collections.reverseOrder());

            Map<Integer, Double> watchedMoviesMap = user.getRatings();

//            filtering out the movies that the current user has not watched yet. Doesn't make sense to recommend movies he already watched.
            for (Integer movieID: watchedMoviesMap.keySet()){
                if (!currentUserRatings.keySet().contains(movieID)){
                    currentlyComparedUserNotYetWatchedMap.put(movieID, watchedMoviesMap.get(movieID));
                }
            }

//            getting 2 best rated movies from user + imdb

                for (Integer movieID: currentlyComparedUserNotYetWatchedMap.keySet()){
                    try {
                        generalRating = currentlyComparedUserNotYetWatchedMap.get(movieID) + imdbRatings.get(movieID)/2;
                    }
                    catch (NullPointerException e){
                        e.getMessage();
                        generalRating = 0.0;
                    }

                    if (generalRating > bestMovieRating){
                        bestMovieRating = generalRating;
                        bestMovieID = movieID;
                    }
                    else if (generalRating > secondBestMovieRating){
                        secondBestMovieRating = generalRating;
                        secondBestMovieID = movieID;
                    }

                }

                if (bestMovieID != -1){
                    System.out.println("For User: " + limitedUsersWithSimilarTasteMap.get(key).getUserName() + " Best MovieID is: " + bestMovieID);
                    recommendationIDs.add(bestMovieID);
                }
                if (secondBestMovieID != -1){
                    System.out.println("For User: " + limitedUsersWithSimilarTasteMap.get(key).getUserName() + " Best MovieID is: " + bestMovieID);
                    recommendationIDs.add(secondBestMovieID);
                }

        }

        addRecommendations(recommendationIDs);


    }

    public static void addRecommendations(Set<Integer> recommendationIDs){
        System.out.println("In addRecommendationsFunction");

        recommendations = MovieData.getMoviesByID(recommendationIDs);

        for (Movie movie: recommendations){
            System.out.println("Movie title: " + movie.getTitle() + " IMDB Rating: " + movie.getImdbRating());
        }

    }

    public static ObservableList<Movie> getRecommendations() {
        return recommendations;
    }


}
