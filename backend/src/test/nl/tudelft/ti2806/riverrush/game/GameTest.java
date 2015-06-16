package nl.tudelft.ti2806.riverrush.game;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for the main game workings.
 */
public class GameTest extends AbstractModule {

    private static final int ANIMAL_ID = 1;
    private static final int TEAM_ID = 1;
    private Game game;

    /**
     * The event dispatcher.
     */
    @Mock
    private EventDispatcher dispatcher;

    /**
     * The game track.
     */
    @Mock
    private GameTrack track;

    /**
     * The animal.
     */
    @Mock
    private Animal animal;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Injector injector = Guice.createInjector(this);

        this.game = injector.getInstance(Game.class);

        when(this.animal.getId()).thenReturn(ANIMAL_ID);
        when(this.animal.getTeamId()).thenReturn(TEAM_ID);
        when(this.track.addAnimal(TEAM_ID, this.animal)).thenReturn(TEAM_ID);

    }

    @Test
    public void testAddPlayerToTeam() throws Exception {
        this.game.addPlayerToTeam(this.animal, this.animal.getTeamId());
        verify(this.track, times(1)).addAnimalToTeam(this.animal, this.animal.getTeamId());
    }

    @Test
    public void testCollideAnimal() throws Exception {
        this.game.collideAnimal(this.animal.getId(), this.animal.getTeamId());
        verify(this.track, times(1)).collideAnimal(this.animal.getId(), this.animal.getTeamId());
    }

    @Override
    protected void configure() {
        this.bind(GameTrack.class).toInstance(this.track);
        this.bind(EventDispatcher.class).toInstance(this.dispatcher);
    }
}
