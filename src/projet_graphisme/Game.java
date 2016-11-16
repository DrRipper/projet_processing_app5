package projet_graphisme;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import ddf.minim.*;

public class Game extends PApplet {
	private static Son son;

	private Player player1;
	private Player player2;

	PFont font;

	boolean wait = true;
	boolean fin = false;

	int startTime;
	Integer maxTime = null;//new Integer(90); // Exemple : 90 secondes de jeux (null si pas de timer)
	private PImage imgInfini;

	public static void main(String[] args) {
		PApplet.main("projet_graphisme.Game");
	}

	public void settings(){
		size(1000, 900);
		smooth();
		//frameRate(24);

	}

	public void setup(){
		//fill(120,50,240);

		son = new Son(this);

		player1 = new Player(this, 1);
		player2 = new Player(this, 3); 
		font = createFont("Georgia",40,true);

		imgInfini = loadImage("../ressources/icon_infini.png");


	}

	public void draw(){
		/*if (son.getMusicMenu().isPlaying() == false){
			son.getMusicMenu().play(); //rewind() possible
		}

		background(0);
		stroke(255);

		String monitoringState = son.getVoice().isMonitoring() ? "enabled" : "disabled";
		text( "Input monitoring is currently " + monitoringState + ".", 5, 15 );
		if (son.getVoice().isMonitoring()) {
			rect( 0, 40, son.getVoice().left.level()*width, 20 );
			int level = (int)(son.getVoice().left.level() * 100);
			text(level + " %", 5, 100);
		}*/
		if (wait) { // on attend d'avoir les deux joueurs 
			background(0, 0, 0);
			textFont(font);
			textAlign(CENTER);
			text("Wait for player...", width/4, 100);

			// En attente du joureur 1 (personnage en pointillés)
			if (!player1.get_validity()) {
				player1.display_when_wait();
				text("Press A !", width/4, (height/4)+400);
			}else {
				player1.display_when_valid();

				fill(255);
				stroke(0);
				text("Ready !", width/4, (height/4)+400); 
			}

			// En attente du joureur 2 (personnage en pointillés)
			if (!player2.get_validity()) {
				player2.display_when_wait();
				text("Press B !", 3*(width/4), (height/4)+400);
			}else {
				player2.display_when_valid();

				fill(255);
				stroke(0);
				text("Ready !", 3*(width/4), (height/4)+400);
			}

			wait = ! player1.get_validity() || ! player2.get_validity();

			if (son.getMusicMenu().isPlaying() == false){
				son.getMusicMenu().play(); //rewind() possible
			}

			if (!wait) { // transition 
				startTime = millis();
				son.stop();
				//background(0, 0, 0);
				//animation_letsGo.display(0,0);
			}
		} else { // début du combat
			if (! fin) {
				// init du background
				background(0, 0, 0);

				// on affiche les barres de PV et de mana
				player1.display_pv();
				player2.display_pv();

				player1.display_mana();
				player2.display_mana();

				// display timer
				time(); 

				// placement des deux personnages

			}
		}
	}

		public void keyPressed() {
			if (wait) { // etat menu
				if (key  == 'a' || key == 'A') {
					player1.set_validity(true);
				} else if (key  == 'b' || key == 'B') {
					player2.set_validity(true);
				}
			}

			/*if ( key == 'm' || key == 'M' )
		{
			if ( son.getVoice().isMonitoring() )
			{
				son.getVoice().disableMonitoring();
			}
			else
			{
				son.getVoice().enableMonitoring();
			}
		}*/
		}

		void time() {
			fill(0); 
			textAlign(CENTER);
			fill(255);
			stroke(0);

			if ( maxTime != null) {
				int elapsed = millis() - startTime;
				text(maxTime - (elapsed) / 1000, (width/2), 40); 
			}else { // on affiche un symbole infini (pas de temps limite)
				image(imgInfini, (width/2)-(imgInfini.width/4)/2, 0, imgInfini.width/4, imgInfini.height/4);
			} 
		}

		public void stop() {
			son.stop();
			super.stop();
		}
	}
