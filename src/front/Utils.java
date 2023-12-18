package front;

public class Utils {

    /**
     * méthode utilitaire pour calculer les coordonnées des traits
     * @param r distance centre du cercle - début du point
     * @param r2 distance centre du cercle - fin du point
     * @param angl l'angle pour décaler la ligne
     * @param center le centre du cercle intérieur - x = y
     * @return les coordonnées du début du point puis les coordonnées de fin du point
     */
    protected static double[] coordinates(int r, int r2, double angl, int center) {
        double x = center + r * Math.cos(angl);
        double y = center + r * Math.sin(angl);
        double x1 = center + r2 * Math.cos(angl);
        double y1 = center + r2 * Math.sin(angl);
        return new double[]{x, y, x1, y1};
    }   

      /**
     * méthode utilitaire pour calculer les coordonnées des traits
     * @param r distance centre du cercle - début du point
     * @param r2 distance centre du cercle - fin du point
     * @param r3 position du texte
     * @param angl l'angle pour décaler la ligne
     * @param center le centre du cercle intérieur - x = y
     * @return les coordonnées du début du point puis les coordonnées de fin du point, les coordonnées du texte
     */
    protected static double[] coordinates(int r, int r2, int r3, double angl, int center) {
        double x = center + r * Math.cos(angl);
        double y = center + r * Math.sin(angl);
        double x1 = center + r2 * Math.cos(angl);
        double y1 = center + r2 * Math.sin(angl);
        double x2 = center + r3 * Math.cos(angl);
        double y2 = center + r3 * Math.sin(angl);
        return new double[]{x, y, x1, y1, x2, y2};
    }   

    /**
     * Les points N et S sont 0, E, W 90, des conditions sont mises en place pour ajuster l'angle en fonction des 
     * coordonnées du curseur de la sourir par rapport au centre du cercle
     * @param coords les coordonnées x, y de la position de la souris
     * @return l'angle absolu
     */
    protected static double euclidien(double[] coords, int center) {
        
        // distance euclidienne
        double hypotenuse = Math.sqrt( Math.pow(coords[0] - center, 2) + Math.pow(coords[1] - center, 2));
        // projection du y du curseur
        double d = Math.abs(coords[1] - center);
        if (hypotenuse == 0) return 0.0d;  // éviter la division par 0
        
        // trouver l'angle avec la droite centre - curseur -> cos, cos-1 (rad) -> degré
        double angleB = Math.toDegrees(Math.acos(d/hypotenuse));

        if (coords[1] > center & coords[0] > center) {
            angleB = 180 - angleB;
        } else if (coords[1] > center & coords[0] < center) {
            angleB = 180 + angleB;
        } else if (coords[1] < center & coords[0] < center) {
            angleB = 360 - angleB;
        }

        if (angleB > 360) {
            angleB -= 360;
        }
        
        return angleB;   
    }

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

    protected static double angle(double debAngle, int texte) {
        double currentAngle = debAngle + texte;
            if (currentAngle >= 360) {
                currentAngle = Math.toRadians(texte + debAngle - 360);
            } else {
                currentAngle = Math.toRadians(debAngle + texte);
            }    
        return currentAngle;
    }
}
