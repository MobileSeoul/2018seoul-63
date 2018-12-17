package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.util.Log;

import todday.funny.seoulcatcher.ui.dialog.ScheduleDialog;
import todday.funny.seoulcatcher.ui.dialog.educationDialog.BurnFragmentDialog;
import todday.funny.seoulcatcher.ui.dialog.educationDialog.ColdboomFragmentDialog;
import todday.funny.seoulcatcher.ui.dialog.educationDialog.CollapseFragmentDialog;
import todday.funny.seoulcatcher.ui.dialog.educationDialog.CprFargmentDialog;
import todday.funny.seoulcatcher.ui.dialog.educationDialog.DustboomFragmentDialog;
import todday.funny.seoulcatcher.ui.dialog.educationDialog.EarthboomFragmentDialog;
import todday.funny.seoulcatcher.ui.dialog.educationDialog.FireboomFragmentDialog;
import todday.funny.seoulcatcher.ui.dialog.educationDialog.OneonenineFragmentDialog;
import todday.funny.seoulcatcher.ui.dialog.educationDialog.SubwayboomFragmentDialog;
import todday.funny.seoulcatcher.ui.dialog.educationDialog.SunnyboomFragmentDialog;
import todday.funny.seoulcatcher.ui.dialog.educationDialog.TrafficboomFragmentDialog;
import todday.funny.seoulcatcher.ui.dialog.educationDialog.UsingFireFragmentDialog;
import todday.funny.seoulcatcher.ui.dialog.educationDialog.UsingStifFragmentDialog;

public class EducationViewModel extends BaseViewModel {
    public EducationViewModel(Context context) {
        super(context);
    }

    public void open_cprFragment() {
        Log.e("aaaa","!!!!!!");
        CprFargmentDialog dialog = CprFargmentDialog.newInstance();
        Log.e("aaaa","&^&^&E^&^&^&^");
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }

    public void open_burnFragment() {
        BurnFragmentDialog dialog = BurnFragmentDialog.newInstance();
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }

    public void open_extinguisherFragment() {
        UsingFireFragmentDialog dialog = UsingFireFragmentDialog.newInstance();
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }

    public void open_stiffFragment() {
        UsingStifFragmentDialog dialog = UsingStifFragmentDialog.newInstance();
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }

    public void open_dustFragment() {
        DustboomFragmentDialog dialog = DustboomFragmentDialog.newInstance();
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }

    public void open_earthquakeFragment() {
        EarthboomFragmentDialog dialog = EarthboomFragmentDialog.newInstance();
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }

    public void open_coldboomFragment() {
        ColdboomFragmentDialog dialog = ColdboomFragmentDialog.newInstance();
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }

    public void open_sunnyFragment() {
        SunnyboomFragmentDialog dialog = SunnyboomFragmentDialog.newInstance();
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }

    public void open_caraccidentFragment() {
        TrafficboomFragmentDialog dialog = TrafficboomFragmentDialog.newInstance();
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }

    public void open_subwayFragment() {
        SubwayboomFragmentDialog dialog = SubwayboomFragmentDialog.newInstance();
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }

    public void open_fireaccidentFragment() {
        FireboomFragmentDialog dialog = FireboomFragmentDialog.newInstance();
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }

    public void open_collapseFragment() {
        CollapseFragmentDialog dialog = CollapseFragmentDialog.newInstance();
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }

    public void open_layout_119() {
        OneonenineFragmentDialog dialog = OneonenineFragmentDialog.newInstance();
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }


}
