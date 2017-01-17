package Model;

import processing.core.PApplet;

public class HurtBox extends Box {

	public HurtBox(PApplet p, boolean right) {
		super(p);
		setSens(right);
		w = (int) (190*0.48f);
		h = (int) (180*0.48f);
		r = (short) (255*0.48f);
	}

	public void setSens(boolean right) {
		if (right) {
			dx = (int) ((35-90)*0.48f);
			dy = (int) (145*0.48f);
		}
		else {
			dx = (int) ((55+190)*0.48f);
			dy = (int) (145*0.48f);
		}
	}
}
