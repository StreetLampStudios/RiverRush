package nl.tudelft.ti2806.riverrush.backend.eventlisteners;

import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.entity.game.Game;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.listener.EventListener;
import nl.tudelft.ti2806.riverrush.network.UserMapper;
import nl.tudelft.ti2806.riverrush.network.event.JoinEvent;

/**
 * Listens to join events.
 */
public class JoinEventListener extends EventListener<JoinEvent> {

    private final Game game;
    private final UserMapper userMapper;

    @Inject
    public JoinEventListener(Game game, UserMapper userMapper) {
        this.game = game;
        this.userMapper = userMapper;
    }

    @Override
    public void handle(final JoinEvent event, final EventDispatcher dispatcher) {
        Player player = new Player();
        userMapper.addMapping(player, dispatcher);
    }

    @Override
    public Class<JoinEvent> getEventType() {
        return JoinEvent.class;
    }

}
