package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.entity.Team;
import nl.tudelft.ti2806.riverrush.domain.event.AbstractTeamEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.TeamProgressEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

/**
 * Test fot the game track.
 */
public class GameTrackTest {

    private GameTrack track = null;
    private EventDispatcher dispatcher;
    private Team team;
    private static final double DELTA = 0.0001;
    private Game game;

    @Before
    public void setUp() throws Exception {
        this.dispatcher = Mockito.spy(EventDispatcher.class);
        game = mock(Game.class);
        this.track = GameTrack.readFromFile("/simpletrack.txt", dispatcher, game);
        //this.track = new GameTrack("--[#5]-[#5]---[@5]--[#5]--[#5]-[#5]--", this.dispatcher, this.game);
        this.team = new Team();
        this.track.addTeam(this.team);
    }

    @Test
    public void testAddTeam() throws Exception {
        assertEquals(this.track.getTeam(this.team.getId()), this.team);
    }

    @Test
    public void testAddTeamLength() throws Exception {
        assertTrue(0.0 == this.track.getDistanceTeam(this.team.getId()));
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

}
