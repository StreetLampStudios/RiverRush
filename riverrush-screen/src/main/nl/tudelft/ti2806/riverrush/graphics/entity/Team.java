package nl.tudelft.ti2806.riverrush.graphics.entity;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractTeam;

/**
 * Constructs a team that holds a boatGroup.
 */
public class Team extends AbstractTeam {

    private BoatGroup boat;

    /**
     * Construct a team with a given ID.
     *
     * @param id the given ID.
     */
    public Team(final int id) {
        super(id);
    }

    public BoatGroup getBoat() {
        return this.boat;
    }

    public void setBoat(final BoatGroup newBoat) {
        this.boat = newBoat;
    }
}
