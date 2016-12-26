import Controler.GameControler;
import Controler.MenuControler;
import Controler.PlayerControler;
import processing.core.PApplet;

public class GlitchesBattle extends PApplet {
	private final static int TITLE_SCREEN = 0;
	private final static int WAITING_PLAYER = 1;
	private final static int IN_GAME = 2;
	private final static int END_SCREEN = 3;

	private PlayerControler player1;
	private PlayerControler player2;

	private MenuControler menuControler;

	private GameControler gameControler;

	private int state;

	public static void main(String[] args) {
		//PApplet.main(new String[] { "--present", "projet_graphisme.Game"});
		PApplet.main("GlitchesBattle");
	}

	public void settings(){
		size(1000, 900/*, P3D*/);
		
		smooth();
	}

	public void setup(){
		/*lights();
		ambientLight(51, 102, 126);*/
		frameRate(30);
		state = WAITING_PLAYER;

		initPlayersSettings();		
		initMenu();
		
	}

	public void initAll() {
		state = WAITING_PLAYER;

		initPlayersSettings();		
		initMenu();
	}

	public void initMenu() {
		menuControler = new MenuControler(this, player1, player2);
	}

	private void initPlayersSettings() {
		player1 = new PlayerControler(this, 1, "../ressources/p1.png" );
		player2 = new PlayerControler(this, 3, "../ressources/p2.png"); 

		player1.setEnnemie(player2);
		player2.setEnnemie(player1);
	}

	public void initGame() {
		gameControler = new GameControler(this, player1, player2);
	}

	public void draw(){
		if (state == WAITING_PLAYER) { // on attend d'avoir les deux joueurs 
			if (!menuControler.display()) {
				state = IN_GAME;
				initGame();
				gameControler.setStartTime(millis());
			}
		} else if(state == IN_GAME){ // début du combat
			gameControler.display();
			if (gameControler.getModel().isGameFinish())
				state = END_SCREEN;
		} else if (state == END_SCREEN) {
			initAll();
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
		} else  {
			int increment = 10;
			// PLAYER 1
			if (key  == 'z' || key == 'Z') {
				player1.move(0, -increment);
			} else if (key  == 'q' || key == 'Q') {
				player1.move(-increment, 0);
			} else if (key  == 'd' || key == 'D') {
				player1.move(increment, 0);
			} else if (key  == 's' || key == 'S') {
				player1.move(0, increment);
			} else if (key == 'a' || key == 'A') {
				player1.hit();
			}  else if (key == 'e' || key == 'E') {
				player1.magicalHit();
			} // PLAYER 2
			else if (key  == 'i' || key == 'I') {
				player2.move(0, -increment);
			} else if (key  == 'j' || key == 'J') {
				player2.move(-increment, 0);
			} else if (key  == 'l' || key == 'L') {
				player2.move(increment, 0);
			} else if (key  == 'k' || key == 'K') {
				player2.move(0, increment);
			} else if (key == 'u' || key == 'U') {
				player2.hit();
			}  else if (key == 'o' || key == 'O') {
				player2.magicalHit();
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
		menuControler.stop();
		super.stop();
	}
}
