/**
David Nesterov-Rappoport
Class: Player
Purpose: Holds info about the player and controls player behavior
 */
import java.awt.event.KeyEvent;




public class Player extends Entity {

    private int dx;
    private int dy;
    private int score = 0;
    private int speed = 4;
    private int bombs = 0;
    private boolean layingABomb = false;
    
    public Player(int x, int y) {
        super(x, y);
        
        updateSprite();
    }

    private void updateSprite() {
    	int orientation;				// 1 = right, 2 = left, 3 = down, 4 = up
    	if(dx > 0) {
    		orientation = 1;
    	}
    	else if(dx < 0){
    		orientation = 2;
    	}
    	else if(dy > 0) {
    		orientation = 3;
    	}
    	else if(dy < 0){
    		orientation = 4;
    	}
    	else {
    		orientation = 3;
    	}
    	switch(orientation) {
    	case 1:
    		loadImage("Graphics/playersprite_R.png");
    		break;
    	case 2:
    		loadImage("Graphics/playersprite_L.png");
    		break;
    	case 3:
    		loadImage("Graphics/playersprite_front.png");
    		break;
    	case 4:
    		loadImage("Graphics/playersprite_back.png");
    		break;
    	}
		getImageDimensions();
    }
 //---------------------------------------------------------------------------------------------------//
    public void move() { // Movement method 
    	int newx = x+=dx;
    	int newy = y+=dy;
    	if(newx > XLIMIT_high) {
    		x = XLIMIT_high;
    	}
    	else if(newx < XLIMIT_low) {
    		x = XLIMIT_low;
    	}else {
    		x = newx;
    	}
    	if(newy > YLIMIT_high) {
    		y = YLIMIT_high;
    	}
    	else if(newy < YLIMIT_low) {
    		y = YLIMIT_low;
    	}
    	else {
    		y = newy;
    	}
    	updateSprite();
    }
    public void eat(int x) {
    	score = score + x;
    }
    public int getscore() {
    	return score;
    }
    public void powerUp() {
    	if (speed<=8) {
			speed++;
		}else {
			score = score + 50;
		}
    	
    }
    public void addBomb() {
    	bombs++;
    }
    public int getInventory() {
    	return bombs;
    }
    private void dropBomb() {
    	bombs--;
    	layingABomb = true;
    }
    public void doneBombing() {
    	layingABomb = false;
    }
    public boolean getBombPhase() {
    	return layingABomb;
    }
//---------------------------------------------------------------------------------------------------// 
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            dx = -speed;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {					// These are all the method to make the player move places
            dx = speed;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            dy = -speed;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            dy = speed;
        }
        if (key == KeyEvent.VK_SPACE) {
        	dropBomb();
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            dy = 0;
        }
    }
 //---------------------------------------------------------------------------------------------------//    
}