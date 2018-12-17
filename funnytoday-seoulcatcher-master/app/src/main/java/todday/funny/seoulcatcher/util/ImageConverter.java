package todday.funny.seoulcatcher.util;

import android.content.Context;
import android.graphics.Bitmap;


import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import todday.funny.seoulcatcher.GlideApp;

public class ImageConverter {


    public static Single<Bitmap> stringToBitmap(final Context context, final String url) {
        return Single.create(new SingleOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(SingleEmitter<Bitmap> emitter) throws Exception {
                try {
                    Bitmap bitmap = GlideApp.with(context).asBitmap().load(url).submit(1024, 1024).get();
                    emitter.onSuccess(bitmap);
                } catch (Exception e) {
                    emitter.onError(e);
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
