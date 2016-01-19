package com.android.movies;

import android.content.Intent;
import android.util.Log;

import com.android.media.service.web.MediaListResponse;
import com.android.media.service.web.MediaListWebRequest;
import com.android.movies.media.MediaDetailsActivity;
import com.android.movies.util.UIConstants;
import com.android.movies.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MediaListController implements MediaListView.MediaListViewListener,
        MediaListAdapter.MediaCardClickListener, MediaListWebRequest.ServiceCallback {
    private MediaListView mMediaListView;
    private List<VideoListInfo> mVideoInfo;
    private MediaListWebRequest mMediaListWebRequest;

    public MediaListController(MediaListView view) {
        this.mMediaListView = view;
        this.mMediaListView.setMediaListViewListener(this);
        mMediaListWebRequest = new MediaListWebRequest(view.getContext());
        mMediaListWebRequest.setCallback(this);
        mMediaListWebRequest.sendRequest(UIConstants.DEFAULT_MEDIA_SEARCH);
    }

    public void renderData(List<VideoListInfo> list) {
        if (null != list) {
            MediaListAdapter tripsAdapter = new MediaListAdapter(list, mMediaListView.getContext(), this);
            mMediaListView.getMediaListView().setAdapter(tripsAdapter);
        }
    }

    /**
     * For vertical list like card view
     */
    private void initializeData(MediaListResponse responseData) {
        if (null != responseData) {
            mVideoInfo = new ArrayList<>();
            for (int i = 0; i < responseData.Search.length; i++) {
                mVideoInfo.add(new VideoListInfo(responseData.Search[i].Title,
                        responseData.Search[i].Year, responseData.Search[i].imdbID, responseData.Search[i].Type, responseData.Search[i].Poster));
            }
        }
    }

    @Override
    public void onSearchButtonClick(String mediaTitle) {
        Log.i("Searched", "Searched = "+mediaTitle);
        mediaTitle = mediaTitle.trim();
        mMediaListWebRequest.sendRequest(mediaTitle);
    }

    @Override
    public void onMediaClick(String videoId) {
        Log.i("MediaListCont", "videoId = "+videoId);
        Intent intent = new Intent();
        intent.putExtra(UIConstants.MEDIA_ID, videoId);
        intent.setClass(mMediaListView.getContext(), MediaDetailsActivity.class);
        mMediaListView.getContext().startActivity(intent);
    }

    @Override
    public void onSuccess(MediaListResponse data) {
        Log.i("MediaListCont", "Response = "+data);
        initializeData(data);
        renderData(mVideoInfo);
    }

    @Override
    public void onError(String error) {
        Log.i("MediaListCont", "error = "+error);
        Utility.showDialog(mMediaListView.getContext(), "Error", error);
    }

    @Override
    public void onNetworkError(MediaListResponse data, String error) {
        Log.i("MediaListCont", "network error = "+error);
        initializeData(data);
        renderData(mVideoInfo);
        Utility.showDialog(mMediaListView.getContext(), "Error", error);
    }
}
