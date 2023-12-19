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
    
    /**
     * largeur de la fenêtre
     */
    private final short windowsWidth = 365;

    /**
     * hauteur de la fenêtre
     */
    private final short windowsHeight = 500;

    /**
     * padding des JPanels par rapprt à la JFrame
     */
    private final short padding = 10;

    /**
     * variable utilisée pour arrondir les angles des boutons
     */
    private Container c;
    
    /**
     * initialisation du nom de la note émise par le bip du métronome
     * @see notes.Notes
     */
    private Notes n = Notes.A;

    /**
     * initialisation de la fréquence de la note jouée par le métronome
     */
    private double hertzNote = 440.0d;

    /**
     * lecteur audio
     * @see play.Play
     */
    private Play play;

    /**
     * initialisation des fréquences des notes
     * @see notes.Frequences
     */
    private Frequences freqs;

    /**
     * bouton play
     * @see ControlsPlay
     */
    private ControlsPlay playBpm;

    /**
     * bouton stop 
     * @see ControlsStop
     */
    private ControlsStop stop;

    /**
     * constructeur
     */
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
        radio.setBounds(padding, 190, windowsWidth - padding*3, 400);
       
        
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
     * @see ControlsPlay
     * @see ControlsStop
     */
    public class Logics extends JPanel {

        /**
         * constructeur
         */
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
        
        /**
         * le nom de la note jouée par le métronome
         * @see notes.Notes
         */
        private Notes note;

        /**
         * la fréquence de la note jouée par le métronome
         */
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
         * @see ControlsPlay
         * @param e event
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

        /**
         * absisse du centre du cercle
         */
        private final int centerX = 155;

        /**
         * ordonnée du centre du cercle
         */
        private final int centerY = 135;
        
        /**
         * taille de la police pour le cercle
         */
        private final short bpmRadioFontSize = 95;

        /**
         * transformer l'angle du cercle avec un coefficient pour éviter
         * des bpm de 0 à 360
         */
        private final float coefficientBpm = 1.7f;

        /**
         * un incrémenteur pour affichage en texte
         */
        private int texte = 0;

        /**
         * largeur du rayon du cercle intérieur
         */
        private int r = 110;

        /**
         * largeur du rayon du cercle extérieur
         */
        private int r2 = r+10; 

        /**
         * constructeur
         */
        public Dessi() {
            this.setLayout(null);
            addMouseMotionListener(this);
        }


        /**
         * affichage des éléments
         * @param g graphics
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
         * @see Utils
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
         * @see ControlsPlay
         * @see Utils
         * @param e event
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
         * @param e event
         */
        public void mouseMoved(MouseEvent e) {}
       

    } // fin classe Dessi 

} // fin classe Fenetre
