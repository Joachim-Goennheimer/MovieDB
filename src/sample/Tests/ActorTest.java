import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.datamodel.Actor;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActorTest {

    private Actor testactor;

    @BeforeEach
    void setup(){
        testactor = new Actor(10125, "Orlando Bloom");
    }

    @Test
    void getName() {

        assertEquals("Orlando Bloom", testactor.getName());

    }

    @Test
    void getActorID() {
        assertEquals(10125, testactor.getActorID());
    }


    @Test
    void toString1() {
        assertEquals("Orlando Bloom", testactor.toString());
    }
}