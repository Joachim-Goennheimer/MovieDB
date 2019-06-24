package sample;

import sample.datamodel.*;

public abstract class DataLoader {


    public static void loadBaseData() {
        DataOrganisation.load_MovieID_DirectorID_Map();
        DataOrganisation.load_MovieID_ActorID_Map();
        DataOrganisation.load_MovieID_GenreMap();


        DirectorData.loadDirectors();
        ActorData.loadActors();

    }

}
