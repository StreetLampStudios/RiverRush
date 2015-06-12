package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.entity.Team;
import nl.tudelft.ti2806.riverrush.domain.event.AddObstacleEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AddRockEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.TeamProgressEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Representation of a game track.
 */
public class GameTrack {

    private static final long UPDATE_DELAY = 1000;
    public static final Integer TRACK_LENGTH = 100;
    private static final Integer DISTANCE_INTERVAL = 5;
    public static final int TEAM_SIZE = 50;
    private final HashMap<Integer, Team> teams;

    private HashMap<Team, Double> teamDistances;
    private final HashMap<Double, List<Event>> levelMap;

    private EventDispatcher dispatcher;
    private Game game;


    /**
     * Create a gametrack.
     *
     * @param level    - String which will determine when to add an cannonball
     * @param dispatch - See {@link EventDispatcher}
     * @param gme      - the game
     */
    public GameTrack(final EventDispatcher dispatch, final Game gme) {
        this.dispatcher = dispatch;
        game = gme;
        this.teams = new HashMap<>();
        this.teamDistances = new HashMap<>();
        this.levelMap = new HashMap<>();
        reset();
    }

    /**
     * Resets the gametrack to start over.
     */
    public void reset() {
        for (Team team : teamDistances.keySet()) {
            teamDistances.put(team, 0.0);
        }
    }

    protected void parseLevel(final InputStream inputStream) {
        this.parseLevel(new Scanner(inputStream));
    }

    /**
     * This will parse the level for you.
     *
     * @param level - String that represents when the cannonballs need to start flying
     */
    protected void parseLevel(final Scanner level) {
        level.useDelimiter(",|\n");
        while (level.hasNextDouble()) {
            Double spawnTime = level.nextDouble();
            char thingToSpawn = (char) level.next().charAt(0);

            if (thingToSpawn == 'O') {
                Double spawnLocation = level.nextDouble();

                AddObstacleEvent event = new AddObstacleEvent();
                event.setLocation(spawnLocation);
                levelMap.put(spawnTime, event);
            } else if (thingToSpawn == 'R') {
                AddRockEvent event = new AddRockEvent();
                if (level.next().charAt(0) == 'L') {
                    event.setLocation(Direction.LEFT);
                } else {
                    event.setLocation(Direction.RIGHT);
                }
                levelMap.put(spawnTime, event);
            }
        }
    }

    /**
     * Start updating the gametrack.
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
     * This method will update the progress of the gametrack.
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

            this.updateCannonballObstacles(team, currentDistance);
            this.updateRockObstacles(team, currentDistance);

            TeamProgressEvent event = new TeamProgressEvent();
            event.setProgress(currentDistance + speed);
            event.setTeam(team.getId());
            this.dispatcher.dispatch(event);
        }
        if (finishedTeams.size() > 0) {
            Team winner = this.determineWinningTeam(finishedTeams);
            game.finish(winner.getId());

        }
        return someoneFinished;
    }

    /**
     * This will check if it is time for the team to get a cannonball to their faces.
     *
     * @param team            - The team
     * @param currentDistance - The distance this team has travelled
     */
    protected void updateCannonballObstacles(final Team team, final Double currentDistance) {
//        if (this.levelMap.containsKey(currentDistance.intValue())) {
//            HashMap<Double, String> map = this.levelMap.get(currentDistance.intValue());
//            for (Map.Entry<Double, String> entry : map.entrySet()) {
//                if (entry.getValue().equals("#")) {
//                    AddObstacleEvent event = new AddObstacleEvent();
//                    event.setTeam(team.getId());
//                    // TODO: Make direction variable
//                    event.setLocation(entry.getKey());
//                    this.dispatcher.dispatch(event);
//                }
//            }
//        }
    }

    /**
     * This will check if it is time for the team to get a rock to their faces.
     *
     * @param team            - The team
     * @param currentDistance - The distance this team has travelled
     */
    protected void updateRockObstacles(final Team team, final Double currentDistance) {
//        if (this.levelMap.containsKey(currentDistance.intValue())) {
//            HashMap<Double, String> map = this.levelMap.get(currentDistance.intValue());
//            for (Map.Entry<Double, String> entry : map.entrySet()) {
//                if (entry.getValue().equals("@")) {
//                    AddRockEvent event = new AddRockEvent();
//                    event.setTeam(team.getId());
//                    // TODO: Make direction variable
//                    Direction dir = Direction.LEFT;
//                    if (entry.getKey() < 0.45) {
//                        dir = Direction.LEFT;
//                    } else if (entry.getKey() > 0.55) {
//                        dir = Direction.RIGHT;
//                    }
//                    event.setLocation(dir);
//                    this.dispatcher.dispatch(event);
//                }
//            }
//        }
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
        for (AbstractAnimal anim : t.getAnimals().values()) {
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
    }

    /**
     * Adds an animal to a team.
     *
     * @param teamID - The id of the team
     * @param animal - The animal to add
     * @return The team id the player joined
     * @throws NoSuchTeamException - if team is not found
     * @throws TeamFullException - if team is full
     */
    public Integer addAnimal(final Integer teamID, final AbstractAnimal animal) throws NoSuchTeamException, TeamFullException {
        if (!this.teams.containsKey(teamID)) {
            throw new NoSuchTeamException();
        }

        if (this.getTeam(teamID).size() < TEAM_SIZE) {
            this.getTeam(teamID).addAnimal(animal);
            animal.setTeamId(teamID);
            return teamID;
        }

        //TODO: Find a better way to get this
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

    public static GameTrack readFromFile(final String fileName,
                                         final EventDispatcher dispatch,
                                         final Game gme) throws IOException {
        GameTrack track = new GameTrack(dispatch, gme);
        InputStream in = GameTrack.class.getResourceAsStream(fileName);
        track.parseLevel(in);
        return track;
    }
}
