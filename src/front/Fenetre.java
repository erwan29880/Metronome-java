package front;

import java.util.Map;
import java.util.Iterator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import config.Config;
import notes.Frequences;
import notes.Notes;
import play.Play;


/**
 * classe de la fenêtre principale 
 * @author erwan tanguy
 */
public class Fenetre extends JFrame {
    // JFrame
    private final short windowsWidth = 365;
    private final short windowsHeight = 500;
    private final short padding = 10;
    private Container c;
    
    // Dessi.drawCirlcesLines
    private final int center = 155;   
    private final int centerX = 155;
    private final int centerY = 135;        
    private final short radioSize = 400; 
    private final short bpmRadioFontSize = 95;
    private final float coefficientBpm = 1.7f;
    private int texte = 0;
    private int r = 110, r2 = r+10; // début ligne, fin ligne, début texte 

    // audio 
    private Notes n = Notes.A;
    private double hertzNote = 440.0d;
    private Play play;
    private Frequences freqs;

    // boutons
    private ControlsPlay playBpm;
    private ControlsStop stop;

    // constructeur
    public Fenetre () {
        super();
        
        // calcul des fréquences
        freqs = new Frequences();

        // instance de Play qui sera partagée par ControlsPlay et ControlsStop
        play = new Play();

        // initialisation du contenu
        main();
    }

