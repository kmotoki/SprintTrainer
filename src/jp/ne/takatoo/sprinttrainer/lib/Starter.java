package jp.ne.takatoo.sprinttrainer.lib;

import java.io.IOException;

import jp.ne.takatoo.sprinttrainer.R;
import android.app.Activity;
import android.media.MediaPlayer;
import android.widget.Toast;


public class Starter {

    private final Activity activity;
    
    private final MediaPlayer mp;
    
    public Starter(Activity activity) {
        if (activity == null) {
            throw new NullPointerException("activity is null.");
        }
        this.activity = activity;
        
        mp = MediaPlayer.create(activity, R.raw.n100);
    }
    
    public void execute() {
        Toast.makeText(activity, "execute", Toast.LENGTH_LONG).show();
        
        if (mp.isPlaying()) {
            mp.stop();
            try {
                mp.prepare();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            mp.start();
        }
    }
}
