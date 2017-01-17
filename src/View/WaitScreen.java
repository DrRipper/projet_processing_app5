package View;

import Controler.MenuControler;
import Controler.OptionsControler;
import Controler.PlayerControler;
import Main.GlitchesBattle;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class WaitScreen {
	private PApplet my_parent; 
	private PImage logo;
	private int avancement;
	private PFont font; 
	private float da;
	private PlayerControler player1, player2;
	private int step;

	public WaitScreen(PApplet p) {
		my_parent = p;
		avancement = 0;
		step = 0;
		font = my_parent.createFont("Georgia",20,true);
		my_parent.textFont(font);
		logo = my_parent.loadImage("../ressources/logo.png");
		da = (logo.width*3f)/5;
		player1 = null;
		player2 = null;
	}
	
	public void init() {
		avancement = 0;
		step = 0;
		my_parent.textFont(font);
		
		
	}

	public void display() {
		my_parent.background(255);
		my_parent.fill(255, 140, 102);

		my_parent.image(logo, my_parent.width/2 - logo.width, my_parent.height/2 - logo.height, logo.width*2, logo.height*2);

		my_parent.text("Please wait...", my_parent.width/2 - 50, logo.height + my_parent.height/2);
		my_parent.noStroke();
		my_parent.rect( my_parent.width/2 - logo.width*1.5f, logo.height*1.5f + my_parent.height/2, logo.width*3, 10);
		my_parent.fill(255, 51, 51);
		my_parent.rect( my_parent.width/2 - logo.width*1.5f, logo.height*1.5f + my_parent.height/2, avancement, 10);

	}

	public boolean nextStep() {
		switch (step) {
		case 1: {
			if (player1 == null)
				player1 = new PlayerControler(my_parent, 1);
			else
				player1.initRound();
			avancement += da;
			step++;
			return true;
		}
		case 2: {
			if (player2 == null)
				player2 = new PlayerControler(my_parent, 3);
			else
				player2.initRound();
			avancement += da;
			step++;
			return true;
		}
		case 3: {
			player1.setEnnemie(player2);
			player2.setEnnemie(player1);
			((GlitchesBattle)my_parent).setPlayer1(player1);
			((GlitchesBattle)my_parent).setPlayer2(player2);
			avancement += da;
			step++;
			return true;
		}
		case 4: {
			MenuControler menuControler = new MenuControler(my_parent, player1, player2);
			((GlitchesBattle)my_parent).setMenuControler(menuControler);
			avancement += da;
			step++;
			return true;
		}
		case 5: {
			OptionsControler optionsControler = new OptionsControler(my_parent);
			((GlitchesBattle)my_parent).setOptionsControler(optionsControler);
			avancement += da;
			step++;
			return false;
		}
		default: {
			step++;
			return true;
		}
		}
	}

	public void setAvancement(int d) {
		avancement = d;
	}
}
