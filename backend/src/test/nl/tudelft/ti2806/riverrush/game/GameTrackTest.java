package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.entity.Team;
import nl.tudelft.ti2806.riverrush.domain.event.AbstractTeamEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.TeamProgressEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test fot the game track.
 */
public class GameTrackTest {

    /**
     * The game track to test.
     */
    private GameTrack track;

    /**
     * The event dispatcher spy.
     */
    @Mock
    private EventDispatcher dispatcher;

    /**
     * The game mock.
     */
    @Mock
    private Game game;

    private static final double DELTA = 0.0001;
    private Team team;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        TreeMap<Double, AbstractTeamEvent> levelMap = LevelMapParser.readFromFile("/simpletrack.txt");
        this.track = new GameTrack(this.dispatcher, this.game, levelMap);

        this.team = new Team();
        this.track.addTeam(this.team);
    }

    @Test
    public void testAddTeam() throws Exception {
        assertEquals(this.track.getTeam(this.team.getId()), this.team);
    }

    @Test
    public void testAddTeamLength() throws Exception {
        assertEquals(new Double(0.0), this.track.getDistanceTeam(this.team.getId()));
    }

    @Test
    public void testAddAnimal() throws Exception {
        Animal animal = new Animal(this.dispatcher);
        try {
            this.track.addAnimal(this.team.getId(), animal);
            AbstractAnimal gottenAnimal = this.track.getTeam(this.team.getId()).getAnimal(animal.getId());
            assertEquals(animal, gottenAnimal);
        } catch (NoSuchTeamException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSpeed() throws Exception {
        Animal animal = new Animal(this.dispatcher);
        this.team.addAnimal(animal);
        assertEquals(this.track.getSpeed(this.team), 1.0, DELTA);
    }

    @Test
    public void testSpeedWhenFallenOff() throws Exception {
        Animal animal = new Animal(this.dispatcher);
        this.team.addAnimal(animal);
        animal.fall();
        assertEquals(this.track.getSpeed(this.team), 0.0, DELTA);
    }

    @Test
    public void testUpdateProgressOneCycle() throws Exception {
        Animal animal = new Animal(this.dispatcher);
        this.team.addAnimal(animal);
        this.track.updateProgress();
        assertEquals(1.0, this.track.getDistanceTeam(this.team.getId()), DELTA);
        Mockito.verify(this.dispatcher, Mockito.times(1)).dispatch(
            Mockito.any(TeamProgressEvent.class));

    }

    @Test
    public void testUpdateProgressOneHundredCycles() {
        Animal animal = new Animal(this.dispatcher);
        this.team.addAnimal(animal);
        for (int i = 0; i < GameTrack.TRACK_LENGTH; i++) {
            this.track.updateProgress();
        }

        Mockito.verify(this.game, times(1)).finish(Mockito.any());

        assertEquals(GameTrack.TRACK_LENGTH, this.track.getDistanceTeam(this.team.getId()), DELTA);
    }

    @Test
    public void testDetermineWinningTeamOneHundredCycles() throws Exception {
        Animal animal = new Animal(this.dispatcher);
        this.team.addAnimal(animal);
        for (int i = 0; i < GameTrack.TRACK_LENGTH; i++) {
            this.track.updateProgress();
        }

        ArrayList<Team> list = new ArrayList<>();
        list.add(this.team);
        assertEquals(this.team, this.track.determineWinningTeam(list));
    }

    @Test
    public void testDetermineWinningTeamTwoTeams() throws Exception {
        Animal animal = new Animal(this.dispatcher);
        this.team.addAnimal(animal);

        Team team2 = new Team();
        Animal animal2 = new Animal(this.dispatcher);
        team2.addAnimal(animal2);
        Animal animal3 = new Animal(this.dispatcher);
        team2.addAnimal(animal3);

        this.track.addTeam(team2);

        for (int i = 0; i <= GameTrack.TRACK_LENGTH - 1; i++) {
            this.track.updateProgress();
        }

        animal2.fall();

        this.track.updateProgress();
        assertEquals("Distance of team 1 is not equal", GameTrack.TRACK_LENGTH + 1,
            this.track.getDistanceTeam(this.team.getId()), DELTA);
        assertEquals("Distance of team 2 is not equal", GameTrack.TRACK_LENGTH + 0.5,
            this.track.getDistanceTeam(team2.getId()), DELTA);

        ArrayList<Team> list = new ArrayList<>();
        list.add(this.team);
        list.add(team2);

        assertEquals(this.team, this.track.determineWinningTeam(list));

    }

    @Test
    public void testDispatchObstacleAt10() throws Exception {
        Animal animal = new Animal(this.dispatcher);
        this.team.addAnimal(animal);

        this.track.fireGameTrackEvents(this.team, 10.1);

        Mockito.verify(this.dispatcher, Mockito.times(1)).dispatch(
            Mockito.isA(AbstractTeamEvent.class));
    }

    @Test
    public void testGetTeams() throws Exception {
        Collection<Team> teams = this.track.getTeams();
        assertEquals(1, teams.size());
        assertTrue(teams.contains(this.team));
    }

    @Test
    public void testRemoveAnimalFromTeam() throws Exception {
        Animal animal = new Animal(this.dispatcher);
        this.team.addAnimal(animal);

        this.track.removeAnimalFromTeam(this.team.getId(), animal.getId());

        assertEquals(0, this.track.getTeam(this.team.getId()).size());
    }

    @Test
    public void testCollideAnimal() throws Exception {
        Animal animal = mock(Animal.class);
        this.team.addAnimal(animal);

        this.track.collideAnimal(animal.getId(), this.team.getId());

        verify(animal, times(1)).fall();
    }
}
