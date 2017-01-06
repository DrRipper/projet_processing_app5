package Controler;

import Model.Menu;
import Model.Options;
import View.MenuView;
import View.OptionsView;
import processing.core.PApplet;

public class OptionsControler {
	private OptionsView my_view;
	private Options my_model;
	
	public OptionsControler(PApplet p) {
		my_model = new Options();
		my_view = new OptionsView(p, my_model.getOptionsList());
	}
	
	public void display() {
		my_view.display();
	}
	
	public void stop() {
		my_view.stopMusic();
	}

	public Integer getMaxTime() {
		return my_model.getTimeSelected();
	}
	
	public void setIdxOption(int idx) { // changement d'option (1 pour le moment)
		my_view.setSelectedOption(my_model.setIdxOption(idx));
	}
	
	public void setIdxOptionValue(int idx) { // changement de la valeur de l'option
		my_view.setSelectedOptionValue(my_model.setIdxOptionValue(idx));
	}

	public void setIdxTime(int idx) { // changement d'option (1 pour le moment)
		my_view.setSelectedTime(my_model.setIdxTime(idx));
	}
	
	public void setIdxTimeValue(int idx) { // changement de la valeur de l'option
		my_view.setSelectedTimeValue(my_model.setIdxTimeValue(idx));
	}
	
	public int getOptionIdx() {
		return my_model.getOptionIdx();
	}

	public int[] getOptionValueIdx() {
		return my_model.getOptionValueIdx();
	}

	public boolean isOptionsValid() {
		// Si l'option "set time" est coché, il faut renseigner le temps voulu
		if (my_model.getOptionsList()[0][my_model.getOptionValueIdx()[0]]=="Set time" && !isWaiting()){
			my_view.displayTimeSelection();
			return false;
		}
		else return (my_model.getTimeSelected()==null || my_model.getTimeSelected()>30);
	}
	
	public boolean isWaiting() {
		return my_view.isWaiting();
	}

	public int getTimeIdx() {
		return my_model.getTimeIdx();
	}
}
