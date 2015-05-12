package nl.tudelft.ti2806.monkeyrush.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameScreen implements Screen {

    private static int WIDTH = 800;
    private static int HEIGHT = 480;
    private final RiverGame game;
    public OrthographicCamera camera;
    public InputProcessor processor;
    private Stage leftStage;
    private Stage rightStage;
    private Stage midStage;
    private Skin skin;
    private Table table;
    private TextButton buttonPlay, buttonCredits, buttonExit, buttonScores,
            buttonAchievements;
    private Label title, author;
    private TextureAtlas atlas;
    public Level level;
    private AssetManager manager = new AssetManager();

    public GameScreen(RiverGame gm) {
        this.game = gm;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, WIDTH, HEIGHT);
        this.level = new Level(this);
        this.level.run();
    }

    @Override
    public void show() {
        this.manager.load("data/boat.jpg", Texture.class);
        this.manager.finishLoading();
        // Assets.manager.clear();
        // Assets.loading();

        this.leftStage = new Stage();
        this.midStage = new Stage();
        this.rightStage = new Stage();
        this.atlas = new TextureAtlas("uiskin.atlas");
        this.skin = new Skin(Gdx.files.internal("uiskin.json"), this.atlas);

        Texture lefttexture = new Texture(Gdx.files.internal("data/left.jpg"));
        TextureRegion leftregion = new TextureRegion(lefttexture, 0, 0, 472,
                455);

        Texture midTex = new Texture(Gdx.files.internal("data/mid.jpg"));
        TextureRegion midRegion = new TextureRegion(midTex, 0, 0, 275, 183);

        Texture righttexture = new Texture(Gdx.files.internal("data/right.jpg"));
        TextureRegion rightregion = new TextureRegion(righttexture, 0, 0, 540,
                540);

        // Image leftImage = new Image(this.skin.getPatch("left"));
        Image leftImage = new Image(leftregion);
        leftImage.setFillParent(true);
        this.leftStage.addActor(leftImage);

        Image midImage = new Image(midRegion);
        midImage.setFillParent(true);
        this.midStage.addActor(midImage);

        Image rightImage = new Image(rightregion);
        rightImage.setFillParent(true);
        this.rightStage.addActor(rightImage);

        // this.table = new Table(this.skin);
        // this.rightStage.addActor(this.table);
        // this.table.setBounds(0, 0, this.rightStage.getWidth(),
        // this.rightStage.getHeight());
        // this.title = new Label("TITLE", this.skin);
        // this.author = new Label("AUTHOR", this.skin);
        //
        // this.table.add(this.title).padBottom(30);
        // this.table.row();
        // this.table.add(this.author).padBottom(70);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (this.manager.update()) {
            this.camera.update();
            this.game.batch.setProjectionMatrix(this.camera.combined);
            this.game.batch.begin();

            // As if its paused for now
            // this.game.font.draw(this.game.batch, "HELLO", 350, 300);
            // this.game.font.draw(this.game.batch, "EYY", 350, 250);

            this.game.batch.end();
            this.leftStage.act(Gdx.graphics.getDeltaTime());
            Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth() / 3,
                    Gdx.graphics.getHeight());
            this.leftStage.draw();

            this.midStage.act(Gdx.graphics.getDeltaTime());
            Gdx.gl.glViewport(Gdx.graphics.getWidth() / 3, 0,
                    Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight());
            this.midStage.draw();

            this.rightStage.act(Gdx.graphics.getDeltaTime());
            Gdx.gl.glViewport(Gdx.graphics.getWidth() / 3 * 2, 0,
                    Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight());
            this.rightStage.draw();

            for (GameGraphics g : this.level.graphics) {
                this.game.batch.draw(g.getSprite(), g.getX(), g.getY());
                g.update();
            }
        }

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
