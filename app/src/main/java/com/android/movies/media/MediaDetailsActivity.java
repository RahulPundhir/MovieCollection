package com.android.movies.media;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.android.movies.R;
import com.android.movies.util.UIConstants;

/**
 *
 */
public class MediaDetailsActivity extends AppCompatActivity {
    private MediaDetailsFragment mMediaDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String mediaId = getIntent().getStringExtra(UIConstants.MEDIA_ID);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mMediaDetailsFragment = new MediaDetailsFragment();
        mMediaDetailsFragment.setMediaId(mediaId);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, mMediaDetailsFragment, mMediaDetailsFragment.getFragmentDefaultTag())
                    .commit();
        }
    }
}