package nl.tudelft.ti2806.riverrush.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import nl.tudelft.ti2806.riverrush.sound.Sound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Centralizes loading and creating of Textures and TextureRegions.
 */
public final class Assets {

    private static Texture riverBanksTexture, monkeyShipTexture, raccoonShipTexture, riverTexture,
        raccoonBrownTexture, raccoonBlackTexture, raccoonBlueTexture,
        raccoonOrangeTexture, raccoonGreenTexture, raccoonPinkTexture, raccoonPurpleTexture,
        raccoonRedTexture, raccoonWhiteTexture, raccoonYellowTexture, monkeyBrownTexture,
        monkeyBlackTexture, monkeyBlueTexture, monkeyOrangeTexture, monkeyGreenTexture,
        monkeyPinkTexture, monkeyPurpleTexture, monkeyRedTexture, monkeyWhiteTexture,
        monkeyYellowTexture, flagTexture, endGameTexture, grassTexture, cannonballTexture,
        icebergTexture, woodFloorTexture, lineTexture, qrTexture,
        shadowTexture, bootjeMonkeyTexture, bootjeRaccoonTexture;

    /**
     * These are the texture regions extracted from the set textures.
     * These are made public static rather than private with accessor
     * methods due to the amount of necessary textures.
     */
    public static TextureRegion riverBank, river, monkeyShip, raccoonShip, qr,
        monkeyBrown, monkeyBlack, monkeyBlue, monkeyOrange, monkeyGreen, monkeyPink,
        monkeyPurple, monkeyRed, monkeyWhite, monkeyYellow, raccoonBrown, raccoonBlack,
        raccoonBlue, raccoonOrange, raccoonGreen, raccoonPink, raccoonPurple, raccoonRed,
        raccoonWhite, raccoonYellow, flag, endGame, grass, cannonball, iceberg,
        bootjeMonkey, bootjeRaccoon, woodFloor, line, shadow;

    /**
     * These are the lists of varying raccoon and monkey sprites.
     */
    public static List<TextureRegion> raccoonMap, monkeyMap;

    public static Sound backgroundMusic;

    public static Sound cheeringSound;

    public static Sound waitingMusic;

    private static final String DIRECTORY = "data/";

    /**
     * Overrides the default constructor.
     */
    private Assets() {
        // Utility class, constructor never needed.
    }

