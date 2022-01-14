import java.io.*;
import java.sql.SQLOutput;
import java.util.Objects;
import javax.sound.sampled.*;
public class Sound {

    private Clip clip;
    private boolean valid = true;
    private AudioInputStream audioInput;

    public Sound(String soundLocation) {
        try {
            File soundPath = new File(soundLocation);
            if (soundPath.exists()) {
                audioInput = AudioSystem.getAudioInputStream(soundPath.getAbsoluteFile());
                clip = AudioSystem.getClip();
            } else {
                System.out.println("Can't find audio file at: " + soundLocation);
                valid = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            valid = false;
        }
    }

    public void play() {
        if (valid) {
            try {
                this.clip.open(audioInput);
                this.clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}