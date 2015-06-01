package nl.tudelft.ti2806.riverrush.graphics.entity;

import java.util.HashMap;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractTeam;

public class Team extends AbstractTeam {

    private HashMap<Integer, AbstractAnimal> animals;
    private BoatGroup boat;

    public Team(int id) {
        super(id);
    }

}
