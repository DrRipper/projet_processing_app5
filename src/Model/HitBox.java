package Model;

import processing.core.PApplet;

public class HitBox extends Box {

	public HitBox(PApplet p, boolean right) {
		super(p);
		setSens(right);
		w = 90;
		h = 180;
		b = 255;
	}
	public void setSens(boolean right) {
		if (right) {
			dx = 35;
			dy = 145;
		}
		else {
			dx = 55;
			dy = 145;
		}
	}
}
