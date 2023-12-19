package front;
import javax.swing.border.Border;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Component;

/**
 * classe pour arrondir les boutons
 */
public class RoundedBorder implements Border {

    /**
     * valeur du radius
     */
    private int radius;

    /**
     * constructeur
     * @param radius le radius des boutons
     */
    RoundedBorder(int radius) {
        this.radius = radius;
    }


    /**
     * dimensions à laisser dans les coins des boutons
     * @param c le composant
     * @return la valeur des dimensions
     */
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }


    /**
     * vérifie si la bordure est opaque
     * @return true ou false
     */
    public boolean isBorderOpaque() {
        return true;
    }


    /**
     * arrondir les angles 
     * @param c le composant
     * @param g graphics
     * @param x absisse du rectangle à dessiner
     * @param y ordonnée du rectangle à dessiner
     * @param width largeur du rectangle
     * @param height hauteur du rectangle
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}
