package nl.tudelft.ti2806.riverrush.domain.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTeam {

    private static Integer highestId = 0;
    private final Integer id;

    private final List<AbstractAnimal> animals;

    public AbstractTeam() {
        this.animals = new ArrayList<>();
        this.id = highestId + 1;
        highestId++;
    }

    public AbstractTeam(Integer teamId) {
        this.animals = new ArrayList<>();
        this.id = teamId;
        if (teamId > highestId) {
            highestId = teamId;
        }
    }

    public List<AbstractAnimal> getAnimals() {
        return animals;
    }

    public void addAnimal(AbstractAnimal animal) {
        this.animals.add(animal);
    }

    public Integer getId() {
        return this.id;
    }
}
