package config;

import notes.Notes;

public class Config {
    public static final double LA3 = 329.6d;       // E
    public static final double SEMITONE = Math.pow(2.0, 1/12.0);
    public static final short NBNOTES = 12;        // un ocatve
    public static final float SAMPLERATE = 22050;  // 22050 frames par secondes
    private float bpm = 1.0f;                      // initialisation de la durée à 1, soit un bpm de 60
    private Notes note = Notes.A;                  // LA comme note initiale pour le rendu sonore

    /**
     * bpm pour battements par minutes
     * @return la valeur de battements par minutes en secondes
     */
    public float getBpm() {
        return this.bpm;
    }

    /**
     * régler le battement par minutes
     * le setter transforme les battements par minutes en secondes
     * @param bpm en secondes
     */
    public void setBpm(float bpm) {
        this.bpm = 1 / (bpm / 60.f);
    }

    // getters setters
    public Notes getNote() {
        return this.note;
    }

    public void setNote(Notes note) {
        this.note = note;
    }
    


}