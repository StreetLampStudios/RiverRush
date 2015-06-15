package nl.tudelft.ti2806.riverrush.domain.entity.state;

import junit.framework.TestCase;
import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.event.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Tests for the AnimalInWater class.
 */
public class AnimalInWaterTest extends TestCase {
    private EventDispatcher dispatcher;
    private AnimalInWater animalState;

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

        animalState = spy(new AnimalInWater(animalMock, dispatcher));

        argument = ArgumentCaptor.forClass(Event.class);
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
        verify(dispatcher).dispatch(argument.capture());
        assertEquals(AnimalReturnedToBoatEvent.class.getName(), argument.getValue().getClass().getName());
        assertTrue(newState instanceof AnimalOnBoat);
    }

    @Test
    public void testVoteDirection() throws Exception {
        AnimalState newState = animalState.voteDirection(Direction.RIGHT);
        verify(dispatcher, never()).dispatch(any(Event.class));
        assertTrue(newState instanceof AnimalInWater);
    }
}
