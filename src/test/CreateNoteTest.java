package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import config.Config;
import notes.CreateNote;

public class CreateNoteTest {
    
    @Test 
    public void createSignalTest() {
        float bpm = 0.5f;
        double hz = 440.0d;
        byte[] d = CreateNote.createSignal(bpm, hz);
        assertEquals(d.length, Config.SAMPLERATE);
    }
}
