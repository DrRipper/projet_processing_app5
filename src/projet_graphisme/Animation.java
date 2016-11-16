package projet_graphisme;

import processing.core.PApplet;
import processing.core.PImage;

//Class for animating a sequence of GIFs
public class Animation {
	PImage[] images;
	int imageCount;
	int frame;
	PApplet my_parent;

	Animation(PApplet p, String filename, int count) {
		my_parent = p;
		imageCount = count;
		images = new PImage[imageCount];

		for (int i = 0; i < imageCount; i++) {
			// Use nf() to number format 'i' into four digits
			images[i] = my_parent.loadImage(filename);
		}
	}

	void display(float xpos, float ypos) {
		frame = (frame+1) % imageCount;
		my_parent.image(images[frame], xpos, ypos);
	}

	int getWidth() {
		return images[0].width;
	}
}


