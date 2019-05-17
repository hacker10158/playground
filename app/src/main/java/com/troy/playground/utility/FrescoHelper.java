package com.troy.playground.utility;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class FrescoHelper {

    public static void loadInto(@NonNull String photoUrl, @NonNull final SimpleDraweeView simpleDraweeView) {
        loadInto(photoUrl, simpleDraweeView, null); // default landscape
    }

    public static void loadInto(@NonNull String photoUrl, @NonNull final SimpleDraweeView simpleDraweeView, int w, int h) {
        loadInto(photoUrl, simpleDraweeView, new ResizeOptions(w, h));
    }
    public static void loadInto(@NonNull String photoUrl, @NonNull final SimpleDraweeView simpleDraweeView, ResizeOptions resizeOptions) {
        if (TextUtils.isEmpty(photoUrl)) {
            Log.w("photoUrl empty" + photoUrl);
//            loadInto(R.drawable.default_screen_image, simpleDraweeView);
            return;
        }

        Uri uri = Uri.parse(photoUrl);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(resizeOptions)
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .setTapToRetryEnabled(true)
                .build();

        simpleDraweeView.setController(controller);
    }

    // load resource file
    public static void loadInto(int resourceId, @NonNull final SimpleDraweeView simpleDraweeView) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(resourceId).build();
        simpleDraweeView.setImageURI(imageRequest.getSourceUri());
    }

    public synchronized static void clearMemoryCaches() {
        try {
            Fresco.getImagePipeline().clearMemoryCaches();
        } catch (Exception e) {
            // may produced from leak canary
        }
    }
}
