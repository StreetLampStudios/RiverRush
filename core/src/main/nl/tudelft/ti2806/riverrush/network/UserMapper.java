package nl.tudelft.ti2806.riverrush.network;

import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

import java.util.Hashtable;
import java.util.Map;

@Singleton
public class UserMapper {
    private final Map<EventDispatcher, Player> dispatcher2player;
    private final Map<Player, EventDispatcher> player2dispatcher;

    public UserMapper() {
        this.dispatcher2player = new Hashtable<>();
        this.player2dispatcher = new Hashtable<>();
    }

    public void addMapping(Player player, EventDispatcher dispatcher) {
        this.dispatcher2player.put(dispatcher, player);
        this.player2dispatcher.put(player, dispatcher);
    }

    public Player getPlayer(EventDispatcher dispatcher) {
        return dispatcher2player.get(dispatcher);
    }

    public EventDispatcher getConnection(Player player) {
        return player2dispatcher.get(player);
    }
}
