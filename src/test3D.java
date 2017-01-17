import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;



public class test3D extends PApplet {
	PShape tree;
	PShape mountain;
	
	public static void main(String[] args) {
		//PApplet.main(new String[] { "--present", "projet_graphisme.Game"});
		PApplet.main("test3D");
	}

	public void settings(){
		size(1000, 900, P3D);
	}

	public void setup() {
		//cam = new PeasyCam(this, 100);
		//cam.setMinimumDistance(0);
		//cam.setMaximumDistance(500);

		mountain = loadShape("./ressources/background/94k9b8bowvb4-LowPolyMountains/Model/lowpolymountains.obj");
		mountain.scale(60);
		
		tree = loadShape("./ressources/background/7bmzbr68wxkw-LowPolyTree/lowpolytree.obj");
		tree.scale(20);

		//img_tree = loadImage("./ressources/background/Cobblestones3/Textures/BrickRound0105_5_S.jpg");
		//tree.setTexture(img_tree);
	}
	
	public void draw() {
		lights(); 

		background(0, 255, 255);
		
		pushMatrix();
		translate(width/2, height-300, 50);
		rotateZ(3.14f);
		shape(mountain);
		popMatrix();
		
		pushMatrix();
		translate(350, height-400, 450);
		rotateZ(3.14f);
		shape(tree);
		popMatrix();
	}
	
	public void lights() { 
		int w=170, m=-422, p=+422; 
		ambientLight(w,w,w); 
		directionalLight(w, w, w, m, m, m); 
		directionalLight(w, w, w, p, p, p); 
		directionalLight(w, w, w, m, m, p); 
		directionalLight(w, w, w, p, p, m); } 
}


/*
import processing.opengl.*; 
import saito.objloader.*; 
import peasy.*; 
int detail = 70; 
color bg= #ffffff; 
PeasyCam cam; 
OBJModel model; 
void setup() {
	size(1000 , 600 , OPENGL); 
	model = new OBJModel (this, "mol3.obj"); 
	cam = new PeasyCam(this, 700); 
	model.scale(0.3); model.translateToCenter(); noStroke(); smooth();
	} 

void draw() {
	lights(); 
	background(200);
	smooth(); 
	model.draw(); 
	} 

void lights() { 
	int w=170, m=-422, p=+422; 
	ambientLight(w,w,w); 
	directionalLight(w, w, w, m, m, m); 
	directionalLight(w, w, w, p, p, p); 
	directionalLight(w, w, w, m, m, p); 
	directionalLight(w, w, w, p, p, m); } 

*/