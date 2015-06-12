package nl.tudelft.ti2806.riverrush.domain.entity;

import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalInAir;
import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalInWater;
import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalOnBoat;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

/**
 * Created by Martijn on 8-6-2015.
 */
public class AnimalTest {

    private EventDispatcher dispatcher;
    private Animal animal;

    @Before
    public void setUp() throws Exception {
        dispatcher = mock(EventDispatcher.class);
        animal = spy(new Animal(dispatcher));
    }

    @Test
    public void testIsOnBoat() throws Exception {

    }


    @Test
    public void testSetState() throws Exception {
        AnimalInAir state = new AnimalInAir(animal, dispatcher);
        animal.setState(state);
        assertEquals(state, animal.getState());
    }

    @Test
    public void testCollide() throws Exception {
        animal.fall();
        assertEquals(animal.getState().getClass().getName(), AnimalInWater.class.getName());
    }

    @Test
    public void testJump() throws Exception {
        animal.jump();
        assertEquals(animal.getState().getClass().getName(), AnimalInAir.class.getName());

    }

    @Test
    public void testDrop() throws Exception {
        animal.jump();
        animal.drop();
        assertEquals(animal.getState().getClass().getName(), AnimalOnBoat.class.getName());
    }

    @Test
    public void testReturnToBoat() throws Exception {
        animal.fall();
        animal.returnToBoat();
        assertEquals(animal.getState().getClass().getName(), AnimalOnBoat.class.getName());
    }
}
