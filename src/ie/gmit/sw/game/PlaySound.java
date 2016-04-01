package ie.gmit.sw.game;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Clip;

/**
 * This class handles all sounds played in-game
 * @author John
 *
 */
public class PlaySound {
	
	public PlaySound(String fileName){
		play(fileName);
	}
	
	private void play(String fileName){
		try {		    
		    AudioInputStream stream = AudioSystem.getAudioInputStream(new File(fileName));
		    DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
		    Clip clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		    clip.start();
		}catch (Exception error) {
			System.out.println("Error - " + error);
		}
	}
}