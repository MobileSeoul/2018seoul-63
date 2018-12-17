package todday.funny.seoulcatcher.ui.fragment;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.HashMap;
import java.util.Locale;

import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.EducationBinding;
import todday.funny.seoulcatcher.util.HeartSpeech;
import todday.funny.seoulcatcher.viewmodel.EducationViewModel;

public class EducationFragment extends Fragment {

    private EducationBinding binding = null;
    private EducationViewModel model = null;


    public static EducationFragment newInstance() {
        Bundle args = new Bundle();
        EducationFragment fragment = new EducationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_education, container, false);
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            model = ((BaseActivity) getActivity()).getEducationViewModel();
            binding.setModel(model);
        }
        return binding.getRoot();
    }

}
