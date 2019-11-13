/**
David Nesterov-Rappoport
Class: Speed Potion
Purpose: PowerUp which speeds the user up when picked up
 */
public class SpeedPotion extends Entity {

    public SpeedPotion(int x, int y) {
        super(x, y);
        
        init();
    }
   
    private void init() {
        loadImage("Graphics/speedpotion.png");
        getImageDimensions();
    }
}
