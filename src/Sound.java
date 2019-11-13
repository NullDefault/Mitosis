/**
David Nesterov-Rappoport
Class: Sound
Purpose: Used to make Sound files available to the game master
 */

import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Sound {
	
private Clip myClip;

public Sound(String filename){
	try {
		
		URL yourfile = getClass().getResource(filename);
		AudioInputStream stream = AudioSystem.getAudioInputStream(yourfile);
		AudioFormat format = stream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		myClip = (Clip) AudioSystem.getLine(info);
		myClip.open(stream);

	}
	catch (Exception e) {
	    //whatevers
	}
}

public void play() { // plays the sound
	myClip.setFramePosition(0);
	myClip.loop(0);
	myClip.start();
}
@SuppressWarnings("static-access")
public void loop() {
	myClip.loop(myClip.LOOP_CONTINUOUSLY);		// loops the sound
	myClip.start();
}
public void stop() { // stops the sound
	myClip.stop();
}





}


	
