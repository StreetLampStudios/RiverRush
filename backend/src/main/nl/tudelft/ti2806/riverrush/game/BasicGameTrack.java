package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.Team;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * A basic implementation of a game track.
 */
public class BasicGameTrack extends GameTrack {

    /**
     * Create a basic level.
     *
     * @param dispatcher - See {@link EventDispatcher}
     */
    public BasicGameTrack(final EventDispatcher dispatcher) {
        super("-[@2]-[@2]-[@8]-[@8]-[@2]-[@8]-", dispatcher);
        // --[@2]-[@2]---[@8]--[@8]--[@2]-[@8]--
        // --[#5]-[#5]---[@5]--[#5]--[#5]-[#5]--
        this.addTeam(new Team());
        this.addTeam(new Team());
    }
}
