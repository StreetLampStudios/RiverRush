package nl.tudelft.ti2806.riverrush.domain.entity;

import java.util.HashMap;

public abstract class AbstractTeam {

  private final Integer id;

  private final HashMap<Integer, AbstractAnimal> animals;

  public AbstractTeam(final int aId) {
    this.id = aId;
    this.animals = new HashMap<Integer, AbstractAnimal>();
  }

  public HashMap<Integer, AbstractAnimal> getAnimals() {
    return this.animals;
  }

  public void addAnimal(AbstractAnimal animal) {
    this.animals.put(animal.getId(), animal);
  }

  public Integer getId() {
    return this.id;
  }
}
