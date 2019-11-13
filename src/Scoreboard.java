/**
David Nesterov-Rappoport
Class: ScoreBoard
Purpose: Manages the High Score
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Scoreboard {
	 //private static File highScoreFile= new File("/Graphics/highscore.txt");

	InputStream in = getClass().getResourceAsStream("/Graphics/highscore.txt");
	
	
    private static int highScore;
	
	public static int geths() {
		return highScore;
	}
	public void ScoreUpdate(int userscore) {
	    try {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        String line = reader.readLine();
	        while (line != null)                 // read the score file line by line
	        {
	            try {
	                int score = Integer.parseInt(line.trim());
	             
	                if (score > highScore)                       // and keep track of the largest
	                { 
	                    highScore = score; 
	                }
	                if (userscore > highScore) {
	                	highScore = userscore;
	                }
	            } catch (NumberFormatException e1) {
	                // ignore invalid scores
	            }
	            line = reader.readLine();
	        }
	        reader.close();
	 } catch (IOException ex) {
	        System.err.println("Error reading scores from file");
	    }
	 try {
        
	        PrintWriter output = 
	                new PrintWriter(
	                      new File(this.getClass().getResource("/Graphics/highscore.txt").getPath()));
	        output.println("" + userscore);
	        output.close();

	    } catch (IOException ex1) {
	        System.out.printf("ERROR writing score to file: %s\n", ex1);
	    }
}
}