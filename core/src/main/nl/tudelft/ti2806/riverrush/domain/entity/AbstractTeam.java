package nl.tudelft.ti2806.riverrush.domain.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTeam {

    private int id;
    private final List<AbstractAnimal> animals;

    public AbstractTeam(final int aId) {
        this.id = aId;
        this.animals = new ArrayList<>();
    }

    public void addAnimal(AbstractAnimal animal) {
        this.animals.add(animal);
    }

    public Integer getId() {
        return this.id;
    }
}
