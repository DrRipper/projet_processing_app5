package projet_graphisme;
import java.awt.Rectangle;

import processing.core.PApplet;
import processing.core.PImage;

class Player {
	private int my_idx;
	private float my_health;
	private float my_mana;
	private boolean my_validity;

	public final static float MAX_HEALTH = 100;
	public final static float MAX_MANA = 100;

	private float my_x_initial;
	private float my_y_initial;

	private float my_x;
	private float my_y;

	private PApplet my_parrent;

	private PImage me;
	private int my_height;
	private int my_width;

	private Meteorite my_meteorite;	
	
	private Player my_ennemie;

	Player(PApplet p, int idx, String img) {
		my_parrent = p;
		my_idx = idx;
		my_health = MAX_HEALTH;
		my_validity = false;
		my_mana = 0;

		my_y_initial = ((float)my_parrent.height)*0.75f;
		my_x_initial = ((float)my_parrent.width)*0.25f*my_idx;

		my_x = my_x_initial;
		my_y = my_y_initial;

		me = my_parrent.loadImage(img);

		my_height = 100;
		my_width = 100;
		
		

	}

	public void setEnnemie(Player p) {
		my_ennemie = p;
		my_meteorite = new Meteorite(my_parrent, this, my_ennemie);
	}

	void displayPlayer() {
		my_parrent.image(me, my_x, my_y, my_width, my_height);
	}

	void setX(float x) {
		if (x>=0 && x<=my_parrent.width-my_width )
			if (!collision_with_ennemie(true, x, my_y))
				my_x = x;
	}

	void magicalHit() {
		if(my_mana == MAX_MANA) {
			my_meteorite.hit();
			my_mana=0;
			my_ennemie.set_pv(my_ennemie.get_pv()-30);
		}
	}

	void setY(float y) {
		if (y>=0 && y<=my_parrent.height-my_height)
			if (!collision_with_ennemie(true, my_x, y))
				my_y = y;
	}

	int getX() {
		return  Math.round(my_x);
	}

	int getY() {
		return  Math.round(my_y);
	}

	void display_when_wait(){
		//my_parrent.noFill();
		//my_parrent.stroke(255);
		my_parrent.fill(255);
		my_parrent.stroke(0);
		draw_head_wait();
		draw_body_wait();
		draw_arm_wait();
		draw_legs_wait();
	}

	void display_when_valid(){
		my_parrent.fill(0);
		my_parrent.stroke(255);
		draw_head_valid();
		draw_body_valid();
		draw_arm_valid();
		draw_legs_valid();
	}

	void draw_head_wait() {
		for(int i= 0; i<16;i++){

			float x = my_parrent.cos( i*my_parrent.TWO_PI/16   )*50;
			float y = my_parrent.sin( i*my_parrent.TWO_PI/16   )*50;
			my_parrent.ellipse(my_idx*(my_parrent.width/4)+x,(my_parrent.height/4)+y,4,4);
			/*my_parrent.pushMatrix();
		my_parrent.translate(my_idx*(my_parrent.width/4),(my_parrent.height/4));
		my_parrent.sphereDetail(5,5);
		my_parrent.sphere(50);
		my_parrent.popMatrix();*/
		}

	}

	public boolean hit() {
		if (collision_with_ennemie(false, 0, 0)) {
			if (my_mana+5<MAX_MANA)
				my_mana += 5;
			else 
				my_mana= MAX_MANA;

			my_ennemie.set_pv(my_ennemie.get_pv()-5);
			return true;
		}
		return false;
	}

	public int getWidht() {
		return my_width;
	}

	public int getHeight() {
		return my_height;
	}

	public boolean collision_with_ennemie(boolean duringWalking, float x, float y) {
		return getBounds(duringWalking, x, y).intersects(my_ennemie.getBounds(duringWalking, my_ennemie.getX(), my_ennemie.getY()));
	}

	public Rectangle getBounds(boolean duringWalking, float x, float y) {
		if (duringWalking)
			return new Rectangle((int)x, (int)y, my_width, my_height);
		else // collision durant l'attaque, il faut être face à l'ennemie
			if (my_idx==1)
				return new Rectangle((int)my_x+my_width, (int)my_y, (int)my_x, my_height);
			else 
				return new Rectangle((int)my_x, (int)my_y, my_width, my_height);

	}

	void draw_body_wait() {
		float y = (my_parrent.height/4)+60;
		float x = my_idx*(my_parrent.width/4)-40;
		float w = 80;
		int h = 150;
		int step = 5;

		dottedLine (x,y,x+w,y, step); // upper line - 
		dottedLine (x,y,x,y+h, step);  // line left | 
		dottedLine (x+w,y,x+w,y+h, step); // line right | 
		dottedLine (x,y+h, x+w,y+h, step);     // lower line - 

		/*my_parrent.pushMatrix();
		my_parrent.translate(x+w/2,y+h/2);
		my_parrent.box(w,h,10);
		my_parrent.popMatrix();*/
	}

