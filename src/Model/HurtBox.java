package Model;

import processing.core.PApplet;

class HurtBox extends Box {

	public HurtBox(PApplet p, boolean right) {
		super(p);
		setSens(right);
		w = 190;
		h = 180;
		r = 255;
	}

	public void setSens(boolean right) {
		if (right) {
			dx = 35-90;
			dy = 145;
		}
		else {
			dx = 55+190;
			dy = 145;
		}
	}
}
