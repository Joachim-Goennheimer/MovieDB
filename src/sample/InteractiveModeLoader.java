package sample;

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

        NonRegisteredUserData.getInstance().loadUsers();
        RegisteredUserData.getInstance().loadRegisteredUsers();

    }

    private static void saveUserData(){

        RegisteredUserData.getInstance().saveRegisteredUsers();

    }


}
