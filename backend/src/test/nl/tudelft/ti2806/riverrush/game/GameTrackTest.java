package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.entity.Team;
import nl.tudelft.ti2806.riverrush.domain.event.AddObstacleEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameFinishedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.TeamProgressEvent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by MARTIJN.
 */
public class GameTrackTest {

    GameTrack track = null;
    EventDispatcher dispatcher;
    private Team team;
    private final static double delta = 0.001;

    @Before
    public void setUp() throws Exception {
        this.dispatcher = Mockito.spy(EventDispatcher.class);
        this.track = new GameTrack("--[#5]-[#5]---[@5]--[#5]--[#5]-[#5]--", this.dispatcher, mock(Game.class));
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
            AbstractAnimal gottenAnimal = this.track.getTeam(this.team.getId()).getAnimals()
                    .get(animal.getId());
            assertEquals(animal, gottenAnimal);
        } catch (NoSuchTeamException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSpeed() throws Exception {
        Animal animal = new Animal(this.dispatcher);
        this.team.addAnimal(animal);
        assertEquals(this.track.getSpeed(this.team), 1.0, delta);
    }

    @Test
    public void testSpeedWhenFallenOff() throws Exception {
        Animal animal = new Animal(this.dispatcher);
        this.team.addAnimal(animal);
        animal.fall();
        assertEquals(this.track.getSpeed(this.team), 0.0, delta);
    }

    @Test
    public void testUpdateProgressOneCycle() throws Exception {
        Animal animal = new Animal(this.dispatcher);
        this.team.addAnimal(animal);
        this.track.updateProgress();
        assertEquals(1.0, this.track.getDistanceTeam(this.team.getId()), delta);
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

        Mockito.verify(this.dispatcher, Mockito.times(1)).dispatch(
                Mockito.isA(GameFinishedEvent.class));

        assertEquals(GameTrack.TRACK_LENGTH, this.track.getDistanceTeam(this.team.getId()), delta);
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
                this.track.getDistanceTeam(this.team.getId()), delta);
        assertEquals("Distance of team 2 is not equal", GameTrack.TRACK_LENGTH + 0.5,
                this.track.getDistanceTeam(team2.getId()), delta);

        ArrayList<Team> list = new ArrayList<>();
        list.add(this.team);
        list.add(team2);

        assertEquals(this.team, this.track.determineWinningTeam(list));

    }

    @Test
    public void testDispatchObstacleAt10() throws Exception {
        Animal animal = new Animal(this.dispatcher);
        this.team.addAnimal(animal);

        this.track.updateCannonballObstacles(this.team, 10.0);

        Mockito.verify(this.dispatcher, Mockito.times(1)).dispatch(
                Mockito.isA(AddObstacleEvent.class));
    }

}
