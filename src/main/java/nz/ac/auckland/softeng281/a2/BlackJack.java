package nz.ac.auckland.softeng281.a2;

import java.util.ArrayList;
import java.util.List;

import nz.ac.auckland.softeng281.a2.Participant.Winner;

public class BlackJack {

	private List<Participant> players;
	private Participant dealer;

	public BlackJack() {
		players = new ArrayList<>();
		dealer = new BotDealer("Dealer", players);
		players.add(new HumanPlayer("Player1"));
		players.add(new BotPlayer("Bot1"));
		players.add(new BotPlayer("Bot2"));
	}

	// getter setter for testing purposes
	public List<Participant> getPlayers() {
		return players;
	}

	public Participant getDealer() {
		return dealer;
	}

	public void setPlayers(List<Participant> players) {
		this.players = players;
	}

	public void setDealer(Participant dealer) {
		this.dealer = dealer;
	}

	public static void main(String[] args) {
		BlackJack game = new BlackJack();
		game.start();
	}

	protected void start() {
		Utils.printBlackJack();
		// create a new deck of cards
		Deck deck = new Deck();
		String result;
		do {
			for (Participant player : players) {
				player.play(deck);
			}
			
			dealer.play(deck);
			checkWinner();
			System.out.println("Do you want to play again?");
			result = Utils.scanner.next();
			while (!result.equals("yes") && !result.equals("no")) {
				System.out.println("please type either \"yes\" or \"no\"");
				result = Utils.scanner.next();
			}
		} while (result.equals("yes"));
		printPlayerHighestGain();
	}

	public void checkWinner() {
		for (Participant player : players) {
			if (Participant.winnerIs(player.getCurrentHand(), dealer.getCurrentHand()) != Winner.Dealer) {
				System.out.println(player.getName() + " wins"); 
			}
		}
	}
	
	public void printPlayerHighestGain() {
		
		double gain;
		double totalGain = -100000;
		String name = null;
		
		for (int i = 0; i < players.size(); i++) {
			gain = Participant.gainIs(players.get(i), dealer);
			if (gain > totalGain) {
				totalGain = gain;
				name = players.get(i).getName();
			}
		}
		
		System.out.println("The player with the highest gain is: " + name + " with " + totalGain + " chips");
	}
	
	
}