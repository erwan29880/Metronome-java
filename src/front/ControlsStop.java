package front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import play.Play;

/**
 * bouton pour stopper le métronome
 * @author erwan tanguy
 */
public class ControlsStop extends JButton implements ActionListener {

    // avoir la même instance de Play entre plusieurs classes pour stopper le thread
    private Play play;
    
    /**
     * constructeur
     * @param texte le texte du bouton
     * @param play instance de Play
     */
    public ControlsStop(String texte, Play play) {
        setText(texte);
        this.play = play;
        addActionListener(this);
    }


    /**
     * stopper le thread
     */
    public void actionPerformed(ActionEvent e) {
        play.loop = true;
    }
}
