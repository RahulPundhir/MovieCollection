package com.android.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 */
public class MediaListFragment extends Fragment {

    private MediaListView mMediaListView;
    private MediaListController mMediaListController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMediaListView = (MediaListView) inflater.inflate(R.layout.media_list_view, container, false);
        return mMediaListView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMediaListController = new MediaListController(mMediaListView);
    }

    public String getFragmentDefaultTag() {
        return ((Object) this).getClass().getName();
    }
}
