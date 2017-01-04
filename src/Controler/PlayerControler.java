package Controler;

import Model.Player;
import Model.Son;
import View.PlayerView;
import processing.core.PApplet;

public class PlayerControler {
	private Player my_model;
	private PlayerView my_view;
	
	public PlayerControler(PApplet p, int idx, String img) {
		my_model = new Player(p, idx, img, this);
		my_view = new PlayerView(my_model);
	}
	
	public void setEnnemie(PlayerControler p) {
		my_model.setEnnemie(p.getModel());
	}
	
	public void update() {
		my_model.update();
	}
	
	public Player getModel() {
		return my_model;
	}
	
	public PlayerView getView() {
		return my_view;
	}
	
	public void set_validity(boolean state) {
		if (state != my_model.get_validity()) {
			System.out.println("HERE !!!");
			my_view.play_validation();
			my_view.jump();
		}
		my_model.set_validity(state);
	}

	public void setY(int y) {
		my_model.setY(y);
		
	}

	public void setX(int x) {
		my_model.setX(x);
	}
	
	public void setZ(int z) {
		my_model.setZ(z);
	}

	public boolean hit() {
		my_model.hurting = true;
		my_view.slash();
		if(my_model.hurtbox.collision(my_model.getEnnemie().hitbox)) {
	    	  my_model.getEnnemie().controler.getView().hurt();
	    	  if (my_model.get_mana()+5<Player.MAX_MANA)
					my_model.set_mana(my_model.get_mana()+5);
				else 
					my_model.set_mana(Player.MAX_MANA);

				my_model.getEnnemie().set_pv(my_model.getEnnemie().get_pv()-5);
				my_model.getParent().draw();
				return true;
	    }
		return false;
		
		
		/*if (my_model.collision_with_ennemie(false, 0, 0)) {
			if (my_model.get_mana()+5<Player.MAX_MANA)
				my_model.set_mana(my_model.get_mana()+5);
			else 
				my_model.set_mana(Player.MAX_MANA);

			my_model.getEnnemie().set_pv(my_model.getEnnemie().get_pv()-5);
			my_model.getParent().draw();
			return true;
		}
		return false;*/
		
	}
	
	public void magicalHit() {
		if(my_model.get_mana() == Player.MAX_MANA) {
			my_model.getMeteorite().hit();
			my_model.set_mana(0);
			my_model.getEnnemie().set_pv(my_model.getEnnemie().get_pv()-30);
		}
		my_model.getParent().draw();
	}
	
	public void move(int dx, int dy, int dz) {
		my_model.move(dx, dy, dz);
		my_view.walk();
		System.out.println("PlayerControler.move dx="+dx + " dy="+ dy);
		System.out.println("Player.x="+my_model.getX() + " Player.y="+ my_model.getX());
	}
	
	public void idle() {
		my_view.idle();
		my_model.move(0, 0, 0);
		my_model.hurting = false;
	}
}
