package blackjack;

import java.awt.Graphics;
import java.util.Random;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author John Fisher
 */
public class CardPanel extends JPanel {

    private int preferredCardWidth;
    private int preferredCardHeight;
    private int topHandStartY, bottomHandStartY;
    private Stack<PlayingCard> shoe;
    private Stack<PlayingCard> dealerHand;
    private Stack<PlayingCard> playerHand;
    private Graphics gPage;
    private TablePanel parent;
    private int deckCount;

    public enum HANDSTATE { BUSTED, BLACKJACK, VALID, TWENTYONE }

    public CardPanel(TablePanel parent) {
        //We'll start with a shoe of 6 decks
        //Future: Give user config option to adjust this
        deckCount = 6;
        
        this.parent = parent;

        preferredCardWidth = 100;
        preferredCardHeight = 150;
        topHandStartY = 20;
        bottomHandStartY = 375;

        dealerHand = new Stack<>();
        playerHand = new Stack<>();

        loadShoe();
        shuffleShoe();

        initHands();
    }

    public void draw(Graphics gPage) {
        this.gPage = gPage;
        for (int i = 0; i < dealerHand.size(); i++) {
            dealerHand.get(i).draw(gPage,
                    (this.getWidth() / 2) - (preferredCardWidth / 2) + (i * 32),
                    topHandStartY,
                    preferredCardWidth,
                    preferredCardHeight);
        }

        for (int i = 0; i < playerHand.size(); i++) {
            playerHand.get(i).draw(gPage,
                    (this.getWidth() / 2) - (preferredCardWidth / 2) + (i * 32),
                    (int)(parent.getHeight()-preferredCardHeight-parent.getControlPanelHeight()-topHandStartY),
                    preferredCardWidth,
                    preferredCardHeight);
        }
        parent.repaintTable();
    }

    public void resetHands() {
        dealerHand = new Stack<>();
        playerHand = new Stack<>();

        if (shoe.size() < 4) {
            loadShoe();
        }

        gPage.clearRect(0, 0, getWidth(), getHeight());
        initHands();
    }

    private void initHands() {
        for (int i = 0; i < 2; i++) {
            dealerHand.add(shoe.remove(0));            
            if(i==0) dealerHand.elementAt(0).setFaceUp(false);
            
            playerHand.add(shoe.remove(0));
        }
    }

    public void hitPlayer() {
        handHit(playerHand);

        parent.repaintTable();
        if (validateHand(playerHand) == HANDSTATE.BUSTED) {
            JOptionPane.showMessageDialog(null, "YOU BUST!");
            standPlayer();
        }

        if (validateHand(playerHand) == HANDSTATE.BLACKJACK) {
            JOptionPane.showMessageDialog(null, "21!");
            standPlayer();
        }

    }

    public void standPlayer() {
        dealerPlay();
    }

    private void dealerPlay() {
        dealerHand.elementAt(0).setFaceUp(true);
        
        if (validateHand(playerHand) != HANDSTATE.BUSTED) {
            while (getHandPointValue(dealerHand) < 17) {
                handHit(dealerHand);
            }
        }

        HANDSTATE playerState = validateHand(playerHand);
        HANDSTATE dealerState = validateHand(dealerHand);

        parent.repaintTable();

        //Winner rules here
        //Check for BlackJacks
        if (playerState == HANDSTATE.BLACKJACK
                && dealerState == HANDSTATE.BLACKJACK) {
            JOptionPane.showMessageDialog(null, "BLACKJACK! Player and Dealer Wins! PUSH");
        } else

        if (playerState == HANDSTATE.BLACKJACK
                && dealerState != HANDSTATE.BLACKJACK) {
            JOptionPane.showMessageDialog(null, "BLACKJACK! Player Wins!");
        } else

        if (playerState != HANDSTATE.BLACKJACK
                && dealerState == HANDSTATE.BLACKJACK) {
            JOptionPane.showMessageDialog(null, "BLACKJACK! Dealer Wins!");
        } else 

        //Check for Busts
        if (playerState == HANDSTATE.BUSTED && dealerState != HANDSTATE.BUSTED) {
            JOptionPane.showMessageDialog(null, "Dealer Wins!");
        } else

        if (dealerState == HANDSTATE.BUSTED && playerState != HANDSTATE.BUSTED) {
            JOptionPane.showMessageDialog(null, "Player Wins!");
        } else
        
        if (dealerState == HANDSTATE.BUSTED && playerState == HANDSTATE.BUSTED) {
            JOptionPane.showMessageDialog(null, "Player and Dealer Loose!");
        } else
                
        //Check for winner via points
        if (getHandPointValue(playerHand) > getHandPointValue(dealerHand)) {
            JOptionPane.showMessageDialog(null, "Player Wins!");
        } else

        if (getHandPointValue(dealerHand) > getHandPointValue(playerHand)) {
            JOptionPane.showMessageDialog(null, "Dealer Wins!");
        } else

        if (getHandPointValue(dealerHand) == getHandPointValue(playerHand)) {
            JOptionPane.showMessageDialog(null, "Dealer and Player Wins!");
        }

        resetHands();
    }

