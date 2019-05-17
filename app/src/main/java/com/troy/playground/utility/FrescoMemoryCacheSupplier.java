package com.troy.playground.utility;

import android.app.ActivityManager;

import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.cache.MemoryCacheParams;

public class FrescoMemoryCacheSupplier implements Supplier<MemoryCacheParams> {
    private ActivityManager activityManager;
    private static final int MAX_CACHE_ENTRIES = 64;
    private static final int MAX_CACHE_EVICTION_SIZE = 5;
    private static final int MAX_CACHE_EVICTION_ENTRIES = 10;

    public FrescoMemoryCacheSupplier(ActivityManager activityManager) {
        this.activityManager = activityManager;
    }

    @Override
    public MemoryCacheParams get() {
        return new MemoryCacheParams(
                getMaxCacheSize(),
                MAX_CACHE_ENTRIES,
                MAX_CACHE_EVICTION_SIZE,
                MAX_CACHE_EVICTION_ENTRIES,
                10);
    }

    private int getMaxCacheSize() {
        final int maxMemory = Math.min(activityManager.getMemoryClass() * ByteConstants.MB, Integer.MAX_VALUE);

        if (maxMemory < 32 * ByteConstants.MB) {
            return 4 * ByteConstants.MB;
        } else if (maxMemory < 64 * ByteConstants.MB) {
            return 6 * ByteConstants.MB;
        } else {
            return 16 * ByteConstants.MB;
        }
    }
}