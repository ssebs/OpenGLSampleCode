import javax.sound.sampled.*;

public class Sound
{
	/**
	 * plays the sound file when thte ball touches something
	 */
	public void playSound()
	{
		try
		{
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("res/HitMarker.wav"));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
			clip.start();
		} catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
}