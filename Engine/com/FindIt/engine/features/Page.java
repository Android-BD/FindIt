package com.FindIt.engine.features;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.uoft.designProject.FindIt.R;


public class Page extends Activity {
	
	static private MediaPlayer mMediaPlayer;
	static private AudioManager mAudioManager;
	static private boolean mLockMode = false;
	
	static public boolean performPage(Context context) throws InterruptedException {
		mLockMode = true;

		mAudioManager = (AudioManager)(context.getSystemService(Context.AUDIO_SERVICE));
		
		int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 
				AudioManager.FLAG_PLAY_SOUND);	
		mAudioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, 
				AudioManager.VIBRATE_SETTING_ON);
		
		mMediaPlayer = MediaPlayer.create(context, R.raw.siren_1);
		mMediaPlayer.setLooping(true);
		mMediaPlayer.setVolume(100, 100);
        mMediaPlayer.start();
        
        /*
        while(mLockMode) {
        	Thread.sleep(2000);
        	mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 
    				AudioManager.FLAG_PLAY_SOUND);	
        }
        */
        
		return true;
	}
	
	static public boolean isLockMode() {
		return mLockMode;
	}
	
	static public void disableLockMode() {
		mMediaPlayer.stop();
		mLockMode = false;
	}
}
