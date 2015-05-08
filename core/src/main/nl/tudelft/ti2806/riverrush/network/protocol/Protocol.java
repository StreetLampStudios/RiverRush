package nl.tudelft.ti2806.riverrush.network.protocol;

import java.util.ArrayList;
import java.util.Set;

/**
 * Protocol class with the predefined actions
 */
public class Protocol {

    public static final String JOIN_ACTION = "join";
    public static final String LEAVE_ACTION = "leave";
    public static final String JUMP_ACTION = "jump";

    public static ArrayList<String> getActions() {
        ArrayList<String> actions = new ArrayList<>();
        actions.add(JOIN_ACTION);
        actions.add(LEAVE_ACTION);
        actions.add(JUMP_ACTION);
        return actions;
    }
}
