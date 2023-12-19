package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import notes.Frequences;
import notes.Notes;

public class FrequencesTest {
    @Test 
    public void testCreateNotes() {
        Frequences freq = new Frequences();
        boolean check = (freq.getHz(Notes.A) > 439.9 & freq.getHz(Notes.A) < 440.1);
        assertEquals(true, check);
    }
}
