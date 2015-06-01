package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.GameState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.PlayerAddedEvent;
import nl.tudelft.ti2806.riverrush.graphics.entity.Animal;
import nl.tudelft.ti2806.riverrush.graphics.entity.MonkeyActor;
import nl.tudelft.ti2806.riverrush.graphics.entity.state.AnimalOnBoat;

import com.badlogic.gdx.assets.AssetManager;

public class AbstractGameState implements GameState {

    protected final EventDispatcher dispatcher;
    protected final AssetManager assets;
    protected final Game game;

    public AbstractGameState(final EventDispatcher eventDispatcher,
            final AssetManager assetManager, final Game gm) {
        this.game = gm;
        this.assets = assetManager;
        this.dispatcher = eventDispatcher;

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    @Override
    public GameState start() {
        return this;
    }

    @Override
    public GameState stop() {
        return this;
    }

    @Override
    public GameState finish() {
        return this;
    }

    @Override
    public GameState waitForPlayers() {
        return this;
    }

    /**
     * Add an animal based on the given event. Should be overwritten for all states that do not
     * allow addition of animals.
     *
     * @param event
     *            tells the state where to add the animal.
     */
    public void addAnimal(PlayerAddedEvent event) {
        MonkeyActor actor = new MonkeyActor(this.assets, 0, 0, this.dispatcher);
        AbstractAnimal anim = new Animal(this.dispatcher, new AnimalOnBoat(actor, this.dispatcher));
        this.game.getTeams().get(event.getTeamID()).addAnimal(anim);
    }

}
