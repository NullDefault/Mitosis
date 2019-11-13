/**
David Nesterov-Rappoport
Class: Game Master
Purpose: Controls all game behavior and rendering
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GameMaster extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	//------------------------------------------------------------------------------------------------------------------------------//
    private Timer timer; // Timer variables
    private final int DELAY = 10;
   
    private Scoreboard scoreboard;
    
    private Player player; // Creates the player object
    private boolean ingame = true; // Starts the game with ingame as true
    
    private Random rand = new Random(); // Random number generator
    
    private Vector<Enemy> enemies = new Vector<Enemy>(); // Creates the game entity vectors
    private Vector<Foodbean> beans = new Vector<Foodbean>();
    private Vector<Foodmango> mangoes = new Vector<Foodmango>();
    private Vector <SpeedPotion> speedpotions = new Vector<SpeedPotion>();
    private Vector <Bloompf> bloompfs = new Vector<Bloompf>();
    private Vector <BloompfEgg> bloompfeggs = new Vector<BloompfEgg>();
    private Vector <Bomb> bombs = new Vector<Bomb>();
    private Bomb placeholder = new Bomb(-20,-20, 0);
    
    private int startingEnemies = 2; // Decides how many enemies/mangoes/beans spawn initially
    private final int numberOfBeans = 60;      
    private final int numberOfMangoes = 20;
        
    private final int CARRYING_CAPACITY = 12; // How many enemies are needed for Bloompfs to start spawning
    

    private Sound deathsound = new Sound("/Graphics/death.wav"); // Game sounds
    private Sound music = new Sound("/Graphics/bgMusic.wav"); // Music Credit - "Land of 8-Bits" by Stephen Bennet @ FeslyianStudios.com
//------------------------------------------------------------------------------------------------------------------------------//   
    public GameMaster(Scoreboard s) { // Constuctor
    	scoreboard = s;
    	initBoard();
    }
    private void initBoard() { // Initializes the board

        addKeyListener(new TAdapter()); // Adds Key Listener for User Input
        setBackground(Color.white); 
        setFocusable(true);
        
        player = new Player(0,0); // Spawns the player object
        for(int i=0;i<startingEnemies;i++) {
        	enemies.add(new Enemy(rand.nextInt(750),rand.nextInt(530))); // Creates the initial enemies
        }
        Rectangle deadzone = new Rectangle(150,150); // Part of the Map Where Enemies wont spawn when the game starts so the player doesnt die instantly
        for(int i=0;i<enemies.size();i++) { // Re-does the enemies untill none are in the deadzone
        	Enemy enemy = enemies.get(i);
        	if(deadzone.contains(enemy.getX(),enemy.getY())){
        		enemies.remove(enemy);
        		enemies.add(new Enemy(rand.nextInt(750),rand.nextInt(530)));
        	}
        }
        
        
        
        for(int i=0; i<numberOfBeans;i++) { // Spawns initial beans
        	beans.add(new Foodbean(rand.nextInt(750),rand.nextInt(530)));
        }
        for(int i=0; i<numberOfMangoes;i++) { // Spawns initial mangoes
        	mangoes.add(new Foodmango(rand.nextInt(750),rand.nextInt(530)));
        }

        timer = new Timer(DELAY, this);
        timer.start();
        music.loop();
    }
    public void dispose() { // Method for clearing the stack
        JFrame parent = (JFrame) this.getTopLevelAncestor();
        parent.dispose();
    }
//------------------------------------------------------------------------------------------------------------------------------//
    @Override
    public void paintComponent(Graphics g) { // Drawing method
        super.paintComponent(g);

        doDrawing(g);
        
        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) { // This is the main rendering method where everything is drawn
        
        Graphics2D g2d = (Graphics2D) g;

        
			g2d.drawImage(player.getImage(), player.getX(), player.getY(), this); // Draws the game if the player isnt dead
			if(player.getBombPhase() == true) {
				bombs.add(new Bomb(player.getX(),player.getY(), 1));
				player.doneBombing();
			}
			for (Foodbean bean : beans) {
				g2d.drawImage(bean.getImage(), bean.getX(), bean.getY(), this);
			}
			for (Foodmango mango: mangoes) {
				g2d.drawImage(mango.getImage(),mango.getX(),mango.getY(),this);
			}
			for (Enemy enemy : enemies) {
				g2d.drawImage(enemy.getImage(),enemy.getX(),enemy.getY(), this);
			}
			for (SpeedPotion pup: speedpotions) {
				g2d.drawImage(pup.getImage(), pup.getX(),pup.getY(),this);
			}
			for (Bloompf bf: bloompfs) {
				g2d.drawImage(bf.getImage(),bf.getX(),bf.getY(),this);
			}
			for (BloompfEgg bs: bloompfeggs) {
				g2d.drawImage(bs.getImage(),bs.getX(), bs.getY(), this);
			}
			for (Bomb b: bombs) {
				
				if(b.getPhase()>0) {
					b.updateSprite();
				}
				if(b.getPhase() == 2) {
					b.upTick();
					if(b.checkTick() == 40) {
						b.setVisible(false);
					}
				}
				g2d.drawImage(b.getImage(),b.getX(), b.getY(), this);
			}
			g2d.drawString("Score: " + player.getscore(), 5, 15); // Draws the current score
			for(int i=0; i<player.getInventory();i++) {				// Draws the player inventory
				g2d.drawImage(placeholder.getInventoryImage(), 120+i*20, 2, this);
			}
		if(!ingame) { // Plays the death sequence
			music.stop();
			deathsound.play();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			setVisible(false);
			dispose();
			scoreboard.ScoreUpdate(player.getscore());
			GameMain.setState(4);
		}
			
    }

//------------------------------------------------------------------------------------------------------------------------------//
    @Override
    public void actionPerformed(ActionEvent e) { 
		step();
    }
    private void checkCollisions() { // Method for collision detection

        Rectangle r3 = player.getBounds();      
        
       
       // BLOOMPFS
        for(int i=0; i < bloompfs.size();i++) {
        	Bloompf b = bloompfs.get(i);
        	Rectangle rb = b.getBounds();
        	boolean satiated = false;
        	for(int j=0; j < enemies.size();j++) {
        		Enemy enemy = enemies.get(j);
        		Rectangle re = enemy.getBounds();
        		if(rb.intersects(re)) {
        			enemy.setVisible(false);
        			
        			if (satiated == false) {
						b.lessHungry();
						if(b.getHunger()==0) {
						satiated = true;
						}
					}
					updateStock();
        		}
        	}
        }
        
       // ENEMIES
       for (int i=0; i < enemies.size();i++) {
            	Enemy enemy = enemies.get(i);
            	Rectangle r2 = enemy.getBounds();
            	if (r3.intersects(r2)) {
                player.setVisible(false);
                ingame = false;
            }
           }
        for (int i=0; i < beans.size();i++) {
        	Foodbean bean = beans.get(i);
        	Rectangle r1=bean.getBounds();
        	if(r1.intersects(r3)) {
        		player.eat(bean.getVal());
        		bean.setVisible(false);
        		updateStock();
        	}
        	for(int e=0;e<enemies.size();e++) {
        		Enemy enemy = enemies.get(e);
        		Rectangle ree = enemy.getBounds();
        		if(ree.intersects(r1)) {
        			enemy.addMass(bean.getVal());
        			bean.setVisible(false);
        			updateStock();
        		}
        	}
        
        }
        
        
        // MANGOES
        for (int i=0; i < mangoes.size();i++) {
        	Foodmango mango = mangoes.get(i);
        	Rectangle r1=mango.getBounds();
        	if(r1.intersects(r3)) {
        		player.eat(mango.getVal());
        		mango.setVisible(false);
        		updateStock();
        	}
        	for(int e=0;e<enemies.size();e++) {
        		Enemy enemy = enemies.get(e);
        		Rectangle ree = enemy.getBounds();
        		if(ree.intersects(r1)) {
        			enemy.addMass(mango.getVal());
        			mango.setVisible(false);
        			updateStock();
        		}
        	}
        }
        
        
        // POWERUPS
        for(int i=0; i < speedpotions.size();i++) {
        	SpeedPotion pup = speedpotions.get(i);
        	Rectangle r1=pup.getBounds();
        	if(r1.intersects(r3)) {
        		player.powerUp();
        		pup.setVisible(false);
        		updateStock();
        	}
        }
        for(int i=0; i< bloompfeggs.size();i++) {
        	BloompfEgg bs = bloompfeggs.get(i);
        	Rectangle rbs = bs.getBounds();
        	if(rbs.intersects(r3)) {
        		bloompfs.add(new Bloompf(rand.nextInt(750), rand.nextInt(530), enemies.size()));
        		bs.setVisible(false);
        		updateStock();
        	}
        }
        for(int i=0; i< bombs.size();i++) {
        	Bomb b = bombs.get(i);
        	Rectangle rb = b.getBounds();
        	if(rb.intersects(r3) && b.getPhase() == 0) {
        		player.addBomb();
        		b.setVisible(false);
        		updateStock();
        	}
        	for(int j=0; j<enemies.size();j++) {
        		Enemy e = enemies.get(j);
        		Rectangle re = e.getBounds();
        		if(re.intersects(rb) && b.getPhase() == 1) {
        			rb.grow(80, 80);
        			b.explode();
        			for(int l=0;l<enemies.size();l++) {
        				Enemy v = enemies.get(l);
        				Rectangle rv = v.getBounds();
        				if(rv.intersects(rb)) {
        					v.setVisible(false);
        					player.eat(v.getValue());
        					updateStock();
        				}
        			}
        		}
        	}
        }
        
    }
    
    private void updateEnemyBehavior() { //     [0] Check if currently colliding with anyone [1] - Check if the player is nearby [2] Check if there are mangos nearby [3] Check if there are beans nearby
    	for(int i=0;i < enemies.size();i++) {
    		
    		Enemy enemy = enemies.get(i);
    		Rectangle enemyBounds = enemy.getBounds();
    		boolean actionAssigned = false;
    		
    		for(int j=0; j < enemies.size();j++) {
    			if(j==i) {
    				continue;
    			}
    			else {
    				Enemy neighbor = enemies.get(j);
    				Rectangle neighborBounds = neighbor.getBounds();
    				if(enemyBounds.intersects(neighborBounds)) {
    					enemy.moveAway(neighbor.getX(),neighbor.getY());
    					actionAssigned = true;
    				}
    			}
    		}
	
    		Rectangle grazingRange = enemy.getBounds();
    		grazingRange.grow(100,100);
    		Rectangle aggroRange = grazingRange; aggroRange.grow(100, 100);
    		
    		if(actionAssigned == false) {
    			if(aggroRange.contains(player.getX(),player.getY())) {
    				enemy.seekUnit(player.getX(), player.getY());
    				enemy.setAggro(true);
    				actionAssigned = true;
    			}else {
    				enemy.setAggro(false);
    			}
    		}
    		
    		if(actionAssigned == false) {
    			for(Foodmango mango: mangoes) {
    				if (grazingRange.contains(mango.getX(), mango.getY())) {
    					enemy.seekUnit(mango.getX(), mango.getY());
    					actionAssigned = true;
    				}
    			}
    		}
    		if(actionAssigned == false) {
    			for (Foodbean bean : beans) {
    				if (grazingRange.contains(bean.getX(), bean.getY())) {
    					enemy.seekUnit(bean.getX(), bean.getY());	
    					actionAssigned = true;
    				}
    			}
    		}
    		enemy.move();
    		
    		
    	}
    	// Bloompf section of the method which makes all the bloompfs hunt for nearby enemies
    	for(int i=0; i < bloompfs.size();i++) {
    		Bloompf b = bloompfs.get(i);
    		boolean actionAssigned = false;
    		Rectangle huntingGround = b.getBounds();
    		huntingGround.grow(200, 200);
    		for(Enemy enemy : enemies) {
    			if(huntingGround.contains(enemy.getX(),enemy.getY())) {
    				b.hunt(enemy.getX(), enemy.getY());
    				actionAssigned = true;
    				break;
    			}
    		}
    		if(actionAssigned == false) {
    			b.wander();
    		}
    		b.move();
    	}
    }
        
    
    private void updateStock() { // Method that goes through each entity list and updates it to match the game state (removes invisible units and adds new ones if necessary)
    		for (int i=0; i < enemies.size();i++) {
    			Enemy enemy = enemies.get(i);
    			if(enemy.getMassAccumulated() > enemy.maxMass) {
    				enemy.setMassAccumulated(0);
    				enemies.add(new Enemy(enemy.getX(),enemy.getY()));
    			}
    			
    		}
    		if(enemies.size() == 0) {
    				for (int j = 0; j < 3; j++) {
						enemies.add(new Enemy(rand.nextInt(750), rand.nextInt(530)));
				}
    		}
    		for(int i=0;i<enemies.size();i++) {
    			Enemy enemy = enemies.get(i);
    			if(enemy.visible) {
    				
    			}else {
    				enemies.remove(enemy);
    			}
    		}
    		for(int i=0;i<bloompfs.size();i++) {
    			Bloompf b = bloompfs.get(i);
    			if(b.getHunger() <= 0) {
    				bloompfs.remove(b);
    				b.setVisible(false);
    			}
    		}
	        for (int i=0; i < beans.size();i++) {    
	        	Foodbean bean = beans.get(i);
	            if (bean.isVisible()) {
	            } else {
	                beans.remove(bean);
	                beans.add(new Foodbean(rand.nextInt(750),rand.nextInt(530)));
	            }

	        }
	        for (int i=0; i < mangoes.size();i++) {    
	        	Foodmango mango = mangoes.get(i);
	            if (mango.isVisible()) {
	            } else {
	                mangoes.remove(mango);
	                mangoes.add(new Foodmango(rand.nextInt(750),rand.nextInt(530)));
	            }
	        }
	        for (int i=0; i < speedpotions.size();i++) {    
	        	SpeedPotion pup = speedpotions.get(i);
	            if (pup.isVisible()) {
	            } else {
	                speedpotions.remove(pup);
	            }
	        }
	        for (int i=0; i < bloompfeggs.size();i++) {    
	        	BloompfEgg bs = bloompfeggs.get(i);
	            if (bs.isVisible()) {
	            } else {
	                bloompfeggs.remove(bs);
	            }
	        }
	        for(int i=0; i < bombs.size();i++) {
	        	Bomb b = bombs.get(i);
	        	if (b.isVisible()) {
	        	} else {
	        		bombs.remove(b);
	        	}
	        }
	        
	        if(enemies.size() > CARRYING_CAPACITY && bloompfs.size() < 1 && bloompfeggs.size()<1) { // Checks if a bloompf needs to be spawned or not
	        	bloompfeggs.add(new BloompfEgg(rand.nextInt(750),rand.nextInt(530)));
	        }
	        
        }
    private void rollForPowerups() { // Rolls to see if a powerup should spawn or not
    	int getLucky = rand.nextInt(3000);
        if (getLucky == 1 && speedpotions.size()<3) {
        	speedpotions.add(new SpeedPotion(rand.nextInt(750),rand.nextInt(530)));
        }	
        if (getLucky == 4 && player.getInventory()<3) {
        	bombs.add(new Bomb(rand.nextInt(750),rand.nextInt(530), 0));
        }
        else if (getLucky == 20 && player.getInventory()<3) {
        	bombs.add(new Bomb(rand.nextInt(750),rand.nextInt(530), 0));
        }
    }
    private void step() { // One game "turn"
        player.move();
        checkCollisions();
        updateEnemyBehavior();
        rollForPowerups();        
        repaint();     
    }    
//------------------------------------------------------------------------------------------------------------------------------//
    private class TAdapter extends KeyAdapter { // Private class used by the GameMaster to monitor user input
        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
           player.keyPressed(e);
        }
    }
//------------------------------------------------------------------------------------------------------------------------------//
    public int getNumberOfEnemies() { // Return current number of enemies
    	return enemies.size();
    }
}