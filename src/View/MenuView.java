package View;

import Controler.PlayerControler;
import Model.Menu;
import Model.Player;
import processing.core.PFont;
import processing.core.PImage;
import Model.Son;

public class MenuView {
	private static Son son;
	private Menu my_model;
	private PFont font;
	private PImage imgTitle;
	
	private PlayerControler player1;
	private PlayerControler player2;
	
	
	public MenuView(Menu m, PlayerControler p1, PlayerControler p2) {
		my_model = m;
		player1 = p1;
		player2 = p2;
		son = new Son(my_model.getParent(), "../ressources/music.mp3");
		font = my_model.getParent().createFont("Georgia",40,true);
		imgTitle = my_model.getParent().loadImage("../ressources/title_test.png");

	}
	
	public boolean display() {
		boolean wait;
		
		if (son.getMusicMenu().isPlaying() == false){
			son.getMusicMenu().play(); //rewind() possible
		}
		
		my_model.getParent().background(0, 0, 0);
		my_model.getParent().textFont(font);
		my_model.getParent().textAlign(my_model.getParent().CENTER);

		my_model.getParent().image(imgTitle, my_model.getParent().width/4, 0, imgTitle.width, imgTitle.height);
		//text("Wait for player...", width/4, 100);

		// En attente du joureur 1 (personnage en pointillés)
		if (!player1.getModel().get_validity()) {
			player1.getView().display_when_wait();
			my_model.getParent().text("Press A !", my_model.getParent().width/4, (my_model.getParent().height/4)+400);
		}else {
			player1.getView().display_when_valid();

			my_model.getParent().fill(255);
			my_model.getParent().stroke(0);
			my_model.getParent().text("Ready !", my_model.getParent().width/4, (my_model.getParent().height/4)+400); 
		}

		// En attente du joureur 2 (personnage en pointillés)
		if (!player2.getModel().get_validity()) {
			player2.getView().display_when_wait();
			my_model.getParent().text("Press B !", 3*(my_model.getParent().width/4), (my_model.getParent().height/4)+400);
		}else {
			player2.getView().display_when_valid();

			my_model.getParent().fill(255);
			my_model.getParent().stroke(0);
			my_model.getParent().text("Ready !", 3*(my_model.getParent().width/4), (my_model.getParent().height/4)+400);
		}

		wait = ! player1.getModel().get_validity() || ! player2.getModel().get_validity();

		if (!wait) { // transition 
			//startTime = millis();
			son.stop();
			//background(0, 0, 0);
			//animation_letsGo.display(0,0);
		}
		
		return wait;
	}
	
	public void stopMusic() {
		son.stop();
	}
}
