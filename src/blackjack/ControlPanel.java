package blackjack;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author John Fisher
 */
public class ControlPanel extends JPanel {

    private TablePanel parent;
    
    public ControlPanel(TablePanel parent) {
        this.parent = parent;
        
        JButton hitButton = new JButton("Hit");
        hitButton.addMouseListener(new CustomButtonListener());
        hitButton.setName("playerhit");
        JButton stayButton = new JButton("Stand");
        stayButton.addMouseListener(new CustomButtonListener());
        stayButton.setName("playerstand");

        add(hitButton);
        add(stayButton);
        
    }

    private class CustomButtonListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent me) {
            JButton sourceButton = (JButton) me.getSource();

            switch (sourceButton.getName().toLowerCase()) {
                case "playerhit":
                    parent.hitPlayer();
                    break;
                case "playerstand":
                    parent.stayPlayer();
                    break;
            }
        }
    }
    
}
