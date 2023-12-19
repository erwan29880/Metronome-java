package test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import javax.sound.sampled.LineUnavailableException;
import org.junit.jupiter.api.Test;

import notes.CreateNote;
import play.Play;

public class PlayTest {
    @Test 
    public void runTest() throws LineUnavailableException {
        float bpm = 0.5f;
        double hz = 440.0d;
        byte[] d = CreateNote.createSignal(bpm, hz);
        Play play = new Play();

        assertDoesNotThrow(() -> LineUnavailableException.class);

        // break play loop
        play.loop = true;
        
    }
    
}
