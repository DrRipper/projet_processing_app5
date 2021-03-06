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

	static private Scene my_scene = null;

	private PlayerControler player1;
	private PlayerControler player2;

	private Integer startDecompte;
	private boolean decompte;

	private int last_time;

	private boolean finish;	
	static private PFont my_font = null;

	private static Son son_countDown;

	public GameView(Game model, PlayerControler p1, PlayerControler p2) {
		in_pause = false;
		finish = false;
		my_model = model;
		//my_scene = new Scene(my_model.getParent());
		if (my_scene == null)
			my_scene = new Scene(my_model.getParent());
		//if (son == null)
			son = new Son(((GlitchesBattle)my_model.getParent()).getMinim(), my_model.getParent(), "../ressources/fight.mp3");
		son.play();
		player1 = p1;
		player2 = p2;
		imgInfini = my_model.getParent().loadImage("../ressources/icon_infini.png");
		imgBackground = my_model.getParent().loadImage("../ressources/fond1.jpg");

		son_countDown = new Son(((GlitchesBattle) my_model.getParent()).getMinim(), my_model.getParent(), "../ressources/Countdown.mp3");

		startDecompte = my_model.getParent().millis();
		decompte = true;
	}

	public boolean display() {
		my_model.getParent().background(0, 255, 255);

		my_model.getParent().lights();
		my_scene.display();
		my_model.getParent().noLights(); // pas de lumi�re sur les �l�ments 2D

		// on affiche les barres de PV et de mana
		player1.getView().display_pv();
		player2.getView().display_pv();

		player1.getView().display_mana();
		player2.getView().display_mana();

		// display timer
		time(); 


		if (decompte)
			displayDecompte();

		// placement des deux personnages
		boolean animation1Finished;
		boolean animation2Finished;
		my_model.getParent().hint(my_model.getParent().DISABLE_DEPTH_TEST);
		//my_model.getParent().hint(my_model.getParent().ENABLE_DEPTH_SORT);
		if(player1.getModel().getZ()<player2.getModel().getZ()) {
			animation1Finished = player1.getView().displayPlayer();
			animation2Finished = player2.getView().displayPlayer();
		} else {
			animation2Finished = player2.getView().displayPlayer();
			animation1Finished = player1.getView().displayPlayer();
		}

		//my_model.getParent().hint(my_model.getParent().DISABLE_DEPTH_SORT);
		my_model.getParent().hint(my_model.getParent().ENABLE_DEPTH_TEST);


		my_model.getParent().hint(my_model.getParent().DISABLE_DEPTH_TEST);
		my_model.getParent().textMode(my_model.getParent().MODEL);
		my_model.getParent().fill(0, 153, 255);
		if (in_pause)
			display_pause_screen();
		if (finish)
			display_end_screen();
		if (decompte)
			displayDecompte();
		my_model.getParent().hint(my_model.getParent().ENABLE_DEPTH_TEST);


		return (animation1Finished && animation2Finished);	
	}

	private void display_end_screen() {		

		int id = my_model.getWinner();
		if (my_font == null)
			my_font = my_model.getParent().createFont("../ressources/Sketch Gothic School.ttf",100,true);
		my_model.getParent().textFont(my_font);
		if (id == 0)
			my_model.getParent().text("Equality !", (my_model.getParent().width/2), (my_model.getParent().height/2)-100, 100);
		else
			my_model.getParent().text("Player "+ id + "\nWins !", (my_model.getParent().width/2), (my_model.getParent().height/2)-100, 100);
		my_model.getParent().textSize(50);
		my_model.getParent().text("Press A to continue", (my_model.getParent().width/2), (my_model.getParent().height/2)+300);

	}

	private void display_pause_screen() {	

		PFont font = my_model.getParent().createFont("../ressources/Sketch Gothic School.ttf",100,true);
		my_model.getParent().textFont(font);
		my_model.getParent().text("PAUSE", (my_model.getParent().width/2), (my_model.getParent().height/2), 100);
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
		my_model.getParent().textAlign(PApplet.CENTER);
		my_model.getParent().fill(0, 51, 153);
		my_model.getParent().stroke(0);

		if ( maxTime != null) {
			if(in_pause || finish)
				my_model.getParent().text(last_time, (my_model.getParent().width/2), 40);
			else {
				int elapsed = decompte ? 0:my_model.getParent().millis() - my_model.getStartTime();
				int t = maxTime - (elapsed) / 1000;
				Integer sec = t%60;
				Integer min = t/60;
				String time = min.toString() +":"+sec.toString();
				my_model.getParent().text(time, (my_model.getParent().width/2), 40); 
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
			//System.out.println(maxTime + "  "+elapsed);
			last_time = maxTime - (elapsed) / 1000;
		}
	}

}
