package Model;

import processing.core.PApplet;

public class Menu {
	private PApplet my_parent;
	
	public Menu(PApplet p) {
		my_parent = p;

	}
	
	public PApplet getParent() {
		return my_parent;
	}
}
