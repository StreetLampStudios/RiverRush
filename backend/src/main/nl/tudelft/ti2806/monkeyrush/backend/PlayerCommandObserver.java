package nl.tudelft.ti2806.monkeyrush.backend;

/**
 * Created by thomas on 6-5-15.
 */
public interface PlayerCommandObserver {
    void onCommand(PlayerCommand command);
}
