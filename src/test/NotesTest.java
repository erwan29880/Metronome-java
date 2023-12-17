package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import notes.Frequences;
import notes.Notes;

/**
 * test de l'enum Notes
 */
public class NotesTest {

    @Test 
    public void testNotes() {
        Notes n = Notes.values()[0];
        boolean check = n.toString().equals("A");
        assertEquals(check, true);
    }

    @Test 
    public void testCreateNotes() {
        Frequences freq = new Frequences();
        assertEquals(freq.getHz(Notes.A), 440);
    }
}