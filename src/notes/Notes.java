package notes;

/**
 * Enumération des notes sur une ocatve
 * @author erwan tanguy
 */
public enum Notes {
    E("E"),
    F("F"),
    GB("F#"),
    G("G"),
    AB("G#"),
    A("A"),
    BB("Bb"),
    B("B"), 
    C("C"),
    DB("C#"),
    D("D"),
    EB("Eb");

    private final String name;       

    /**
     * constructeur
     * @param s la note
     */
    private Notes(String s) {
        name = s;
    }

    /**
     * méthode de vérification de chaîne de caractère
     * @param otherName le nom de la note à vérifier
     * @return la vérification
     */
    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    /**
     * la note au format String
     * @return la note au format String
     */
    public String toString() {
        return this.name;
    }
}
