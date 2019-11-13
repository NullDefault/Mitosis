/**
David Nesterov-Rappoport
Class: BloompfEgg
Purpose: Game Entity for Spawning Bloompf eggs
 */
public class BloompfEgg extends Entity{
	public BloompfEgg(int x, int y) {
        super(x, y);
        
        init();
    }
   
    private void init() {
        loadImage("Graphics/bloompf_call.png");
        getImageDimensions();
    }
}
