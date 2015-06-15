package nl.tudelft.ti2806.riverrush.domain.entity.state;

import junit.framework.TestCase;
import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for the AnimalOnBoat class.
 */
public class AnimalOnBoatTest extends TestCase {
    private EventDispatcher dispatcher;
    private AnimalOnBoat animalState;

    private ArgumentCaptor<Event> argument;

    /**
     * Used to verify calls from the server.
     */
    @Mock
    protected Animal animalMock;

    @Before
    public void setUp() throws Exception {
        dispatcher = mock(EventDispatcher.class);

        animalMock = mock(Animal.class);

        when(this.animalMock.getId())
            .thenReturn(1);

        when(this.animalMock.getTeamId())
            .thenReturn(1);

        animalState = spy(new AnimalOnBoat(animalMock, dispatcher));

        argument = ArgumentCaptor.forClass(Event.class);
    }

    @Test
    public void testJump() throws Exception {
        AnimalState newState = animalState.jump();
        verify(dispatcher).dispatch(argument.capture());
        assertEquals(AnimalJumpedEvent.class.getName(), argument.getValue().getClass().getName());
        assertTrue(newState instanceof AnimalInAir);
    }

    @Test
    public void testDrop() throws Exception {
        AnimalState newState = animalState.drop();
        verify(dispatcher, never()).dispatch(any(Event.class));
        assertTrue(newState instanceof AnimalOnBoat);
    }

    @Test
    public void testFall() throws Exception {
        AnimalState newState = animalState.fall();
        verify(dispatcher).dispatch(argument.capture());
        assertEquals(AnimalFellOffEvent.class.getName(), argument.getValue().getClass().getName());
        assertTrue(newState instanceof AnimalInWater);
    }

    @Test
    public void testReturnToBoat() throws Exception {
        AnimalState newState = animalState.returnToBoat();
        verify(dispatcher, never()).dispatch(any(Event.class));
        assertTrue(newState instanceof AnimalOnBoat);
    }

    @Test
    public void testVoteDirection() throws Exception {
        AnimalState newState = animalState.voteDirection(Direction.LEFT);
        verify(dispatcher, never()).dispatch(any(Event.class));
        assertTrue(newState instanceof AnimalOnBoat);
    }
}
