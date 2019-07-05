import org.junit.jupiter.api.Test;
import sample.datamodel.Actor;
import sample.datamodel.ActorData;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests the ActorData Class
 */
class ActorDataTest {

    /**
     * tests whether instance returns correct type of object and that the object is not null.
     */
    @Test
    void getInstance() {
        ActorData actorData = ActorData.getInstance();
        assertTrue(actorData.getClass().getName().equals("sample.datamodel.ActorData"));
        assertNotEquals(null, actorData);
    }

    /**
     * tests whether actors are loaded correctly from file. Testing with one actor and his id that is part of the file.
     */
    @Test
    void loadActors() {
        ActorData actorData = ActorData.getInstance();
        actorData.loadActors();
        Actor testactor = new Actor(17160, "Elijah Wood");
        assertEquals(testactor.getName(), actorData.getActorByID(17160).getName());
        assertEquals(testactor.getActorID(), actorData.getActorByID(17160).getActorID());

    }

    /**
     * first loading actors and then trying to get an actor by id that is part of the file.
     */
    @Test
    void getActorByID() {
        ActorData actorData = ActorData.getInstance();
        actorData.loadActors();
        Actor testactor = new Actor(17160, "Elijah Wood");
        assertEquals(testactor.getName(), actorData.getActorByID(17160).getName());
        assertEquals(testactor.getActorID(), actorData.getActorByID(17160).getActorID());
    }
}