package nl.tudelft.ti2806.riverrush.state;

public class AnimalJumping implements AnimalState {
    @Override
    public AnimalState jump() {
        return this;
    }
}