    /**
     * configuration basique pour la fenetre
     */
    private void init () {
        setTitle("Metronome");
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Config.ICONPATH));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * ajout des JPanels
     */
    private void contenu () {
        setLayout(null);
        setBounds(0,0, windowsWidth, windowsHeight);
        c = getContentPane();
        Logics logic = new Logics(); // boutons de choix
        Dessi radio = new Dessi(); // cercle "radio"

        // définir taille et bordures des JPanels
        logic.setBounds(padding, padding, windowsWidth - padding*3, 180);
        radio.setBounds(padding, 190, windowsWidth - padding*3, radioSize);
       
        
        // ajouter les JPanels à la fenêtre
        c.add(logic);
        c.add(radio);
    }


    /**
     * méthode appellée dans le constructeur
     */
    public void main() {
        this.init();
        this.contenu();
        this.setVisible(true);
    }

    // -------------------------------------------------

    /**
     * création des boutons dans un Jpanel séparé
     * voir la classe ControlsNote ci sessous pour les boutons des notes
     * @see fr.erwan.metronome.front.ControlsPlay
     * @see fr.erwan.metronome.front.ControlsStop
     */
    public class Logics extends JPanel {
        public Logics() {
            this.setLayout(null);
            init();
            notesButtons();
        }

        /**
         * création des boutons play et stop
         */
        private void init() {
            playBpm = new ControlsPlay("play", play);
            stop = new ControlsStop("stop", play);
            playBpm.setBounds(75, 10, 80, 40);
            stop.setBounds(180, 10, 80, 40);
            playBpm.setBorder(new RoundedBorder(10));
            stop.setBorder(new RoundedBorder(10));
            this.add(playBpm);
            this.add(stop);
        }

        /**
         * création des boutons des notes
         * voir la classe ci-dessous ControlsNote
         */
        private void notesButtons() {
            int inc = 0;
            int incColonne = 5;
            int incLigne = 60;
            int butSize = 50;

            // récupérer le dictionnaire note-fréquence
            Iterator<Map.Entry<Notes, Double>> iterator = freqs.getNotesAsIterator();
            while(iterator.hasNext()) {
                // changement de ligne et reset de l'incrémentation des colonnes
                if (inc == 6) {
                    incLigne += incLigne;
                    incColonne = 5;
                }
                Map.Entry<Notes, Double> mapentry = (Map.Entry<Notes, Double>) iterator.next();
                ControlsNote c = new ControlsNote(mapentry.getKey(), mapentry.getValue());
                c.setBounds(incColonne, incLigne, butSize, butSize);
                c.setBorder(new RoundedBorder(10));
                incColonne += butSize + 5;
                this.add(c);
                inc++;
            }
            
        }   
    } // fin classe Logics
    

    // ------------------------
    
    /**
     * classe de gestion des boutons des notes
     * permet au clic sur bouton de changer la fréquence de la note générée
     */
    public class ControlsNote extends JButton implements ActionListener {
        
        private Notes note;
        private double hz;

        /**
         * constructeur
         * @param n la note au format anglo-saxon
         * @param hz la fréquence qui est associée à la note 
         */
        public ControlsNote (Notes n, double hz) {
            this.note = n;
            this.hz = hz;
            setText(n.toString());
            addActionListener(this);
        }

        /**
         * mise à jour des variables globales de la classe Fenetre
         * mise à jour de la classe ControlsPlay
         * @see fr.erwan.metronome.front.ControlsPlay
         */
        public void actionPerformed(ActionEvent e) {
            n = this.note;
            hertzNote = this.hz;
            playBpm.setHz(this.hz);
        }
    }

    /**
     * classe pour dessiner le bouton-cercle-radio qui définit le bpm
     * implémentée dans la classe Fenetre pour mise à jour des variables en fonction de l'action de la souris
     * hérite de JPanel pour le dessin graphics, et implémente des actions de souris (clique et bouge)
     * la logique qui peut être écartée du JPanel et des actions souris sont dans la classe Utils.
     * mets à jour la classe ControlsPlay
     */
    class Dessi extends JPanel implements MouseMotionListener{

        // constructeur
        public Dessi() {
            this.setLayout(null);
            addMouseMotionListener(this);
        }


        /**
         * affichage des éléments
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawCirlcesLines(g);
        }

        /**
         * tracer le cercle des lignes
         * tracer une ligne rouge en fonction du bpm
         * @param g graphics
         * @see fr.erwan.metronome.front.Utils
         */
        private void drawCirlcesLines(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;  // parsage en 2d pour utilisation de double pour affichage texte
            double theta = 10;  // angle pour affichage des traits
            int inc = (int) (360 / theta);  // nombre d'angles
            
            
            double debAngle = 270;
            // sens horaire, début point cardinal N = angle 270 : tracé des lignes du cercle
            for (int i = 0; i < inc ; i++) {         
                // voir classe Utils
                double currentAngle = Utils.angle(debAngle, theta, i);  
                double[] coord = Utils.coordinates(r, r2, currentAngle, centerX, centerY);
                
                // affichage
                g.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(2));
                g2.draw(new Line2D.Double(coord[0], coord[1], coord[2], coord[3]));
            }

            // tracé rouge et texte bpm---------------------------

            // voir classe Utils
            double currentAngle = Utils.angle(debAngle, texte);
            double[] coord = Utils.coordinates(r, r2, currentAngle, centerX, centerY);
            
            // affichage 
            String realBpm = Integer.toString((int)(texte/coefficientBpm));
            g.setColor(Color.RED);
            g2.setStroke(new BasicStroke(6));
            g2.draw(new Line2D.Double(coord[0], coord[1], coord[2], coord[3]));


            // le BPM au milieu du cercle
            g2.setFont(new Font("serif", Font.PLAIN, bpmRadioFontSize)); 
            g2.setColor(Color.GRAY);

            // calcul centrage
            int stringWidthLength = (int) g.getFontMetrics().getStringBounds(realBpm, g2).getWidth();
            int horizontalCenter = centerX - stringWidthLength/2;
            int verticalCenter = centerY + (int)(bpmRadioFontSize/4);

            g.drawString(realBpm, horizontalCenter, verticalCenter);
        }


        // implémentations interface MouseMotionListener---------------

        /**
         * suivi du déplacement de la souris
         * calcul de l'anglen mise à jour de l'affichage du cercle et du bpm
         * mise à jour du bpm dans la classe ControlsPlay (playBpm)
         * @see fr.erwan.metronome.front.ControlsPlay
         * @see fr.erwan.metronome.front.Utils
         */
        public void mouseDragged(MouseEvent e) {
            double[] coord = {e.getX(), e.getY()};
            double d = Utils.euclidien(coord, centerX, centerY);
            texte = (int) d;
            this.repaint();
            playBpm.setBpm((float) (texte/coefficientBpm));
            playBpm.setHz(hertzNote);
        }

        /**
         * implémentation de l'interface
         */
        public void mouseMoved(MouseEvent e) {}
       

    } // fin classe Dessi 

} // fin classe Fenetre
