package jp.ne.takatoo.sprinttrainer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


public class Starter extends Service{
    
    private static final String TAG = Starter.class.getSimpleName();
    
    private static final int DELAY_OF_ON_YOUR_MARK = 3000;
    
    private static final int DELAY_OF_SET = 3000;
    
    private static final int DELAY_OF_BANG = 3000;
    
    private boolean isExecuting = false;
    
    private MediaPlayer mp = null;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: intent = " + intent.toString());
        throw new UnsupportedOperationException("I do not support binding.");
    }
    
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, String.format(
                "onStartCommand: intent = %s, flags = %d, startId = %d",
                intent.toString(),
                flags,
                startId));
        if (!isExecuting) {
            isExecuting = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onYourMark();
                }
            }, DELAY_OF_ON_YOUR_MARK);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        try {
            if (mp != null && mp.isPlaying()) {
                mp.stop();
            }
        } catch (IllegalArgumentException e) {
            Log.w(TAG, "Illegal state of MediaPlayer.", e);
        }
        isExecuting = false;
        super.onDestroy();
    }

    private void onYourMark() {
        Log.d(TAG, "onYourMark");
        
        mp = MediaPlayer.create(this, R.raw.ichinitsuite_01);
        mp.start();
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                set();
            }
        }, DELAY_OF_SET);
    }
    
    private void set() {
        Log.d(TAG, "set");
        
        mp = MediaPlayer.create(this, R.raw.youidon_01);
        mp.start();
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bang();
            }
        }, DELAY_OF_BANG);
    }
    
    private void bang() {
        Log.d(TAG, "bang");
        
        mp = MediaPlayer.create(this, R.raw.kennjyuusoto);
        mp.start();
        
        isExecuting = false;
    }
}
