package blackjack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author John Fisher
 */
public class PlayingCard implements Drawable {

    private int cardPointValue;
    private char[] cardValueCharArray;
    private String cardValueString;
    private suit cardSuit;
    private Image suitImage;
    private boolean faceUp;

    public enum suit {

        HEART, SPADE, CLUB, DIAMOND
    }

    public PlayingCard() {
    }

    public PlayingCard(String cardValue, PlayingCard.suit cardSuit) {
        faceUp = true;
        this.cardValueCharArray = cardValue.toCharArray();
        this.cardSuit = cardSuit;
        this.cardValueString = cardValue;

        try {
            if (cardSuit.equals(PlayingCard.suit.CLUB)) {
                suitImage = ImageIO.read(new File("club.png"));
            }
            if (cardSuit.equals(PlayingCard.suit.DIAMOND)) {
                suitImage = ImageIO.read(new File("diamond.png"));
            }
            if (cardSuit.equals(PlayingCard.suit.SPADE)) {
                suitImage = ImageIO.read(new File("spade.png"));
            }
            if (cardSuit.equals(PlayingCard.suit.HEART)) {
                suitImage = ImageIO.read(new File("heart.png"));
            }
        } catch (IOException ex) {
            System.out.println("IOException in PlayingCard when attempting to open card images.");
            System.exit(1);
        }

        switch (cardValueString) {
            case "2":
                cardPointValue = 2;
                break;
            case "3":
                cardPointValue = 3;
                break;
            case "4":
                cardPointValue = 4;
                break;
            case "5":
                cardPointValue = 5;
                break;
            case "6":
                cardPointValue = 6;
                break;
            case "7":
                cardPointValue = 7;
                break;
            case "8":
                cardPointValue = 8;
                break;
            case "9":
                cardPointValue = 9;
                break;
            case "10":
            case "J":
            case "Q":
            case "K":
                cardPointValue = 10;
                break;
            case "A":
                cardPointValue = 11;
                break;
        }
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;

        if (!faceUp) {
            try {
                suitImage = ImageIO.read(new File("cardart.png"));
            } catch (IOException ex) {
                System.out.println("IOException in PlayingCard when attempting to open card images.");
                System.exit(1);
            }
        }
    }

    public boolean getFaceUp() {
        return faceUp;
    }

    public boolean isAce() {
        if (cardValueString.equals("A")) {
            return true;
        }
        return false;
    }

    public String getCardValue() {
        return cardValueString;
    }

    public int getCardPointValue() {
        return cardPointValue;
    }

    public PlayingCard.suit getCardSuit() {
        return cardSuit;
    }

    /*
     * Playing card suit graphic master images obtained from 
     * http://sweetclipart.com and http://looble.org
     * then modified via gimp
     */
    @Override
    public void draw(Graphics gPage, int startX, int startY, int width, int height) {
        if (faceUp) {
            gPage.setColor(Color.black);
            gPage.fillRoundRect(startX, startY, width, height, 10, 10);
            gPage.setColor(Color.white);
            gPage.fillRoundRect(startX + 2, startY + 2, width - 4, height - 4, 10, 10);
            gPage.setColor(Color.BLACK);
            gPage.drawImage(suitImage, (startX + (width / 2) - 25), (startY + (height / 2) - 25), 50, 50, null);
            gPage.drawChars(cardValueCharArray, 0, cardValueCharArray.length, startX + 5, startY + 12);
        } else {
            gPage.setColor(Color.black);
            gPage.fillRoundRect(startX, startY, width, height, 10, 10);
            gPage.setColor(Color.white);
            gPage.fillRoundRect(startX + 2, startY + 2, width - 4, height - 4, 10, 10);
            gPage.setColor(Color.BLACK);
            gPage.drawImage(suitImage, startX + 2, startY + 2, width - 4, height - 4, null);
        }
    }
}
