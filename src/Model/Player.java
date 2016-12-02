package Model;

import java.awt.Rectangle;

import processing.core.PApplet;
import processing.core.PImage;

public class Player {
	private int my_idx;
	private float my_health;
	private float my_mana;
	private boolean my_validity;

	public final static float MAX_HEALTH = 100;
	public final static float MAX_MANA = 100;

	private float my_x_initial;
	private float my_y_initial;
	
	private PApplet my_parent;

	private float my_x;
	private float my_y;

	private PImage me;
	private int my_height;
	private int my_width;
	
	private Player my_ennemie;
	private Meteorite my_meteorite;
	
	public Player(PApplet p, int idx, String img) {
		my_parent = p;
		my_idx = idx;
		my_health = MAX_HEALTH;
		my_validity = false;
		my_mana = 0;

		my_y_initial = ((float)my_parent.height)*0.75f;
		my_x_initial = ((float)my_parent.width)*0.25f*my_idx;

		my_x = my_x_initial;
		my_y = my_y_initial;

		me = my_parent.loadImage(img);

		my_height = 100;
		my_width = 100;

	}
	
	public int getIdx() {
		return my_idx;
	}
	
	public PApplet getParent() {
		return my_parent;
	}

	public void setEnnemie(Player p) {
		my_ennemie = p;
		my_meteorite = new Meteorite(my_parent, this, my_ennemie);
	}
	
	public Meteorite getMeteorite() {
		return my_meteorite;
	}
	
	public void setX(float x) {
		if (x>=0 && x<=my_parent.width-my_width )
			if (!collision_with_ennemie(true, x, my_y))
				my_x = x;
	}

	public void setY(float y) {
		if (y>=0 && y<=my_parent.height-my_height)
			if (!collision_with_ennemie(true, my_x, y))
				my_y = y;
	}

	public int getX() {
		return  Math.round(my_x);
	}

	public int getY() {
		return  Math.round(my_y);
	}

	public int getWidht() {
		return my_width;
	}

	public int getHeight() {
		return my_height;
	}
	
	public void set_validity(boolean validity) {
		my_validity = validity; 
	}

	public boolean get_validity() {
		return my_validity; 
	}

	public void set_mana(float mana) {
		if (mana>=0 && mana<=MAX_MANA)
			my_mana = mana;
	}

	public void set_pv(float pv) {
		if (pv>=0 && pv<=MAX_HEALTH)
			my_health = pv;
		else if (pv<0)
			my_health = 0;
	}

	public float get_pv() {
		return my_health;
	}

	public float get_mana() {
		return my_mana;
	}
	
	public boolean collision_with_ennemie(boolean duringWalking, float x, float y) {
		return getBounds(duringWalking, x, y).intersects(my_ennemie.getBounds(duringWalking, my_ennemie.getX(), my_ennemie.getY()));
	}

	public Rectangle getBounds(boolean duringWalking, float x, float y) {
		if (duringWalking)
			return new Rectangle((int)x, (int)y, my_width, my_height);
		else // collision durant l'attaque, il faut être face à l'ennemie
			if (my_idx==1)
				return new Rectangle((int)my_x+my_width, (int)my_y, (int)1, my_height);
			else 
				return new Rectangle((int)my_x, (int)my_y, my_width, my_height);

	}

	public PImage getSprite() {
		return me;
	}

	public Player getEnnemie() {
		return my_ennemie;
	}
}
