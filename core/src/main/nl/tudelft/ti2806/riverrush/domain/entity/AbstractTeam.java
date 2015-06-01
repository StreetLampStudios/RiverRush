package nl.tudelft.ti2806.riverrush.domain.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTeam {

  private int ID;
  private final List<AbstractAnimal> animals;

  public AbstractTeam(int id) {
    this.animals = new ArrayList<AbstractAnimal>();
    this.ID = id;

  }

  public void addAnimal(AbstractAnimal anim) {
    this.animals.add(anim);
  }
}
