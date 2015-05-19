package nl.tudelft.ti2806.riverrush.graphics;

import com.badlogic.gdx.Screen;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

public abstract class AbstractScreen implements Screen {

    protected final EventDispatcher dispatcher;

    public AbstractScreen(EventDispatcher eventDispatcher) {
        this.dispatcher = eventDispatcher;
    }

    @Override
    public abstract void pause();

    @Override
    public abstract void resume();

    @Override
    public abstract void dispose();
}
