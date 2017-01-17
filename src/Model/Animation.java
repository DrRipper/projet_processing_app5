package Model;

import processing.core.PApplet;
import processing.core.PImage;

public class Animation {
	public PImage[] images;
	public int imageCount;
	public int frame;
	public boolean loopable;
	String name;
	PApplet parent;
	private boolean meteor;

	public Animation(PApplet p, String imagePrefix, int count, boolean loop, String n) {
		parent = p;
		imageCount = count;
		images = new PImage[imageCount];
		loopable = loop;
		name = n;

		meteor = n.equals("meteor");
		for (int i = 0; i < imageCount; i++) {
			// Use nf() to number format 'i' into four digits
			String filename = imagePrefix + PApplet.nf(i+1, 4) + ".png";
			images[i] = parent.loadImage(filename);
		}
	}

	// vrai quand c'est la derniere frame de l'animation
	public boolean display(int xpos, int ypos, int zpos) {
		frame = (frame+1) % imageCount;
		parent.pushMatrix();
		parent.translate(0,0, zpos);
		parent.noFill();
		parent.image(images[frame], xpos, ypos, 250, 250);
		parent.popMatrix();
		return (frame == imageCount-1);
	}
}
