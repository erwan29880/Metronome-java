package front;

/**
 * Classe de calculs d'angles et de coordonnées 
 * @see fr.erwan.metronome.front.Fenetre.Dessi
 * @author erwan tanguy
 */
public class Utils {

    /**
     * méthode utilitaire pour calculer les coordonnées des traits
     * @param r distance centre du cercle - début du point
     * @param r2 distance centre du cercle - fin du point
     * @param angl l'angle pour décaler la ligne
     * @param centerX l'abscisse du centre du cercle intérieur 
     * @param centerY l'ordonnée du centre du cercle intérieur
     * @return les coordonnées du début du point puis les coordonnées de fin du point
     */
    protected static double[] coordinates(int r, int r2, double angl, int centerX, int centerY) {
        double x = centerX + r * Math.cos(angl);
        double y = centerY + r * Math.sin(angl);
        double x1 = centerX + r2 * Math.cos(angl);
        double y1 = centerY + r2 * Math.sin(angl);
        return new double[]{x, y, x1, y1};
    }   


    /**
     * Les points N et S sont 0, E, W 90, des conditions sont mises en place pour ajuster l'angle en fonction des 
     * coordonnées du curseur de la sourir par rapport au centre du cercle
     * méthode utilisée par Fenetre.Dessin.mouseDragged
     * @param coords les coordonnées x, y de la position de la souris
     * @param centerX l'abscisse du centre du cercle intérieur 
     * @param centerY l'ordonnée du centre du cercle intérieur
     * @return l'angle absolu
     */
    protected static double euclidien(double[] coords, int centerX, int centerY) {
        
        // distance euclidienne
        double hypotenuse = Math.sqrt( Math.pow(coords[0] - centerX, 2) + Math.pow(coords[1] - centerY, 2));
        // projection du y du curseur
        double d = Math.abs(coords[1] - centerY);
        if (hypotenuse == 0) return 0.0d;  // éviter la division par 0
        
        // trouver l'angle avec la droite centre - curseur -> cos, cos-1 (rad) -> degré
        double angleB = Math.toDegrees(Math.acos(d/hypotenuse));

        if (coords[1] > centerY & coords[0] > centerX) {
            angleB = 180 - angleB;
        } else if (coords[1] > centerY & coords[0] < centerX) {
            angleB = 180 + angleB;
        } else if (coords[1] < centerY & coords[0] < centerX) {
            angleB = 360 - angleB;
        }

        if (angleB > 360) {
            angleB -= 360;
        }
        
        return angleB;   
    }

    /**
     * Calcul d'un angle avec vérification que l'angle ne dépasse pas 360
     * voir Fenetre.Dessin.drawCirlcesLines
     * @param debAngle l'angle initial en degrés
     * @param theta la différente par rapport à l'angle initial en degrés
     * @param i l'incrémentable de la boucle
     * @return l'angle en radians
     */
    protected static double angle(double debAngle, double theta, int i) {
        double currentAngle;
        double test = debAngle + theta * i;
        if (test >= 360) {
            currentAngle = Math.toRadians(debAngle + (theta-360) * i);
        } else {
            currentAngle = Math.toRadians(debAngle + theta*i);
        }  
        return currentAngle;
    }

    /**
     * calcul de l'angle pour affichage du trait rouge et du bpm
     * voir Fenetre.Dessin.drawCirlcesLines
     * @param debAngle angle initial en degrés
     * @param texte l'angle actuel en degrés
     * @return l'angle en radians
     */
    protected static double angle(double debAngle, int texte) {
        double currentAngle = debAngle + texte;
            if (currentAngle >= 360) {
                currentAngle = Math.toRadians(texte + debAngle - 360);
            } else {
                currentAngle = Math.toRadians(debAngle + texte);
            }    
        return currentAngle;
    }


    // pour tests --------------------------

    public static boolean coordinatesTest() {
        int r = 2;
        double angl = 0.0d;
        double x = r * Math.cos(angl);
        double y = r * Math.sin(angl);
        return (x == 2 & y ==0);
    }  

    public static boolean angleTest() {
        double debAngle = 0, theta = 10;
        int i = 1;
        double currentAngle = Math.toRadians(debAngle + theta*i);
        currentAngle = Math.round(currentAngle*100) / 100.0;
        return currentAngle == 0.17;
    }
}
