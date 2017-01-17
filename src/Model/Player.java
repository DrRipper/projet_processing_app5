package Model;

import java.awt.Rectangle;

import Controler.PlayerControler;
import View.PlayerView;
import processing.core.PApplet;
import processing.core.PImage;

public class Player {
	private int my_idx;
	private float my_health;
	private float my_mana;
	private boolean my_validity;
	public PlayerControler controler;

	public final static float MAX_HEALTH = 100;
	public final static float MAX_MANA = 100;

	private float my_x_initial;
	private float my_y_initial;
	private float my_z_initial;

	private PApplet my_parent;
	
	// TODO : int ?
	private float my_x;
	private float my_y;
	private float my_z;
	
	private float x_meteor;
	private float y_meteor;
	
	private int deltaX;
	private int deltaY;

	private int my_height;
	private int my_width;

	private Player my_ennemie;
	private Meteorite my_meteorite;

	private boolean right;
	public HitBox hitbox;
	public HurtBox hurtbox;
	public boolean hurting;
	public int color;

	public final static int RED = 0;
	public final static int BLUE = 1;

	public Player(PApplet p, int idx, PlayerControler control) {
		my_parent = p;
		my_idx = idx;
		my_health = MAX_HEALTH;
		my_validity = false;
		my_mana = 0;
		controler = control;

		my_y_initial = 800;//((float)my_parent.height)*0.75f; -45
		
		if(my_idx==1)
			my_x_initial = 580;
		else
			my_x_initial = 780;

		my_z_initial = 220;
				
		my_x = my_x_initial;
		my_y = my_y_initial;
		my_z = my_z_initial;

		x_meteor = ((float)my_parent.width)*0.25f*(1-my_idx);
		y_meteor = 0;

		my_height = 100;
		my_width = 100;

		if (idx == 1) {
			color = RED;
			right = true;
		}
		else {
			color = BLUE;
			right = false;
		}

		initBox();

	}

	public int getIdx() {
		return my_idx;
	}

	public PApplet getParent() {
		return my_parent;
	}

	public void setEnnemie(Player p) {
		my_ennemie = p;
		//my_meteorite = new Meteorite(my_parent, this, my_ennemie);
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

	public void setZ(float z) {
		if (z>=-40 && z<=460)
			if (!collision_with_ennemie(true, my_x, my_y))
				my_z = z;
	}
	
	public int getX() {
		return  Math.round(my_x);
	}

	public int getY() {
		return  Math.round(my_y);
	}
	
	public int getZ() {
		return  Math.round(my_z);
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
		if (pv>0 && pv<=MAX_HEALTH)
			my_health = pv;
		else if (pv<=0) {
			my_health = 0;
			controler.getView().death();
		}
	}

	public float get_pv() {
		return my_health;
	}

	public float get_mana() {
		return my_mana;
	}

	public boolean collision_with_ennemie(boolean duringWalking, float x, float y) {
		if (my_z != my_ennemie.getZ())
			return false;
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

	public Player getEnnemie() {
		return my_ennemie;
	}

	private void initBox() {
		boolean displayBoxes = false;
		hitbox = new HitBox(my_parent, right);
		hitbox.displaying = displayBoxes;
		hurtbox = new HurtBox(my_parent, right);
		hurtbox.displaying = displayBoxes;
	}

	public void changerSens() {
		right = !right;
		hitbox.setSens(right);
		hurtbox.setSens(right);
	}

	public boolean right() {
		return right;
	}

	public void move(int dx, int dy, int dz) {
		setX(my_x + dx);
		setY(my_y + dy);
		setZ(my_z + dz);
		//my_x += dx;
		//my_y += dy;
		//my_z += dz;

		// tester vitesse ? moving?
		if ((right && dx<0) || (!right && dx>0))
			changerSens();
	}

	public PlayerView getView() {
		return controler.getView();
	}
	
	public void update() {
		my_x += deltaX;
		my_y += deltaY;
		hitbox.update((int)my_x, (int)my_y);
		if (hurting) {
			hurtbox.update((int)my_x, (int)my_y);
	    }
	}

	public int getX_meteor() {
		return Math.round(x_meteor);
	}
	
	public int getY_meteor() {
		return Math.round(y_meteor);
	}
}
