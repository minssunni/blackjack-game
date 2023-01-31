package nz.ac.auckland.softeng281.a2;

import java.util.ArrayList;
import java.util.List;

import nz.ac.auckland.softeng281.a2.Participant.Winner;

public abstract class Participant {

    enum Action {
        HIT, HOLD
    }

    private String name;
    private List<Hand> hands;


    public Participant(String name) {
        this.name = name;
        hands = new ArrayList<>();
    }

    public List<Hand> getHands() {
        return hands;
    }


    public Hand getCurrentHand() {
        if (hands.isEmpty()) {
            throw new RuntimeException("You should't call this method if there are no hands");
        }

        return hands.get(hands.size() - 1);
    }

    public String getName() {
        return name;
    }


    public Hand createNewHand(int bet) {
        Hand newHand = new Hand(bet);
        hands.add(newHand);
        return newHand;
    }
    
    public static int getBet(Participant player) {
		return (player.getCurrentHand()).getBet();
	}
    
    enum Winner {
    	BlackJack, Player, Dealer
    }
    
    public static Winner winnerIs(Hand player, Hand dealer) {
    	if (player.isBlackJack()) {
    		return Winner.BlackJack;
    	} else if ((!player.isBust()) && (dealer.isBust())) {
    		return Winner.Player;
    	} else if ((!player.isBust()) && (!dealer.isBust()) && (player.getScore() > dealer.getScore())) {
    		return Winner.Player;
    	} else {
    		return Winner.Dealer;
    	}
    }
    
    public static double gainIs(Participant player, Participant dealer) {
    	double gain = 0;
    	double totalGain = gain;
    	
    	for (int i = 0; i < player.getHands().size(); i++) {
    		if (player.getHands().get(i).isBlackJack()) {
    			totalGain = gain + 1.5 * player.getHands().get(i).getBet();
        	} else if (winnerIs(player.getHands().get(i), dealer.getHands().get(i)) == Winner.Player) {
        		totalGain = gain + player.getHands().get(i).getBet();
        	} else if (winnerIs(player.getHands().get(i), dealer.getHands().get(i)) == Winner.Dealer) {
        		totalGain = gain - player.getHands().get(i).getBet();
        	}
    		gain = totalGain;
    	}
    	return gain;
    }
    
    
    
    /**
     * do not change this method
     *
     * @param deck
     */
    public void play(Deck deck) {
        System.out.println("==================");
        System.out.println(name + " is playing");
        int bet = makeABet();
        Hand hand = createNewHand(bet);
        hand.addCard(deck.draw());
        hand.addCard(deck.draw());
        System.out.println("the initial two cards are");
        hand.print();
        System.out.println(name + "'s score is: " + hand.getScore());
        if (hand.countAces() > 0 && this instanceof HumanPlayer) {
            System.out.println("Remember that an ACE can have rank either 1 or 11");
        }
        Participant.Action decision = decideAction();
        while (decision == Participant.Action.HIT) {
            hand.addCard(deck.draw());
            hand.print();
            System.out.println(name + "'s score is: " + hand.getScore());
            if (hand.is21() || hand.isBust()) {
                break;
            }
            decision = decideAction();
        }
        pressEnterKeyToContinue();
    }

    private void pressEnterKeyToContinue() {
        System.out.println("Press Enter key to continue...");
        Utils.scanner.nextLine();
    }

    abstract Action decideAction();

    abstract int makeABet();
}
