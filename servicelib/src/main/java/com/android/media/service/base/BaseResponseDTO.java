package com.android.media.service.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Object that maps to the base response structure.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponseDTO {
    public String Error;
    public String Response;
}
