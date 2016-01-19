package com.android.media.service.database;

import android.content.Context;

import java.util.List;

public interface ICacheDAO {

    /**
     * Opens writable DB connection.
     */
    public void open();

    /**
     * Closes DB connection.
     */
    public void close();

    /**
     * Creates and inserts CacheEntity.
     *
     * @param requestId request ID.
     * @param content   content of the request.
     * @param timestamp timestamp of the request.
     */
    public void createAndInsertCacheEntity(String requestId, String content, long timestamp);

    /**
     * Deletes CacheEntity by it's request ID.
     *
     * @param requestId request ID.
     */
    public void deleteCacheEntity(String requestId);

    /**
     * Gets all of the cache entities.
     *
     * @return List of CacheEntity.
     */
    public List<CacheEntity> getAllCacheEntities();

    /**
     * Gets cache entity for specific request ID.
     *
     * @param requestId request ID.
     * @return CacheEntity object for given request ID.
     */
    public CacheEntity getCacheEntity(String requestId);

    /**
     * Clears cache table.
     */
    public void deleteAllCacheEntries();

    /**
     * Gets context.
     *
     * @return context.
     */
    public Context getContext();

    /**
     * This method is used to update cache entity.
     *
     * @param requestId
     * @param jsonEntity
     * @param timestamp
     */
    public void updateCacheEntity(String requestId, String jsonEntity, long timestamp);
}
