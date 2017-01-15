package Model;
import Main.GlitchesBattle;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.opengl.*;

public class Scene {
	PApplet my_parent;
	PImage floor_texture;
	PImage bordures;
	
	private static Son son_music;

	float floorLevel = 900.0f;

	// camera / where you are 
	float xpos,ypos,zpos, xlookat,ylookat,zlookat; 
	float angle=0.0f; // (angle left / right; 0..359

	public Scene(PApplet p) {
		my_parent = p;
		
		son_music = new Son(((GlitchesBattle) my_parent).getMinim(), my_parent, "../ressources/fight.mp3");

		floor_texture = my_parent.loadImage("../ressources/sol.jpg");
		bordures = my_parent.loadImage("../ressources/bas_sol.png");
	}

	public void display(int joueurEnAvance) {
		/*my_parent.camera(
				my_parent.width/2.0f, 
				my_parent.height/3f, 
				(my_parent.height/1.5f) / my_parent.tan(my_parent.PI*30.0f / 180.0f), 
				my_parent.width/2.0f, 
				200+my_parent.height/2.0f, 
				500f, 
				0f, 
				1f, 
				0f);*/
		//System.out.println(GlitchesBattle.cam);
		
		if (son_music.getMusicMenu().isPlaying() == false){
			son_music.getMusicMenu().loop(); //rewind() possible
		}
		
		plane(floor_texture);
	}

	private void plane (PImage img) {
		float x = -250;
		float x_prim = x+400+my_parent.width;
		float y = my_parent.height+100;
		float z = -1500;

		my_parent.image(bordures, x, y);
		my_parent.image(bordures, x+bordures.width, y);

		my_parent.fill(13, 179, 113);
		my_parent.stroke(0);
		my_parent.pushMatrix();
		//my_parent.translate(x, y, z); 
		my_parent.beginShape(PConstants.QUAD); 
		my_parent.textureMode(PConstants.NORMAL);
		my_parent.texture(img);
		//my_parent.box(1500, 10, 1500);

		my_parent.fill(255, 255, 255);
		// +Z "front" face
		my_parent.vertex(x, y,  z+1000, 0, 0);
		my_parent.vertex(x_prim, y,  z+1000, 1, 0);
		my_parent.vertex(x_prim,  y+30,  z+1000, 1, 1);
		my_parent.vertex(x,  y+30,  z+1000, 0, 1);

		// -Z "back" face
		my_parent.vertex(600+x, y, z, 0, 0);
		my_parent.vertex(600+x_prim, y, z, 1, 0);
		my_parent.vertex(600+x_prim,  y+30, z, 1, 1);
		my_parent.vertex(600+x,  y+30, z, 0, 1);

		// +Y "bottom" face
		my_parent.vertex(x,  y+30,  z+1000, 0, 0);
		my_parent.vertex(x_prim,  y+30,  z+1000, 1, 0);
		my_parent.vertex(600+x_prim,  y+30, z, 1, 1);
		my_parent.vertex(600+x,  y+30, z, 0, 1);

		// -Y "top" face
		my_parent.vertex(600+x, y, z, 0, 0);
		my_parent.vertex(600+x_prim, y, z, 1, 0);
		my_parent.vertex(x_prim, y,  z+1000, 1, 1);
		my_parent.vertex(x, y,  z+1000, 0, 1);

		// +X "right" face
		my_parent.vertex(x_prim, y,  z+1000, 0, 0);
		my_parent.vertex(x_prim, y, z, 1, 0);
		my_parent.vertex(600+x_prim,  y+30, z, 1, 1);
		my_parent.vertex(x_prim,  y+30,  z+1000, 0, 1);

		// -X "left" face
		my_parent.vertex(600+x, y, z, 0, 0);
		my_parent.vertex(x, y,  z+1000, 1, 0);
		my_parent.vertex(x,  y+30,  z+1000, 1, 1);
		my_parent.vertex(600+x,  y+30, z, 0, 1);




		my_parent.endShape();
		my_parent.popMatrix();
	}
	
	public void stopMusic() {
		son_music.stop();
	}
}
