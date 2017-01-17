package Model;
import Main.GlitchesBattle;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;
import processing.opengl.*;

public class Scene {
	PApplet my_parent;

	PShape tree;
	PShape mountain;
	PShape grass;
	
	private static Son son_music;

	float floorLevel = 900.0f;

	// camera / where you are 
	float xpos,ypos,zpos, xlookat,ylookat,zlookat; 
	float angle=0.0f; // (angle left / right; 0..359

	public Scene(PApplet p) {
		my_parent = p;

		son_music = new Son(((GlitchesBattle) my_parent).getMinim(), my_parent, "../ressources/fight.mp3");

		mountain = my_parent.loadShape("../ressources/background/94k9b8bowvb4-LowPolyMountains/Model/lowpolymountains.obj");
		mountain.scale(60);

		tree = my_parent.loadShape("../ressources/background/7bmzbr68wxkw-LowPolyTree/lowpolytree.obj");
		tree.scale(20);
		
		
		
		grass = my_parent.loadShape("../ressources/background/9ixfzsvil37k-Grass/grass obj.obj");
		grass.setFill(my_parent.color(0, 128, 0));
		grass.scale(20);


	}

	public void display() {
		if (son_music.getMusicMenu().isPlaying() == false){
			son_music.getMusicMenu().loop(); //rewind() possible
		}

		my_parent.pushMatrix();
		my_parent.translate(my_parent.width/2, my_parent.height-300, 50);
		my_parent.rotateZ(3.14f);
		my_parent.shape(mountain);
		my_parent.popMatrix();

		my_parent.pushMatrix();
		my_parent.translate(150, 500, 150);
		my_parent.rotateZ(3.14f);
		my_parent.shape(tree);
		my_parent.popMatrix();
		
		my_parent.pushMatrix();
		my_parent.translate(190, 530, 155);
		my_parent.rotateZ(3.14f);
		my_parent.shape(tree);
		my_parent.popMatrix();
		
		my_parent.pushMatrix();
		my_parent.translate(200, 600, 200);
		my_parent.rotateZ(3.14f);
		my_parent.shape(grass);
		my_parent.popMatrix();
		
		my_parent.pushMatrix();
		my_parent.translate(750, 600, 200);
		my_parent.rotateZ(3.14f);
		my_parent.shape(grass);
		my_parent.popMatrix();
		
		my_parent.pushMatrix();
		my_parent.translate(250, 600, 190);
		my_parent.rotateZ(3.14f);
		my_parent.shape(grass);
		my_parent.popMatrix();

	}

	

	public void stopMusic() {
		son_music.stop();
	}
}
