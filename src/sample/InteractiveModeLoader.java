package sample;

import sample.datamodel.NonRegisteredUserData;
import sample.datamodel.RecommendationHandler;
import sample.datamodel.RegisteredUserData;

/**
 * Class responsible for loading the required data for the interactive mode. In addition to the basic data from the
 * DataLoader class it also loads and saves the data of registered users.
 * The class is called from the main method if no input parameters are given.
 */
public class InteractiveModeLoader extends DataLoader  {


    public static void loadDataInteractiveMode(){
        loadUserData();
        loadBaseData();
        RecommendationHandler.setSimilarTasteValueThreshold(2);
//        less similar users in interactive mode because there are no additional restrictions on gerne, actors, directors
//        unlike in static mode.
//        The +1 is because oneself is always part of the similar users.
        RecommendationHandler.setSimilarUsersLimit(15);
    }

    public static void saveDataInteractiveMode(){

        saveUserData();

    }

    private static void saveUserData(){

        RegisteredUserData.getInstance().saveRegisteredUsers();

    }


}
