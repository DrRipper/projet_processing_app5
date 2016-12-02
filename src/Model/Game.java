package Model;

import Controler.PlayerControler;
import processing.core.PApplet;

public class Game {
	private PApplet my_parent;
	private PlayerControler player1;
	private PlayerControler player2;
	
	private int startTime;
	
	public Game(PApplet p, PlayerControler p1, PlayerControler p2) {
		my_parent = p;
		player1 = p1;
		player2 = p2;

	}

	public PApplet getParent() {
		return my_parent;
	}
	
	public boolean isGameFinish() {
		return (player1.getModel().get_pv()==0 || player2.getModel().get_pv()==0);
			
	}

	public void setStartTime(int time) {
		startTime = time;
	}
	
	public int getStartTime() {
		return startTime;
	}
}
