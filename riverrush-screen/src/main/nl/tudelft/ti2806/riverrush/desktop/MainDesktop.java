package nl.tudelft.ti2806.riverrush.desktop;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.tudelft.ti2806.riverrush.CoreModule;
import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.controller.RenderController;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.Client;

import java.awt.*;
import java.net.URISyntaxException;

/**
 * This class is the main class to be ran when starting the game. This class sets up the graphics
 * and the client connections.
 */
public class MainDesktop extends CoreModule {
    private Injector injector;

    /**
     * Calls the main desktop constructor that starts the game.
     *
     * [url] [fullscreen (true/false)]
     * [fullscreen (true/false)]
     *
     * @param arg not used
     * @throws URISyntaxException   handles the situation where the URI has the wrong syntax.
     * @throws InterruptedException handles the situation where it interrupts.
     */
    public static void main(final String[] arg) throws URISyntaxException, InterruptedException {
        if (arg.length == 2) {
            new MainDesktop(arg[0], Boolean.parseBoolean(arg[1]));
        } else if (arg.length == 1) {
            new MainDesktop(Boolean.parseBoolean(arg[0]));
        } else {
            new MainDesktop();
        }
    }

    /**
     * Constructor for main desktop.
     *
     * @throws URISyntaxException   handles the situation where the URI has the wrong syntax.
     * @throws InterruptedException handles the situation where it interrupts.
     */
    public MainDesktop() throws URISyntaxException, InterruptedException {
        super();

        this.init("localhost", false);
    }

    /**
     * Constructor for main desktop.
     *
     * @param fullscreen If the game should be fullscreen
     * @throws URISyntaxException   handles the situation where the URI has the wrong syntax.
     * @throws InterruptedException handles the situation where it interrupts.
     */
    public MainDesktop(final boolean fullscreen) throws URISyntaxException, InterruptedException {
        super();

        this.init("localhost", fullscreen);
    }

    /**
     * Constructor for main desktop.
     *
     * @param url        URL to the server
     * @param fullscreen If the game should be fullscreen
     * @throws URISyntaxException   handles the situation where the URI has the wrong syntax.
     * @throws InterruptedException handles the situation where it interrupts.
     */
    public MainDesktop(final String url, final boolean fullscreen)
        throws URISyntaxException, InterruptedException {
        super();

        this.init(url, fullscreen);
    }

    /**
     * Init for main desktop. Configures the client connections and sets up the graphics.
     *
     * @param url        URL to the server
     * @param fullscreen If the game should be fullscreen
     * @throws URISyntaxException   handles the situation where the URI has the wrong syntax.
     * @throws InterruptedException handles the situation where it interrupts.
     */
    private void init(final String url, final boolean fullscreen) throws URISyntaxException {
        this.injector = Guice.createInjector(this);

        this.setupGraphics(fullscreen);

        Client client = new Client(url, this.configureRendererProtocol());
        RenderController controller = this.injector.getInstance(RenderController.class);
        controller.setClient(client);
        client.setController(controller);

        client.connect();
    }

    /**
     * Creates a Lwjgl Configurations with the given height and width. It will then get an instance
     * of the game class and use it to create the application.
     *
     * @param fullscreen If the game should be fullscreen
     */
    private void setupGraphics(final boolean fullscreen) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "RiverRush";
        config.width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        config.height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        config.fullscreen = fullscreen;

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
