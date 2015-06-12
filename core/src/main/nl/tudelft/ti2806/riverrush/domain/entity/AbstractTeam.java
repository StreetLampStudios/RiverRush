package nl.tudelft.ti2806.riverrush.domain.entity;

import java.util.Collection;
import java.util.HashMap;

/**
 * Abstract class for all the base logic of a team.
 */
public abstract class AbstractTeam {

    private static Integer highestId = 0;
    private final Integer id;

    private final HashMap<Integer, AbstractAnimal> animals;

    /**
     * Create a team with an unique id.
     */
    public AbstractTeam() {
        this.animals = new HashMap<>();
        this.id = highestId;
        highestId++;
    }

    /**
     * Create a team with an existing id.
     *
     * @param aId The id of the team
     */
    public AbstractTeam(final int aId) {
        this.id = aId;
        this.animals = new HashMap<>();
    }

    /**
     * Get the id of the team.
     *
     * @return The id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Get an animal from the team.
     *
     * @param animalId The id of the animal
     * @return The animal
     */
    public AbstractAnimal getAnimal(final Integer animalId) {
        return this.animals.get(animalId);
    }

    /**
     * Get all the animals.
     *
     * @return the animals.
     */
    public Collection<AbstractAnimal> getAnimals() {
        return this.animals.values();
    }

    /**
     * Add an animal to the team.
     *
     * @param animal The animal to add
     */
    public void addAnimal(final AbstractAnimal animal) {
        this.animals.put(animal.getId(), animal);
    }

    /**
     * Remove an animal from the team.
     *
     * @param animalId The id of the animal to remove.
     */
    public void removeAnimal(final Integer animalId) {
        this.animals.remove(animalId);
    }

    /**
     * Get the size of the team.
     *
     * @return the amount of animals in a team
     */
    public int size() {
        return animals.values().size();
    }
}
