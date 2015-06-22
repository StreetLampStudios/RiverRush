package nl.tudelft.ti2806.riverrush.domain.entity.state;

import junit.framework.TestCase;
import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalFellOffEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalJumpedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for the AnimalOnBoat class.
 */
public class AnimalOnBoatTest extends TestCase {

    private AnimalOnBoat animalState;

    /**
     * The main event dispatcher.
     */
    @Mock
    private EventDispatcher dispatcher;

    /**
     * Used to verify calls from the server.
     */
    @Mock
    private Animal animalMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(this.animalMock.getId()).thenReturn(1);
        when(this.animalMock.getTeamId()).thenReturn(1);

        this.animalState = new AnimalOnBoat(animalMock, dispatcher);
    }

    @Test
    public void testJump() throws Exception {
        AnimalState newState = animalState.jump();
        ArgumentCaptor<AnimalJumpedEvent> argument = ArgumentCaptor.forClass(AnimalJumpedEvent.class);
        verify(dispatcher).dispatch(argument.capture());
        assertEquals(new Integer(1), argument.getValue().getAnimal());
        assertEquals(new Integer(1), argument.getValue().getTeam());
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
        ArgumentCaptor<AnimalFellOffEvent> argument = ArgumentCaptor.forClass(AnimalFellOffEvent.class);
        verify(dispatcher).dispatch(argument.capture());
        assertEquals(new Integer(1), argument.getValue().getAnimal());
        assertEquals(new Integer(1), argument.getValue().getTeam());
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
