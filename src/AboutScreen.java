/**
David Nesterov-Rappoport
Class: About Screen
Purpose: Displays useful info about the game
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

public class AboutScreen extends Screen {
	public AboutScreen(){
		init();
	}

	
	public void init() {
		//============================================================//
		JFrame coreframe = new JFrame(GameTitle);
		coreframe.setResizable(false);
		coreframe.setSize(WIDTH, HEIGHT);																// <- INITIALLY CREATES THE FRAME
		coreframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		coreframe.setIconImage(ICON.getImage());
		//============================================================================================//
		JLabel bgimage = new JLabel();
		InputStream is = getClass().getResourceAsStream("/Graphics/bgimageAbout.gif"); //
		ImageIcon image;
		try {
			image = new ImageIcon(ImageIO.read(is));
			bgimage.setIcon(image);
		} catch (IOException e) {
			e.printStackTrace();
		}				
		//===========================================================================================//

		JButton exitButton = new JButton();
		exitButton.setText("Back To Start Menu");
		exitButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		exitButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent evt) { // <- Sends you back to the start menu
				    coreframe.setVisible(false);
				   	GameMain.setState(2);		  
				  }
				}); 
		
		//=======================================================================//
		bgimage.setLayout(new BoxLayout(bgimage, BoxLayout.Y_AXIS));
		bgimage.add(Box.createVerticalStrut(150));									// <- LAYOUT MAGIC
		bgimage.add(exitButton);
		bgimage.add(Box.createHorizontalStrut(50));
		//======================================================================//
		coreframe.setContentPane(bgimage);	
		coreframe.setLocationRelativeTo(null);
		coreframe.setVisible(true);									
		//===========================================================================================// 
	}
}
