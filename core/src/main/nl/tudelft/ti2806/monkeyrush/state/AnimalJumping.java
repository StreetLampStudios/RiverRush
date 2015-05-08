package nl.tudelft.ti2806.monkeyrush.state;

public class AnimalJumping implements AnimalState {
    @Override
    public AnimalState jump() {
        return this;
    }
}
