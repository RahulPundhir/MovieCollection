package com.android.media.service.web;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.media.service.ServiceLayerModule;
import com.android.media.service.WebConstants;
import com.android.media.service.base.JsonMapper;
import com.android.media.service.database.CacheEntity;
import com.android.media.service.database.ICacheDAO;
import com.android.media.service.network.NetworkStateManager;
import com.android.media.service.util.CopyingInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 */
public class VideoWebRequest {
    public static final String VIDEO_PARAMETER = "i=";
    private ICacheDAO mCacheDB;
    private NetworkStateManager mNewNetworkStateManager;
    private String mParam;
    private boolean isNetworkAvailable;
    private CopyingInputStream mInputStream;
    private String mURL;
    private ServiceCallback mCallback;

    public VideoWebRequest(Context context) {
        this.mCacheDB = ServiceLayerModule.getInstance(context).providesCacheDAO();
        this.mNewNetworkStateManager = ServiceLayerModule.getInstance(context).providesNetworkStateManage();
    }

    /**
     * This method is used to send web request.
     *
     * @param param
     */
    public void sendRequest(String param) {
        this.mParam = param;
        mInputStream = null;
        mURL = null;
        new WebRequest().execute();
    }

    /**
     *
     */
    public class WebRequest extends AsyncTask<String, Void, Void> {
        VideoResponse cachedData;
        VideoResponse mResponse;

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(String... params) {
            isNetworkAvailable = false;
            mURL = createURL(mParam);
            cachedData = getCachedData();
            if (mNewNetworkStateManager.isNetworkAvailable()) {
                isNetworkAvailable = true;
                InputStream in = openHttpConnection(mURL);
                try {
                    mResponse = parse(in);
                    Log.i("Response", "Response = " + new String(mInputStream.getCopiedBytes()));
                    saveIntoDB(mInputStream.getCopiedBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (null != mResponse) {
                if (null != mResponse.Response && mResponse.Response.equalsIgnoreCase(WebConstants.FALSE)) {
                    mCallback.onError(mResponse.Error);
                } else {
                    mCallback.onSuccess(mResponse);
                }
            } else if (!isNetworkAvailable) {
                mCallback.onNetworkError(cachedData, WebConstants.NETWORK_ISSUE);
            } else {
                mCallback.onError(WebConstants.SOME_TECHNICAL_PROBLEM);
            }
        }

    }

    /**
     * This function is used to make web request.
     *
     * @param webUrl
     * @return
     */
    private InputStream openHttpConnection(String webUrl) {
        int resCode = -1;
        try {
            URL url = new URL(webUrl);
            URLConnection urlConn = url.openConnection();

            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException(WebConstants.URL_IS_NOT_AN_HTTP_URL);
            }
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod(WebConstants.REQUEST_METHOD);
            httpConn.connect();
            resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                mInputStream = new CopyingInputStream(httpConn.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mInputStream;
    }

    /**
     * This method is used to parse json file and return parsed response.
     *
     * @param in
     * @return
     * @throws Exception
     */
    public VideoResponse parse(InputStream in) throws Exception {
        VideoResponse data = fromJSON(VideoResponse.class, in);
        return data;
    }

    /**
     * Function is used to provide parsed response.
     *
     * @param clazz
     * @param in
     * @return
     */
    public static VideoResponse fromJSON(Class clazz,
                                             final InputStream in) {
        VideoResponse data = null;
        try {
            data = JsonMapper.getObjectMapper().readValue(in, JsonMapper.getObjectMapper().constructType(clazz));
        } catch (Exception e) {
            // Handle the problem
            e.printStackTrace();
        }
        return data;
    }

    private String createURL(String param) {
        if (param.contains(WebConstants.SPACE)) {
            param = param.trim();
        }
        return WebConstants.SERVER_URL + VIDEO_PARAMETER + param + WebConstants.PLOT_SHORT_R_JSON;
    }

    /**
     * This method saved response in DB.
     * @param data
     */
    private void saveIntoDB(byte[] data) {
        mCacheDB.open();
        CacheEntity cacheEntity = mCacheDB.getCacheEntity(mURL);
        if (cacheEntity == null) {
            mCacheDB.createAndInsertCacheEntity(mURL, new String(data), System.currentTimeMillis());
        } else {
            mCacheDB.updateCacheEntity(mURL, new String(data), System.currentTimeMillis());
        }
        mCacheDB.close();
    }

    /**
     * This method returns response from DB.
     * @return
     */
    private VideoResponse getCachedData() {
        mCacheDB.open();
        //retrieve data from cache.
        CacheEntity entity = mCacheDB.getCacheEntity(mURL);
        if (entity != null) {
            String cachedContent = entity.getContent();
            Log.d("CachedContent", cachedContent);
            try {
                InputStream stream = new ByteArrayInputStream(cachedContent.getBytes("UTF-8"));
                VideoResponse data = parse(stream);
                return data;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mCacheDB.close();
        return null;
    }

    public void setCallback(ServiceCallback callback) {
        this.mCallback = callback;
    }

    /**
     * Callback to provide service callback.
     */
    public interface ServiceCallback {
        void onSuccess(VideoResponse data);

        void onError(String error);

        void onNetworkError(VideoResponse data, String error);
    }
}
