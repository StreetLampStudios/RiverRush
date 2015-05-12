package nl.tudelft.ti2806.monkeyrush.desktop;

import java.util.LinkedList;
import java.util.List;

public class Level {

    protected GameScreen screen;
    protected List<GameGraphics> graphics = new LinkedList<GameGraphics>();

    public Level(GameScreen scrn) {
        this.screen = scrn;
    }

    public void update() {

    }

    public void add(GameGraphics graph) {
        this.graphics.add(graph);
    }

    public void run() {
        this.add(new BoatGraphic("data/boat.jpg", 800, 500));
    }
}