	void dottedLine(float x1, float y1, float x2, float y2, float steps){
		for(int i=0; i<=steps; i++) {
			float x = my_parrent.lerp(x1, x2, i/steps);
			float y = my_parrent.lerp(y1, y2, i/steps);
			my_parrent.noStroke();
			my_parrent.ellipse(x,y,4,4);
		}
	}

	void draw_arm_wait() {
		float y = (my_parrent.height/4)+60;
		float x = my_idx*(my_parrent.width/4)-40;

		float x_bis_left = x-100;
		float y_bis_left = y+150;

		float x_bis_right = x+100+80;
		float y_bis_right = y+150;

		int step = 5;

		dottedLine (x, y, x_bis_left, y_bis_left, step); // left line / 
		dottedLine (x+80, y, x_bis_right, y_bis_right, step);  // right line \ 
	}

	void draw_legs_wait() {
		float y = (my_parrent.height/4)+60;
		float x = my_idx*(my_parrent.width/4)-40;
		float w = 80;
		int h = 150;



		int step = 5;

		dottedLine (x+w, y+h, x+w, y+h+h, step); // right line | 
		dottedLine (x, y+h, x, y+h+h, step);  // left line | 

	}

	void draw_head_valid() {
		my_parrent.ellipse(my_idx*(my_parrent.width/4),(my_parrent.height/4),100,100);
	}

	void draw_body_valid() {
		float y = (my_parrent.height/4)+60;
		float x = my_idx*(my_parrent.width/4)-40;
		float w = 80;
		int h = 150;

		my_parrent.line (x,y,x+w,y); // upper line - 
		my_parrent.line (x,y,x,y+h);  // line left | 
		my_parrent.line (x+w,y,x+w,y+h); // line right | 
		my_parrent.line (x,y+h, x+w,y+h); // lower line - 
	}

	void draw_arm_valid() {
		float y = (my_parrent.height/4)+60;
		float x = my_idx*(my_parrent.width/4)-40;

		float x_bis_left = x-100;
		float y_bis_left = y-150;

		float x_bis_right = x+100+80;
		float y_bis_right = y-150;

		my_parrent.line (x, y, x_bis_left, y_bis_left); // left line / 
		my_parrent.line (x+80, y, x_bis_right, y_bis_right);  // right line \ 
	}

	void draw_legs_valid() {
		float y = (my_parrent.height/4)+60;
		float x = my_idx*(my_parrent.width/4)-40;
		float w = 80;
		int h = 150;

		my_parrent.line (x+w, y+h, x+w, y+h+h); // right line | 
		my_parrent.line (x, y+h, x, y+h+h);  // left line | 
	}

	void set_validity(boolean validity) {
		my_validity = validity; 
	}

	boolean get_validity() {
		return my_validity; 
	}

	void set_mana(float mana) {
		if (mana>=0 && mana<=MAX_MANA)
			my_mana = mana;
	}

	void set_pv(float pv) {
		if (pv>=0 && pv<=MAX_MANA)
			my_health = pv;
	}

	float get_pv() {
		return my_health;
	}

	float get_mana() {
		return my_mana;
	}

	void display_pv() {
		float rectWidth = 200;

		// Change color
		if (my_health < 25)
		{
			my_parrent.fill(255, 0, 0);
		}  
		else if (my_health < 50)
		{
			my_parrent.fill(255, 200, 0);
		}
		else
		{
			my_parrent.fill(0, 255, 0);
		}

		// Draw bar
		my_parrent.noStroke();
		// Get fraction 0->1 and multiply it by width of bar
		float drawWidth = (my_health / MAX_HEALTH) * rectWidth;
		my_parrent.rect((float) (((my_idx*1.5)-1.5)*(my_parrent.width/4)+10), 10, drawWidth, 10);

		// Outline
		my_parrent.stroke(0);
		my_parrent.noFill();
		my_parrent.rect((float) (((my_idx*1.5)-1.5)*(my_parrent.width/4)+10), 10, rectWidth, 10);

	}

	void display_mana() {
		float rectWidth = 200;

		// Change color
		my_parrent.fill(196, 254, 246);


		// Draw bar
		my_parrent.noStroke();
		// Get fraction 0->1 and multiply it by width of bar
		float drawWidth = (my_mana / MAX_MANA) * rectWidth;
		my_parrent.rect((float) (((my_idx*1.5)-1.5)*(my_parrent.width/4)+10), 30, drawWidth, 10);

		// Outline
		my_parrent.stroke(0);
		my_parrent.noFill();
		my_parrent.rect((float) (((my_idx*1.5)-1.5)*(my_parrent.width/4)+10), 30, rectWidth, 10);

	}

}