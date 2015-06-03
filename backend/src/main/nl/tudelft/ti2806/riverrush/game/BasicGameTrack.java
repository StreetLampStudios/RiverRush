package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.Team;

/**
 * A basic implementation of a game track.
 */
public class BasicGameTrack extends AbstractGameTrack {

    public BasicGameTrack(final Integer aTrackLength) {
        super(aTrackLength);
        this.addTeam(new Team());
        this.addTeam(new Team());
    }
}
