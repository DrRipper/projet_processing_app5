package Main;
import Controler.GameControler;
import Controler.MenuControler;
import Controler.OptionsControler;
import Controler.PlayerControler;
import Model.Son;
import ddf.minim.Minim;
import processing.core.PApplet;

public class GlitchesBattle extends PApplet {
	private final static int TITLE_SCREEN = 0;
	private final static int WAITING_PLAYER = 1;
	private final static int OPTIONS_SETTINGS = 2;
	private final static int IN_GAME = 3;
	private final static int END_SCREEN = 4;
	private final static int PAUSE = 5;

	private PlayerControler player1;
	private PlayerControler player2;
	private Minim minim;

	private MenuControler menuControler;
	private OptionsControler optionsControler;

	private GameControler gameControler;

	private int state;

	public static int cam=0;

	public static void main(String[] args) {
		//PApplet.main(new String[] { "--present", "projet_graphisme.Game"});
		PApplet.main("Main.GlitchesBattle");
	}

	public void settings(){
		size(1000, 900/*, P3D*/);
		
		smooth();
	}

	public void setup(){
		/*lights();
		ambientLight(51, 102, 126);*/
		frameRate(30); //30
		state = WAITING_PLAYER;

		minim = new Minim(this);

		initPlayersSettings();		
		initMenus();

		
	}

	public void initAll() {
		state = WAITING_PLAYER;

		initPlayersSettings();	
		System.out.println("PLAYER INIT OK");
		initMenus();
		System.out.println("MENU INIT OK");
	}

	public void initMenus() {
		menuControler = new MenuControler(this, player1, player2);
		optionsControler = new OptionsControler(this);
	}

	private void initPlayersSettings() {
		player1 = new PlayerControler(this, 1);
		player2 = new PlayerControler(this, 3); 

		player1.setEnnemie(player2);
		player2.setEnnemie(player1);
	}

	public void initGame(Integer time) {
		gameControler = new GameControler(this, player1, player2);
		gameControler.setMaxTime(time);
	}

	public void draw(){
		if (state == WAITING_PLAYER) { // on attend d'avoir les deux joueurs 
			if (!menuControler.display()) {
				state = OPTIONS_SETTINGS;
				menuControler.stop();
			}
		} else if (state == OPTIONS_SETTINGS){
			optionsControler.display();	
		} else if(state == IN_GAME){ // début du combat
			gameControler.display();
			if (gameControler.getModel().isGameFinish()) { // TODO
				state = END_SCREEN;
				gameControler.isGameFinish(true);
			}
		} else if (state == END_SCREEN) {
			gameControler.display();
		} else if (state == PAUSE) {
			gameControler.display();
		}
		player1.update();
		player2.update();
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
			int increment = 10;
			// PLAYER 1
			if (key  == 'z' || key == 'Z') {
				//player1.setZ(player1.getModel().getZ()-100);
				player1.move(0, 0, -increment);
			} else if (key  == 'q' || key == 'Q') {
				player1.move(-increment, 0, 0);
			} else if (key  == 'd' || key == 'D') {
				player1.move(increment, 0, 0);
			} else if (key  == 's' || key == 'S') {
				//player1.setZ(player1.getModel().getZ()+100);
				player1.move(0, 0, increment);
			} else if (key == 'a' || key == 'A') {
				player1.hit();
			}  else if (key == 'e' || key == 'E') {
				player1.magicalHit();
			} // PLAYER 2
			else if (key  == 'i' || key == 'I') {
				//player2.setZ(player2.getModel().getZ()-100);
				player2.move(0, 0, -increment);
			} else if (key  == 'j' || key == 'J') {
				player2.move(-increment, 0, 0);
			} else if (key  == 'l' || key == 'L') {
				player2.move(increment, 0, 0);
			} else if (key  == 'k' || key == 'K') {
				player2.setZ(player2.getModel().getZ()+100);
				player2.move(0, 0, increment);
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

	public int getC() {
		return cam;
	}

	public void stop() {
		
		super.stop();
	}

	public Minim getMinim() {
		return minim;
	}
}
