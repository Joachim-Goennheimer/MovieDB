package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

/**
 * Purpose of this class is to handle the logic for recommending movies based on the user preferences.
 *
 */
public class RecommendationHandler {



    private static ObservableList<Movie> recommendations = FXCollections.observableArrayList();

//    both values are set based on whether the program is launched in interactive or in static mode.
    private static int similarTasteValueThreshold;
    private static int similarUsersLimit;



    /**
     * Loads movie recommendations for the user that is currently logged in.
     *
     * Idea behind the algorithm: RegisteredUser has a number of votes given for movies. When he wants to
     * receive recommendations the algorithm will calculate a similarTaste value for each other user and
     * rank the similarUserLimit most similar users in a List. Afterwards it will calculate a general rating for all movies the similar
     * users have watched that the user currently logged in has not watched yet. This general rating is the average of
     * the respective similar users rating for that movie and the Imdb-rating/2. For each similar user the two movies with
     * the best general rating will be added to the recommendations that the user receives.
     *
     * If user has to few ratings it is possible that no recommedation will be given. The reason is that there is a hard
     * threshold in the similarTaste value that ensures that there is at least some basis for a recommendation.
     */
    public static void loadRecommendations(){

        Set<Integer> recommendationIDs = new TreeSet<>();

        RegisteredUser currentUser = RegisteredUserData.getInstance().getCurrentlyLoggedIn();
        Map<Integer, Double> currentUserRatings = currentUser.getRatings();

        Map<Integer, Double> imdbRatings = MovieData.getInstance().getImdbRatings();

//        Registered as well a Nonregistered users are added to the allUsers list because all users are used in the calculation.
        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(NonRegisteredUserData.getInstance().getNonRegisteredUsers());
        allUsers.addAll(RegisteredUserData.getInstance().getRegisteredUsersList());

        Map<Double, User> allUsersWithSimilarTasteMap = new TreeMap<>(Collections.reverseOrder());
        Map<Double, User> limitedUsersWithSimilarTasteMap= new TreeMap<>(Collections.reverseOrder());
        int similarUsersLimitCounter = 0;
        Double generalRating;


//        looping over all users to calculate similarTasteValue for each of them.
        for (User otherUser: allUsers){

            double similarTasteValue = 0;
            int sameMoviesWatched = 0;


//            going through all the movies the other user has watched
                for (Integer movieID: otherUser.getRatings().keySet()){

//                checking whether the current user has also watched the movie
                    if (currentUserRatings.keySet().contains(movieID)){

                        double currentUserRating = currentUserRatings.get(movieID);
                        double otherUserRating = otherUser.getRating(movieID);

                        if (currentUserRating == otherUserRating){
                            similarTasteValue += 2;
                        }
                        else if ((currentUserRating - otherUserRating) <= 0.5 || (otherUserRating - currentUserRating) <= 0.5){
                            similarTasteValue += 1.5;
                        }
                        else if ((currentUserRating - otherUserRating) <= 1.0 || (otherUserRating - currentUserRating) <= 1.0){
                            similarTasteValue += 1;
                        }
                        else if ((currentUserRating - otherUserRating) <= 1.5 || (otherUserRating - currentUserRating) <= 1.5){
                            similarTasteValue += 0.5;
                        }
                        else {
//                        have watched same movie but rated very differently
                            similarTasteValue -= 1;
                        }

                        sameMoviesWatched ++;
                    }

                }

//            accounts for amount of movies watched. First divides by moviesWatched to see average and correct for bias if too many
//            of the same movies were watched. Then adds the amount of movies watched to correct for validity of comparison

                if (sameMoviesWatched != 0){
                    similarTasteValue = (similarTasteValue/sameMoviesWatched) + ((double)sameMoviesWatched)/5;

                    similarTasteValue = similarTasteValue*100;
                    similarTasteValue = Math.round(similarTasteValue);
                    similarTasteValue = similarTasteValue /100;

//                    this is the threshold that has to be passed as a minimum requirement to be part of the most
//                    similar users.
                    if ((int)similarTasteValue >= similarTasteValueThreshold){

//                necessary to ensure that all keys are unique
                        boolean addedToMap = false;
                        while (!addedToMap){

                            if (allUsersWithSimilarTasteMap.containsKey(similarTasteValue)){
//                                If another user with the same similarTasteValue is already contained in the map 0.001
//                                is added. This does not change the result much but allows to easily solve the problem
//                                of having the same key twice.
                                similarTasteValue += 0.001;
                                similarTasteValue = similarTasteValue*1000;
                                similarTasteValue = Math.round(similarTasteValue);
                                similarTasteValue = similarTasteValue /1000;

                            }
                            else {
//                                System.out.println("Adding: " + otherUser.getUserName() + " SimliarTasteValue: " + similarTasteValue);
                                allUsersWithSimilarTasteMap.put(similarTasteValue, otherUser);
                                addedToMap = true;

                            }
                        }

                    }
                }

            }



//        for (User user: allUsersWithSimilarTasteMap.values()){
//            System.out.println("User: " + user.getUserName() + " Similarity Value: ");
//        }

//        deletes all but the most similar users from the Map
        for (Double key: allUsersWithSimilarTasteMap.keySet()){

            if (similarUsersLimitCounter < similarUsersLimit){
                similarUsersLimitCounter++;
//                System.out.println("Preserving: " + allUsersWithSimilarTasteMap.get(key).getUserName() + " with Rating: " + key);
                limitedUsersWithSimilarTasteMap.put(key, allUsersWithSimilarTasteMap.get(key));
            }
            else {
//                System.out.println("Deleting: " + allUsersWithSimilarTasteMap.get(key).getUserName() + " with Rating: " + key);
            }


        }

//        looping over the 15 +1 / 25 + 1 users with the most similar taste to the currently logged in user.
        for (Double key: limitedUsersWithSimilarTasteMap.keySet()){

            int bestMovieID = -1;
            double bestMovieRating = 0.0;
            int secondBestMovieID = -1;
            double secondBestMovieRating = 0.0;

            User user = limitedUsersWithSimilarTasteMap.get(key);
            Map<Integer, Double> currentlyComparedUserNotYetWatchedMap = new TreeMap<>(Collections.reverseOrder());

            Map<Integer, Double> watchedMoviesMap = user.getRatings();

//            filtering out the movies that the similar user has watched but the currently logged in user has not watched yet.
//            Doesn't make sense to recommend movies he already watched.
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
                        generalRating = 0.0;
                    }

                    if (generalRating > bestMovieRating){
                        secondBestMovieRating = bestMovieRating;
                        secondBestMovieID = bestMovieID;
                        bestMovieRating = generalRating;
                        bestMovieID = movieID;
                    }
                    else if (generalRating > secondBestMovieRating){
                        secondBestMovieRating = generalRating;
                        secondBestMovieID = movieID;
                    }

                }

