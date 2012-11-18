package blackjack;

import java.awt.Graphics;

/**
 * @author John Fisher
 */
//Interface for the cards
public interface Drawable {   
    public void draw(Graphics gPage, int startX, int startY, int width, int height);
}
