package com.android.media.service.web;


import com.android.media.service.base.BaseResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Object that maps to the authentication response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaListResponse extends BaseResponseDTO {
    public Search[] Search;

    @JsonIgnoreProperties(ignoreUnknown = true)
    static public class Search {
        public String Title;
        public String Year;
        public String imdbID;
        public String Type;
        public String Poster;
    }
}
