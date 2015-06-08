package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.entity.Team;
import nl.tudelft.ti2806.riverrush.domain.event.AddObstacleEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameFinishedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.TeamProgressEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Representation of a game track.
 */
public class GameTrack {

    private static final long UPDATE_DELAY = 1000;
    public static final Integer TRACK_LENGTH = 100;
    private static final Integer DISTANCE_INTERVAL = 10;

    private HashMap<Integer, Team> teams;
    private HashMap<Team, Double> teamDistances;
    private final HashMap<Integer, Double> levelMap;

    private EventDispatcher dispatcher;

    /**
     * Create a gametrack.
     *
     * @param level    - String which will determine when to add an cannonball
     * @param dispatch - See {@link EventDispatcher}
     */
    public GameTrack(final String level, final EventDispatcher dispatch) {
        this.dispatcher = dispatch;
        this.teams = new HashMap<>();
        this.teamDistances = new HashMap<>();
        this.levelMap = new HashMap<>();
        parseLevel(level);
    }

    /**
     * This will parse the level for you.
     *
     * @param level - String that represents when the cannonballs need to start flying
     */
    public void parseLevel(final String level) {
        for (int i = 0; i < level.length(); i++) {
            char c = level.charAt(i);
            if (c == '#') {
                this.levelMap.put((i - this.levelMap.size()) * DISTANCE_INTERVAL, 0.5);
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
                GameTrack.this.updateProgress();
            }
        }, UPDATE_DELAY, UPDATE_DELAY);
    }

    /**
     * This method will update the progress of the gametrack.
     */
    protected void updateProgress() {
        ArrayList<Team> finishedTeams = new ArrayList<>();
        for (Team team : this.teamDistances.keySet()) {
            Double speed = this.getSpeed(team);
            Double currentDistance = this.teamDistances.get(team);
            this.teamDistances.put(team, currentDistance + speed);

            if (currentDistance + speed >= TRACK_LENGTH) {
                finishedTeams.add(team);
            }

            updateCannonballObstacles(team, currentDistance);

            TeamProgressEvent event = new TeamProgressEvent();
            event.setProgress(currentDistance + speed);
            event.setTeamID(team.getId());
            this.dispatcher.dispatch(event);
        }

        if (finishedTeams.size() > 0) {
            Team winner = this.determineWinningTeam(finishedTeams);
            GameFinishedEvent event = new GameFinishedEvent();
            event.setWonTeam(winner.getId());
            this.dispatcher.dispatch(event);
        }

    }

    /**
     * This will check if it is time for the team to
     * get a cannonball to their faces.
     *
     * @param team            - The team
     * @param currentDistance - The distance this team has travelled
     */
    protected void updateCannonballObstacles(final Team team, final Double currentDistance) {
        if (this.levelMap.get(currentDistance.intValue()) != null) {
            AddObstacleEvent addEvent = new AddObstacleEvent();
            addEvent.setTeam(team.getId());
            addEvent.setLocation(0.5);
            this.dispatcher.dispatch(addEvent);
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
    }

    /**
     * Adds an animal to a team.
     *
     * @param teamID - The id of the team
     * @param animal - The animal to add
     * @throws NoSuchTeamException - if team is not found
     */
    public void addAnimal(final Integer teamID, final AbstractAnimal animal)
        throws NoSuchTeamException {
        if (!this.teams.containsKey(teamID)) {
            throw new NoSuchTeamException();
        }

        this.getTeam(teamID).addAnimal(animal);
        animal.setTeamId(teamID);
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
}
