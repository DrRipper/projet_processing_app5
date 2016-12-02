package Model;
import processing.core.PApplet;
import processing.opengl.*;

public class Scene {
	PApplet my_parent;

	float floorLevel = 900.0f;

	// camera / where you are 
	float xpos,ypos,zpos, xlookat,ylookat,zlookat; 
	float angle=0.0f; // (angle left / right; 0..359

	public Scene(PApplet p) {
		my_parent = p;
	}

	public void display() {
		my_parent.camera(my_parent.width/2.0f, my_parent.height/2.0f, (my_parent.height/2.0f) / my_parent.tan(my_parent.PI*30.0f / 180.0f), my_parent.width/2.0f, my_parent.height/2.0f, 0f, 0f, 1f, 0f);
		plane();
	}

	private void plane () {
		my_parent.fill(250);
		my_parent.stroke(255);
		my_parent.pushMatrix();
		my_parent.translate(my_parent.width/2, floorLevel, 0); 
		my_parent.box(500, 10, 300);
		my_parent.popMatrix();
		
		
	}
}
