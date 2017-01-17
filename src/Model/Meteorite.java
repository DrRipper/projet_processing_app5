package Model;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class Meteorite {
	private Player my_ami;
	private Player my_ennemie;
	private PApplet my_parent;
	private PImage me;
	
	private int INIT_X;
	private int INIT_Y;
	
	private int my_x;
	private int my_y;
	
	private String pathAnims = "./ressources/meteorite/sprites/";
	public HashMap<String,PairAnim> anims;
	public PairAnim currentAnim;


	
	private boolean isVisible;
	
	/*Meteorite(PApplet parent, Player ami, Player ennemie) {
		my_parent = parent;
		my_ami = ami;
		my_ennemie = ennemie;
		
		isVisible = false;
		
		INIT_X = my_parent.width/2;
		INIT_Y = 0;
		
		me = my_parent.loadImage("../ressources/meteorite.png");
		
		anims = new HashMap<String,PairAnim>();
		addAnimation("meteor", 10, false);
	}*/
	
	/*public void hit() {
		System.out.println("HIT METEORITE");
		isVisible = true;
		for(float cpt=0; cpt<=my_ennemie.getY()+my_ennemie.getHeight(); cpt+=0.02){
			my_parent.image(me, my_ennemie.getX(), cpt, 100, 200);
			my_parent.redraw();
			//my_parent.delay(1000);
		}
		my_x = INIT_X;
		my_y = INIT_Y;
		//my_parrent.draw();
		isVisible = false;
	}*/
	
	
	private void addAnimation(String nameAnim, int count, boolean loop) {
		PairAnim panims = new PairAnim();
		panims.right = new Animation(my_parent, pathAnims, count, loop, nameAnim);
		panims.left = new Animation(my_parent, pathAnims, count, loop, nameAnim);
		anims.put(nameAnim, panims);
	}
	
}
