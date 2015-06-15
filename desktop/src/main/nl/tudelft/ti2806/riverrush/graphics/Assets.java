package nl.tudelft.ti2806.riverrush.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Centralizes loading and creating of Textures and TextureRegions.
 */
public final class Assets {

    private static Texture
        riverBanksTexture, monkeyShipTexture, raccoonShipTexture, riverTexture,
        boatSectorTexture, raccoonTexture, monkeyTexture, flagTexture,
        endGameTexture, grassTexture, cannonballTexture,
        icebergTexture, winTexture, loseTexture,
        bootjeTexture, woodFloorTexture, lineTexture;

    public static TextureRegion
        riverBank, boatSector, river,
        monkeyShip, raccoonShip, raccoon, monkey, flag, endGame,
        grass, cannonball, iceberg,
        win, lose, bootje, woodFloor,
        line;

    private static final String dir = "data/";

    private Assets() {
        // Utility class, constructor never needed.
    }

    /**
     * Load all assets, creating each TextureRegion in the game.
     */
    public static void load() {
        riverBanksTexture = new Texture(Gdx.files.internal(dir + "field.jpg"));
        riverBank = getFullRegion(riverBanksTexture);

        monkeyShipTexture = new Texture(Gdx.files.internal(dir + "boat_monkey.png"));
        monkeyShip = getFullRegion(monkeyShipTexture);

        raccoonShipTexture = new Texture(Gdx.files.internal(dir + "boat_raccoon.png"));
        raccoonShip = getFullRegion(raccoonShipTexture);

        riverTexture = new Texture(Gdx.files.internal(dir + "river.png"));
        river = getFullRegion(riverTexture);

        boatSectorTexture = new Texture(Gdx.files.internal(dir + "sector.png"));
        boatSector = getFullRegion(boatSectorTexture);

        monkeyTexture = new Texture(Gdx.files.internal(dir + "monkey.png"));
        monkey = getFullRegion(monkeyTexture);

        raccoonTexture = new Texture(Gdx.files.internal(dir + "raccoon.png"));
        raccoon = getFullRegion(raccoonTexture);

        flagTexture = new Texture(Gdx.files.internal(dir + "flag.png"));
        flag = getFullRegion(flagTexture);

        endGameTexture = new Texture(Gdx.files.internal(dir + "end.jpg"));
        endGame = getFullRegion(endGameTexture);

        grassTexture = new Texture(Gdx.files.internal(dir + "grass.jpg"));
        grass = getFullRegion(grassTexture);

        cannonballTexture = new Texture(Gdx.files.internal(dir + "cannonball.png"));
        cannonball = getFullRegion(cannonballTexture);

        icebergTexture = new Texture(Gdx.files.internal(dir + "iceberg.png"));
        iceberg = getFullRegion(icebergTexture);

        winTexture = new Texture(Gdx.files.internal(dir + "win.png"));
        win = getFullRegion(winTexture);

        loseTexture = new Texture(Gdx.files.internal(dir + "lose.png"));
        lose = getFullRegion(loseTexture);

        bootjeTexture = new Texture(Gdx.files.internal(dir + "bootje.jpg"));
        bootje = getFullRegion(bootjeTexture);

        woodFloorTexture = new Texture(Gdx.files.internal(dir + "wood-floor.jpg"));
        woodFloor = getFullRegion(woodFloorTexture);

        lineTexture = new Texture(Gdx.files.internal(dir + "dividingLine.png"));
        line = getFullRegion(lineTexture);
    }

    /**
     * Get the loading screen image before any other assets have been loaded.
     *
     * @return
     */
    public static Image getLoadingImage() {
        Texture loadingScreenTex = new Texture(Gdx.files.internal(dir + "loading.jpeg"));
        TextureRegion loadingImage = getFullRegion(loadingScreenTex);

        Image image = new Image(loadingImage);
        image.setFillParent(true);
        return image;
    }

    /**
     * Get rid of all resources.
     */
    public static void dispose() {

    }

    private static TextureRegion getFullRegion(Texture tx) {
        return new TextureRegion(tx, 0, 0, tx.getWidth(), tx.getHeight());
    }
}