                if (bestMovieID != -1){
//                    System.out.println("For User: " + limitedUsersWithSimilarTasteMap.get(key).getUserName() + " Best MovieID is: " + bestMovieID);
                    recommendationIDs.add(bestMovieID);
                }
                if (secondBestMovieID != -1){
//                    System.out.println("For User: " + limitedUsersWithSimilarTasteMap.get(key).getUserName() + " Best MovieID is: " + bestMovieID);
                    recommendationIDs.add(secondBestMovieID);
                }

        }

        addRecommendations(recommendationIDs);


    }

    /**
     * Gets the movie objects for the movieIDs that have been calculated in the loadRecommendations() method in a list
     * and assigns them to the recommendations variable
     * @param recommendationIDs The Set containing all the movieIDs of the movies that will be recommended to the currently
     *                          logged in user.
     */
    private static void addRecommendations(Set<Integer> recommendationIDs){

        recommendations = MovieData.getInstance().getMoviesByID(recommendationIDs);

//        for (Movie movie: recommendations){
//            System.out.println("Movie title: " + movie.getTitle() + " IMDB Rating: " + movie.getImdbRating());
//        }

    }

    public static ObservableList<Movie> getRecommendations() {
        return recommendations;
    }

    public static void setSimilarTasteValueThreshold(int similarTasteValueThreshold) {
        RecommendationHandler.similarTasteValueThreshold = similarTasteValueThreshold;
    }

    public static void setSimilarUsersLimit(int similarUsersLimit) {
        RecommendationHandler.similarUsersLimit = similarUsersLimit;
    }
}
