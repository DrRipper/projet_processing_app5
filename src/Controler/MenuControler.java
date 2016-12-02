package Controler;

import Model.Menu;
import Model.Player;
import View.MenuView;
import processing.core.PApplet;

public class MenuControler {
	private Menu my_model;
	private MenuView my_view;
	
	public MenuControler(PApplet p, PlayerControler p1, PlayerControler p2) {
		my_model = new Menu(p);
		my_view = new MenuView(my_model, p1, p2);
	}
	
	public boolean display() {
		return my_view.display();
	}
	
	public void stop() {
		my_view.stopMusic();
	}
}
