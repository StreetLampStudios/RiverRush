package nl.tudelft.ti2806.riverrush.desktop;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.tudelft.ti2806.Graphics.RiverGame;
import nl.tudelft.ti2806.riverrush.CoreModule;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

public class DesktopLauncher extends CoreModule {

    public static final double WIDTH = 1920;
    public static final double HEIGHT = 1080;


    public static void main(String[] arg) {
        new DesktopLauncher();

    }

    public DesktopLauncher() {
        Injector injector = Guice.createInjector(this);

        // This injector can inject all dependencies configured in CoreModule
        // and this, the desktop module.


        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.x = 0;
        config.width = (int) WIDTH;
        config.height = (int) HEIGHT;
        // config.fullscreen = true;

        // Get the game from the injector.
        // The injector should not be passed to any other classes as a
        // dependency!
        RiverGame game = injector.getInstance(RiverGame.class);
        LwjglApplication frame = new LwjglApplication(game, config);
    }

    /**
     * Method is called when creating an {@link Injector}. It configures all
     * dependencies specific to the desktop application.
     */
    @Override
    protected void configure() {
        // When injecting an AssetManager, use this specific instance.
        this.bind(AssetManager.class).toInstance(new AssetManager());
        // this.bind(Table.class).to(RunningGame.class);
    }

    @Override
    protected EventDispatcher configureEventDispatcher() {
        return null;
    }
}
