package config;

/**
 * classe de configuration
 * @see fr.erwan.metronome.notes.Frequences
 * @see fr.erwan.metronome.notes.CreateNote
 * @see fr.erwan.metronome.front.Fenetre
 */
public class Config {
    public static final double LA3 = 329.6d;       // E
    public static final double SEMITONE = Math.pow(2.0, 1/12.0);
    public static final short NBNOTES = 12;        // un ocatve
    public static final float SAMPLERATE = 22050;  // 22050 frames par secondes
    public static final String ICONPATH = new StringBuilder()
                                            .append(System.getProperty("user.dir"))
                                            .append(System.getProperty("file.separator"))
                                            .append("metronome.png")
                                            .toString();
}