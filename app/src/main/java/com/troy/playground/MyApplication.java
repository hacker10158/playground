package com.troy.playground;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.troy.playground.di.AppComponent;
import com.troy.playground.di.DaggerAppComponent;
import com.troy.playground.utility.FrescoHelper;
import com.troy.playground.utility.FrescoMemoryCacheSupplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;

public class MyApplication extends DaggerApplication {

    private AppComponent appComponent;
    private List<MemoryTrimmable> memoryTrimmables;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupFresco();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

    private void setupFresco() {

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(5, 25, TimeUnit.SECONDS))
                //.addNetworkInterceptor(netInterceptor)
                .retryOnConnectionFailure(false)
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();

        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(getApplicationContext())
                .setMaxCacheSize(100 * ByteConstants.MB)//100MB
                .build();
        DiskCacheConfig smallImageDiskCacheConfig = DiskCacheConfig.newBuilder(getApplicationContext())
                .setMaxCacheSize(20 * ByteConstants.MB)//20MB
                .build();

        MemoryTrimmableRegistry registry = new MemoryTrimmableRegistry() {
            @Override
            public void registerMemoryTrimmable(MemoryTrimmable trimmable) {

                if (memoryTrimmables == null) {
                    memoryTrimmables = new ArrayList<>();
                }

                if (!memoryTrimmables.contains(trimmable)) {
                    memoryTrimmables.add(trimmable);
                }
            }

            @Override
            public void unregisterMemoryTrimmable(MemoryTrimmable trimmable) {
                if (memoryTrimmables == null) {
                    return;
                }

                if (memoryTrimmables.contains(trimmable)) {
                    memoryTrimmables.remove(trimmable);
                }
            }
        };

        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(getApplicationContext(), client)
                .setDownsampleEnabled(true)
                .setResizeAndRotateEnabledForNetwork(true)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setSmallImageDiskCacheConfig(smallImageDiskCacheConfig)
                .setBitmapMemoryCacheParamsSupplier(new FrescoMemoryCacheSupplier((ActivityManager)getSystemService(ACTIVITY_SERVICE)))
                .setMemoryTrimmableRegistry(registry)
                .build();
        Fresco.initialize(this, config);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        FrescoHelper.clearMemoryCaches();
    }
}
