package sample;

import sample.datamodel.*;

public abstract class DataLoader {


    public static void loadBaseData() {
        DataOrganisation dataOrganisation = DataOrganisation.getInstance();
        dataOrganisation.load_MovieID_DirectorID_Map();
        dataOrganisation.load_MovieID_ActorID_Map();
        dataOrganisation.load_MovieID_GenreMap();

        DirectorData.getInstance().loadDirectors();
        ActorData.getInstance().loadActors();

    }

}
