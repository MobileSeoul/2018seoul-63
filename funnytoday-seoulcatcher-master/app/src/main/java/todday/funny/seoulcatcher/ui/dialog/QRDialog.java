package todday.funny.seoulcatcher.ui.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.QRBinding;
import todday.funny.seoulcatcher.interactor.OnQRSuccessListener;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.util.ToastMake;
import todday.funny.seoulcatcher.viewmodel.QRViewModel;

public class QRDialog extends DialogFragment {
    private QRBinding binding;

    public static QRDialog newInstance(User user) {
        Bundle args = new Bundle();
        args.putParcelable(Keys.USER, user);
        QRDialog fragment = new QRDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_qr, container, false);
        if (getArguments() != null) {
            User user = getArguments().getParcelable(Keys.USER);
            QRViewModel model = new QRViewModel(getActivity(), user, binding.cameraSurface);
            binding.setModel(model);
        }
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        if (binding != null && binding.getModel() != null) {
            binding.getModel().destory();
        }
        super.onDestroyView();
    }

}
