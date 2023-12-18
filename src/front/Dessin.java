package front;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

public class Dessin extends JPanel implements MouseMotionListener{

    private final short B_WIDTH = 200;  // taille jpanel
    private final short B_HEIGHT = 200;
    private final int center = 100;
    private int texte = 0;

    public Dessin() {
        this.setLayout(null);
        addMouseMotionListener(this);  
        initBoard();
    }

    // configuration du JPanel
    private void initBoard() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
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
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("serif", Font.PLAIN, 10)); 

        double theta = 10;  // angle pour affichage des traits
        int inc = (int) (360 / theta);  // nombre d'angles
        int r = 50; // rayon du cercle, début du tiret
        int r2 = 65; // rayon du deuxième cercle, fin du tiret
        int r3 = 70; // début chiffre
        
        double debAngle = 270;
        // sens horaire, début point cardinal N = anfle 270
        for (int i = 0; i < inc ; i++) {         
            double currentAngle;

            // vérification que m'angle ne dépasse pas 360
            double test = debAngle + theta * i;
            if (test >= 360) {
                currentAngle = Math.toRadians(debAngle + (theta-360) * i);
            } else {
                currentAngle = Math.toRadians(debAngle + theta*i);
            }  
            
            // calcul des coordonnées
            double[] coord = Utils.coordinates(r, r2, currentAngle, center);
            
            // tracé
            g.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(2));
            g2.draw(new Line2D.Double(coord[0], coord[1], coord[2], coord[3]));
        }

        // ligne rouge, texte
        double currentAngle = debAngle + this.texte;
        if (currentAngle >= 360) {
            currentAngle = Math.toRadians(this.texte + debAngle - 360);
        } else {
            currentAngle = Math.toRadians(debAngle + this.texte);
        }        
        
        // ligne en rouge pour le BPM
        double[] coord = Utils.coordinates(r, r2, r3, currentAngle, center);
        g.setColor(Color.RED);
        g2.setStroke(new BasicStroke(4));
        g2.drawString(Integer.toString((int)(texte/1.7)) , (int) coord[4], (int) coord[5]);
        g2.draw(new Line2D.Double(coord[0], coord[1], coord[2], coord[3]));
    }


 

    public void mouseDragged(MouseEvent e) {
        double[] coord = {e.getX(), e.getY()};
        double d = euclidien(coord, center);
        this.texte = (int) d;
        this.repaint();
    }

    public void mouseMoved(MouseEvent e) {
        // System.out.println(e.getX());
    }
}
