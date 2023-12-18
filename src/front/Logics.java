package front;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Logics extends JPanel{

    public Logics() {
        this.setLayout(null);
        init();
    }

    public void init() {
        // FlowLayout f = new FlowLayout();
        // this.setLayout(f);

        JButton but1 = new JButton("1");
        but1.setBounds(10,10,40,40);
        this.add(but1);
        // this.add(new JButton("1"));
        // this.add(new JButton("2"));
        // this.add(new JButton("3"));
        
    }
    
}
