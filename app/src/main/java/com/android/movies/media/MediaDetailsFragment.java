package com.android.movies.media;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.movies.R;

/**
 *
 */
public class MediaDetailsFragment extends Fragment {

    private MediaDetailsView mMediaDetailsView;
    private MediaDetailsController mMediaDetailsController;
    private String mMediaId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMediaDetailsView = (MediaDetailsView) inflater.inflate(R.layout.media_detail_view, container, false);
        return mMediaDetailsView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMediaDetailsController = new MediaDetailsController(mMediaDetailsView, mMediaId);
    }

    public void setMediaId(String mediaId) {
        this.mMediaId = mediaId;
    }

    public String getFragmentDefaultTag() {
        return ((Object) this).getClass().getName();
    }
}

