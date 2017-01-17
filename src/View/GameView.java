package View;

import Controler.PlayerControler;
import Main.GlitchesBattle;
import Model.Game;
import Model.Scene;
import processing.core.PApplet;
import processing.core.PImage;
import Model.Son;

public class GameView {
	Integer maxTime = null; //new Integer(90); // Exemple : 90 secondes de jeux (null si pas de timer)
	private PImage imgInfini;
	private PImage imgBackground;
	private static Son son;

	private Game my_model;

	private Scene my_scene;

	private PlayerControler player1;
	private PlayerControler player2;

	private Integer startDecompte;
	private boolean decompte;


	public GameView(Game model, PlayerControler p1, PlayerControler p2) {
		my_model = model;
		my_scene = new Scene(my_model.getParent());
		son = new Son(((GlitchesBattle)my_model.getParent()).getMinim(), my_model.getParent(), "../ressources/fight.mp3");
		player1 = p1;
		player2 = p2;
		imgInfini = my_model.getParent().loadImage("../ressources/icon_infini.png");
		imgBackground = my_model.getParent().loadImage("../ressources/fond1.jpg");

		startDecompte = my_model.getParent().millis();
		decompte = true;
	}

	public boolean display() {

		my_model.getParent().background(0, 0, 0);

		/*if (son.getMusicMenu().isPlaying() == false){
			son.getMusicMenu().play(); //rewind() possible
		}

		my_model.getParent().background(0, 0, 0);*/

		//my_scene.display();
		//my_model.getParent().image(imgBackground, 0, 0, my_model.getParent().width, my_model.getParent().height);
		my_scene.display();

		// placement des deux personnages
		player1.getView().displayPlayer();
		player2.getView().displayPlayer();

		// on affiche les barres de PV et de mana
		player1.getView().display_pv();
		player2.getView().display_pv();

		player1.getView().display_mana();
		player2.getView().display_mana();

		// display timer
		time(); 

		if (decompte)
			displayDecompte();


		// check end
		return my_model.isGameFinish();
	}

	public void stopMusic() {
		son.stop();
	}

	public void displayDecompte() {
		int elapsed = my_model.getParent().millis() - startDecompte;
		if ((3 - (elapsed) / 1000)>=0)
			my_model.getParent().text(3 - (elapsed) / 1000, (my_model.getParent().width/2), (my_model.getParent().height/2)); 
		else if ((3 - (elapsed) / 1000)==-1)
		{
			my_model.getParent().text("GO !", (my_model.getParent().width/2), (my_model.getParent().height/2)); 
			decompte=false;
			my_model.setStartTime(my_model.getParent().millis());
		}
	}

	public void time() {
		my_model.getParent().fill(0); 
		my_model.getParent().textAlign(PApplet.CENTER);
		my_model.getParent().fill(255);
		my_model.getParent().stroke(0);

		if ( maxTime != null) {
			int elapsed = decompte ? 0:my_model.getParent().millis() - my_model.getStartTime();
			//int elapsed = my_model.getParent().millis() - my_model.getStartTime();
			my_model.getParent().text(maxTime - (elapsed) / 1000, (my_model.getParent().width/2), 40); 
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

}
