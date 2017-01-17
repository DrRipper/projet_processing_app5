package View;

import java.util.HashMap;
import Model.Player;
import Model.Son;
import ddf.minim.AudioInput;
import processing.core.PApplet;
import processing.core.PGraphics;
import Model.PairAnim;
import Model.Animation;
import Main.GlitchesBattle;

public class PlayerView {
	private Player my_model;
	private int deltaX = 512/2;
	private int deltaY = 512-50;
	private int deltaZ = 512-50;
	private String pathAnims = "./ressources/character/";
	private String pathAnimsMeteor = "./ressources/meteorite/";
	public PApplet parent;
	public HashMap<String,PairAnim> anims;
	public PairAnim currentAnim;
	public PairAnim lastAnim;
	private static Son son_validation;
	private boolean meteor = false;
	private static Son sonHit;
	private static Son sonBlessure;
	private boolean voice;
	private final static int METEOR_SIZE = 140;
	private int idx_curseur_voix;
	private int increment_voix;
	private Son recording;
	private AudioInput in;
	private int bonusDegats;

	public PlayerView(Player p) {
		my_model = p;

		son_validation = new Son(((GlitchesBattle) my_model.getParent()).getMinim(), my_model.getParent(), "../ressources/epee.mp3");
		sonHit = new Son(((GlitchesBattle) my_model.getParent()).getMinim(), my_model.getParent(), "../ressources/epee.mp3");
		sonBlessure = new Son(((GlitchesBattle) my_model.getParent()).getMinim(), my_model.getParent(), "../ressources/coup_ventre.mp3");
		recording = new Son(((GlitchesBattle) my_model.getParent()).getMinim());

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
		addAnimation("meteor", 10, false);

		idle();

		//PVDisplay = parent.createGraphics(parent.width, 100);
	}

	public void display_pv() {
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
		if(my_model.getIdx()==1)
			my_model.getParent().rect((float) (((my_model.getIdx()*1.5)-1.5)*(my_model.getParent().width/4)+10), 10, drawWidth, 10);
		else
			my_model.getParent().rect((float) (((my_model.getIdx()*1.5)-1.5)*(my_model.getParent().width/4)+10)+rectWidth, 10, -drawWidth, 10);


		// Outline
		my_model.getParent().stroke(0);
		my_model.getParent().noFill();
		my_model.getParent().rect((float) (((my_model.getIdx()*1.5)-1.5)*(my_model.getParent().width/4)+10), 10, rectWidth, 10);

	}

	void display_mana() {
		float rectWidth = 200;

		// Change color
		my_model.getParent().fill(26, 140, 255);


		// Draw bar
		my_model.getParent().noStroke();
		// Get fraction 0->1 and multiply it by width of bar
		float drawWidth = (my_model.get_mana() / Player.MAX_MANA) * rectWidth;
		if(my_model.getIdx()==1)
			my_model.getParent().rect((float) (((my_model.getIdx()*1.5)-1.5)*(my_model.getParent().width/4)+10), 30, drawWidth, 10);
		else
			my_model.getParent().rect((float) (((my_model.getIdx()*1.5)-1.5)*(my_model.getParent().width/4)+10)+rectWidth, 30, -drawWidth, 10);


		// Outline
		my_model.getParent().stroke(0);
		my_model.getParent().noFill();
		my_model.getParent().rect((float) (((my_model.getIdx()*1.5)-1.5)*(my_model.getParent().width/4)+10), 30, rectWidth, 10);

	}

