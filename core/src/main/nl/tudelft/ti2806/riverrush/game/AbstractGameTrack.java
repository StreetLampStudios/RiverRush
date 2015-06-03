package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractTeam;

import java.util.HashMap;

/**
 * Abstract representation of a game track.
 */
public abstract class AbstractGameTrack {

    private HashMap<Integer, AbstractTeam> teams;
    private HashMap<Integer, Integer> distances;

    private Integer trackLength;

    public AbstractGameTrack(final Integer trackLength) {
        this.teams = new HashMap<>();
        this.distances = new HashMap<>();
        this.trackLength = trackLength;
    }

    /**
     * Adds a team to the gametrack.
     * @param team - the team you want to add
     */
    public void addTeam(final AbstractTeam team) {
        this.teams.put(team.getId(), team);
        this.distances.put(team.getId(), 0);
    }

    /**
     * Adds an animal to a team.
     * @param teamID - The id of the team
     * @param animal - The animal to add
     * @throws NoSuchTeamException - if team is not found
     */
    public void addAnimal(final Integer teamID, final AbstractAnimal animal) throws NoSuchTeamException {
        if (!this.teams.containsKey(teamID)) {
            throw new NoSuchTeamException();
        }

        this.getTeam(teamID).addAnimal(animal);
        animal.setTeam(teamID);
    }

    public Integer getTrackLength() {
        return trackLength;
    }

    public Integer getDistanceTeam(final Integer team) {
        return this.distances.get(team);
    }

    public AbstractTeam getTeam(final Integer team) {
        return this.teams.get(team);
    }
}
