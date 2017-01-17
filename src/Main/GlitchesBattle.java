package Main;
import Controler.GameControler;
import Controler.MenuControler;
import Controler.OptionsControler;
import Controler.PlayerControler;
import View.WaitScreen;
import ddf.minim.Minim;
import peasy.PeasyCam;
import processing.core.PApplet;

public class GlitchesBattle extends PApplet {
	private final static int TITLE_SCREEN = 0;
	private final static int CHARGEMENT = 1;
	private final static int WAITING_PLAYER = 2;
	private final static int OPTIONS_SETTINGS = 3;
	private final static int IN_GAME = 4;
	private final static int END_SCREEN = 5;
	private final static int PAUSE = 6;

	private final static int INCREMENT = 10;
	private PlayerControler player1;
	private PlayerControler player2;
	private Minim minim;

	private MenuControler menuControler;
	private OptionsControler optionsControler;

	private GameControler gameControler;

	private int state = CHARGEMENT;
	public 	PeasyCam cam;

	private boolean allAnimationFinished;
	
	private WaitScreen waitScreen;

	public static void main(String[] args) {
		//PApplet.main(new String[] { "--present", "projet_graphisme.Game"});
		PApplet.main("Main.GlitchesBattle");
	}

	public void settings(){
		size(1000, 900, P3D);
		smooth();
	}

	public void setup(){
		/*lights();
		ambientLight(51, 102, 126);*/
		/*cam = new PeasyCam(this, 100);
		cam.setMinimumDistance(0);
		cam.setMaximumDistance(1500);*/

		frameRate(30); //30

		minim = new Minim(this);	
		waitScreen = new WaitScreen(this);
		allAnimationFinished = true;
	}

	public void lights() { 
		noLights();
		int w=170, m=-422, p=+422; 
		ambientLight(w,w,w); 
		directionalLight(w, w, w, m, m, m); 
		directionalLight(w, w, w, p, p, p); 
		directionalLight(w, w, w, m, m, p); 
		directionalLight(w, w, w, p, p, m); 
	} 

	public void initAll() {
		state = CHARGEMENT;
		waitScreen.init();

	}

	public void setPlayer1(PlayerControler p) {
		player1 = p;
	}

	public void setPlayer2(PlayerControler p) {
		player2 = p;
	}

	public void setMenuControler(MenuControler m) {
		menuControler = m;
	}

	public void setOptionsControler(OptionsControler o) {
		optionsControler = o;
	}

	public void initGame(Integer time) {
		gameControler = new GameControler(this, player1, player2);
		gameControler.setMaxTime(time);
	}

	public void draw(){
		clear();
		if (state == CHARGEMENT) {
			if (!waitScreen.nextStep())
				state = WAITING_PLAYER;
			waitScreen.display();

		}
		if (state == WAITING_PLAYER) { // on attend d'avoir les deux joueurs 
			if (!menuControler.display()) {
				state = OPTIONS_SETTINGS;
				menuControler.stop();
			}
		} else if (state == OPTIONS_SETTINGS){
			optionsControler.display();	
		} else if(state == IN_GAME){ // début du combat
			gameControler.display();
			if (gameControler.getModel().isGameFinish()) {
				state = END_SCREEN;
				gameControler.isGameFinish(true);
			}
			player1.update();
			player2.update();
		} else if (state == END_SCREEN) {
			lights();
			gameControler.display();
		} else if (state == PAUSE) {
			lights();
			gameControler.display();
		}
	}

