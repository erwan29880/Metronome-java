package notes;

/**
 * Enumération des notes sur une ocatve
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

    private Notes(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
