package front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;

import notes.CreateNote;
import play.Play;

/**
 * bouton play
 * @author erwan tanguy
 */
public class ControlsPlay extends JButton implements ActionListener {

    /**
     * même instance de Play que dans Fenetre et ControlsStop
     */
    private Play play;

    /**
     * initialisation bpm à 100
     */
    private float bpm = 0.54f;

    /**
     * initialisation fréquence à 440Hz
     */
    private double hz = 440.0d;

    /**
     * constructeur
     * @param texte le texte du bouton
     * @param play l'instance de Play
     */
    public ControlsPlay(String texte, Play play) {
        setText(texte);
        this.play = play;
        addActionListener(this);
    }

    /**
     * régler le battement par minutes
     * le setter transforme les battements par minutes en secondes
     * @param bpm en secondes
     */
    public void setBpm(float bpm) {
        if (bpm == 0) this.bpm = 1 / (100 / 60.f);
        else this.bpm = 1 / (bpm / 60.f);
    }

    /**
     * setter hertz
     * @param hz la fréquence en hertz
     */
    public void setHz(double hz) {
        this.hz = hz;
    }

    /**
     * lancement d'un nouveau Thread pour jouer le son en boucle selon une fréquence en hertz et un bpm
     * le thread est arreté par passage de la variable volatile play.loop à true dans ControlsStop
     * @param e évènement
     */
    public void actionPerformed(ActionEvent e) {
        play.loop = false;
        Thread th = new Thread(){
            public void run() {
                byte[] b = CreateNote.createSignal(bpm, hz);
                try {
                    play.run(b);
                } catch (LineUnavailableException e) {
                    System.out.println("Ligne de sortie non disponible");
                }
            }
        };
        th.start(); 
    }

    
}
