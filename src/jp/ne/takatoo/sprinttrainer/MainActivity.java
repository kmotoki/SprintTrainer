package jp.ne.takatoo.sprinttrainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {
    
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d(TAG, "onOptionsItemSelected: " + item.toString());
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            this.startActivity(new Intent(this, Preferences.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void onClickButtonStart(View view) {
        Log.d(TAG, "onClickButtonStart");
        startService(new Intent(getApplicationContext(), Starter.class));
    }
    
    public void onClickButtonRecord(View view) {
        Log.d(TAG, "onClickButtonRecord");
        startActivity(new Intent(this, CameraActivity.class));
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        stopService(new Intent(getApplicationContext(), Starter.class));
        super.onDestroy();
    }
}
