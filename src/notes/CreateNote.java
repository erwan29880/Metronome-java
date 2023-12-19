package notes;
import config.Config;

/**
 * classe de création des notes en bytes 
 * @author erwan tanguy
 */
public class CreateNote {
 
    /**
     * créer un signal audio avec un silence
     * @param bpm les battements par minutes convertis en secondes
     * @param hz la fréquence en hertz
     * @return le signal audio en bytes
     */
    public static byte[] createSignal(float bpm, double hz) {

        // nombre de frames : frameRate  * nb de secondes * 2 -> une partie son une partie silence 
        final int numFrames = (int) (bpm * Config.SAMPLERATE);

        // tableau de bytes pour 1 canal
        byte[] audioData = new byte[2 * numFrames];
        
        // diminuer la durée du son par rapport au silence en fonction du bpm
        int milieu = (int) (numFrames / 2.5f);
        if (bpm >= 1 & bpm < 2) {   // bpm entre 60 et 30
            milieu = (int) (numFrames / 6.0f);  
        }
        if (bpm >= 2) {     // en dessous de 30
            milieu = (int) (Config.SAMPLERATE / 8.0f);
        }


        final double maxShort = (double) Short.MAX_VALUE - 1;  
        // le son    
        for (int i = 0; i < milieu ; i++) {
            short sampleValue = (short) (maxShort * Math.sin(2.0 * Math.PI * hz * i / Config.SAMPLERATE));
            audioData[2 * i] = (byte) (sampleValue & 0xFF);
            audioData[2 * i + 1] = (byte) ((sampleValue >> 8) & 0xFF);
        }

        // le silence
        for (int i = milieu ; i < numFrames ; i++) {
            short sampleValue = 0;
            audioData[2 * i] = (byte) (sampleValue & 0xFF);
            audioData[2 * i + 1] = (byte) ((sampleValue >> 8) & 0xFF);
        }
        return audioData;
    }
}
