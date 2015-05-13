package nl.tudelft.ti2806.riverrush.desktop;

import nl.tudelft.ti2806.Graphics.LoadingScreen;
import nl.tudelft.ti2806.Graphics.RiverGame;
import nl.tudelft.ti2806.riverrush.CoreModule;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class DesktopLauncher extends AbstractModule {

    private LoadingScreen loadingScreen;

    public static void main(String[] arg) {
        // This injector can inject all dependencies configured in CoreModule
        // and this, the desktop module.
        Injector injector = Guice.createInjector(new CoreModule(),
                new DesktopLauncher());

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.x = 0;
        config.width = 1920;
        config.height = 1080;
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
}
