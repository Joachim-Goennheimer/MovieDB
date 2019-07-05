package sample;

import sample.datamodel.*;

/**
 * Static class that is responsible for loading the basic data that is required both in static and in interactive mode.
 */
public abstract class DataLoader {

    /**
     * Loads the basic data that is required both in static and in interactive mode.
     */
    public static void loadBaseData() {
        DataOrganisation dataOrganisation = DataOrganisation.getInstance();
        dataOrganisation.load_MovieID_DirectorID_Map();
        dataOrganisation.load_MovieID_ActorID_Map();
        dataOrganisation.load_MovieID_GenreMap();

        DirectorData.getInstance().loadDirectors();
        ActorData.getInstance().loadActors();

    }

    protected static void loadUserData(){

        NonRegisteredUserData.getInstance().loadUsers();
        RegisteredUserData.getInstance().loadRegisteredUsers();

    }

}
