/**
David Nesterov-Rappoport
Class: Game Over Screen
Purpose: Displays the game over screen
 */
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameOverScreen extends Screen {

	
	public GameOverScreen(){
		init();
	}

	
	public void init() {
//============================================================//
		
//============================================================//
		JFrame coreframe = new JFrame(GameTitle);
		coreframe.setResizable(false);
		coreframe.setSize(WIDTH, HEIGHT);																// <- INITIALLY CREATES THE CORE FRAME
		coreframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		coreframe.setIconImage(ICON.getImage());
//============================================================================================//
		JLabel bgimage = new JLabel();
		int highscore = Scoreboard.geths();
		JButton highestScore = new JButton("High Score: "+highscore);
		highestScore.setAlignmentX(Component.CENTER_ALIGNMENT);
		InputStream is = getClass().getResourceAsStream("/Graphics/bgimageGameOver.gif"); //
		ImageIcon image;
		try {
			image = new ImageIcon(ImageIO.read(is));
			bgimage.setIcon(image);
		} catch (IOException e) {
			e.printStackTrace();
		}	
//===========================================================================================//
		JButton startButton = new JButton();
		startButton.setText("Play Again");
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent evt) {
				  	coreframe.setVisible(false);
				    GameMain.setState(1);
		   
				  }
				}); 
		JButton exitButton = new JButton();
		exitButton.setText("Back To Start Menu");
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent evt) {
				    coreframe.setVisible(false);
				   	GameMain.setState(2);
				  
				  }
				}); 
		
//=======================================================================//
		bgimage.setLayout(new BoxLayout(bgimage, BoxLayout.Y_AXIS));
		bgimage.add(Box.createVerticalStrut(300));									// <- LAYOUT MAGIC
		bgimage.add(highestScore);
		bgimage.add(Box.createVerticalStrut(100));
		bgimage.add(startButton);
		bgimage.add(exitButton);
//======================================================================//
		coreframe.setContentPane(bgimage);
		coreframe.setLocationRelativeTo(null);
		coreframe.getRootPane().setDefaultButton(startButton);
		startButton.requestFocus();
		coreframe.setVisible(true);									//  <- END OF GUI 
//===========================================================================================// 
	}
}
