package blackjack;

import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * @author John Fisher
 */
public class TablePanel extends JPanel {

    private CardPanel cardPanel;
    private ControlPanel controlPanel;
    
    public TablePanel() {
        setLayout(new BorderLayout());
        cardPanel = new CardPanel(this);
        controlPanel = new ControlPanel(this);

        add(cardPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    @Override
    public void paintComponent(Graphics gPage){
        super.paintComponent(gPage);
        cardPanel.draw(gPage);
    }
    
    public void hitPlayer(){
        cardPanel.hitPlayer();
    }
    
    public void stayPlayer(){
        cardPanel.standPlayer();
    }
    
    public void repaintTable(){
        repaint();
    }
}
