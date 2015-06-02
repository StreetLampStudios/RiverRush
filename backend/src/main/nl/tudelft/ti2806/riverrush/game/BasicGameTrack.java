package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.Team;

/**
 * A basic implementation of a game track.
 */
public class BasicGameTrack extends AbstractGameTrack {

    public BasicGameTrack(Integer aTrackLength) {
        super(new Team(), new Team(), aTrackLength);
    }
}
