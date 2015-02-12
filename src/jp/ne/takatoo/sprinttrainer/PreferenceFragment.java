package jp.ne.takatoo.sprinttrainer;

import android.os.Bundle;

public class PreferenceFragment extends android.preference.PreferenceFragment {
    
    private static final String TAG = PreferenceFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
    
}