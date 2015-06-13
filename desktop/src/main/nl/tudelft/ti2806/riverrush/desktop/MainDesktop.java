package nl.tudelft.ti2806.riverrush.desktop;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.tudelft.ti2806.riverrush.CoreModule;
import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.controller.RenderController;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.Client;

import java.net.URISyntaxException;

/**
 * This class is the main class to be ran when starting the game. This class sets up the graphics
 * and the client connections.
 */
public class MainDesktop extends CoreModule {
    private final Injector injector;
    private final EventDispatcher eventDispatcher;

    /**
     * Calls the main desktop constructor that starts the game.
     *
     * @param arg not used
     * @throws URISyntaxException handles the situation where the URI has the wrong syntax.
     */
    public static void main(final String[] arg) throws URISyntaxException, InterruptedException {
        new MainDesktop();
    }

    /**
     * Constructor for main desktop. Configures the client connections and sets up the graphics.
     *
     * @throws URISyntaxException handles the situation where the URI has the wrong syntax.
     */
    public MainDesktop() throws URISyntaxException, InterruptedException {
        this.injector = Guice.createInjector(this);

        this.setupGraphics();

        Client client = new Client("localhost", this.configureRendererProtocol());
        RenderController cntrl = this.injector.getInstance(RenderController.class);
        cntrl.setClient(client);
        client.setController(cntrl);

        this.eventDispatcher = this.injector.getInstance(EventDispatcher.class);
        client.connect();
    }

    /**
     * Creates a Lwjgl Configurations with the given height and width. It will then get an instance
     * of the game class and use it to create the application.
     */
    private void setupGraphics() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.x = 0;
        //TODO: Don't hardcode screen size
        config.width = 1920;
        config.height = 1080;

        Game game = this.injector.getInstance(Game.class);
        new LwjglApplication(game, config);
    }

    /**
     * Method is called when creating an {@link Injector}. It configures all dependencies specific
     * to the desktop application.
     */
    @Override
    protected void configure() {
        super.configure();
        this.bind(AssetManager.class).toInstance(new AssetManager());
        this.bind(Controller.class).to(RenderController.class);
    }
}
