package nl.tudelft.ti2806.riverrush.domain.entity;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.failfast.FailFastException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

/**
 * Test for {@link AbstractTeam}
 */
public class AbstractTeamTest {

    public class AbstractAnimalTestImplementation extends AbstractAnimal {

        public AbstractAnimalTestImplementation(EventDispatcher dispatch) {
            super(dispatch);
        }
    }

    public class AbstractTeamTestImplementation extends AbstractTeam {

        public AbstractTeamTestImplementation() {
            super();
        }

        public AbstractTeamTestImplementation(int id) {
            super(id);
        }
    }

    @Test
    public void testAddAnimal() throws Exception {
        EventDispatcher dispatcher = mock(EventDispatcher.class);
        AbstractAnimal animal = new AbstractAnimalTestImplementation(dispatcher);

        AbstractTeam team = new AbstractTeamTestImplementation();
        team.addAnimal(animal);
        team.getAnimal(animal.getId());
    }

    @Test(expected = FailFastException.class)
    public void testAddNullAnimal() {

        AbstractTeam team = new AbstractTeamTestImplementation();
        team.addAnimal(null);
    }

    @Test
    public void testTeamID() throws Exception {
        AbstractTeam team = new AbstractTeamTestImplementation(10);
        assertEquals(new Integer(10), team.getId());
    }

    @Test
    public void testRemoveAnimal() {
        EventDispatcher dispatcher = mock(EventDispatcher.class);
        AbstractAnimal animal = new AbstractAnimalTestImplementation(dispatcher);

        AbstractTeam team = new AbstractTeamTestImplementation();
        team.addAnimal(animal);

        team.removeAnimal(animal.getId());

        assertNull(team.getAnimal(animal.getId()));
    }
}
