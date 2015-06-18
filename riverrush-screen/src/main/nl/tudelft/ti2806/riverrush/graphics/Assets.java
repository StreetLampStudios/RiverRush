package nl.tudelft.ti2806.riverrush.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

/**
 * Centralizes loading and creating of Textures and TextureRegions.
 */
public final class Assets {

    private static Texture riverBanksTexture, monkeyShipTexture, raccoonShipTexture, riverTexture,
        boatSectorTexture, raccoonBrownTexture, raccoonBlackTexture, raccoonBlueTexture,
        raccoonOrangeTexture, raccoonGreenTexture, raccoonPinkTexture, raccoonPurpleTexture,
        raccoonRedTexture, raccoonWhiteTexture, raccoonYellowTexture, monkeyBrownTexture,
        monkeyBlackTexture, monkeyBlueTexture, monkeyOrangeTexture, monkeyGreenTexture,
        monkeyPinkTexture, monkeyPurpleTexture, monkeyRedTexture, monkeyWhiteTexture,
        monkeyYellowTexture, flagTexture, endGameTexture, grassTexture, cannonballTexture,
        icebergTexture, winTexture, loseTexture, woodFloorTexture, lineTexture,
        shadowTexture, bootjeMonkeyTexture, bootjeRaccoonTexture;

    public static TextureRegion riverBank, boatSector, river, monkeyShip, raccoonShip, raccoon,
        monkeyBrown, monkeyBlack, monkeyBlue, monkeyOrange, monkeyGreen, monkeyPink,
        monkeyPurple, monkeyRed, monkeyWhite, monkeyYellow, raccoonBrown, raccoonBlack,
        raccoonBlue, raccoonOrange, raccoonGreen, raccoonPink, raccoonPurple, raccoonRed,
        raccoonWhite, raccoonYellow, flag, endGame, grass, cannonball, iceberg, win, lose,
        bootjeMonkey, bootjeRaccoon, woodFloor, line, shadow;

    public static ArrayList<TextureRegion> raccoonMap, monkeyMap;

    private static final String dir = "data/";

    private Assets() {
        // Utility class, constructor never needed.
    }

    /**
     * Load all assets, creating each TextureRegion in the game.
     */
    public static void load() {
        raccoonMap = new ArrayList<TextureRegion>();
        monkeyMap = new ArrayList<TextureRegion>();

        riverBanksTexture = new Texture(Gdx.files.internal(dir + "field.jpg"));
        riverBank = getFullRegion(riverBanksTexture);

        monkeyShipTexture = new Texture(Gdx.files.internal(dir + "boat_monkey.png"));
        monkeyShip = getFullRegion(monkeyShipTexture);

        raccoonShipTexture = new Texture(Gdx.files.internal(dir + "boat_raccoon.png"));
        raccoonShip = getFullRegion(raccoonShipTexture);

        riverTexture = new Texture(Gdx.files.internal(dir + "river.png"));
        river = getFullRegion(riverTexture);

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

        bootjeMonkeyTexture = new Texture(Gdx.files.internal(dir + "bootje4_monkey.png"));
        bootjeMonkey = getFullRegion(bootjeMonkeyTexture);

        bootjeRaccoonTexture = new Texture(Gdx.files.internal(dir + "bootje4_raccoon.png"));
        bootjeRaccoon = getFullRegion(bootjeRaccoonTexture);

        woodFloorTexture = new Texture(Gdx.files.internal(dir + "wood-floor.jpg"));
        woodFloor = getFullRegion(woodFloorTexture);

        lineTexture = new Texture(Gdx.files.internal(dir + "dividingLine.png"));
        line = getFullRegion(lineTexture);

        shadowTexture = new Texture(Gdx.files.internal(dir + "shadow.png"));
        shadow = getFullRegion(shadowTexture);

        monkeyBrownTexture = new Texture(Gdx.files.internal(dir + "monkeyBrown.png"));
        monkeyBrown = getFullRegion(monkeyBrownTexture);

        monkeyBlackTexture = new Texture(Gdx.files.internal(dir + "monkeyBlack.png"));
        monkeyBlack = getFullRegion(monkeyBlackTexture);

        monkeyBlueTexture = new Texture(Gdx.files.internal(dir + "monkeyBlue.png"));
        monkeyBlue = getFullRegion(monkeyBlueTexture);

        monkeyOrangeTexture = new Texture(Gdx.files.internal(dir + "monkeyOrange.png"));
        monkeyOrange = getFullRegion(monkeyOrangeTexture);

        monkeyGreenTexture = new Texture(Gdx.files.internal(dir + "monkeyGreen.png"));
        monkeyGreen = getFullRegion(monkeyGreenTexture);

        monkeyPinkTexture = new Texture(Gdx.files.internal(dir + "monkeyPink.png"));
        monkeyPink = getFullRegion(monkeyPinkTexture);

        monkeyPurpleTexture = new Texture(Gdx.files.internal(dir + "monkeyPurple.png"));
        monkeyPurple = getFullRegion(monkeyPurpleTexture);

        monkeyRedTexture = new Texture(Gdx.files.internal(dir + "monkeyRed.png"));
        monkeyRed = getFullRegion(monkeyRedTexture);

        monkeyWhiteTexture = new Texture(Gdx.files.internal(dir + "monkeyWhite.png"));
        monkeyWhite = getFullRegion(monkeyWhiteTexture);

        monkeyYellowTexture = new Texture(Gdx.files.internal(dir + "monkeyYellow.png"));
        monkeyYellow = getFullRegion(monkeyYellowTexture);

        raccoonBrownTexture = new Texture(Gdx.files.internal(dir + "raccoonBrown.png"));
        raccoonBrown = getFullRegion(raccoonBrownTexture);

        raccoonBlackTexture = new Texture(Gdx.files.internal(dir + "raccoonBlack.png"));
        raccoonBlack = getFullRegion(raccoonBlackTexture);

        raccoonBlueTexture = new Texture(Gdx.files.internal(dir + "raccoonBlue.png"));
        raccoonBlue = getFullRegion(raccoonBlueTexture);

        raccoonOrangeTexture = new Texture(Gdx.files.internal(dir + "raccoonOrange.png"));
        raccoonOrange = getFullRegion(raccoonOrangeTexture);

        raccoonGreenTexture = new Texture(Gdx.files.internal(dir + "raccoonGreen.png"));
        raccoonGreen = getFullRegion(raccoonGreenTexture);

        raccoonPinkTexture = new Texture(Gdx.files.internal(dir + "raccoonPink.png"));
        raccoonPink = getFullRegion(raccoonPinkTexture);

        raccoonPurpleTexture = new Texture(Gdx.files.internal(dir + "raccoonPurple.png"));
        raccoonPurple = getFullRegion(raccoonPurpleTexture);

        raccoonRedTexture = new Texture(Gdx.files.internal(dir + "raccoonRed.png"));
        raccoonRed = getFullRegion(raccoonRedTexture);

        raccoonWhiteTexture = new Texture(Gdx.files.internal(dir + "raccoonWhite.png"));
        raccoonWhite = getFullRegion(raccoonWhiteTexture);

        raccoonYellowTexture = new Texture(Gdx.files.internal(dir + "raccoonYellow.png"));
        raccoonYellow = getFullRegion(raccoonYellowTexture);

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
