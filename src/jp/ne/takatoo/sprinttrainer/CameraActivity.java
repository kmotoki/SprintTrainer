package jp.ne.takatoo.sprinttrainer;

import java.io.IOException;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

public class CameraActivity extends ActionBarActivity {

    private static final String TAG = CameraActivity.class.getSimpleName();

    private static final String OUTPUT_FILE =
            Environment.getExternalStorageDirectory().getPath() + "/sample.mp4";

    private Camera camera = null;
    private MediaRecorder recorder = null;
    private SurfaceView surfaceView = null;

    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(callBack);

        surfaceView.setOnClickListener(onClickListener);

        final int frontCameraId = getFrontCameraId();
        if (frontCameraId < 0) {
            Toast.makeText(this, "This device doesn't have front camera.", Toast.LENGTH_LONG).show();
            this.finish();
        }
        
        camera = Camera.open(frontCameraId);
        camera.unlock();
        
        recorder = new MediaRecorder();
        recorder.setCamera(camera);
//        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
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
    
    private static int getFrontCameraId() {
        final int numberOfCameras = Camera.getNumberOfCameras();
        Log.d(TAG, "numberOfCamera = " + numberOfCameras);
        
        final CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
                Log.d(TAG, "Front camera id = " + i);
                return i;
            }
        }
        
        Log.i(TAG, "Front camera is not exists");
        return -1;
    }

    private SurfaceHolder.Callback callBack = new SurfaceHolder.Callback() {

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d(TAG, "surfaceDestroyed");
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated");

            Log.d(TAG, OUTPUT_FILE);
            recorder.setOutputFile(OUTPUT_FILE);
//            recorder.setVideoFrameRate(30);
//            recorder.setVideoSize(320, 240);

            recorder.setPreviewDisplay(holder.getSurface());

            try {
                recorder.prepare();
            } catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // TODO Auto-generated method stub
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isRecording) {
                Log.d(TAG, "Stop recording");
                recorder.stop();
                recorder.reset();
                recorder.release();
                isRecording = false;
                
                camera.lock();
                camera.release();
            } else {
                Log.d(TAG, "Start recording");
                recorder.start();
                isRecording = true;
            }
        }
    };
}
