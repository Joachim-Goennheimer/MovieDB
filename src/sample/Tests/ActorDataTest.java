import org.junit.jupiter.api.Test;
import sample.datamodel.Actor;
import sample.datamodel.ActorData;

import static org.junit.jupiter.api.Assertions.*;

class ActorDataTest {

    @Test
    void getInstance() {
        ActorData actorData = ActorData.getInstance();
        assertTrue(actorData.getClass().getName().equals("sample.datamodel.ActorData"));
        assertNotEquals(null, actorData);
    }

    @Test
    void loadActors() {
        ActorData actorData = ActorData.getInstance();
        actorData.loadActors();
        Actor testactor = new Actor(17160, "Elijah Wood");
        assertEquals(testactor.getName(), actorData.getActorByID(17160).getName());
        assertEquals(testactor.getActorID(), actorData.getActorByID(17160).getActorID());

    }

    @Test
    void getActorByID() {
        ActorData actorData = ActorData.getInstance();
        actorData.loadActors();
        Actor testactor = new Actor(17160, "Elijah Wood");
        assertEquals(testactor.getName(), actorData.getActorByID(17160).getName());
        assertEquals(testactor.getActorID(), actorData.getActorByID(17160).getActorID());
    }
}