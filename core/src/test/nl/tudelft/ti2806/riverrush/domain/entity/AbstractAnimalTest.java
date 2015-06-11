package nl.tudelft.ti2806.riverrush.domain.entity;

import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Martijn on 8-6-2015.
 */
public class AbstractAnimalTest {



    public class AbstractAnimalTestImplementation extends AbstractAnimal{

        public AbstractAnimalTestImplementation(EventDispatcher dispatch) {
            super(dispatch);
        }

        public AbstractAnimalTestImplementation(EventDispatcher dispatcher, Integer animal) {
            super(dispatcher, animal);
        }
    }

    private AnimalState state;
    private EventDispatcher dispatcher;
    private AbstractAnimalTestImplementation animal;

    @Before
    public void setUp() throws Exception {
        dispatcher = mock(EventDispatcher.class);
        animal = spy(new AbstractAnimalTestImplementation(dispatcher));
        state = mock(AnimalState.class);

        when(state.fall()).thenReturn(state);
        when(state.jump()).thenReturn(state);
        when(state.returnToBoat()).thenReturn(state);
        when(state.drop()).thenReturn(state);

        animal.setState(state);
    }

    @Test
    public void testConstructor() {
        animal = new AbstractAnimalTestImplementation(dispatcher, 10);
        assertEquals(animal.getId(), new Integer(10));
    }

    @Test
    public void testSetState() throws Exception {

        assertEquals(state, animal.getState());
    }

    @Test
    public void testCollide() throws Exception {
        animal.fall();
        verify(state).fall();
    }

    @Test
    public void testJump() throws Exception {
        animal.jump();
        verify(state).jump();
    }

    @Test
    public void testDrop() throws Exception {
        animal.drop();
        verify(state).drop();
    }

    @Test
    public void testReturnToBoat() throws Exception {
        animal.returnToBoat();
        verify(state).returnToBoat();
    }

    @Test
    public void testSetVariation() throws Exception {
        animal.setVariation(10);
        assertEquals(animal.getVariation(), new Integer(10));
    }

    @Test
    public void testSetTeamId() throws Exception {
        animal.setTeamId(10);
        assertEquals(animal.getTeamId(),new Integer(10));
    }

    @Test
    public void testEqualsTrue()
    {
        assertTrue(animal.equals(animal));
    }

    @Test
    public void testEqualsFalse()
    {
        assertFalse(animal.equals(new AbstractAnimalTestImplementation(dispatcher)));
    }
}
