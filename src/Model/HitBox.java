package Model;

import processing.core.PApplet;

public class HitBox extends Box {

	public HitBox(PApplet p, boolean right) {
		super(p);
		setSens(right);
		w = (int) (90*0.48f);
		h = (int) (180*0.48f);
		b = (short) (255*0.48f);
	}
	public void setSens(boolean right) {
		if (right) {
			dx = (int) (35*0.48f);
			dy = (int) (145*0.48f);
		}
		else {
			dx = (int) (55*0.48f);
			dy = (int) (145*0.48f);
		}
	}
}
