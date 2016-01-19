package com.android.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class MediaListActivity extends AppCompatActivity {
    private MediaListFragment mMediaListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mMediaListFragment = new MediaListFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, mMediaListFragment, mMediaListFragment.getFragmentDefaultTag())
                    .commit();
        }
    }
}
