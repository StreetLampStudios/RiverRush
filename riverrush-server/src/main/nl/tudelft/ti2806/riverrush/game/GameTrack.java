package nl.tudelft.ti2806.riverrush.game;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.entity.Sector;
import nl.tudelft.ti2806.riverrush.domain.entity.Team;
import nl.tudelft.ti2806.riverrush.domain.event.AbstractTeamEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalAddedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.TeamProgressEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

/**
 * Representation of a game track.
 */
@Singleton
public class GameTrack {

    private static final long UPDATE_DELAY = 1000;

    /**
     * Track length.
     */
    public static final Integer TRACK_LENGTH = 100;

    /**
     * Max team size.
     */
    public static final int TEAM_SIZE = 50;
    private final HashMap<Integer, Team> teams;

    private final HashMap<Team, Double> teamDistances;
    private final TreeMap<Double, AbstractTeamEvent> levelMap;
    private final HashMap<Team, Double> nextEvents;
    private final Provider<Game> gameProvider;


    private EventDispatcher dispatcher;
    private Game game;

    private List<Sector> currentPlayerSectors = Lists.newArrayList(Sector.FRONT, Sector.FRONT);

    /**
     * Create a game track.
     *
     * @param dispatch  - See {@link EventDispatcher}
     * @param aGame     - the game
     * @param aLevelMap - The map of the level
     */
    @Inject
    public GameTrack(final EventDispatcher dispatch,
                     final Provider<Game> aGame,
                     @Named("levelMap") final TreeMap<Double, AbstractTeamEvent> aLevelMap) {
        this.dispatcher = dispatch;
        this.gameProvider = aGame;
        this.levelMap = aLevelMap;

        this.teams = new HashMap<>();
        this.teamDistances = new HashMap<>();
        this.nextEvents = new HashMap<>();

        this.reset();
    }

