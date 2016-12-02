package Model;

import processing.core.PApplet;

class Box {

	public int x, y, dx, dy, w, h;
	public boolean displaying = false;
	public short r=0, v=0, b=0, a=100;
	private PApplet parent;

	public Box(PApplet p) {
		parent = p;
	}

	public void update(int x, int y) {
		this.x = x;
		this.y = y;
		if (displaying) {
			parent.noStroke();
			parent.fill(r,v,b,a);
			parent.rect(x-dx,y-dy,w,h);
		}
	}

	public boolean collision(Box b) {
		int x1 = x-dx,
				y1 = y-dy,
				x2 = b.x-b.dx,
				y2 = b.y-b.dy;
		return (x1 < x2 + b.w  &&
				x1 + w > x2    &&
				y1 < y2 + b.h  &&
				y1 + h > y2);
	}
}
