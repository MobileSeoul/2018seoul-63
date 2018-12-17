package todday.funny.seoulcatcher.ui.dialog;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.TransitionInflater;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.ImageViewerDialogBinding;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.viewmodel.ImageViewerViewModel;


public class ImageViewerDialog extends DialogFragment {
    public ImageViewerDialogBinding binding;

    public static ImageViewerDialog newInstance(String path) {
        Bundle args = new Bundle();
        args.putString(Keys.PATH, path);
        ImageViewerDialog fragment = new ImageViewerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static ImageViewerDialog newInstance(String path, String transName) {
        Bundle args = new Bundle();
        args.putString(Keys.PATH, path);
        args.putString(Keys.TRANS_NAME, transName);
        ImageViewerDialog fragment = new ImageViewerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_image_viewer, container, false);
        if (getArguments() != null) {
            String path = getArguments().getString(Keys.PATH);
            ImageViewerViewModel model = new ImageViewerViewModel(getActivity(), path);
            binding.setModel(model);
        }
        return binding.getRoot();
    }

}
