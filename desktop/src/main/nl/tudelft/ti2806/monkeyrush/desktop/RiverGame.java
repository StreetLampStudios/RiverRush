package nl.tudelft.ti2806.monkeyrush.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Shared application class.
 */
@Singleton
public class RiverGame extends Game {

  private final Provider<LoadingScreen> provider;
  protected SpriteBatch batch;
    protected BitmapFont font;

    // We don't need this anymore.
    // public AssetManager manager = new AssetManager();


  private LoadingScreen loadingScreen;

  /**
   * Automagically inject the agument to this constructor,
   * whenever a RiverGame is created through Guice dependency injection.
   */
  @Inject
  public RiverGame(final Provider<LoadingScreen> provider) {
    this.provider = provider;
  }

  @Override
    public void create() {
        this.loadingScreen = provider.get();
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.setScreen(loadingScreen);

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
    }

}
