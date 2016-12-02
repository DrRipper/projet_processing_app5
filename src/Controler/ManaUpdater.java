package Controler;

import java.util.TimerTask;

import Model.Player;

public class ManaUpdater extends TimerTask {
	private PlayerControler player1;
	private PlayerControler player2;

	public ManaUpdater(GameControler g, PlayerControler p1, PlayerControler p2) {
		player1 = p1;
		player2 = p2;
	}

	public void run() {
		if (player1.getModel().get_mana()!=Player.MAX_MANA)
			player1.getModel().set_mana(player1.getModel().get_mana()+1);
		if (player2.getModel().get_mana()!=Player.MAX_MANA)
			player2.getModel().set_mana(player2.getModel().get_mana()+1);
	}
}