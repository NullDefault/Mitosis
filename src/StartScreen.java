/**
David Nesterov-Rappoport
Class: Start Screen
Purpose: Displays the start menu
 */
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class StartScreen extends Screen{
	public StartScreen(){
		init();
	}

	
	public void init() {
		//============================================================//
		JFrame coreframe = new JFrame(GameTitle);
		coreframe.setResizable(false);
		coreframe.setSize(WIDTH, HEIGHT);																// <- INITIALLY CREATES THE CORE FRAME
		coreframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		coreframe.setIconImage(ICON.getImage());
		//============================================================================================//
		JLabel bgimage = new JLabel();
		java.net.URL imgURL = getClass().getResource("/Graphics/bgimageStartScreen.gif");
		ImageIcon image = new ImageIcon(imgURL);				// <- DOES THE WHOLE BACGROUND THING
		bgimage.setIcon(image);
		//===========================================================================================//
		
		JButton startButton = new JButton();
		startButton.setText("New Game");
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent evt) {
				  	coreframe.setVisible(false);
				    GameMain.setState(1);
		   
				  }
				}); 
		
		JButton aboutButton = new JButton();
		aboutButton.setText("About");														// <- BUTTON COMMAND CENTRE
		aboutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		aboutButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent evt) {
				  	coreframe.setVisible(false);
				   	GameMain.setState(5);
				  
				  }
				}); 
		
		JButton exitButton = new JButton();
		exitButton.setText("Exit");
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent evt) {
				    System.exit(0);
				  }
				}); 
		
		//=======================================================================//
		bgimage.setLayout(new BoxLayout(bgimage, BoxLayout.Y_AXIS));
		bgimage.add(Box.createVerticalStrut(400));									// <- LAYOUT MAGIC
		bgimage.add(startButton);
		bgimage.add(aboutButton);
		bgimage.add(exitButton);
		//======================================================================//
		coreframe.setContentPane(bgimage);
		coreframe.setLocationRelativeTo(null);
		coreframe.setVisible(true);									//  <- END OF GUI HORSESHIT
		//===========================================================================================// 
	}
	


}
