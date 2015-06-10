package nl.tudelft.ti2806.riverrush.domain.entity;

import java.util.HashMap;

public abstract class AbstractTeam {

    private static Integer highestId = 0;
    private final Integer id;

    private final HashMap<Integer, AbstractAnimal> animals;

    public AbstractTeam() {
        this.animals = new HashMap<>();
        this.id = highestId;
        highestId++;
    }

    public AbstractTeam(final int aId) {
        this.id = aId;
        this.animals = new HashMap<>();
    }

    public HashMap<Integer, AbstractAnimal> getAnimals() {
        return this.animals;
    }

    public void addAnimal(final AbstractAnimal animal) {
        this.animals.put(animal.getId(), animal);
    }

    public Integer getId() {
        return this.id;
    }

    /**
     * @return the amount of animals in a team
     */
    public int size() {
        return animals.values().size();
    }
}