    /**
     * Start updating the game track.
     */
    public void startTimer() {
        Timer tmr = new Timer();
        tmr.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (GameTrack.this.updateProgress()) {
                    tmr.cancel();
                }
            }
        }, UPDATE_DELAY, UPDATE_DELAY);
    }

    /**
     * This method will update the progress of the game track.
     */
    protected boolean updateProgress() {
        this.game = this.gameProvider.get();

        boolean someoneFinished = false;
        ArrayList<Team> finishedTeams = new ArrayList<>();

        for (Team team : this.teamDistances.keySet()) {
            Double speed = this.getSpeed(team);
            Double currentDistance = this.teamDistances.get(team);

            double newDistance = currentDistance + speed;

            this.teamDistances.put(team, newDistance);

            if (newDistance >= TRACK_LENGTH) {
                finishedTeams.add(team);
                someoneFinished = true;
            }

            this.fireGameTrackEvents(team, currentDistance);
            this.fireTeamProgressEvent(speed, newDistance, team.getId());
        }

        if (finishedTeams.size() > 0) {
            Team winner = this.determineWinningTeam(finishedTeams);
            this.game.finish(winner.getId());
        }

        return someoneFinished;
    }

    /**
     * Fire te team progress event.
     *
     * @param speed       The speed of the boat
     * @param newDistance The updated distance from the start
     * @param teamId      The team id of the team on the boat
     */
    private void fireTeamProgressEvent(final Double speed, final Double newDistance, final Integer teamId) {
        TeamProgressEvent event = new TeamProgressEvent();
        event.setProgress(newDistance);
        event.setTeam(teamId);
        event.setSpeed(speed);
        this.dispatcher.dispatch(event);
    }

    /**
     * This will check if it is time for the team to get a cannonball to their faces.
     *
     * @param team            - The team
     * @param currentDistance - The distance this team has travelled
     */
    protected void fireGameTrackEvents(final Team team, final Double currentDistance) {
        if (this.levelMap.isEmpty()) {
            return;
        }

        Double next = this.levelMap.higherKey(this.nextEvents.get(team));
        while (next != null && next <= currentDistance) {
            AbstractTeamEvent event = this.levelMap.get(next);
            event.setTeam(team.getId());
            this.dispatcher.dispatch(event);
            this.nextEvents.put(team, next);
            next = this.levelMap.higherKey(next);
        }
    }

    /**
     * Checks which team won.
     *
     * @param finishedTeams - allTeam that won
     * @return the winning team
     */
    protected Team determineWinningTeam(final ArrayList<Team> finishedTeams) {
        Team finishedTeam = finishedTeams.get(0);
        Double maxDistance = this.teamDistances.get(finishedTeam);

        for (final Team team : finishedTeams) {
            double distance = this.teamDistances.get(team);
            if (distance > maxDistance) {
                maxDistance = distance;
                finishedTeam = team;
            }
        }

        return finishedTeam;
    }

    /**
     * Resets the game track to start over.
     */
    public void reset() {
        for (Team team : this.teamDistances.keySet()) {
            this.teamDistances.put(team, 0.0);
        }
    }

    /**
     * Get the speed.
     *
     * @param t - the team you want the speed of
     * @return the speed of your team between 0 and 1
     */
    protected double getSpeed(final Team t) {
        int amountOnBoat = 0;
        int total = 0;
        for (AbstractAnimal anim : t.getAnimals()) {
            Animal animal = (Animal) anim;

            if (animal.isOnBoat()) {
                amountOnBoat++;
            }
            total++;
        }

        if (total == 0) {
            return 0.0;
        }

        return (double) amountOnBoat / (double) total;
    }

    /**
     * Adds a team to the game track.
     *
     * @param team - the team you want to add
     */
    public void addTeam(final Team team) {
        this.teams.put(team.getId(), team);
        this.teamDistances.put(team, 0.0);
        nextEvents.put(team, -1.0);
    }

    /**
     * Adds an animal to a team.
     *
     * @param teamID - The id of the team
     * @param animal - The animal to add
     * @return The team id the player joined
     * @throws NoSuchTeamException - if team is not found
     * @throws TeamFullException   - if team is full
     */
    public Integer addAnimal(final Integer teamID, final AbstractAnimal animal)
        throws NoSuchTeamException, TeamFullException {
        if (!this.teams.containsKey(teamID)) {
            throw new NoSuchTeamException();
        }

        if (this.getTeam(teamID).size() < TEAM_SIZE) {
            this.getTeam(teamID).addAnimal(animal);
            animal.setTeamId(teamID);
            return teamID;
        }

        // TODO: Find a better way to get this
        int otherTeam = Math.abs(teamID - 1);

        if (this.getTeam(otherTeam).size() < TEAM_SIZE) {
            this.getTeam(otherTeam).addAnimal(animal);
            animal.setTeamId(otherTeam);
            return otherTeam;
        }

        throw new TeamFullException();
    }

    /**
     * Add the animal to the team.
     *
     * @param animal The animal
     * @param teamId The team
     */
    public void addAnimalToTeam(final AbstractAnimal animal, final Integer teamId) {
        Integer newTeamId = this.addAnimal(teamId, animal);

        AnimalAddedEvent event = new AnimalAddedEvent();
        event.setAnimal(animal.getId());
        event.setTeam(newTeamId);
        event.setVariation(animal.getVariation());
        Sector nextSector = currentPlayerSectors.get(newTeamId).getNext();
        currentPlayerSectors.set(newTeamId, nextSector);
        event.setSector(nextSector);
        this.dispatcher.dispatch(event);
    }

    /**
     * Remove all the animals from a given boat that moved to the wrong direction.
     *
     * @param rockDirection the direction given by the boat collided event.
     * @param teamID        the team which the action applies to.
     */
    public void sweepAnimals(final Direction rockDirection, final Integer teamID) {
        Team tm = this.teams.get(teamID);
        for (AbstractAnimal anim : tm.getAnimals()) {
            if (!anim.getVoteDirection().equals(rockDirection)
                || anim.getVoteDirection().equals(Direction.NEUTRAL)) {
                anim.fall();
            }
        }
    }

    /**
     * kick an animal off the boat.
     *
     * @param animalId - integer that represents the animal
     * @param teamId   - integer that represents the team
     */
    public void collideAnimal(final Integer animalId, final Integer teamId) {
        Team team = this.teams.get(teamId);
        AbstractAnimal animal = team.getAnimal(animalId);
        animal.fall();
    }

    /**
     * Remove the animal.
     *
     * @param teamId   - integer that represents the team
     * @param animalId - integer that represents the animal
     */
    public void removeAnimalFromTeam(final Integer teamId, final Integer animalId) {
        this.getTeam(teamId).removeAnimal(animalId);
    }

    /**
     * @param teamId - The team.
     * @return the distance the team has traveled
     */
    public Double getDistanceTeam(final Integer teamId) {
        return this.teamDistances.get(this.teams.get(teamId));
    }

    /**
     * @param team - Integer that represents a team
     * @return the team
     */
    public Team getTeam(final Integer team) {
        return this.teams.get(team);
    }

    /**
     * Return all the teams.
     *
     * @return The teams
     */
    public Collection<Team> getTeams() {
        return this.teams.values();
    }
}
