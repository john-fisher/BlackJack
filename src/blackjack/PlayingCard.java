package blackjack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * @author John Fisher
 */
//Playing card class
public class PlayingCard implements Drawable {

    private int cardPointValue;
    private char[] cardValueCharArray;
    private String cardValueString;
    private suit cardSuit;
    private Image suitImage;
    private boolean faceUp;

    //Enum for suits
    public enum suit { HEART, SPADE, CLUB, DIAMOND }

    //default constructor
    public PlayingCard() {
    }

    public PlayingCard(String cardValue, PlayingCard.suit cardSuit) {
        faceUp = true;
        this.cardValueCharArray = cardValue.toCharArray();
        this.cardSuit = cardSuit;
        this.cardValueString = cardValue;

        //Load images from jar Images directory
        if (cardSuit.equals(PlayingCard.suit.CLUB)) {
            suitImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/club.png"));
        }
        if (cardSuit.equals(PlayingCard.suit.DIAMOND)) {
            suitImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/diamond.png"));
        }
        if (cardSuit.equals(PlayingCard.suit.SPADE)) {
            suitImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/spade.png"));
        }
        if (cardSuit.equals(PlayingCard.suit.HEART)) {
            suitImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/heart.png"));
        }

        //set the value of this Playing Card based on its cardValueString
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

    //Card flipper for dealers first card
    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;

        if (!faceUp) {
            suitImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/cardart.png"));
        }
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
     * http://sweetclipart.com. Samurai Penguine obtained from http://looble.org
     * Background custom generated via GIMP
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
