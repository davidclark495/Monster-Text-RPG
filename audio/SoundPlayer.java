package audio;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
	
	private static Clip clip;// I don't know if this should be static or not
	private static boolean allowSound = true;
	
	public static void playSound(String filepath) {
		// end immediately if sound is turned off
		if(!allowSound)
			return;
		
	    try{
	        //Clip clip = AudioSystem.getClip();
	    	clip = AudioSystem.getClip();
	        clip.open(AudioSystem.getAudioInputStream(new File(filepath)));
	        clip.start();
	        long startTime = System.currentTimeMillis();
	        while(clip.getMicrosecondLength() != clip.getMicrosecondPosition() || System.currentTimeMillis() - startTime < 1500) {}
	        clip.close();
	    } catch (Exception e){
	        e.printStackTrace();
	    }
	}
	
	public static boolean isAllowedSound() {
		return allowSound;
	}
	public static void setAllowSound(boolean allow) {
		allowSound = allow;
	}

}
