package projet_graphisme;

import processing.core.PApplet;
import processing.core.PImage;

public class Meteorite {
	private Player my_ami;
	private Player my_ennemie;
	private PApplet my_parrent;
	private PImage me;
	
	private int INIT_X;
	private int INIT_Y;
	
	private int my_x;
	private int my_y;
	
	private boolean isVisible;
	
	Meteorite(PApplet parent, Player ami, Player ennemie) {
		my_parrent = parent;
		my_ami = ami;
		my_ennemie = ennemie;
		
		isVisible = false;
		
		INIT_X = my_parrent.width/2;
		INIT_Y = 0;
		
		me = my_parrent.loadImage("../ressources/meteorite.png");
	}
	
	public void hit() {
		isVisible = true;
		for(int cpt=0; cpt<=my_ennemie.getY()+my_ennemie.getHeight(); cpt++){
			System.out.println(my_ennemie.getX()+" - "+cpt);
			my_parrent.image(me, my_ennemie.getX(), cpt, 100, 200);
		}
		my_x = INIT_X;
		my_y = INIT_Y;
		//my_parrent.draw();
		isVisible = false;
	}
	
}
