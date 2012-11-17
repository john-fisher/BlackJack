package blackjack;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * @author John Fisher
 */
public class TableFrame extends JFrame {
        
    public TableFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800,600));
        setTitle("BlackJack");
        
        TablePanel tPanel = new TablePanel();       
        add(tPanel);
        
        pack();
        setVisible(true);
    }
}
