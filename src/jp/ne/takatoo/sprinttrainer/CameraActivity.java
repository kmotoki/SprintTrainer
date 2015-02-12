package jp.ne.takatoo.sprinttrainer;

import java.io.IOException;
import java.util.List;

import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class CameraActivity extends ActionBarActivity {

    private static final String TAG = CameraActivity.class.getSimpleName();

    private Camera camera = null;
    private SurfaceView surfaceView = null;
    private SurfaceHolder surfaceHolder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(callBack);

        // TODO Error handling
        camera = Camera.open(1);
        
        surfaceView.setOnClickListener(onClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private SurfaceHolder.Callback callBack = new SurfaceHolder.Callback() {

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d(TAG, "surfaceDestroyed");
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated");
            try {
                camera.setPreviewDisplay(holder);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // TODO Auto-generated method stub
            Log.d(TAG, "surfaceChanged");

            surfaceHolder = holder;

            camera.stopPreview();
            Camera.Parameters parameters = camera.getParameters();
            List<Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
            Size smallestSize = supportedPreviewSizes.get(supportedPreviewSizes.size() - 1);

            Log.d(TAG, "Before: width = " + width + ", height = " + height);
            Log.d(TAG, "After: width = " + smallestSize.width + ", height = " + smallestSize.height);
            parameters.setPreviewSize(smallestSize.width, smallestSize.height);
            
            LayoutParams layoutParams = surfaceView.getLayoutParams();
            layoutParams.width = smallestSize.width;
            layoutParams.height = smallestSize.height;
            
            surfaceView.setLayoutParams(layoutParams);
            
            camera.startPreview();
        }
    };
    
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Toast.makeText(CameraActivity.this, "onClick", Toast.LENGTH_LONG).show();
            
        }
    };
}