	public void keyPressed() {
		if (state == WAITING_PLAYER) { // etat menu
			if (key  == 'a' || key == 'A') {
				player1.set_validity(true);
			} else if (key  == 'b' || key == 'B') {
				player2.set_validity(true);
			}
		} else if (state == OPTIONS_SETTINGS) {
			if (key  == 'a' || key == 'A') {
				if (optionsControler.isOptionsValid()) {
					optionsControler.stop();
					state = IN_GAME;
					Integer time = optionsControler.getMaxTime();
					initGame(time);
					gameControler.setStartTime(millis());
				}
			} else if (!optionsControler.isWaiting()){ // on selectionne les options et leur valeurs
				if (key  == 'z' || key == 'Z') {
					optionsControler.setIdxOption(optionsControler.getOptionIdx()-1);
				} else if (key  == 's' || key == 'S') {
					optionsControler.setIdxOption(optionsControler.getOptionIdx()+1);
				}else if (key  == 'q' || key == 'Q') {
					optionsControler.setIdxOptionValue(optionsControler.getOptionValueIdx()[optionsControler.getOptionIdx()]-1);
				}else if (key  == 'd' || key == 'D') {
					optionsControler.setIdxOptionValue(optionsControler.getOptionValueIdx()[optionsControler.getOptionIdx()]+1);
				} 
			} else { // on indique le temps de jeu voulu
				if (key  == 'q' || key == 'Q') {
					optionsControler.setIdxTime(optionsControler.getTimeIdx()-1);
				} else if (key  == 'd' || key == 'D') {
					optionsControler.setIdxTime(optionsControler.getTimeIdx()+1);
				}else if (key  == 's' || key == 'S') {
					optionsControler.setIdxTimeValue(-1);
				}else if (key  == 'z' || key == 'Z') {
					optionsControler.setIdxTimeValue(+1);
				}
			}
		} else if (state == IN_GAME && !gameControler.isDecompting()) {
			
			if(!allAnimationFinished)
				return;
			// PLAYER 1
			if (key  == 'z' || key == 'Z') {
				//player1.setZ(player1.getModel().getZ()-100);
				player1.move(0, 0, -INCREMENT);
			} else if (key  == 'q' || key == 'Q') {
				player1.move(-INCREMENT, 0, 0);
			} else if (key  == 'd' || key == 'D') {
				player1.move(INCREMENT, 0, 0);
			} else if (key  == 's' || key == 'S') {
				player1.move(0, 0, INCREMENT);
			} else if (key == 'a' || key == 'A') {
				player1.hit();
			}  else if (key == 'e' || key == 'E') {
				player1.magicalHit();
			} // PLAYER 2
			else if (key  == 'i' || key == 'I') {
				//player2.setZ(player2.getModel().getZ()-100);
				player2.move(0, 0, -INCREMENT);
			} else if (key  == 'j' || key == 'J') {
				player2.move(-INCREMENT, 0, 0);
			} else if (key  == 'l' || key == 'L') {
				player2.move(INCREMENT, 0, 0);
			} else if (key  == 'k' || key == 'K') {
				player2.move(0, 0, INCREMENT);
			} else if (key == 'u' || key == 'U') {
				player2.hit();
			}  else if (key == 'o' || key == 'O') {
				player2.magicalHit();
			}else if (key == 'p' || key == 'P') {
				state = PAUSE;
				gameControler.setPauseState(true);
			}
		} else if (state == PAUSE) {
			if (key  == 'a' || key == 'A') {
				state = IN_GAME;
				gameControler.setPauseState(false);
			}
		} else if (state == END_SCREEN) {
			if (key == 'a' || key == 'A') {
				initAll();
			}
		}
	}

	public void keyReleased() {
		if (state != WAITING_PLAYER) {
			int increment = 10;
			// PLAYER 1
			if (key  == 'z' || key == 'Z') {
				player1.idle();
			} else if (key  == 'q' || key == 'Q') {
				player1.idle();
			} else if (key  == 'd' || key == 'D') {
				player1.idle();
			} else if (key  == 's' || key == 'S') {
				player1.idle();
			} 
			// PLAYER 2
			else if (key  == 'i' || key == 'I') {
				player2.idle();
			} else if (key  == 'j' || key == 'J') {
				player2.idle();
			} else if (key  == 'l' || key == 'L') {
				player2.idle();
			} else if (key  == 'k' || key == 'K') {
				player2.idle();
			} 
		}
	}

	public void stop() {

		super.stop();
	}

	public Minim getMinim() {
		return minim;
	}
}
