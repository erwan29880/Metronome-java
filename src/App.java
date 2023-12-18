import javax.sound.sampled.LineUnavailableException;

import config.Config;
import front.Fenetre;
import notes.CreateNote;
import notes.Frequences;
import notes.Notes;
import play.Play;

public class App {

    
    public static void main(String[] args) throws Exception {
        new Fenetre();
        // System.exit(0);


        // Config config = new Config();
        // Frequences freqs = new Frequences();
        // config.setBpm(60);
        // byte[] b = CreateNote.createSignal(config.getBpm(), freqs.getHz(Notes.B));
        // Play play = new Play();
        // Thread th = new Thread(){
        //     public void run() {
        //         try {
        //             play.run(b);
        //         } catch (LineUnavailableException e) {
        //             System.out.println("Ligne de sortie non disponible");
        //         }
        //     }
        // };
        // th.start(); 
        // try{
        //     Thread.sleep(5000);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // play.loop = true;
    
        

    }
}
