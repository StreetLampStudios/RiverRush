package nl.tudelft.ti2806.riverrush.graphics.entity;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractTeam;

public class Team extends AbstractTeam {

    private BoatGroup boat;

    public Team(final int id) {
        super(id);
    }

    public BoatGroup getBoat() {
        return this.boat;
    }

    public void setBoat(final BoatGroup boat) {
        this.boat = boat;
    }
}
