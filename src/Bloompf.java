import java.util.Random;

public class Bloompf extends Entity{
		private int dx;
	    private int dy;
	    private Random rand = new Random();
	    private int count = 0;
	    private boolean sleeping;
	    private int sleepingPhase = 0;
	    private int hunger;
	    
	    public Bloompf(int x, int y, int e) { // <- Constructor
	        super(x, y);
	        hunger = e;
	        hunger = hunger/2;
	        sleeping = true;
	        updateSprite();
	    }

	    
	    public void updateSprite() { // Updates the sprite according to the entity state
			if (sleeping) {
				loadImage("Graphics/Bloompf_sleep1.png");
				sleepingPhase++;
				if(sleepingPhase > 100) {
					sleeping = false;
				}
			}
			else{
				switch (count) {
				case 0:
					count++;
					loadImage("Graphics/Bloompf_front.png");
					break;
				case 1:
					count++;
					loadImage("Graphics/Bloompf_left.png");
					break;
				case 2:
					count++;
					loadImage("Graphics/Bloompf_front.png");
					break;
				case 3:
					count = 0;
					loadImage("Graphics/Bloompf_right.png");
					break;
				}
			}
			getImageDimensions();
			
			
	    }
	    

	    public void hunt(int px, int py) { // <- Seeks out specified unit
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
	    public void wander() { // <- Creates random behavior for bloompf
	    	int coinflipa = rand.nextInt(2);
	    	int coinflipb = rand.nextInt(2);
	    	dx = rand.nextInt(4);
	    	if(coinflipa == 1) {
	    		dx = -dx;
	    	}
	    	dy = rand.nextInt(5);
	    	if(coinflipb == 1) {
	    		dy = -dy;
	    	}
	    }
	    
	    public void move() { // <- Moves the enemy and makes sure it doesn't go out of bounds
	    	if (!sleeping) {
				int newx = x += dx;
				int newy = y += dy;
				if (newx > XLIMIT_high) {
					x = XLIMIT_high;
				} else if (newx < XLIMIT_low) {
					x = XLIMIT_low;
				} else {
					x = newx;
				}
				if (newy > YLIMIT_high) {
					y = YLIMIT_high;
				} else if (newy < YLIMIT_low) {
					y = YLIMIT_low;
				} else {
					y = newy;
				} 
			}
			updateSprite();

	    }
	    
	    public void lessHungry() {
	    	hunger--;
	    }
	    public int getHunger() {
	    	return hunger;
	    }
	
}
