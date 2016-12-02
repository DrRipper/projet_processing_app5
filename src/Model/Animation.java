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

	public Animation(PApplet p, String imagePrefix, int count, boolean loop, String n) {
		parent = p;
		imageCount = count;
		images = new PImage[imageCount];
		loopable = loop;
		name = n;

		for (int i = 0; i < imageCount; i++) {
			// Use nf() to number format 'i' into four digits
			String filename = imagePrefix + PApplet.nf(i+1, 4) + ".png";
			images[i] = parent.loadImage(filename);
		}
	}

	// vrai quand c'est la derniere frame de l'animation
	public boolean display(int xpos, int ypos) {
		frame = (frame+1) % imageCount;
		parent.image(images[frame], xpos, ypos);
		return (frame == imageCount-1);
	}
}
