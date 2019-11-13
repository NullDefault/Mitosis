/**
David Nesterov-Rappoport
Class: Main
Purpose: Entry point for the code, controls which game phase the user is in
 */
public class GameMain {
	public enum gameState{
			START, RUNNING, PAUSE, GAME_OVER, ABOUT
    }
	static gameState State = gameState.START;
	
	public static void setState(int stt) {
		switch(stt) {
		case 1:
				State = gameState.RUNNING;
			
				break;
		case 2:
				State = gameState.START;
			
				break;
		case 3:
				State = gameState.PAUSE;
				
				break;
		case 4:
				State = gameState.GAME_OVER;
			
				break;
		case 5:
				State = gameState.ABOUT;
				
				break;
		default:
				State = gameState.START;
			
				break;
		}
		updateState();
		
	}
	public static void updateState() {
		System.out.flush();
		switch(State) {
			case START:
				new StartScreen();
				break;
			case RUNNING:
				Mainloop.exec();				
				break;
			case PAUSE:
				//run.pause();
				break;
			case GAME_OVER:
				new GameOverScreen();
				break;
			case ABOUT:
				new AboutScreen();
				break;
		}
}
	public static void main(String[] args) {
		updateState();
		}
	}


