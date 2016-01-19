package com.android.movies;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 *
 */
public class MediaListAdapter extends RecyclerView.Adapter<MediaListAdapter.MediaListHolder> {

    public static final String VIDEO_RELEASE_YEAR = "Video Release year: ";
    public static final String VIDEO_TYPE = "Video type: ";
    private List<VideoListInfo> mVideos;
    private Context mContext;
    private MediaCardClickListener mListener;

    public MediaListAdapter(List<VideoListInfo> videos, Context context, MediaCardClickListener listener) {
        this.mVideos = videos;
        this.mContext = context;
        this.mListener = listener;
    }


    @Override
    public MediaListHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View holderView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.media_card_view, viewGroup, false);
        MediaListHolder pvh = new MediaListHolder(holderView, new ItemClickListener() {
            @Override
            public void onClick(View view, MediaListHolder holder) {
                mListener.onMediaClick(holder.videoId);

            }});
        return pvh;
    }

    @Override
    public void onBindViewHolder(MediaListHolder listHolder, int i) {
        listHolder.cv.setCardBackgroundColor(Color.TRANSPARENT);
        listHolder.videoId = mVideos.get(i).videoId;
        setVideoImage(listHolder.videoImage);
        listHolder.videoTitle.setText(mVideos.get(i).videoTitle);
        listHolder.videoType.setText(VIDEO_TYPE + mVideos.get(i).videoType.toUpperCase());
        listHolder.videoReleaseYear.setText(VIDEO_RELEASE_YEAR + mVideos.get(i).videoYear);
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }


    public class MediaListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        String videoId;
        ImageView videoImage;
        TextView videoTitle;
        TextView videoType;
        TextView videoReleaseYear;
        ItemClickListener clickListener;

        MediaListHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            this.clickListener = listener;
            cv = (CardView) itemView.findViewById(R.id.card_view);
            videoImage = (ImageView) itemView.findViewById(R.id.media_image);
            videoTitle = (TextView) itemView.findViewById(R.id.media_title);
            videoType = (TextView) itemView.findViewById(R.id.media_type);
            videoReleaseYear = (TextView) itemView.findViewById(R.id.media_release_year);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            cv.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            clickListener.onClick(v, this);
        }
    }

    /**
     * This method is used to set city background. Glide library is used to download and cache images.
     * For city which do not have any server url, a default image has been set on its view.
     *
     * @param imageView
     */
    private void setVideoImage(final ImageView imageView) {
        imageView.setImageResource(R.drawable.samplemovie);
    }

    public interface ItemClickListener {
        void onClick(View view, MediaListHolder mediaListHolder);
    }

    public interface MediaCardClickListener {
        void onMediaClick(String videoId);
    }
}

