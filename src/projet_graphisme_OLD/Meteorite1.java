package projet_graphisme_OLD;

import processing.core.PApplet;
import processing.core.PImage;

public class Meteorite1 {
	private Player my_ami;
	private Player my_ennemie;
	private PApplet my_parent;
	private PImage me;
	
	private int INIT_X;
	private int INIT_Y;
	
	private int my_x;
	private int my_y;
		
	Meteorite1(PApplet parent, Player ami, Player ennemie) {
		my_parent = parent;
		my_ami = ami;
		my_ennemie = ennemie;
				
		INIT_X = my_parent.width/2;
		INIT_Y = 0;
		
		me = my_parent.loadImage("../ressources/meteorite.png");
	}
	
	public void hit() {
		for(int cpt=0; cpt<=my_ennemie.getY()+my_ennemie.getHeight(); cpt++){
			my_parent.delay(100);
			my_parent.image(me, my_ennemie.getX(), cpt, 100, 200);
		}
		my_x = INIT_X;
		my_y = INIT_Y;
		//my_parrent.draw();
	}
	
}
