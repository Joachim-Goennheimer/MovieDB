package sample;


import sample.datamodel.MovieData;
import sample.datamodel.NonRegisteredUserData;
import sample.datamodel.RegisteredUserData;

public class InteractiveModeLoader extends DataLoader  {


    public static void loadDataInteractiveMode(){
        loadUserData();
        loadBaseData();
    }

    public static void saveDataInteractiveMode(){

        saveUserData();

    }

    private static void loadUserData(){

        NonRegisteredUserData.loadUsers();
        RegisteredUserData.loadRegisteredUsers();
//        RegisteredUserData.dummyData();

    }

    private static void saveUserData(){

        RegisteredUserData.saveRegisteredUsers();

    }


}
