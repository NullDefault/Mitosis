/**
David Nesterov-Rappoport
Class: Entity
Purpose: Superclass for all game objects
 */
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Entity {
	
	protected final int XLIMIT_low = -1;
	protected final int XLIMIT_high = 751; // The bounds so no entity can leave the game space
	protected final int YLIMIT_high = 531;
	protected final int YLIMIT_low = -1;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;

    public Entity(int x, int y) {

        this.x = x;
        this.y = y;
        visible = true;
    }

    protected void loadImage(String imageName) {
    	java.net.URL imgURL = this.getClass().getResource(imageName);
        ImageIcon ii = new ImageIcon(imgURL);
        image = ii.getImage();
    }
    
    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }    

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}