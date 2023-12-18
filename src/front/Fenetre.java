package front;

import config.Config;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Container;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;


public class Fenetre extends JFrame{
    private final short windowsWidth = 350;
    private final short windowsHeight = 500;
    private final short padding = 10;
    private Container c;
    private final int center = 100;
    private int texte = 0;
    private int r = 70, r2 = 80, r3 = 90; // début ligne, fin ligne, début texte

    // constructeur
    public Fenetre () {
        super();
        main();
    }

    /**
     * configuration basique pour la fenetre
     */
    private void init () {
        this.setTitle("Metronome");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		Image icon = Toolkit.getDefaultToolkit().getImage(Config.ICONPATH);
		this.setIconImage(icon);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setBounds(0,0, windowsWidth, windowsHeight);
    }


    /**
     * ajout des JPanels
     */
    private void contenu () {
        Border br = BorderFactory.createLineBorder(Color.black);
        this.c = getContentPane();
        Logics logic = new Logics(); // boutons de choix
        Dessi radio = new Dessi(); // cercle "radio"

        // définir taille et bordures des JPanels
        logic.setBounds(padding, padding, windowsWidth - padding*3, 200);
        radio.setBounds(padding, 220, windowsWidth - padding*3, 200);
        logic.setBorder(br);
        radio.setBorder(br);
        
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


    /**
     * classe pour dessiner le bouton-cercle-radio qui définit le bpm
     * implémentée dans la classe Fenetre pour mise à jour des variables en fonction de l'action de la souris
     * hérite de JPanel pour le dessin graphics, et implémente des actions de souris (clique et bouge)
     * la logique qui peut être écartée du JPanel et des actions souris sont dans la classe Utils.
     */
    class Dessi extends JPanel implements MouseMotionListener{

        // constructeur
        public Dessi() {
            this.setLayout(null);
            addMouseMotionListener(this);
            initBoard();
        }

        // configuration du JPanel
        private void initBoard() {
        }

        // afficher les éléments
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawCirlcesLines(g);
        }

        /**
         * tracer le cercle des lignes
         * tracer une ligne rouge en fonction du bpm
         * @param g graphics 
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
                double[] coord = Utils.coordinates(r, r2, currentAngle, center);
                
                // affichage
                g.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(2));
                g2.draw(new Line2D.Double(coord[0], coord[1], coord[2], coord[3]));
            }

            // tracé rouge et texte bpm---------------------------

            // void classe Utils
            double currentAngle = Utils.angle(debAngle, texte);
            double[] coord = Utils.coordinates(r, r2, r3, currentAngle, center);
            
            // affichage 
            g.setColor(Color.RED);
            g2.setStroke(new BasicStroke(4));
            g2.setFont(new Font("serif", Font.PLAIN, 10)); 
            g2.drawString(Integer.toString((int)(texte/1.7)) , (int) coord[4], (int) coord[5]);
            g2.draw(new Line2D.Double(coord[0], coord[1], coord[2], coord[3]));
        }


        // implémentations interface MouseMotionListener---------------

        public void mouseDragged(MouseEvent e) {
            double[] coord = {e.getX(), e.getY()};
            double d = Utils.euclidien(coord, center);
            texte = (int) d;
            this.repaint();
        }

        public void mouseMoved(MouseEvent e) {}

    } // fin classe Dessi 

} // fin classe Fenetre
