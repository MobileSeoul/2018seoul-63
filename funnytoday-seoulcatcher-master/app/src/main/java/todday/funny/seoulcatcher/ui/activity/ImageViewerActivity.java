package todday.funny.seoulcatcher.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.ImageViewerBinding;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.viewmodel.ImageViewerViewModel;

public class ImageViewerActivity extends AppCompatActivity {
    private ImageViewerBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_viewer);

        String path = getIntent().getStringExtra(Keys.PATH);
        String transName = getIntent().getStringExtra(Keys.TRANS_NAME);

        ImageViewerViewModel model = new ImageViewerViewModel(this, path);
        binding.setModel(model);

        ViewCompat.setTransitionName(binding.imagePhoto, transName);
    }
}
