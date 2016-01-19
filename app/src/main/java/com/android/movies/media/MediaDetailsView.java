package com.android.movies.media;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.movies.R;
import com.android.movies.util.UIConstants;
import com.bumptech.glide.Glide;

/**
 *
 */
public class MediaDetailsView extends RelativeLayout {
    private Toolbar mToolbar;
    private ImageView mVideoImage;
    private TextView mVideoTitle;
    private TextView mVideoRated;
    private TextView mVideoReleasedYear;
    private TextView mVideoDirector;
    private TextView mVideoWriter;
    private TextView mVideoActors;
    private TextView mVideoPlot;

    public MediaDetailsView(Context context) {
        super(context);
    }

    public MediaDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MediaDetailsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mVideoImage = (ImageView) findViewById(R.id.video_image);
        mVideoTitle = (TextView) findViewById(R.id.video_title);
        mVideoRated = (TextView) findViewById(R.id.video_rated);
        mVideoReleasedYear = (TextView) findViewById(R.id.video_released_date);
        mVideoDirector = (TextView) findViewById(R.id.video_director);
        mVideoWriter = (TextView) findViewById(R.id.video_writer);
        mVideoActors = (TextView) findViewById(R.id.video_actors);
        mVideoPlot = (TextView) findViewById(R.id.video_plot);
        setupToolBar();
    }

    private void setupToolBar() {
        mToolbar.setNavigationIcon(R.drawable.ic_album_white_24dp);
        mToolbar.setTitle(R.string.details_page_title);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    public void setVideoImage(String videoImageUrl) {
        if (null != videoImageUrl && !videoImageUrl.isEmpty()) {
            Glide.with(getContext())
                    .load(videoImageUrl)
                    .into(this.mVideoImage);
        } else {
            this.mVideoImage.setImageResource(R.drawable.samplemovie);
        }
    }

    public void setVideoTitle(String videoTitle) {
        this.mVideoTitle.setText(getFormattedText(UIConstants.MEDIA_TITLE, videoTitle), TextView.BufferType.SPANNABLE);
    }

    public void setVideoRated(String videoRated) {
        this.mVideoRated.setText(getFormattedText(UIConstants.MEDIA_RATED, videoRated), TextView.BufferType.SPANNABLE);
    }

    public void setVideoReleasedYear(String videoReleasedYear) {
        this.mVideoReleasedYear.setText(getFormattedText(UIConstants.MEDIA_RELEASE_DATE, videoReleasedYear), TextView.BufferType.SPANNABLE);
    }

    public void setVideoDirector(String videoDirector) {
        this.mVideoDirector.setText(getFormattedText(UIConstants.MEDIA_DIRECTOR, videoDirector), TextView.BufferType.SPANNABLE);
    }

    public void setVideoWriter(String videoWriter) {
        this.mVideoWriter.setText(getFormattedText(UIConstants.MEDIA_WRITER, videoWriter), TextView.BufferType.SPANNABLE);
    }

    public void setVideoActors(String videoActors) {
        this.mVideoActors.setText(getFormattedText(UIConstants.MEDIA_ACTORS, videoActors), TextView.BufferType.SPANNABLE);
    }

    public void setVideoPlot(String videoPlot) {
        this.mVideoPlot.setText(getFormattedText(UIConstants.MEDIA_PLOT, videoPlot), TextView.BufferType.SPANNABLE);
    }

    /**
     * This method is used to return formatted string.
     *
     * @param title
     * @param content
     * @return
     */
    private Spanned getFormattedText(String title, String content) {
        return Html.fromHtml(UIConstants.TEXT_HEADING_START_FONT + title
                + UIConstants.TEXT_HEADING_END_FONT + UIConstants.TEXT_CONTENT_FONT
                + content + UIConstants.TEXT_END_FONT);
    }
}
