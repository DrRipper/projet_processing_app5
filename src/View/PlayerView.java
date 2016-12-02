package View;

import java.util.HashMap;
import Model.Player;
import processing.core.PApplet;
import Model.PairAnim;
import Model.Animation;

public class PlayerView {
	private Player my_model;
	private int deltaX = 512/2;
	private int deltaY = 512-50;
	private String pathAnims = "./ressources/character/";
	public PApplet parent;
	public HashMap<String,PairAnim> anims;
	public PairAnim currentAnim;
	public PairAnim lastAnim;

	public PlayerView(Player p) {
		my_model = p;
		parent = my_model.getParent();
		if (my_model.color == Player.BLUE) 
			pathAnims += "blue/";
		else
			pathAnims += "red/";
		anims = new HashMap<String,PairAnim>();
		addAnimation("death", 1, true);
		addAnimation("hurt", 11, false);
		addAnimation("idle", 15, true);
		addAnimation("jump", 12, false);
		addAnimation("run", 8, true);
		addAnimation("slash", 11, false);
		addAnimation("slashjump", 12, false);
		addAnimation("walk", 8, true);
		idle();
	}

	void display_pv() {
		float rectWidth = 200;

		// Change color
		if (my_model.get_pv() < 25)
		{
			my_model.getParent().fill(255, 0, 0);
		}  
		else if (my_model.get_pv() < 50)
		{
			my_model.getParent().fill(255, 200, 0);
		}
		else
		{
			my_model.getParent().fill(0, 255, 0);
		}

		// Draw bar
		my_model.getParent().noStroke();
		// Get fraction 0->1 and multiply it by width of bar
		float drawWidth = (my_model.get_pv() / Player.MAX_HEALTH) * rectWidth;
		my_model.getParent().rect((float) (((my_model.getIdx()*1.5)-1.5)*(my_model.getParent().width/4)+10), 10, drawWidth, 10);

		// Outline
		my_model.getParent().stroke(0);
		my_model.getParent().noFill();
		my_model.getParent().rect((float) (((my_model.getIdx()*1.5)-1.5)*(my_model.getParent().width/4)+10), 10, rectWidth, 10);

	}

	void display_mana() {
		float rectWidth = 200;

		// Change color
		my_model.getParent().fill(196, 254, 246);


		// Draw bar
		my_model.getParent().noStroke();
		// Get fraction 0->1 and multiply it by width of bar
		float drawWidth = (my_model.get_mana() / Player.MAX_MANA) * rectWidth;
		my_model.getParent().rect((float) (((my_model.getIdx()*1.5)-1.5)*(my_model.getParent().width/4)+10), 30, drawWidth, 10);

		// Outline
		my_model.getParent().stroke(0);
		my_model.getParent().noFill();
		my_model.getParent().rect((float) (((my_model.getIdx()*1.5)-1.5)*(my_model.getParent().width/4)+10), 30, rectWidth, 10);

	}

	void display_when_wait(){
		//my_parrent.noFill();
		//my_parrent.stroke(255);
		my_model.getParent().fill(255);
		my_model.getParent().stroke(0);
		draw_head_wait();
		draw_body_wait();
		draw_arm_wait();
		draw_legs_wait();
	}

	void display_when_valid(){
		my_model.getParent().fill(0);
		my_model.getParent().stroke(255);
		draw_head_valid();
		draw_body_valid();
		draw_arm_valid();
		draw_legs_valid();
	}

	void displayPlayer() {
		//my_model.getParent().image(my_model.getSprite(), my_model.getX(), my_model.getY(), my_model.getWidht(), my_model.getHeight());
		int x = my_model.getX();
		int y = my_model.getY();
		boolean lastFrame = sens(currentAnim).display(x-deltaX,y-deltaY);
	    if (!sens(currentAnim).loopable)
	      if (lastFrame) {
	        currentAnim = lastAnim;
	        my_model.hurting = false;
	      }
	}

	void draw_head_wait() {
		for(int i= 0; i<16;i++){

			float x = my_model.getParent().cos( i*my_model.getParent().TWO_PI/16   )*50;
			float y = my_model.getParent().sin( i*my_model.getParent().TWO_PI/16   )*50;
			my_model.getParent().ellipse(my_model.getIdx()*(my_model.getParent().width/4)+x,(my_model.getParent().height/4)+y,4,4);
			/*my_parrent.pushMatrix();
		my_parrent.translate(my_idx*(my_parrent.width/4),(my_parrent.height/4));
		my_parrent.sphereDetail(5,5);
		my_parrent.sphere(50);
		my_parrent.popMatrix();*/
		}

	}

