package front;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

// TOTO : partie graphique à développer - imaginer agencement
// TODO : sélection du bpm 
// TODO : sélection de la note 
// TODO : affichage lumineux au bip 
// TODO : bouton stop (éventuellement start)
public class Fenetre extends JFrame{
    private final short windowsWidth = 400;
    private final short windowsHeight = 500;
    

    public Fenetre () {
        super();
        main();
    }

    private void init () {
        this.setTitle("Metronome");
		this.setSize(this.windowsWidth, this.windowsHeight);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		Image icon = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + System.getProperty("file.separator") + "metronome.png");
		this.setIconImage(icon);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * ajout du JPanel
     */
    private void contenu () {
        
    }

    /**
     * rendre la fenêtre visible
     */
    private void showWin () {
        this.setVisible(true);
    }

    /**
     * méthode appellée dans le constructeur
     */
    public void main() {
        this.init();
        this.contenu();
        this.showWin();
    }
}
