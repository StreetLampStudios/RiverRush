package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.AnimalCollidedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameFinishedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameStartedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.event.JumpCommand;

/**
 * State when the game is ongoing.
 */
public class PlayingGameState implements GameState {

    private final EventDispatcher dispatcher;
    private final Game game;
    private final HandlerLambda<AnimalCollidedEvent> animalCollidedLambda;

    /**
     * The game transitions to this state when the game starts.
     *
     * @param eventDispatcher The dispatcher used to listen to {@link JumpCommand}.
     * @param aGame
     */
    public PlayingGameState(final EventDispatcher eventDispatcher, final Game aGame) {
        this.dispatcher = eventDispatcher;
        this.game = aGame;

        this.animalCollidedLambda = (e) -> this.game.collideAnimal(e.getAnimal(), e.getTeam());

        this.dispatcher.dispatch(new GameStartedEvent());
        this.dispatcher.attach(AnimalCollidedEvent.class, this.animalCollidedLambda);
    }

    @Override
    public void dispose() {
        this.dispatcher.detach(AnimalCollidedEvent.class, this.animalCollidedLambda);
    }

    @Override
    public GameState start() {
        return this;
    }

    @Override
    public GameState stop() {
        this.dispose();

        return new StoppedGameState(this.dispatcher, this.game);
    }

    @Override
    public GameState finish(final Integer team) {
        this.dispose();

        GameFinishedEvent event = new GameFinishedEvent();
        event.setTeam(team);
        this.dispatcher.dispatch(event);

        return new FinishedGameState(this.dispatcher, this.game);
    }

    @Override
    public GameState waitForPlayers() {
        return this;
    }
}
