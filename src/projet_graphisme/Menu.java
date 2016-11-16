package projet_graphisme;

import processing.core.PFont;
import processing.core.PImage;

public class Menu {
	PFont font;
	boolean wait = true;
	boolean fin = false;

	Animation animation_letsGo;
	
	Player player1;
	Player player2;
	
	int startTime;
	Integer maxTime = new Integer(90); // Exemple : 90 secondes de jeux (null si pas de timer)
	PImage imgInfini; 
}








/*
void setup() {
  size(1000, 900);
  smooth();
  frameRate(24);
  //animation_letsGo = new Animation("C:/Users/amassard/Documents/APP5/graphisme_visualisation/projet/letsgo.gif", 38);
  player1 = new Player(1);
  player2 = new Player(3); 
  font = createFont("Georgia",40,true); 
  
  imgInfini = loadImage("C:/Users/amassard/Documents/APP5/graphisme_visualisation/projet/icon_infini.png");
}

void draw() {
  if (wait) { // on attend d'avoir les deux joueurs 
    background(0, 0, 0);
    textFont(font);
    textAlign(CENTER);
    text("Wait for player...", width/4, 100);
  
    // En attente du joureur 1 (personnage en pointillés)
    if (!player1.get_validity()) {
      player1.display_when_wait();
      text("Press A !", width/4, (height/4)+400);
    }else {
      player1.display_when_valid();
      
      fill(255);
      stroke(0);
      text("Ready !", width/4, (height/4)+400); 
    }
    
    // En attente du joureur 2 (personnage en pointillés)
    if (!player2.get_validity()) {
      player2.display_when_wait();
      text("Press B !", 3*(width/4), (height/4)+400);
    }else {
      player2.display_when_valid();

      fill(255);
      stroke(0);
      text("Ready !", 3*(width/4), (height/4)+400);
    }
    
    wait = ! player1.get_validity() || ! player2.get_validity();
    
    if (!wait) { // transition 
      startTime = millis();
      //background(0, 0, 0);
      //animation_letsGo.display(0,0);
    }
  } else { // début du combat
    if (! fin) {
      // init du background
      background(0, 0, 0);
      
      // on affiche les barres de PV et de mana
      player1.display_pv();
      player2.display_pv();
  
      player1.display_mana();
      player2.display_mana();
      
      // display timer
      time(); 
      
      // placement des deux personnages
      
      
    }
  }
  
}

void keyPressed() {
  if (key  == 'a' || key == 'A') {
    player1.set_validity(true);
  } else if (key  == 'b' || key == 'B') {
    player2.set_validity(true);
  }
}

 void time() {
  fill(#000000); 
  textAlign(CENTER);
  fill(255);
  stroke(0);
  
  if ( maxTime != null) {
    int elapsed = millis() - startTime;
   text(maxTime - int(elapsed) / 1000, (width/2), 40); 
  }else { // on affiche un symbole infini (pas de temps limite)
    image(imgInfini, (width/2)-(imgInfini.width/4)/2, 0, imgInfini.width/4, imgInfini.height/4);
  } 
} */