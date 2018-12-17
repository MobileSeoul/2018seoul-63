package todday.funny.seoulcatcher.ui.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import todday.funny.seoulcatcher.R;

public class LodingDialog  extends ProgressDialog{

    private ImageView imageView;

    public LodingDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
    }

    public LodingDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding_dialog);
        init();
    }

    private void init(){

        imageView = findViewById(R.id.loginDialog_imageView);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.loding);
        imageView.setAnimation(animation);
    }
}
