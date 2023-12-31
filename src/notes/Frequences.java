package notes;

import java.util.Map;
import java.util.Iterator;
import java.util.LinkedHashMap;

import config.Config;

/**
 * Initialiser la fréquence des notes
 * dépendant de l'enum Notes
 * dépendant de la classe Config
 * @author erwan tanguy
 */
public class Frequences {

    /**
     * nom des notes associées à leurs fréquences
     * @see Notes
     */
    private Map<Notes, Double> notes = new LinkedHashMap<Notes, Double>();

    /**
     * initialisation des notes par le constructeur
     */
    public Frequences() {
        createNotes();
    }

    /**
     * Créer les notes en hertz et les associer au nom de note 
     */
    public void createNotes() {        
        for (int i = 0; i < Config.NBNOTES ; i ++) {
            double note = Config.LA3 * Math.pow(Config.SEMITONE, i);  // calcul Note
            note = Math.round(note * 100) / 100.0; // arrondir
            notes.put(Notes.values()[i], note);
        }
    }

    /**
     * getter 
     * @return les notes et leurs fréquences
     */
    public Map<Notes,Double> getNotes() {
        return this.notes;
    }

    /**
     * getter
     * @return un itérateur avec notes et fréquences
     */
    public Iterator<Map.Entry<Notes, Double>> getNotesAsIterator() {
        return this.notes.entrySet().iterator();
    }

    /**
     * getter
     * @param n le nom de la note
     * @return sa fréquence en hertz
     */
    public double getHz(Notes n) {
        return notes.get(n);
    }
}