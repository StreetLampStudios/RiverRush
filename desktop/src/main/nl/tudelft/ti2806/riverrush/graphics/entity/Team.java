package nl.tudelft.ti2806.riverrush.graphics.entity;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractTeam;

import java.util.HashMap;

public class Team extends AbstractTeam {

    private HashMap<Integer, AbstractAnimal> animals;
    private BoatGroup boat;

    public Team(final int id) {
        super(id);
    }
}
