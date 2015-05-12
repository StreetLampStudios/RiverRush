package nl.tudelft.ti2806.monkeyrush.desktop;

import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen {


    @Override
    public abstract void pause();

    @Override
    public abstract void resume();

    @Override
    public abstract void dispose();
}
