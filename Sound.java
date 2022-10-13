import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Daniel Evans on 12/26/2015.
 */
public class Sound {


    private AudioInputStream audioStream;
    private Clip audioClip;

    public Sound(String song) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File audioFile = new File(song);
        audioStream = AudioSystem.getAudioInputStream(audioFile);

        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class,format);
        audioClip = (Clip) AudioSystem.getLine(info);
    }
    public void playAudio() throws IOException, LineUnavailableException {
        audioClip.open(audioStream);
        audioClip.start();
        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stopAudio() throws IOException {
        audioClip.close();
        audioStream.close();
    }
}
