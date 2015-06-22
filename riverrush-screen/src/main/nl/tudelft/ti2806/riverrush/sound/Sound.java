package nl.tudelft.ti2806.riverrush.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Creates a Sound object from a sound filename that you can call play and stop on.
 */
public class Sound {

    private Clip myClip;

    public Sound(final String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                this.myClip = AudioSystem.getClip();
                AudioInputStream ais = AudioSystem.getAudioInputStream(file.toURI().toURL());
                this.myClip.open(ais);
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
        this.myClip.setFramePosition(0);  // Must always rewind!
        this.myClip.loop(0);
        this.myClip.start();
    }

    public void loop() {
        this.myClip.setFramePosition(0);
        this.myClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        this.myClip.stop();
    }
}
