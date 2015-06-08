package nl.tudelft.ti2806.riverrush.domain.entity;

import nl.tudelft.ti2806.riverrush.failfast.FailIf;

import java.util.Collection;
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

    /**
     * Adds an animal to the team.
     *
     * @param animal - Animal you want to add
     */
    public void addAnimal(final AbstractAnimal animal) {
        FailIf.isNull(animal);
        this.animals.put(animal.getId(), animal);
    }

    public Integer getId() {
        return this.id;
    }

    /**
     * Returns an animal.
     *
     * @param animalId - the id of the animal you want to get
     * @return the animal of class {@link AbstractAnimal}
     */
    public AbstractAnimal getAnimal(final Integer animalId) {
        FailIf.isNull(animalId);
        return animals.get(animalId);
    }


    /**
     * Removes an animal.
     *
     * @param animal - Integer that represents an animal
     */
    public void removeAnimal(final Integer animal) {
        FailIf.isNull(animal);
        animals.remove(animal);
    }

    public Collection<AbstractAnimal> getAnimals() {
        return animals.values();
    }
}
