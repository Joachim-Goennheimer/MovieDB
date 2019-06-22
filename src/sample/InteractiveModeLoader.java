package sample;


import sample.datamodel.NonRegisteredUserData;
import sample.datamodel.RegisteredUserData;

public class InteractiveModeLoader extends DataLoader  {


    public static void loadDataInteractiveMode(){
        loadBaseData();
        loadUserData();
    }

    public static void saveDataInteractiveMode(){

        saveUserData();

    }

    private static void loadUserData(){

        NonRegisteredUserData.loadUsers();
        RegisteredUserData.loadRegisteredUsers();
//        RegisteredUserData.dummyData();

        RegisteredUserData.printRegisteredUsers();
    }

    private static void saveUserData(){

        RegisteredUserData.saveRegisteredUsers();

    }


}
