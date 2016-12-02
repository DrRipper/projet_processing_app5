package projet_graphisme_OLD;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import ddf.minim.*;

import java.awt.peer.LightweightPeer;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import ch.dieseite.colladaloader.ColladaLoader;
import ch.dieseite.colladaloader.wrappers.ColladaModel;

class addMana extends TimerTask {
	private Player player1;
	private Player player2;

	public addMana(Game g) {
		player1 = g.getP1();
		player2 = g.getP2();
	}

	public void run() {
		if (player1.get_mana()!=Player.MAX_MANA)
			player1.set_mana(player1.get_mana()+1);
		if (player2.get_mana()!=Player.MAX_MANA)
			player2.set_mana(player2.get_mana()+1);
	}
}

public class Game extends PApplet {
	private static Son son;

	private Player player1;
	private Player player2;

	PFont font;

	boolean wait = false;
	boolean fin = false;

	private ColladaModel modelTest;

	int startTime;
	Integer maxTime = null;//new Integer(90); // Exemple : 90 secondes de jeux (null si pas de timer)
	private PImage imgInfini;
	private PImage imgTitle;
	private PImage imgFond1;

	public static void main(String[] args) {
		//PApplet.main(new String[] { "--present", "projet_graphisme.Game"});
		PApplet.main("projet_graphisme.Game");
	}

	public void settings(){
		size(1000, 900, P3D);
		smooth();
		//camera(70.0, 35.0, 120.0, 50.0, 50.0, 0.0, 0.0, 1.0, 0.0);


	}

	public void setup(){
		//fill(120,50,240);
		lights();
		ambientLight(51, 102, 126);
		//frameRate(14);

		son = new Son(this);

		player1 = new Player(this, 1, "../ressources/p1.png" );
		player2 = new Player(this, 3, "../ressources/p2.png"); 
		
		player1.setEnnemie(player2);
		player2.setEnnemie(player1);
		
		font = createFont("Georgia",40,true);

		imgInfini = loadImage("../ressources/icon_infini.png");
		imgTitle = loadImage("../ressources/title_test.png");
		imgFond1 = loadImage("../ressources/fond1.jpg");

		Properties optionals = new Properties();
		optionals.setProperty("option_no_texture", "true");
		optionals.setProperty("LinkingSchema", "Blender");
		modelTest = ColladaLoader.load("../ressources/3dmodel/women.dae",this,optionals);
		modelTest.rotate(70, 'y');
		modelTest.scale(70);

		// UPDATE DE LA MANA :
		Timer timer = new Timer();
		timer.schedule(new addMana(this), 0, 1000);

	}

	public void draw(){
		if (son.getMusicMenu().isPlaying() == false){
			son.getMusicMenu().play(); //rewind() possible
		}
		clear();
		background(0);
		stroke(255);

		String monitoringState = son.getVoice().isMonitoring() ? "enabled" : "disabled";
		text( "Input monitoring is currently " + monitoringState + ".", 5, 15 );
		if (son.getVoice().isMonitoring()) {
			rect( 0, 40, son.getVoice().left.level()*width, 20 );
			int level = (int)(son.getVoice().left.level() * 100);
			text(level + " %", 5, 100);
		}
		if (wait) { // on attend d'avoir les deux joueurs 
			background(0, 0, 0);
			textFont(font);
			textAlign(CENTER);

			image(imgTitle, width/4, 0, imgTitle.width, imgTitle.height);
			//text("Wait for player...", width/4, 100);

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
				image(imgFond1, 0, 0, width, height);

				// on affiche les barres de PV et de mana
				player1.display_pv();
				player2.display_pv();

				player1.display_mana();
				player2.display_mana();

				//modelTest.shift(cpt,'y');
				//System.out.println(cpt);

				//shift coords skethup like -> point zero should appear in the center
				/*translate(500, 450);
		    	scale(2.5f); 

		    	//draw the same x,y,z lines that you see in google sketchup	
		    	strokeWeight(2);
		    	stroke(125, 0, 0);
		    	line(0, 0, width, 0); 
		    	stroke(0, 0, 125);
		    	line(0, 0, 0, -height);
				modelTest.draw();*/

				// display timer
				time(); 

				// placement des deux personnages
				player1.displayPlayer();
				player2.displayPlayer();
				
				// check end
				if (finDePartie())
					wait = true;
			}
		}
	}
	
	public boolean finDePartie() {
		if (player1.get_pv()==0 || player2.get_pv()==0)
			return true;
		else return false;
	}

	public void keyPressed() {
		if (wait) { // etat menu
			if (key  == 'a' || key == 'A') {
				player1.set_validity(true);
			} else if (key  == 'b' || key == 'B') {
				player2.set_validity(true);
			}
		} else  {
			int increment = 10;
			// PLAYER 1
			if (key  == 'z' || key == 'Z') {
				player1.setY(player1.getY()-increment);
			} else if (key  == 'q' || key == 'Q') {
				player1.setX(player1.getX()-increment);
			} else if (key  == 'd' || key == 'D') {
				player1.setX(player1.getX()+increment);
			} else if (key  == 's' || key == 'S') {
				player1.setY(player1.getY()+increment);
			} else if (key == 'a' || key == 'A') {
				player1.hit();
			}  else if (key == 'e' || key == 'E') {
				player1.magicalHit();
			} // PLAYER 2
			else if (key  == 'i' || key == 'I') {
				player2.setY(player2.getY()-increment);
			} else if (key  == 'j' || key == 'J') {
				player2.setX(player2.getX()-increment);
			} else if (key  == 'l' || key == 'L') {
				player2.setX(player2.getX()+increment);
			} else if (key  == 'k' || key == 'K') {
				player2.setY(player2.getY()+increment);
			} else if (key == 'u' || key == 'U') {
				player2.hit();
			}  else if (key == 'o' || key == 'O') {
				player2.magicalHit();
			}
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

	public Player getP1() {
		return player1;
	}

	public Player getP2() {
		return player2;
	}

	public void stop() {
		son.stop();
		super.stop();
	}
}