	public boolean displayPlayer() {
		//my_model.getParent().image(my_model.getSprite(), my_model.getX(), my_model.getY(), my_model.getWidht(), my_model.getHeight());
		int x = my_model.getX();
		int y = my_model.getY();
		int z = my_model.getZ();

		boolean lastFrame = sens(currentAnim).display(x-deltaX,y-deltaY,z);
		//System.out.println(lastFrame + " ---- " + currentAnim.equals(anims.get("slash")));
		/*if(!lastFrame && currentAnim.equals(anims.get("slash"))) {
			((GlitchesBattle)my_model.getParent()).setAnimationHitState(my_model.getIdx(), false);
		} else if (lastFrame){
			((GlitchesBattle)my_model.getParent()).setAnimationHitState(my_model.getIdx(), true);
		}*/

		if (!sens(currentAnim).loopable)
			if (lastFrame) {
				currentAnim = lastAnim;
				my_model.hurting = false;
			} 

		// Explosion et barre pour la voix si super attaque
		my_model.getParent().hint(my_model.getParent().DISABLE_DEPTH_TEST);
		my_model.getParent().textMode(my_model.getParent().MODEL);
		if (voice || meteor) {

			my_model.getParent().noStroke();
			// barre claire
			my_model.getParent().fill(255, 153, 153);
			my_model.getParent().rect( my_model.getParent().width/2 - 300, 300 + my_model.getParent().height/2, 600, 10);
			my_model.getParent().fill(255, 51, 51);
			my_model.getParent().rect(my_model.getParent().width/2 - 300 + 200, 300 + my_model.getParent().height/2, 200, 10);
			my_model.getParent().fill(179, 0, 0);
			my_model.getParent().rect(my_model.getParent().width/2 - 300 + 250, 300 + my_model.getParent().height/2, 100, 10);

			// curseur
			my_model.getParent().fill(255, 230, 230);
			my_model.getParent().rect(my_model.getParent().width/2 - 300 + idx_curseur_voix, 290 + my_model.getParent().height/2, 10, 30);

			if (idx_curseur_voix+increment_voix>600)
				increment_voix = -20;
			else if (idx_curseur_voix+increment_voix<0)
				increment_voix = 20;

			if((int)(in.left.level() * 100)>9)
			{
				if(idx_curseur_voix>=250 && idx_curseur_voix<=350)
					bonusDegats = 20;
				else if (idx_curseur_voix>=200 && idx_curseur_voix<=400)
					bonusDegats = 10;
				else
					bonusDegats = 0;

				voice=false;
				in.disableMonitoring();
			}

			if(voice)
				idx_curseur_voix += increment_voix;
		}


		if (meteor && !voice) {			
			int xm = my_model.getX()-250;
			int ym = my_model.getY()-350;

			boolean lastFrameMeteor = sens(anims.get("meteor")).display(xm,ym, my_model.getZ()+10);
			if (lastFrameMeteor) {
				meteor = false;
				my_model.controler.getView().hurt();
			}
		}
		my_model.getParent().hint(my_model.getParent().ENABLE_DEPTH_TEST);

		return lastFrame;
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
		sonBlessure.getMusicMenu().play(0);
		currentAnim = anims.get("hurt");
	}

	public void jump() {
		currentAnim = anims.get("jump");
	}

	public void slash() {
		sonHit.getMusicMenu().play(0);
		currentAnim = anims.get("slash"); 
	}

	public void slashjump() {
		currentAnim = anims.get("slashjump"); 
	}

	public void meteor() {
		//currentAnim = anims.get("meteor");
		my_model.getEnnemie().getView().setMeteor(true);
	}

	public void setMeteor(boolean value) {
		idx_curseur_voix = 0;
		increment_voix = 20;
		voice = value;
		meteor = value;

		in = recording.getVoice();

		if ( in.isMonitoring() )
		{
			in.disableMonitoring();
		}
		else
		{
			in.enableMonitoring();
		}
	}

	private void addAnimation(String nameAnim, int count, boolean loop) {
		String l_path;
		String r_path;
		if (nameAnim.equals("meteor"))
		{
			l_path = pathAnimsMeteor+"lr_"+nameAnim+"/";
			r_path = pathAnimsMeteor+"lr_"+nameAnim+"/";
		}else {
			l_path = pathAnims+"l_"+nameAnim+"/";
			r_path = pathAnims+"r_"+nameAnim+"/";
		}
		PairAnim panims = new PairAnim();
		panims.right = new Animation(parent, r_path,count, loop, nameAnim);
		panims.left = new Animation(parent, l_path,count, loop, nameAnim);
		anims.put(nameAnim, panims);
	}

	private Animation sens(PairAnim pa){
		return (my_model.right())? pa.right : pa.left;
	}

	public PairAnim getCurrentAnim() {
		return currentAnim;
	}

	public PairAnim getAnim(String anim) {
		return anims.get(anim);
	}

	public void play_validation() {

		son_validation.getMusicMenu().play(0);

		//	son_validation.getMusicMenu().close();
	}

	public void stopMusic() {
		sonHit.stop();
		sonBlessure.stop();
	}
}
