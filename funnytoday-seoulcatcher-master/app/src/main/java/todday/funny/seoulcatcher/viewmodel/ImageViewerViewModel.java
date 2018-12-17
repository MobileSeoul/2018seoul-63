package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

public class ImageViewerViewModel extends BaseViewModel {
    public ObservableField<String> mPath = new ObservableField<>();

    public ImageViewerViewModel(Context context, String path) {
        super(context);
        mPath.set(path);
    }
}
