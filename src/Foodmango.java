/**
David Nesterov-Rappoport
Class: Food mango
Purpose: Game entity for mangos, which are a basic means for the player to earn points
 */
public class Foodmango extends Entity {
	
	private final int foodvalue = 30;
    public Foodmango(int x, int y) {
        super(x, y);
        
        initFood();
    }
    public int getVal() {
    	return foodvalue;
    }
    private void initFood() {
        loadImage("Graphics/Foodmango.png");
        getImageDimensions();
    }
}