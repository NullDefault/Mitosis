/**
David Nesterov-Rappoport
Class: Bomb
Purpose: Power Up used to kill enemies
 */
import java.awt.Image;

import javax.swing.ImageIcon;

public class Bomb extends Entity{

	private int phase = 0; // 0 - notPickedUp
	private int timer;

	
    public Bomb(int x, int y, int p) {
        super(x, y);
        phase = p;
        updateSprite();
    }
   
    public void updateSprite() {
    	switch(phase){
    	case 0:
    		loadImage("Graphics/bomb_notPickedUp.png");
    		break;
    	case 1:
    		loadImage("Graphics/bomb_active1.png");
    		break;
    	case 2:
    		loadImage("Graphics/bomb_explosion.png");
    		break;
    	}
        getImageDimensions();
    }
    
    public int getPhase() {
    	return phase;
    }
    public Image getInventoryImage() {
    	java.net.URL imgURL = this.getClass().getResource("Graphics/inventorybomb.png");
    	ImageIcon ii = new ImageIcon(imgURL);
    	Image inventoryImage = ii.getImage();
    	return inventoryImage;
    }
    public void explode() {
    	phase = 2;
    	loadImage("Graphics/bomb_explosion.png");
    	timer = 0;
    }
    public void upTick() {
    	timer++;
    }
    public int checkTick() {
    	return timer;
    }
}
