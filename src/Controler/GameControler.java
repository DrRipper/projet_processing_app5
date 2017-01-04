package Controler;

import java.util.Timer;

import Model.Game;
import View.GameView;
import processing.core.PApplet;

public class GameControler {
	private Game my_model;
	private GameView my_view;
	
	public GameControler(PApplet p, PlayerControler p1, PlayerControler p2) {
		my_model = new Game(p, p1, p2);
		my_view = new GameView(my_model, p1, p2);
		
		// UPDATE DE LA MANA :
		Timer timer = new Timer();
		timer.schedule(new ManaUpdater(this, p1, p2), 0, 1000);
	}
	
	public Game getModel() {
		return my_model;
	}
	
	public boolean display() {
		return my_view.display();
	}
	
	public void stop() {
		my_view.stopMusic();
	}

	public void setStartTime(int time) {
		my_model.setStartTime(time);
	}
	
	public void setMaxTime(Integer time) {
		my_view.setMaxTime(time);
	}

	public boolean isDecompting() {
		return my_view.isDecompting();
	}
}