    public void handHit(Stack<PlayingCard> handToHit) {
        if (shoe.size() < 1) {
            loadShoe();
            for (int i = 0; i < dealerHand.size(); i++) {
                shoe.remove(dealerHand.get(i));
            }
            for (int i = 0; i < playerHand.size(); i++) {
                shoe.remove(playerHand.get(i));
            }
            shuffleShoe();
        }

        handToHit.add(shoe.remove(0));
    }

    public HANDSTATE validateHand(Stack<PlayingCard> handToValidate) {
        if (getHandPointValue(handToValidate) < 21) {
            return HANDSTATE.VALID;
        }

        if (getHandPointValue(handToValidate) == 21 && handToValidate.size() == 2) {
            return HANDSTATE.BLACKJACK;
        }

        if (getHandPointValue(handToValidate) == 21) {
            return HANDSTATE.TWENTYONE;
        }

        return HANDSTATE.BUSTED;
    }

    private int getHandPointValue(Stack<PlayingCard> handToValue) {
        /* 
         * Calculate the hand points with cosideration of the ace's dual value 
         * possibility
         */
        int handValue = 0;
        int aceCount = 0;

        for (int i = 0; i < handToValue.size(); i++) {
            if (handToValue.get(i).isAce()) {
                aceCount++;
            }
            handValue += handToValue.get(i).getCardPointValue();
        }

        for (int i = 0; i < aceCount; i++) {
            if (handValue > 21) {
                handValue -= 10;
            }
        }

        return handValue;
    }

    private void loadShoe() {
        //Load the shoe
        shoe = new Stack<>();

        String valueOptions[];
        valueOptions = new String[13];
        valueOptions[0] = "2";
        valueOptions[1] = "3";
        valueOptions[2] = "4";
        valueOptions[3] = "5";
        valueOptions[4] = "6";
        valueOptions[5] = "7";
        valueOptions[6] = "8";
        valueOptions[7] = "9";
        valueOptions[8] = "10";
        valueOptions[9] = "J";
        valueOptions[10] = "Q";
        valueOptions[11] = "K";
        valueOptions[12] = "A";

        for(int loopCtr=0; loopCtr<deckCount; loopCtr++){
            for (int i = 0; i < valueOptions.length; i++) {
                shoe.add(new PlayingCard(valueOptions[i], PlayingCard.suit.CLUB));
            }
            for (int i = 0; i < valueOptions.length; i++) {
                shoe.add(new PlayingCard(valueOptions[i], PlayingCard.suit.SPADE));
            }
            for (int i = 0; i < valueOptions.length; i++) {
                shoe.add(new PlayingCard(valueOptions[i], PlayingCard.suit.DIAMOND));
            }
            for (int i = 0; i < valueOptions.length; i++) {
                shoe.add(new PlayingCard(valueOptions[i], PlayingCard.suit.HEART));
        }
        }
    }

    private void shuffleShoe() {
        //shuffle shoe
        Random myRand = new Random(System.currentTimeMillis());
        Stack<PlayingCard> randomizingStack = new Stack<>();

        for (int x = 0; x < 50; x++) {
            for (int i = 0; shoe.size() > 0; i++) {
                randomizingStack.add(shoe.remove(myRand.nextInt(shoe.size())));
            }

            shoe = randomizingStack;
            randomizingStack = new Stack<>();
        }
    }
}