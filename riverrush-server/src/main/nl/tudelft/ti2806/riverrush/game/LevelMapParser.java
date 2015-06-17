package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.event.AbstractTeamEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AddObstacleEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AddRockEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Parser used to create a level map.
 */
public class LevelMapParser {

    /**
     * Read the game track from a file.
     *
     * @param fileName The name of the file
     * @return The created game track
     * @throws IOException when the file cannot be opened
     */
    public static TreeMap<Double, AbstractTeamEvent> readFromFile(final String fileName) throws IOException {
        InputStream in = LevelMapParser.class.getResourceAsStream(fileName);

        return parseLevel(in);
    }

    /**
     * Parse a level for the game track.
     *
     * @param inputStream The stream to parse from
     * @return The event
     */
    public static TreeMap<Double, AbstractTeamEvent> parseLevel(final InputStream inputStream) {
        return parseLevel(new Scanner(inputStream));
    }

    /**
     * This will parse the level for you.
     *
     * @param level - String that represents when the cannonballs need to start flying
     * @return The event
     */
    public static TreeMap<Double, AbstractTeamEvent> parseLevel(final Scanner level) {
        TreeMap<Double, AbstractTeamEvent> levelMap = new TreeMap<>();

        String currentLine;
        while(level.hasNextLine()) {
            currentLine = level.nextLine();
            String[] lineContent = currentLine.split(",");
            double spawnTime = Double.parseDouble(lineContent[0]);
            char thingToSpawn = lineContent[1].charAt(0);
            double spawnLocation = Double.parseDouble(lineContent[2]);
            AbstractTeamEvent event = generateTrackEvent(thingToSpawn, spawnLocation);
            levelMap.put(spawnTime, event);
        }

        return levelMap;
    }

    /**
     * Generate an event to create elements on the game track.
     *
     * @param parseCode     The element to create
     * @param spawnLocation The location to put the element
     * @return The event
     */
    private static AbstractTeamEvent generateTrackEvent(final char parseCode, final double spawnLocation) {
        if (parseCode == 'O') {
            AddObstacleEvent event = new AddObstacleEvent();
            event.setLocation(spawnLocation);
            return event;
        } else if (parseCode == 'R') {
            AddRockEvent event = new AddRockEvent();
            if (spawnLocation == -1) {
                event.setLocation(Direction.LEFT);
            } else {
                event.setLocation(Direction.RIGHT);
            }
            return event;
        }
        throw new IllegalArgumentException("Illegal event code: " + parseCode);
    }
}
