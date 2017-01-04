package View;

import com.jogamp.common.util.IntIntHashMap;

import Controler.PlayerControler;
import Main.GlitchesBattle;
import Model.Menu;
import Model.Player;
import Model.Son;
import processing.core.PFont;
import processing.core.PImage;

public class MenuView {
	private static Son son_music;
	private Menu my_model;
	private PFont font_title;
	private PFont font_state;
	private PImage imgTitle;

	private PlayerControler player1;
	private PlayerControler player2;

	private final static int RECT_LINE_START = 200;
	private final static int RECT_LINE_END = 650;
	private final static int RECT_PLAYER1_COL_START = 100;
	private final static int RECT_PLAYER2_COL_START = 600;
	private final static int RECT_LARGEUR = 300;

	// noms des joueurs
	private PImage name_player1;
	private PImage name_player2;

	private int initialY;
	private int initialX1;
	private int initialX2;

	public MenuView(Menu m, PlayerControler p1, PlayerControler p2) {
		my_model = m;
		player1 = p1;
		player2 = p2;
		son_music = new Son(((GlitchesBattle) my_model.getParent()).getMinim(), my_model.getParent(), "../ressources/music.mp3");
		
		font_title = my_model.getParent().createFont("../ressources/Sketch Gothic School.ttf",100,true);
		font_state = my_model.getParent().createFont("../ressources/Sketch Gothic School.ttf",50,true);
		imgTitle = my_model.getParent().loadImage("../ressources/title_test.png");

		name_player1 = my_model.getParent().loadImage("../ressources/name_player1.png");
		name_player2 = my_model.getParent().loadImage("../ressources/name_player2.png");
		
		initialY = player1.getModel().getY();
		initialX1 = player1.getModel().getX();
		initialX2 = player2.getModel().getX();

		player1.setX(player1.getModel().getX()+40);
		player1.setY(600);
		
		player2.setX(player2.getModel().getX()-40);
		player2.setY(600);


	}

	public boolean display() {
		boolean wait;

		if (son_music.getMusicMenu().isPlaying() == false){
			son_music.getMusicMenu().loop(); //rewind() possible
		}

		my_model.getParent().background(0, 0, 0);
		my_model.getParent().textFont(font_title);
		my_model.getParent().textAlign(my_model.getParent().CENTER);
		my_model.getParent().fill(255);

		//my_model.getParent().image(imgTitle, my_model.getParent().width/4, 0, imgTitle.width, imgTitle.height);
		my_model.getParent().text("Glithes Battle", my_model.getParent().width/2, 100);

		/* =============================== 
		 * 		PLAYER 1
		  =============================== */
		// box grise du Joueur 1
		my_model.getParent().fill(153);
		my_model.getParent().rect(RECT_PLAYER1_COL_START ,RECT_LINE_START, RECT_LARGEUR, RECT_LINE_END);
		// Nom du "Player 1"
		my_model.getParent().image(name_player1, RECT_PLAYER1_COL_START, RECT_LINE_START, RECT_LARGEUR, name_player1.height/2);
		// State ("press A" or "Ready")
		my_model.getParent().fill(0);
		my_model.getParent().textFont(font_state);
		if (!player1.getModel().get_validity()) {
			my_model.getParent().text("Press A", RECT_PLAYER1_COL_START+120, RECT_LINE_START+100);
		} else {
			my_model.getParent().text("Ready !", RECT_PLAYER1_COL_START+120, RECT_LINE_START+100);
		}
		// image player blue & animation
		player1.getView().displayPlayer();
		
		

		/* =============================== 
		 * 		PLAYER 2
		  =============================== */
		// box grise du Joueur 2
		my_model.getParent().fill(153);
		my_model.getParent().rect(RECT_PLAYER2_COL_START ,RECT_LINE_START, RECT_LARGEUR, RECT_LINE_END);
		// Nom du "Player 2"
		my_model.getParent().image(name_player2, RECT_PLAYER2_COL_START, RECT_LINE_START, RECT_LARGEUR, name_player2.height/2);
		// State ("press A" or "Ready")
		my_model.getParent().fill(0);
		if (!player2.getModel().get_validity()) {
			my_model.getParent().text("Press A", RECT_PLAYER2_COL_START+120, RECT_LINE_START+100);
		} else {
			my_model.getParent().text("Ready !", RECT_PLAYER2_COL_START+120, RECT_LINE_START+100);
		}
		// image player red & animation
		player2.getView().displayPlayer();

		wait = ! player1.getModel().get_validity() || ! player2.getModel().get_validity();

		if (!wait) { // transition (il faut que la derniere animation jump se termine)
			if (wait = (player1.getView().getCurrentAnim()!=player1.getView().getAnim("idle") || player2.getView().getCurrentAnim()!=player2.getView().getAnim("idle"))){}
			else {

				stopMusic();
				
				// re-initialise les position des joueurs
				player1.setX(initialX1);
				player1.setY(initialY);
				
				player2.setX(initialX2);
				player2.setY(initialY);
			}
		}

		return wait;
	}

	public void stopMusic() {
		son_music.stop();
	}
}
