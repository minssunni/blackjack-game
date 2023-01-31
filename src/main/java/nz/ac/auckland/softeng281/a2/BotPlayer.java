package nz.ac.auckland.softeng281.a2;

import java.util.Random;

public class BotPlayer extends Participant {

	public BotPlayer(String name) {
		super(name);
	}

	@Override
	public Action decideAction() {
		if ((super.getCurrentHand()).getScore() < 17) {
			return Action.HIT;
		} else {
			return Action.HOLD;
		}
	}

	@Override
	public int makeABet() {
		Random rand = new Random();
		int lower = 1;
		int upper = 101;
		int result = rand.nextInt(upper-lower) + lower;

		return result;
	}
}
