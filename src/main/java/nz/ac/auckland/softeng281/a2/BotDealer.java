package nz.ac.auckland.softeng281.a2;

import java.util.List;
import java.util.Random;

public class BotDealer extends Participant {

	private List<Participant> players;

	public BotDealer(String name, List<Participant> players) {
		super(name);
		this.players = players;
	}

	@Override
	public Action decideAction() {
		int winningPlayers = 0;
		
		for (Participant player : players) {
			if ((player.getCurrentHand()).isBlackJack() || winnerIs(player.getCurrentHand(), super.getCurrentHand()) == Winner.Player) {
				winningPlayers++;
			}
		}
		
		if (winningPlayers >= 2) {
			return Action.HIT;
		} else {
			return Action.HOLD;
		}

	}

	@Override
	/**
	 * do not touch this method
	 */
	public int makeABet() {
		// the Dealer doesn't bet so is always zero
		return 0;
	}
}
