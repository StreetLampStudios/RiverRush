package nl.tudelft.ti2806.riverrush.sound;
import sun.audio.*;

import javax.sound.sampled.*;
import java.io.*;
import java.net.MalformedURLException;

/**
 * Creates a Sound object from a sound filename that you can call play and stop on
 */
public class Sound {
    private Clip myClip;

    public Sound(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                Clip myClip = AudioSystem.getClip();
                AudioInputStream ais = AudioSystem.getAudioInputStream(file.toURI().toURL());
                myClip.open(ais);
            } else {
                throw new RuntimeException("Sound: file not found: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Sound: Malformed URL: " + e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        } catch (IOException e) {
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException("Sound: Line Unavailable: " + e);
        }
    }

    public void play() {
        myClip.setFramePosition(0);  // Must always rewind!
        myClip.loop(0);
        myClip.start();
    }

    public void loop() {
        myClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        myClip.stop();
    }
}
