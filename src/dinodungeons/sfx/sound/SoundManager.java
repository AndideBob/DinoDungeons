package dinodungeons.sfx.sound;

import lwjgladapter.datatypes.LWJGLAdapterException;
import lwjgladapter.logging.Logger;
import lwjgladapter.sound.AudioMaster;
import lwjgladapter.sound.exceptions.AudioNotInMemoryException;
import lwjgladapter.sound.exceptions.IllegalSoundValueException;

public class SoundManager {
	
	private static final int numberOfSoundInstances = 20;

	private static SoundManager instance;

	private Song currentSong;
	private boolean musicPlaying;
	
	private SoundManager() {
		instance = this;
		musicPlaying = false;
	}
	
	public static SoundManager getInstance(){
		if(instance == null){
			return new SoundManager();
		}
		return instance;
	}

	public void loadSounds(){
		for(Song song : Song.values()){
			AudioMaster.instance.loadSingleInstanceSound(song, song.getSfxResourceID().getFilePath());
			try {
				AudioMaster.instance.manageSound(song, true, 1f, 1f);
			} catch (LWJGLAdapterException e) {
				Logger.logError(e);
			}
		}
		for(SoundEffect soundEffect : SoundEffect.values()){
			AudioMaster.instance.loadMultiInstanceSound(soundEffect, soundEffect.getSfxResourceID().getFilePath(), numberOfSoundInstances);
		}
	}
	
	public void playMusic(Song song) {
		try {
			if(musicPlaying) {
				AudioMaster.instance.stopSound(currentSong);
			}
			currentSong = song;
			musicPlaying = true;
			
			AudioMaster.instance.playSound(currentSong);
		} catch (AudioNotInMemoryException e) {
			Logger.logError(e);
		}
	}
	
	public void stopMusic() {
		try {
			if(musicPlaying) {
				AudioMaster.instance.stopSound(currentSong);
			}
			musicPlaying = false;
		} catch (AudioNotInMemoryException e) {
			Logger.logError(e);
		}
	}
	
	public void playSoundEffect(SoundEffect soundEffect) {
		try {
			AudioMaster.instance.playSound(soundEffect);
		} catch (AudioNotInMemoryException e) {
			Logger.logError(e);
		}
	}
}
