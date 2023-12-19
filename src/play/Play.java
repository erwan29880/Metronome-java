package play;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import config.Config;

/**
 * Lecture audio d'un tableau de bytes
 * @author erwan tanguy
 */
public class Play{
    
    // variable multi thread pour sortie de boucle de lecture
    public volatile boolean loop = false;

    /**
     * Lire le bip du métronome en boucle
     * doit être créé dans un thread
     * @param audioData le signal audio, 1 channel
     * @throws LineUnavailableException si il y a problème d'ouverture de la ligne de sortie
     */
    public void run(byte[] audioData) throws LineUnavailableException {
        AudioFormat format = new AudioFormat((float) Config.SAMPLERATE, 16, 1, true, false);
        SourceDataLine sourceLine = AudioSystem.getSourceDataLine(format);
        sourceLine.open(format);
        sourceLine.start();
        while (!loop) {
            sourceLine.write(audioData, 0, audioData.length);
        }
        sourceLine.drain();
        sourceLine.stop();
        sourceLine.close(); 
    }

    @Override
    public String toString() {
        return "classe Play";
    }
}
