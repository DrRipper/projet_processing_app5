package View;

import Controler.PlayerControler;
import Main.GlitchesBattle;
import Model.Game;
import Model.Scene;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import Model.Son;

public class GameView {
	Integer maxTime = null; //new Integer(90); // Exemple : 90 secondes de jeux (null si pas de timer)
	private PImage imgInfini;
	private PImage imgBackground;
	private static Son son;
	private boolean in_pause;

	private Game my_model;

	private Scene my_scene;

	private PlayerControler player1;
	private PlayerControler player2;

	private Integer startDecompte;
	private boolean decompte;

	private int last_time;

	private boolean finish;	
	
	private static Son son_countDown;

	public GameView(Game model, PlayerControler p1, PlayerControler p2) {
		in_pause = false;
		finish = false;
		my_model = model;
		my_scene = new Scene(my_model.getParent());
		son = new Son(((GlitchesBattle)my_model.getParent()).getMinim(), my_model.getParent(), "../ressources/fight.mp3");
		player1 = p1;
		player2 = p2;
		imgInfini = my_model.getParent().loadImage("../ressources/icon_infini.png");
		imgBackground = my_model.getParent().loadImage("../ressources/fond1.jpg");
		son_countDown = new Son(((GlitchesBattle) my_model.getParent()).getMinim(), my_model.getParent(), "../ressources/Countdown.mp3");

		startDecompte = my_model.getParent().millis();
		decompte = true;
	}

	public boolean display() {
		int joueurEnAvacne = 100;
		if (player1.getModel().get_pv()>player2.getModel().get_pv()+10)
			joueurEnAvacne = 0;
		else if (player2.getModel().get_pv()>player1.getModel().get_pv()+10)
			joueurEnAvacne = 200;

		my_model.getParent().background(0, 0, 0);

		/*if (son.getMusicMenu().isPlaying() == false){
			son.getMusicMenu().play(); //rewind() possible
		}

		my_model.getParent().background(0, 0, 0);*/

		//my_scene.display();
		//my_model.getParent().image(imgBackground, 0, 0, my_model.getParent().width, my_model.getParent().height);


		my_scene.display(joueurEnAvacne);

		if (in_pause)
			display_pause_screen();
		if (finish)
			display_end_screen();

		// on affiche les barres de PV et de mana
		player1.getView().display_pv();
		player2.getView().display_pv();

		player1.getView().display_mana();
		player2.getView().display_mana();

		// display timer
		time(); 

		// placement des deux personnages
		player1.getView().displayPlayer();
		player2.getView().displayPlayer();

		if (decompte)
			displayDecompte();


		// check end
		if (my_model.isGameFinish()) {
			my_scene.stopMusic();
			return true;
		} else 
			return false;
		
	}

	private void display_end_screen() {
		int id = my_model.getWinner();
		PFont font = my_model.getParent().createFont("../ressources/Sketch Gothic School.ttf",200,true);
		my_model.getParent().textFont(font);
		if (id == 0)
			my_model.getParent().text("Equality !", (my_model.getParent().width/2), (my_model.getParent().height/2)-100);
		else
			my_model.getParent().text("Player "+ id + "\nWins !", (my_model.getParent().width/2), (my_model.getParent().height/2)-100);
		my_model.getParent().textSize(50);
		my_model.getParent().text("Press A to continue", (my_model.getParent().width/2), (my_model.getParent().height/2)+300);
	}

	private void display_pause_screen() {
		PFont font = my_model.getParent().createFont("../ressources/Sketch Gothic School.ttf",200,true);
		my_model.getParent().textFont(font);
		my_model.getParent().text("PAUSE", (my_model.getParent().width/2), (my_model.getParent().height/2));
		my_model.getParent().textSize(50);
		my_model.getParent().text("Press A to continue", (my_model.getParent().width/2), (my_model.getParent().height/2)+250);

	}

	public void stopMusic() {
		son.stop();
	}

	public void setPauseState(boolean state) {
		in_pause = state;

		if (maxTime != null )
			if(!in_pause) {
				my_model.setStartTime(my_model.getParent().millis());
				maxTime = last_time;
			} else {	
				int elapsed = decompte ? 0:my_model.getParent().millis() - my_model.getStartTime();
				last_time = maxTime - (elapsed) / 1000;
			}
	}

	public void displayDecompte() {
		if (!son_countDown.getMusicMenu().isPlaying())
			son_countDown.getMusicMenu().play(0);

		PFont font = my_model.getParent().createFont("../ressources/Sketch Gothic School.ttf",200,true);
		my_model.getParent().textFont(font);
		//my_model.getParent().textSize(50);
		int elapsed = my_model.getParent().millis() - startDecompte;
		if ((3 - (elapsed) / 1000)>=1)
			my_model.getParent().text(3 - (elapsed) / 1000, (my_model.getParent().width/2), (my_model.getParent().height/2)); 
		else if ((3 - (elapsed) / 1000)>=0)
		{
			my_model.getParent().text("GO !", (my_model.getParent().width/2), (my_model.getParent().height/2)); 

		}else {
			decompte=false;
			son_countDown.stop();
			my_model.setStartTime(my_model.getParent().millis());
		}
	}

	public void time() {
		my_model.getParent().textSize(50);
		my_model.getParent().fill(0); 
		my_model.getParent().textAlign(PApplet.CENTER);
		my_model.getParent().fill(255);
		my_model.getParent().stroke(0);

		if ( maxTime != null) {
			if(in_pause || finish)
				my_model.getParent().text(last_time, (my_model.getParent().width/2), 40);
			else {
				int elapsed = decompte ? 0:my_model.getParent().millis() - my_model.getStartTime();
				my_model.getParent().text(maxTime - (elapsed) / 1000, (my_model.getParent().width/2), 40); 
				if((maxTime - (elapsed) / 1000)==0)
					my_model.timeIsFinish();
			}
		}else { // on affiche un symbole infini (pas de temps limite)
			my_model.getParent().image(imgInfini, (my_model.getParent().width/2)-(imgInfini.width/4)/2, 0, imgInfini.width/4, imgInfini.height/4);
		} 
	}

	public void setMaxTime(Integer time) {
		maxTime = time;
	}

	public boolean isDecompting() {
		return decompte;
	}

	public void isGameFinish(boolean state) {
		finish = state;

		if (maxTime != null) {
			int elapsed = decompte ? 0:my_model.getParent().millis() - my_model.getStartTime();
			System.out.println(maxTime + "  "+elapsed);
			last_time = maxTime - (elapsed) / 1000;
		}
	}

}
