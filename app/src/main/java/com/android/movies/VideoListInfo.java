package com.android.movies;

/**
 *
 */
public class VideoListInfo {
    String videoTitle;
    String videoYear;
    String videoId;
    String videoType;
    String videoImageURL;

    public VideoListInfo(String title, String year, String id,
                         String type, String poster) {
        this.videoTitle = title;
        this.videoYear = year;
        this.videoId = id;
        this.videoType = type;
        this.videoImageURL = poster;
    }
}
