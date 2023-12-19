package config;

/**
 * classe de configuration
 * @see notes.Frequences
 * @see notes.CreateNote
 * @see front.Fenetre
 */
public class Config {

    /**
     * fréquence de la note de début correspondant à mi
     */
    public static final double LA3 = 329.6d;       // E

    /**
     * valeur d'un demi-ton
     */
    public static final double SEMITONE = Math.pow(2.0, 1/12.0);

    /**
     * nombre de notes
     */
    public static final short NBNOTES = 12;        // un ocatve
    
    /**
     * nombre de frames par seconde
     */
    public static final float SAMPLERATE = 22050;  // 22050 frames par secondes
    
    /**
     * chemin d'accès de l'icone pour la fenêtre swing
     */
    public static final String ICONPATH = new StringBuilder()
                                            .append(System.getProperty("user.dir"))
                                            .append(System.getProperty("file.separator"))
                                            .append("metronome.png")
                                            .toString();
}