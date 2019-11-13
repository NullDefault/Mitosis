/**
David Nesterov-Rappoport
Class: Food bean
Purpose: Basic food class
 */
public class Foodbean extends Entity{
	private final int foodvalue = 10;
    public Foodbean(int x, int y) {
        super(x, y);
        
        initFood();
    }
    public int getVal() {
    	return foodvalue;
    }
    private void initFood() {
        loadImage("Graphics/Foodbean.png");
        getImageDimensions();
    }
}
