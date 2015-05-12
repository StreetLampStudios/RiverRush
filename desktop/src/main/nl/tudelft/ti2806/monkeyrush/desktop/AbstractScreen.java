package nl.tudelft.ti2806.monkeyrush.desktop;

import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen {

    protected RiverGame game;

    public AbstractScreen(RiverGame game) {
        this.game = game;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
