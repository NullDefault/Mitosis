/**
David Nesterov-Rappoport
Class: Enemy
Purpose: Controls Enemy Behavior and features
 */
import java.util.Random;

public class Enemy extends Entity {

    private int dx;
    private int dy;
    private Random rand = new Random();
    private int massAccumulated=0;
    protected int maxMass = 150; // Mass at which the goober splits
    private boolean aggresive = false;
    private int hop = 0;
    private int value = 50;
    
    public Enemy(int x, int y) { // <- Constructor
        super(x, y);
        
        updateSprite();
    }

    
    public void updateSprite() { // Updates the Sprite according to the enemy state
    	boolean right = false;
    	boolean left = false;
    	boolean down = false;
    	boolean up = false;
    	if(dx > 0 && dx > dy) {
    		right = true;
    	}
    	else if(dx <0 && dx > dy){
    		left = true;
    	}
    	else if(dy > 0 && dx < dy) {
    		down = true;
    	}
    	else {
    		up = true;
    	}
    	if (aggresive) {
			if (right) {
				loadImage("Graphics/enemysprite_aggresive_R.png");
			} else if (left) {
				loadImage("Graphics/enemysprite_aggresive_L.png");
			} else if (down) {
				loadImage("Graphics/enemysprite_aggro_D.png");
			} else if (up){
				loadImage("Graphics/enemysprite_aggro_U.png");
			} 
		}else {
			if (right) {
				loadImage("Graphics/enemysprite_passive_R.png");
			} else if (left) {
				loadImage("Graphics/enemysprite_passive_L.png");
			} else if (down) {
				loadImage("Graphics/enemysprite_passive_D.png");
			} else if (up){
				loadImage("Graphics/enemysprite_passive_U.png");
			} 
		}
		getImageDimensions();
    }
    
    public void wander() { // <- Creates random behavior for the enemy
    	int coinflipa = rand.nextInt(2);
    	int coinflipb = rand.nextInt(2);
    	dx = rand.nextInt(5);
    	if(coinflipa == 1) {
    		dx = -dx;
    	}
    	dy = rand.nextInt(5);
    	if(coinflipb == 1) {
    		dy = -dy;
    	}
    }
    public void seekUnit(int px, int py) { // <- Seeks out specified unit
    	if(py>y) {
    		dy = rand.nextInt(3);
    	}
    	if(py<y) {
    		dy = -rand.nextInt(3);
    	}
    	if(px>x) {
    		dx = rand.nextInt(3);
    	}
    	if(px<x) {
    		dx = -rand.nextInt(3);
    	}
    }
    public void moveAway(int px, int py) {
    	if(py<y) {
    		dy = rand.nextInt(5);
    	}
    	if(py>y) {
    		dy = -rand.nextInt(5);
    	}
    	if(px<x) {
    		dx = rand.nextInt(5);
    	}
    	if(px>x) {
    		dx = -rand.nextInt(5);
    	}
    }
    
    public void move() { // <- Moves the enemy and makes sure it doesn't go out of bounds
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
    	if (hop == 8) {
			updateSprite();
			hop = 0;
		}else {
			hop++;
		}
    }
    
    public void addMass(int n) {
    	massAccumulated += n;
    }
	public int getMassAccumulated() {
		return massAccumulated;
	}

	public void setMassAccumulated(int massAccumulated) {
		this.massAccumulated = massAccumulated;
	}
	public void setAggro(boolean x) {
		aggresive = x;
	}
	public int getValue() {
		return value;
	}
}
