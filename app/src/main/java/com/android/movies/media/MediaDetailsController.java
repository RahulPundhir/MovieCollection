package com.android.movies.media;

import android.util.Log;

import com.android.media.service.web.VideoResponse;
import com.android.media.service.web.VideoWebRequest;
import com.android.movies.util.Utility;

/**
 *
 */
public class MediaDetailsController implements VideoWebRequest.ServiceCallback {
    private MediaDetailsView mMediaDetailsView;
    private VideoWebRequest mVideoWebRequest;

    public MediaDetailsController(MediaDetailsView view, String mediaId) {
        this.mMediaDetailsView = view;
        mVideoWebRequest = new VideoWebRequest(view.getContext());
        mVideoWebRequest.setCallback(this);
        mVideoWebRequest.sendRequest(mediaId);
    }

    @Override
    public void onSuccess(VideoResponse data) {
        Log.i("MediaListCont", "Response = " + data);
        renderData(data);
    }

    @Override
    public void onError(String error) {
        Utility.showDialog(mMediaDetailsView.getContext(), "Error", error);
    }

    @Override
    public void onNetworkError(VideoResponse data, String error) {
        renderData(data);
        Utility.showDialog(mMediaDetailsView.getContext(), "Error", error);
    }

    /**
     * This method is used to render video data on screen.
     *
     * @param responseData
     */
    private void renderData(VideoResponse responseData) {
        if (null != responseData) {
            mMediaDetailsView.setVideoImage(responseData.Poster);
            mMediaDetailsView.setVideoTitle(responseData.Title);
            mMediaDetailsView.setVideoRated(responseData.Rated);
            mMediaDetailsView.setVideoReleasedYear(responseData.Released);
            mMediaDetailsView.setVideoDirector(responseData.Director);
            mMediaDetailsView.setVideoWriter(responseData.Writer);
            mMediaDetailsView.setVideoActors(responseData.Actors);
            mMediaDetailsView.setVideoPlot(responseData.Plot);
        }
    }
}
