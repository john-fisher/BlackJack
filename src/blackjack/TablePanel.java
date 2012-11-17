package blackjack;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 * @author John Fisher
 */
public class TablePanel extends JPanel {

    private CardPanel cardPanel;
    private ControlPanel controlPanel;
    private Image backgroundImage;
    
    public TablePanel() {
        setLayout(new BorderLayout());
        cardPanel = new CardPanel(this);
        controlPanel = new ControlPanel(this);

        backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/background.png"));

        add(cardPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    @Override
    public void paintComponent(Graphics gPage){
        super.paintComponent(gPage);
        gPage.drawImage(backgroundImage, 0, 0, null);
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
