package View;

import Controler.GameControler;
import Controler.PlayerControler;
import Main.GlitchesBattle;
import Model.Menu;
import Model.Son;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class OptionsView {
	private final static int RECT_LINE_START = 200;
	private final static int RECT_LINE_END = 650;
	private final static int RECT_COL_START = 100;
	private final static int RECT_LARGEUR = 700;

	private PFont font_title;
	private PFont font_option_name;

	private static Son son;

	private PImage name_option_selected;
	private PImage name_option_noSelected;

	private PApplet my_parent;
	private String [][] list_options;
	boolean waitTimeSelection=false;

	private int[] list_selected_values;

	private int idxSelectedOption;

	private int[] timeSelected;
	private int timeIdx;
	
	public OptionsView(PApplet parent, String [][] list) {
		idxSelectedOption = 0;
		timeSelected = new int[]{0,1,0,0};
		timeIdx = 0;
		my_parent = parent;
		list_options = list.clone();

		list_selected_values = new int[list_options.length];
		for(int cpt=0; cpt<list_options.length; cpt++)
			list_selected_values[cpt]=1;

		son = new Son(((GlitchesBattle) my_parent).getMinim(), my_parent, "../ressources/sons/misc_menu.mp3");
		
		font_title = my_parent.createFont("../ressources/Sketch Gothic School.ttf",100,true);
		font_option_name = my_parent.createFont("Calibri",30,true);

		name_option_selected = my_parent.loadImage("../ressources/name_option.png");
		name_option_noSelected = my_parent.loadImage("../ressources/name_option_noSelected.png");


	}

	public void display() {
		/*if (son_music.getMusicMenu().isPlaying() == false){
			son_music.getMusicMenu().loop(); //rewind() possible
		}*/
		
		if (waitTimeSelection){
			// Faire la box 
			my_parent.fill(153);
			my_parent.rect(my_parent.width*0.375f , my_parent.height*0.33f, 230, 110);
			// Faire 4 box blanches
			my_parent.fill(255);
			my_parent.rect((my_parent.width*0.375f)+5 , (my_parent.height*0.33f)+5, 50, 100);
			my_parent.rect((my_parent.width*0.375f)+60 , (my_parent.height*0.33f)+5, 50, 100);
			my_parent.rect((my_parent.width*0.375f)+120 , (my_parent.height*0.33f)+5, 50, 100);
			my_parent.rect((my_parent.width*0.375f)+175 , (my_parent.height*0.33f)+5, 50, 100);
			
			my_parent.fill(0);
			my_parent.text(":", (my_parent.width*0.375f)+110, (my_parent.height*0.33f)+50);


			// ecrire le temps 
			my_parent.textFont(font_option_name);
			my_parent.textAlign(my_parent.LEFT);
			
			int color = timeIdx==0 ? 0:153;
			my_parent.fill(color);
			my_parent.text(timeSelected[0], (my_parent.width*0.375f)+10, (my_parent.height*0.33f)+50);
			color = timeIdx==1 ? 0:153;
			my_parent.fill(color);
			my_parent.text(timeSelected[1], (my_parent.width*0.375f)+65, (my_parent.height*0.33f)+50);
			color = timeIdx==2 ? 0:153;
			my_parent.fill(color);
			my_parent.text(timeSelected[2], (my_parent.width*0.375f)+125, (my_parent.height*0.33f)+50);
			color = timeIdx==3 ? 0:153;
			my_parent.fill(color);
			my_parent.text(timeSelected[3], (my_parent.width*0.375f)+180, (my_parent.height*0.33f)+50);

			return;
		}

		

		my_parent.background(0, 0, 0);
		my_parent.textFont(font_title);
		my_parent.textAlign(my_parent.CENTER);
		my_parent.fill(255);

		my_parent.text("Options", my_parent.width/2, 100);
		my_parent.fill(153);
		// box grise des options
		my_parent.rect(RECT_COL_START , RECT_LINE_START, RECT_LARGEUR, RECT_LINE_END);

		/* =============================== 
		 * 		OPTIONS
		  =============================== */
		my_parent.textFont(font_option_name);
		my_parent.textAlign(my_parent.LEFT);

		for (int option=0; option<list_options.length; option++) {
			String name = list_options[option][0];
			PImage img = option==idxSelectedOption ? name_option_selected:name_option_noSelected;

			// Nom de l'option
			my_parent.image(img, RECT_COL_START+5, RECT_LINE_START+5+option*(5+img.height/2), img.width/2, img.height/2);
			my_parent.fill(255);
			my_parent.text(name, RECT_COL_START+25, RECT_LINE_START+15+(img.height/4)+option*(5+img.height/2));

			int size = RECT_COL_START+25+img.width/2;
			for (int val=1; val<list_options[option].length;val++) {
				name = list_options[option][val];
				int color = val==list_selected_values[option] ? 0:255;
				my_parent.fill(color);
				my_parent.text(name, size, RECT_LINE_START+15+(img.height/4)+option*(5+img.height/2));
				size += name.length()*5+val*50;
			}

		}
	}

	public void stopMusic() {
		son.stop();
	}

	public void setSelectedOption(int idx) {
		son.getMusicMenu().play(0);
		idxSelectedOption = idx;

	}

	public void setSelectedOptionValue(int[] idx) {
		son.getMusicMenu().play(0);
		list_selected_values = idx;		
	}

	public void displayTimeSelection() {
		waitTimeSelection = true;
	}

	public boolean isWaiting() {
		return waitTimeSelection;
	}

	public void setSelectedTime(int idx) {
		son.getMusicMenu().play(0);
		timeIdx=idx;
		
	}

	public void setSelectedTimeValue(int[] time) {
		son.getMusicMenu().play(0);
		timeSelected=time;
	}

}
