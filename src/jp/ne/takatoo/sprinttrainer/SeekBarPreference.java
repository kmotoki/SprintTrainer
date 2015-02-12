package jp.ne.takatoo.sprinttrainer;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class SeekBarPreference extends Preference implements OnSeekBarChangeListener {
    
    private static final String TAG = SeekBarPreference.class.getSimpleName();
    
    private int currentProgress = 0;

    public SeekBarPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWidgetLayoutResource(R.layout.preference_widget_seekbar);
    }

    public SeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWidgetLayoutResource(R.layout.preference_widget_seekbar);
    }

    public SeekBarPreference(Context context) {
        super(context);
        setWidgetLayoutResource(R.layout.preference_widget_seekbar);
    }

    @Override
    protected void onBindView(View view) {
        final SeekBar seekBar = (SeekBar) view.findViewById(R.id.pref_seekbar);
        if (seekBar != null) {
            seekBar.setProgress(currentProgress);
            seekBar.setOnSeekBarChangeListener(this);
        }
        super.onBindView(view);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        setProgress(
                restorePersistedValue ? getPersistedInt(currentProgress) : (Integer) defaultValue);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // Do nothing
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Do nothing
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        final int progress = (Integer) seekBar.getProgress();
        Log.d(TAG, "onStopTrackingTouch: progress = " + progress);
        
        if (callChangeListener(progress)) {
            setProgress(progress);
        }
    }

    private void setProgress(int progress) {
        if (currentProgress != progress) {
            currentProgress = progress;
            persistInt(progress);
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }
}
