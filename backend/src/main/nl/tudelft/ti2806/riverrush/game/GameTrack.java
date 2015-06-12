package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.entity.Team;
import nl.tudelft.ti2806.riverrush.domain.event.AbstractTeamEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AddObstacleEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AddRockEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.TeamProgressEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

/**
 * Representation of a game track.
 */
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


    private EventDispatcher dispatcher;
    private Game game;

    /**
     * Create a game track.
     *
     * @param dispatch - See {@link EventDispatcher}
     * @param aGame    - the game
     */
    public GameTrack(final EventDispatcher dispatch, final Game aGame) {
        this.dispatcher = dispatch;
        this.game = aGame;
        this.teams = new HashMap<>();
        this.teamDistances = new HashMap<>();
        this.levelMap = new TreeMap<>();
        this.nextEvents = new HashMap<>();
        this.reset();
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
     * Parse a level for the game track.
     *
     * @param inputStream The stream to parse from
     */
    protected void parseLevel(final InputStream inputStream) {
        this.parseLevel(new Scanner(inputStream));
    }

    /**
     * This will parse the level for you.
     *
     * @param level - String that represents when the cannonballs need to start flying
     */
    protected void parseLevel(final Scanner level) {
        final String lineSeperator = System.getProperty("line.separator");
        level.useDelimiter(",|" + lineSeperator);
        while (level.hasNextDouble()) {
            Double spawnTime = level.nextDouble();
            char thingToSpawn = level.next().charAt(0);

            AbstractTeamEvent event = this.generateTrackEvent(thingToSpawn, level.nextDouble());
            levelMap.put(spawnTime, event);
        }
    }

    /**
     * Generate an event to create elements on the game track.
     *
     * @param parseCode     The element to create
     * @param spawnLocation The location to put the element
     * @return The event
     */
    protected AbstractTeamEvent generateTrackEvent(final char parseCode, final double spawnLocation) {
        if (parseCode == 'O') {
            AddObstacleEvent event = new AddObstacleEvent();
            event.setLocation(spawnLocation);
            return event;
        } else if (parseCode == 'R') {
            AddRockEvent event = new AddRockEvent();
            if (spawnLocation == -1) {
                event.setLocation(Direction.LEFT);
            } else {
                event.setLocation(Direction.RIGHT);
            }
            return event;
        }
        throw new IllegalArgumentException("Illegal event code: " + parseCode);
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
        boolean someoneFinished = false;
        ArrayList<Team> finishedTeams = new ArrayList<>();
        for (Team team : this.teamDistances.keySet()) {
            Double speed = this.getSpeed(team);
            Double currentDistance = this.teamDistances.get(team);
            this.teamDistances.put(team, currentDistance + speed);

            if (currentDistance + speed >= TRACK_LENGTH) {
                finishedTeams.add(team);
                someoneFinished = true;
            }

            this.fireGameTrackEvents(team, currentDistance);

            TeamProgressEvent event = new TeamProgressEvent();
            event.setProgress(currentDistance + speed);
            event.setTeam(team.getId());
            event.setSpeed(speed);
            this.dispatcher.dispatch(event);
        }
        if (finishedTeams.size() > 0) {
            Team winner = this.determineWinningTeam(finishedTeams);

            this.game.finish(winner.getId());
        }
        return someoneFinished;
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

        Double next = this.levelMap.lowerKey(currentDistance);
        while (next != null && !next.equals(this.nextEvents.get(team))) {
            AbstractTeamEvent event = this.levelMap.get(next);
            event.setTeam(team.getId());
            this.dispatcher.dispatch(event);
            this.nextEvents.put(team, next);
        }
    }

    /**
     * Checks which team won.
     *
     * @param finishedTeams - allTeam that won
     * @return the winning team
     */
    protected Team determineWinningTeam(final ArrayList<Team> finishedTeams) {
        Team finished = finishedTeams.get(0);
        Double maxDistance = this.teamDistances.get(finished);

        for (final Team team : finishedTeams) {
            double newDistance = this.teamDistances.get(team);
            if (newDistance > maxDistance) {
                maxDistance = newDistance;
                finished = team;
            }
        }

        return finished;
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
    public HashMap<Integer, Team> getTeams() {
        return this.teams;
    }

    /**
     * Read the game track from a file.
     *
     * @param fileName The name of the file
     * @param dispatch The event dispatcher
     * @param game     The main game
     * @return The created game track
     * @throws IOException when the file cannot be opened
     */
    public static GameTrack readFromFile(final String fileName,
                                         final EventDispatcher dispatch,
                                         final Game game) throws IOException {
        GameTrack track = new GameTrack(dispatch, game);
        InputStream in = GameTrack.class.getResourceAsStream(fileName);
        track.parseLevel(in);
        return track;
    }
}
