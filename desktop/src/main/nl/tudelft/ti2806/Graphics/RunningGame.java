//package nl.tudelft.ti2806.Graphics;
//
//import nl.tudelft.ti2806.riverrush.domain.entity.Boat;
//import nl.tudelft.ti2806.riverrush.domain.entity.River;
//
//import com.badlogic.gdx.assets.AssetManager;
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.google.inject.Inject;
//
//public class RunningGame extends Table {
//
//    private final Boat boat;
//    private River river;
//
//    @Inject
//    public RunningGame(AssetManager assets) {
//        // this.boat = new Boat(assets);
//        this.river = new River(assets);
//        this.addActor(this.river);
//        this.addActor(this.boat);
//
//    }
//
//    @Override
//    public void act(float delta) {
//        super.act(delta);
//    }
//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
//    }
//
// }