    /**
     * Load all assets, creating each TextureRegion in the game.
     */
    public static void load() throws IOException {
        raccoonMap = new ArrayList<TextureRegion>();
        monkeyMap = new ArrayList<TextureRegion>();

        riverBanksTexture = new Texture(Gdx.files.internal(DIRECTORY + "field.jpg"));
        riverBank = getFullRegion(riverBanksTexture);

        monkeyShipTexture = new Texture(Gdx.files.internal(DIRECTORY + "boat_monkey.png"));
        monkeyShip = getFullRegion(monkeyShipTexture);

        raccoonShipTexture = new Texture(Gdx.files.internal(DIRECTORY + "boat_raccoon.png"));
        raccoonShip = getFullRegion(raccoonShipTexture);

        riverTexture = new Texture(Gdx.files.internal(DIRECTORY + "river.png"));
        river = getFullRegion(riverTexture);

        flagTexture = new Texture(Gdx.files.internal(DIRECTORY + "flag.png"));
        flag = getFullRegion(flagTexture);

        endGameTexture = new Texture(Gdx.files.internal(DIRECTORY + "end.jpg"));
        endGame = getFullRegion(endGameTexture);

        grassTexture = new Texture(Gdx.files.internal(DIRECTORY + "grass.jpg"));
        grass = getFullRegion(grassTexture);

        cannonballTexture = new Texture(Gdx.files.internal(DIRECTORY + "cannonball.png"));
        cannonball = getFullRegion(cannonballTexture);

        icebergTexture = new Texture(Gdx.files.internal(DIRECTORY + "iceberg.png"));
        iceberg = getFullRegion(icebergTexture);

        bootjeMonkeyTexture = new Texture(Gdx.files.internal(DIRECTORY + "bootje4_monkey.png"));
        bootjeMonkey = getFullRegion(bootjeMonkeyTexture);

        bootjeRaccoonTexture = new Texture(Gdx.files.internal(DIRECTORY + "bootje4_raccoon.png"));
        bootjeRaccoon = getFullRegion(bootjeRaccoonTexture);

        woodFloorTexture = new Texture(Gdx.files.internal(DIRECTORY + "wood-floor.jpg"));
        woodFloor = getFullRegion(woodFloorTexture);

        lineTexture = new Texture(Gdx.files.internal(DIRECTORY + "dividingLine.png"));
        line = getFullRegion(lineTexture);

        shadowTexture = new Texture(Gdx.files.internal(DIRECTORY + "shadow.png"));
        shadow = getFullRegion(shadowTexture);

        monkeyBrownTexture = new Texture(Gdx.files.internal(DIRECTORY + "monkeyBrown.png"));
        monkeyBrown = getFullRegion(monkeyBrownTexture);

        monkeyBlackTexture = new Texture(Gdx.files.internal(DIRECTORY + "monkeyBlack.png"));
        monkeyBlack = getFullRegion(monkeyBlackTexture);

        monkeyBlueTexture = new Texture(Gdx.files.internal(DIRECTORY + "monkeyBlue.png"));
        monkeyBlue = getFullRegion(monkeyBlueTexture);

        monkeyOrangeTexture = new Texture(Gdx.files.internal(DIRECTORY + "monkeyOrange.png"));
        monkeyOrange = getFullRegion(monkeyOrangeTexture);

        monkeyGreenTexture = new Texture(Gdx.files.internal(DIRECTORY + "monkeyGreen.png"));
        monkeyGreen = getFullRegion(monkeyGreenTexture);

        monkeyPinkTexture = new Texture(Gdx.files.internal(DIRECTORY + "monkeyPink.png"));
        monkeyPink = getFullRegion(monkeyPinkTexture);

        monkeyPurpleTexture = new Texture(Gdx.files.internal(DIRECTORY + "monkeyPurple.png"));
        monkeyPurple = getFullRegion(monkeyPurpleTexture);

        monkeyRedTexture = new Texture(Gdx.files.internal(DIRECTORY + "monkeyRed.png"));
        monkeyRed = getFullRegion(monkeyRedTexture);

        monkeyWhiteTexture = new Texture(Gdx.files.internal(DIRECTORY + "monkeyWhite.png"));
        monkeyWhite = getFullRegion(monkeyWhiteTexture);

        monkeyYellowTexture = new Texture(Gdx.files.internal(DIRECTORY + "monkeyYellow.png"));
        monkeyYellow = getFullRegion(monkeyYellowTexture);

        raccoonBrownTexture = new Texture(Gdx.files.internal(DIRECTORY + "raccoonBrown.png"));
        raccoonBrown = getFullRegion(raccoonBrownTexture);

        raccoonBlackTexture = new Texture(Gdx.files.internal(DIRECTORY + "raccoonBlack.png"));
        raccoonBlack = getFullRegion(raccoonBlackTexture);

        raccoonBlueTexture = new Texture(Gdx.files.internal(DIRECTORY + "raccoonBlue.png"));
        raccoonBlue = getFullRegion(raccoonBlueTexture);

        raccoonOrangeTexture = new Texture(Gdx.files.internal(DIRECTORY + "raccoonOrange.png"));
        raccoonOrange = getFullRegion(raccoonOrangeTexture);

        raccoonGreenTexture = new Texture(Gdx.files.internal(DIRECTORY + "raccoonGreen.png"));
        raccoonGreen = getFullRegion(raccoonGreenTexture);

        raccoonPinkTexture = new Texture(Gdx.files.internal(DIRECTORY + "raccoonPink.png"));
        raccoonPink = getFullRegion(raccoonPinkTexture);

        raccoonPurpleTexture = new Texture(Gdx.files.internal(DIRECTORY + "raccoonPurple.png"));
        raccoonPurple = getFullRegion(raccoonPurpleTexture);

        raccoonRedTexture = new Texture(Gdx.files.internal(DIRECTORY + "raccoonRed.png"));
        raccoonRed = getFullRegion(raccoonRedTexture);

        raccoonWhiteTexture = new Texture(Gdx.files.internal(DIRECTORY + "raccoonWhite.png"));
        raccoonWhite = getFullRegion(raccoonWhiteTexture);

        raccoonYellowTexture = new Texture(Gdx.files.internal(DIRECTORY + "raccoonYellow.png"));
        raccoonYellow = getFullRegion(raccoonYellowTexture);

        qrTexture = new Texture(Gdx.files.internal(DIRECTORY + "qr.jpg"));
        qr = getFullRegion(qrTexture);

        monkeyMap.add(monkeyBrown);
        monkeyMap.add(monkeyBlack);
        monkeyMap.add(monkeyBlue);
        monkeyMap.add(monkeyGreen);
        monkeyMap.add(monkeyOrange);
        monkeyMap.add(monkeyPink);
        monkeyMap.add(monkeyPurple);
        monkeyMap.add(monkeyRed);
        monkeyMap.add(monkeyWhite);
        monkeyMap.add(monkeyYellow);

        raccoonMap.add(raccoonBrown);
        raccoonMap.add(raccoonBlack);
        raccoonMap.add(raccoonBlue);
        raccoonMap.add(raccoonGreen);
        raccoonMap.add(raccoonOrange);
        raccoonMap.add(raccoonPink);
        raccoonMap.add(raccoonPurple);
        raccoonMap.add(raccoonRed);
        raccoonMap.add(raccoonWhite);
        raccoonMap.add(raccoonYellow);

        backgroundMusic = new Sound(DIRECTORY + "backgroundMusic.wav");
        cheeringSound = new Sound(DIRECTORY + "cheering.wav");
        waitingMusic = new Sound(DIRECTORY + "waitingMusic.wav");
    }

    /**
     * Get the loading screen image before any other assets have been loaded.
     *
     * @return the newly created loading screen image.
     */
    public static Image getLoadingImage() {
        Texture loadingScreenTex = new Texture(Gdx.files.internal(DIRECTORY + "loading.jpeg"));
        TextureRegion loadingImage = getFullRegion(loadingScreenTex);

        Image image = new Image(loadingImage);
        image.setFillParent(true);
        return image;
    }

    /**
     * Get rid of all resources.
     */
    public static void dispose() {
        //Has to remain empty.
    }

    /**
     * Return the full texture as a texture region.
     * @param tx refers to the texture which a texture region needs to be extracted from.
     * @return the full texture region.
     */
    private static TextureRegion getFullRegion(final Texture tx) {
        return new TextureRegion(tx, 0, 0, tx.getWidth(), tx.getHeight());
    }
}
