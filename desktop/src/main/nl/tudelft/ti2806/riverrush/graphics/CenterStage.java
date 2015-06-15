package nl.tudelft.ti2806.riverrush.graphics;

import com.badlogic.gdx.scenes.scene2d.Stage;
import nl.tudelft.ti2806.riverrush.graphics.entity.LittleBoat;
import nl.tudelft.ti2806.riverrush.graphics.entity.RiverBanksActor;
import nl.tudelft.ti2806.riverrush.graphics.entity.WoodenBackground;

/**
 * Centerstage is a custom made stage that can be used to create the middle partioning of the
 * screen.
 */
public class CenterStage extends Stage {

    public static final int BOAT_HEIGHT = 30;
    private LittleBoat leftBoat;
    private LittleBoat rightBoat;
    private final float TOTALHEIGHT;
    private final float TOTALWIDTH;

    /**
     * The constructor of the center stage class.
     *
     * @param width  - specifies the width
     * @param height - specifies the height
     */
    public CenterStage(final float width, final float height) {
        this.TOTALHEIGHT = height;
        this.TOTALWIDTH = width;

        RiverBanksActor background = new RiverBanksActor(0, 0, width, height);
        this.addActor(background);

        WoodenBackground floor = new WoodenBackground(100, 50, width - 200, height - 100);
        DividingLine line = new DividingLine(300, 30, height, width / 2);

        float top_x = width - 200 - this.TOTALWIDTH / 40; // At max the boat should be at the
        // topMinus a margin of 30

        this.leftBoat = new LittleBoat(100, top_x, TOTALHEIGHT / 5,
            this.TOTALWIDTH / 40, this.TOTALHEIGHT / 4);
        this.rightBoat = new LittleBoat(100, top_x, TOTALHEIGHT / 5 * 3,
            this.TOTALWIDTH / 40, this.TOTALHEIGHT / 4);

        this.addActor(floor);
        this.addActor(line);
        this.addActor(this.leftBoat);
        this.addActor(this.rightBoat);

    }

    public void updateProgress(int teamID, double progress) {
        if (teamID == 0) {
            this.leftBoat.setProgress(progress);
        } else {
            this.rightBoat.setProgress(progress);
        }
    }
}