	void draw_body_wait() {
		float y = (my_model.getParent().height/4)+60;
		float x = my_model.getIdx()*(my_model.getParent().width/4)-40;
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
			float x = my_model.getParent().lerp(x1, x2, i/steps);
			float y = my_model.getParent().lerp(y1, y2, i/steps);
			my_model.getParent().noStroke();
			my_model.getParent().ellipse(x,y,4,4);
		}
	}

	void draw_arm_wait() {
		float y = (my_model.getParent().height/4)+60;
		float x = my_model.getIdx()*(my_model.getParent().width/4)-40;

		float x_bis_left = x-100;
		float y_bis_left = y+150;

		float x_bis_right = x+100+80;
		float y_bis_right = y+150;

		int step = 5;

		dottedLine (x, y, x_bis_left, y_bis_left, step); // left line / 
		dottedLine (x+80, y, x_bis_right, y_bis_right, step);  // right line \ 
	}

	void draw_legs_wait() {
		float y = (my_model.getParent().height/4)+60;
		float x = my_model.getIdx()*(my_model.getParent().width/4)-40;
		float w = 80;
		int h = 150;



		int step = 5;

		dottedLine (x+w, y+h, x+w, y+h+h, step); // right line | 
		dottedLine (x, y+h, x, y+h+h, step);  // left line | 

	}

	void draw_head_valid() {
		my_model.getParent().ellipse(my_model.getIdx()*(my_model.getParent().width/4),(my_model.getParent().height/4),100,100);
	}

	void draw_body_valid() {
		float y = (my_model.getParent().height/4)+60;
		float x = my_model.getIdx()*(my_model.getParent().width/4)-40;
		float w = 80;
		int h = 150;

		my_model.getParent().line (x,y,x+w,y); // upper line - 
		my_model.getParent().line (x,y,x,y+h);  // line left | 
		my_model.getParent().line (x+w,y,x+w,y+h); // line right | 
		my_model.getParent().line (x,y+h, x+w,y+h); // lower line - 
	}

	void draw_arm_valid() {
		float y = (my_model.getParent().height/4)+60;
		float x = my_model.getIdx()*(my_model.getParent().width/4)-40;

		float x_bis_left = x-100;
		float y_bis_left = y-150;

		float x_bis_right = x+100+80;
		float y_bis_right = y-150;

		my_model.getParent().line (x, y, x_bis_left, y_bis_left); // left line / 
		my_model.getParent().line (x+80, y, x_bis_right, y_bis_right);  // right line \ 
	}

	void draw_legs_valid() {
		float y = (my_model.getParent().height/4)+60;
		float x = my_model.getIdx()*(my_model.getParent().width/4)-40;
		float w = 80;
		int h = 150;

		my_model.getParent().line (x+w, y+h, x+w, y+h+h); // right line | 
		my_model.getParent().line (x, y+h, x, y+h+h);  // left line | 
	}

	// loopable anims
	public void idle() {
		lastAnim = currentAnim = anims.get("idle");
	}
	public void run() {
		lastAnim = currentAnim = anims.get("run");
	}
	public void walk() {
		lastAnim = currentAnim = anims.get("walk");
	}
	public void death() {
		lastAnim = currentAnim = anims.get("death");
	}

	// not loopable anims
	public void hurt() {
		currentAnim = anims.get("hurt");
	}
	public void jump() {
		currentAnim = anims.get("jump");
	}
	public void slash() {
		currentAnim = anims.get("slash"); 
	}
	public void slashjump() {
		currentAnim = anims.get("slashjump"); 
	}

	private void addAnimation(String nameAnim, int count, boolean loop) {
		String l_path = pathAnims+"l_"+nameAnim+"/";
		String r_path = pathAnims+"r_"+nameAnim+"/";
		PairAnim panims = new PairAnim();
		panims.right = new Animation(parent, r_path,count, loop, nameAnim);
		panims.left = new Animation(parent, l_path,count, loop, nameAnim);
		anims.put(nameAnim, panims);
	}
	
	private Animation sens(PairAnim pa){
	    return (my_model.right())? pa.right : pa.left;
	  }

}
