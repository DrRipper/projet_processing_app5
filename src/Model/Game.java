package Model;

import Controler.PlayerControler;
import processing.core.PApplet;

public class Game {
	private PApplet my_parent;
	private PlayerControler player1;
	private PlayerControler player2;

	private int startTime;
	private boolean timeFinish;

	public Game(PApplet p, PlayerControler p1, PlayerControler p2) {
		my_parent = p;
		player1 = p1;
		player2 = p2;
		timeFinish = false;

	}

	public void timeIsFinish() {
		timeFinish = true;
	}

	public PApplet getParent() {
		return my_parent;
	}

	public boolean isGameFinish() {
		return (timeFinish ||player1.getModel().get_pv()==0 || player2.getModel().get_pv()==0);

	}

	public int getWinner() {
		if (player1.getModel().get_pv()==0)
			return 2;
		else if (player2.getModel().get_pv()==0)
			return 1;
		else { // fin du temps
			if (player1.getModel().get_pv() == player2.getModel().get_pv())
				return 0;
			else if(player1.getModel().get_pv() > player2.getModel().get_pv())
				return 1;
			else
				return 2;
		}
	}

	public void setStartTime(int time) {
		startTime = time;
	}

	public int getStartTime() {
		return startTime;
	}
}
