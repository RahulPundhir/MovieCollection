package com.android.media.service;


import android.content.Context;

import com.android.media.service.database.CacheDAO;
import com.android.media.service.database.ICacheDAO;
import com.android.media.service.network.NetworkStateManager;


public class ServiceLayerModule {

    private static ServiceLayerModule mServiceLayerModule;
    private final Context mContext;
    private ICacheDAO mCacheDAO;
    private NetworkStateManager mNetworkStateManager;

    private ServiceLayerModule(Context context) {
        super();
        this.mContext = context;
        createCacheDAO();
        createNetworkStateManage();
    }

    public static ServiceLayerModule getInstance(Context context) {
        if (mServiceLayerModule == null) {
            mServiceLayerModule = new ServiceLayerModule(context);
        }
        return mServiceLayerModule;
    }

    public void createCacheDAO() {
        mCacheDAO = new CacheDAO(mContext);
    }

    public ICacheDAO providesCacheDAO() {
        return mCacheDAO;
    }

    public void createNetworkStateManage() {
        mNetworkStateManager = new NetworkStateManager(mContext);
    }

    public NetworkStateManager providesNetworkStateManage() {
        return mNetworkStateManager;
    }
}
