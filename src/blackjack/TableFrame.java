package blackjack;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * @author John Fisher
 */
public class TableFrame extends JFrame {

    public static void main(String[] args) {
        TableFrame tableFrame = new TableFrame();  
    }

    //GUI frame
    public TableFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700,500));
        setTitle("BlackJack");
        
        TablePanel tPanel = new TablePanel();       
        add(tPanel);
        
        pack();
        setVisible(true);
    }
}
