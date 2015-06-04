package nl.tudelft.ti2806.riverrush.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.graphics.entity.RiverBanksActor;

/**
 * Centerstage is a custom made stage that can be used to create the middle partioning of the
 * screen.
 */
public class CenterStage extends Table {

    public static final int BOAT_HEIGHT = 30;
    private LittleBoat leftBoat;
    private LittleBoat rightBoat;
    private final float TOTALHEIGHT;
    private final float TOTALWIDTH;

    /**
     * The constructor of the center stage class.
     *
     * @param assets - specifies the assets
     * @param width  - specifies the width
     * @param height - specifies the height
     */
    @Inject
    public CenterStage(final AssetManager assets, final float width, final float height) {
        this.setBounds(0, 0, width, height);
        this.TOTALHEIGHT = height;
        this.TOTALWIDTH = width;

        RiverBanksActor background = new RiverBanksActor(assets, 0, 0, width, height);
        this.addActor(background);

        WoodenBackground floor = new WoodenBackground(assets, 100, 50, width - 200, height - 100);
        DividingLine line = new DividingLine(assets, 300, 30, width / 2, height);

        float top_y = TOTALHEIGHT
            - 2 * BOAT_HEIGHT   // At max the boat should be at the top
            - 30;               // Minus a margin of 30

        leftBoat  = new LittleBoat(assets, TOTALWIDTH / 5, BOAT_HEIGHT, top_y, TOTALHEIGHT / 4, TOTALWIDTH / 10);
        rightBoat = new LittleBoat(assets, TOTALWIDTH / 5 * 3, BOAT_HEIGHT, top_y, TOTALHEIGHT / 4, TOTALWIDTH / 10);

        this.addActor(floor);
        this.addActor(line);
        this.addActor(leftBoat);
        this.addActor(rightBoat);


    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void updateProgress(int teamID, double progress) {
        if (teamID == 0) {
            leftBoat.setProgress(progress);
        } else {
            rightBoat.setProgress(progress);
        }
    }
}
