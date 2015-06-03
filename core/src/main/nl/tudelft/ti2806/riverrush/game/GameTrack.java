package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractTeam;

/**
 * Representation of a game track.
 */
public interface GameTrack {

    /**
     * Value from 0 to 100 representing the progress of the left team.
     *
     * @return the distance from the start.
     */
    Integer getDistanceLeftTeam();

    /**
     * Value from 0 to 100 representing the progress of the right team.
     *
     * @return the distance from the start.
     */
    Integer getDistanceRightTeam();

    /**
     * Get the left team.
     *
     * @return The team
     */
    AbstractTeam getLeftTeam();

    /**
     * Get the right team.
     *
     * @return The team
     */
    AbstractTeam getRightReam();
}
