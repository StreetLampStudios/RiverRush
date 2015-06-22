package nl.tudelft.ti2806.riverrush.domain.entity.state;

import junit.framework.TestCase;
import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalReturnedToBoatEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for the AnimalInWater class.
 */
public class AnimalInWaterTest extends TestCase {

    private AnimalInWater animalState;

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

        this.animalState = new AnimalInWater(animalMock, dispatcher);
    }

    @Test
    public void testJump() throws Exception {
        AnimalState newState = animalState.jump();
        verify(dispatcher, never()).dispatch(any(Event.class));
        assertTrue(newState instanceof AnimalInWater);
    }

    @Test
    public void testDrop() throws Exception {
        AnimalState newState = animalState.drop();
        verify(dispatcher, never()).dispatch(any(Event.class));
        assertTrue(newState instanceof AnimalInWater);
    }

    @Test
    public void testFall() throws Exception {
        AnimalState newState = animalState.fall();
        verify(dispatcher, never()).dispatch(any(Event.class));
        assertTrue(newState instanceof AnimalInWater);
    }

    @Test
    public void testReturnToBoat() throws Exception {
        AnimalState newState = animalState.returnToBoat();
        ArgumentCaptor<AnimalReturnedToBoatEvent> argument = ArgumentCaptor.forClass(AnimalReturnedToBoatEvent.class);
        verify(dispatcher).dispatch(argument.capture());
        assertEquals(new Integer(1), argument.getValue().getAnimal());
        assertEquals(new Integer(1), argument.getValue().getTeam());
        assertTrue(newState instanceof AnimalOnBoat);
    }

    @Test
    public void testVoteDirection() throws Exception {
        AnimalState newState = animalState.voteDirection(Direction.RIGHT);
        verify(dispatcher, never()).dispatch(any(Event.class));
        assertTrue(newState instanceof AnimalInWater);
    }
}
