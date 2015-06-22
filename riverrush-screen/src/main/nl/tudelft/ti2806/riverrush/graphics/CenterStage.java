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

    /**
     * The size of the given boat on this stage.
     */
    public static final int BOAT_HEIGHT = 30;
    private static final int BG_X_POS = 100;
    private static final int BG_Y_POS = 50;
    private static final int WIDTH_OFFSET = 200;
    private static final int HEIGHT_OFFSET = 100;
    private static final int DIV_X_POS = 300;
    private static final int DIV_Y_POS = 30;
    private static final int TOP_WIDTH_OFFSET = 200;
    private static final int TOP_WIDTH_OFFSET_PERCENTAGE = 40;
    private static final int BOAT_X_POS = 100;
    private static final int TOTAL_HEIGHT_OFFSET_PERCENTAGE = 5;
    private static final int TOTAL_HEIGHT_OFFSET_MULTIPLIER = 3;
    private static final int TOTALHEIGHT_DIVISION_FACTOR = 4;
    private final LittleBoat leftBoat;
    private final LittleBoat rightBoat;

    /**
     * The constructor of the center stage class.
     *
     * @param width  - specifies the width
     * @param height - specifies the height
     */
    public CenterStage(final float width, final float height) {
        RiverBanksActor background = new RiverBanksActor(0, 0, width, height);
        this.addActor(background);

        WoodenBackground floor = new WoodenBackground(BG_X_POS, BG_Y_POS,
                width - WIDTH_OFFSET, height - HEIGHT_OFFSET);
        DividingLine line = new DividingLine(DIV_X_POS, DIV_Y_POS, height, width / 2);

        float topX = width - TOP_WIDTH_OFFSET - width
                / TOP_WIDTH_OFFSET_PERCENTAGE; // At max the boat should be at the
        // topMinus a margin of 30

        this.rightBoat = new LittleBoat(BOAT_X_POS, topX, height / TOTAL_HEIGHT_OFFSET_PERCENTAGE,
                width / TOP_WIDTH_OFFSET_PERCENTAGE, height
                / TOTALHEIGHT_DIVISION_FACTOR, Assets.bootjeRaccoon);
        this.leftBoat = new LittleBoat(BOAT_X_POS, topX, height
                / TOTAL_HEIGHT_OFFSET_PERCENTAGE * TOTAL_HEIGHT_OFFSET_MULTIPLIER, width
                / TOP_WIDTH_OFFSET_PERCENTAGE, height
                / TOTALHEIGHT_DIVISION_FACTOR, Assets.bootjeMonkey);

        this.addActor(floor);
        this.addActor(line);
        this.addActor(this.leftBoat);
        this.addActor(this.rightBoat);

    }

    /**
     * Update the progress bar.
     *
     * @param teamID   refers to the team ID for which the progress must be updated.
     * @param progress refers to the progress made by the given team.
     */
    public void updateProgress(final int teamID, final double progress) {
        if (teamID == 0) {
            this.leftBoat.setProgress(progress);
        } else {
            this.rightBoat.setProgress(progress);
        }
    }
}
