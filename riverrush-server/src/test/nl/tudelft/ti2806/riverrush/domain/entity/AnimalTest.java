package nl.tudelft.ti2806.riverrush.domain.entity;

import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalInAir;
import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalInWater;
import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalOnBoat;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalMovedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Contains tests for the Animal class.
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
        assertEquals(animal.getState().getClass().getName(), AnimalOnBoat.class.getName());
    }

    @Test
    public void testSetState() throws Exception {
        AnimalInAir state = new AnimalInAir(animal, dispatcher);
        animal.setState(state);
        assertEquals(state, animal.getState());
    }

    @Test
    public void testFall() throws Exception {
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

    @Test
    public void testCollide() throws Exception {
        // Collide is supposed to do nothing
        animal.getState().collide();
        assertEquals(animal.getState().getClass().getName(), AnimalOnBoat.class.getName());
    }

    @Test
    public void testSetVoteDirection() throws Exception {
        animal.setVoteDirection(Direction.LEFT);
        ArgumentCaptor<Event> argument = ArgumentCaptor.forClass(Event.class);
        verify(dispatcher).dispatch(argument.capture());
        assertEquals(AnimalMovedEvent.class.getName(), argument.getValue().getClass().getName());
    }
}
