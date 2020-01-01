package com.survey.surveyapp.activities.vendor_flow;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.survey.surveyapp.R;
import com.survey.surveyapp.activities.user_flow.UserBaseActivity;
import com.survey.surveyapp.custom.DayAxisValueFormatter;
import com.survey.surveyapp.custom.MyValueFormatter;
import com.survey.surveyapp.custom.XYMarkerView;
import com.survey.surveyapp.databinding.ActivityVendorSurveyDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class ActivityVendorSurveyDetail extends UserBaseActivity implements OnChartValueSelectedListener {

    ActivityVendorSurveyDetailBinding mActivityVendorSurveyDetailBinding;

    protected final String[] parties = new String[]{
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityVendorSurveyDetailBinding = (ActivityVendorSurveyDetailBinding) putContentView(R.layout.activity_vendor_survey_detail);

        initToolbar();
        mTextViewTitle.setText("Survey Detail");

        //region Set Pie Chart Settings and Data
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setUsePercentValues(true);
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.getDescription().setEnabled(false);
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setExtraOffsets(5, 10, 5, 5);

        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setDragDecelerationFrictionCoef(0.95f);

        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setCenterText(getResources().getString(R.string.app_name));

        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setDrawHoleEnabled(false);
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setHoleColor(Color.WHITE);

        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setTransparentCircleColor(Color.WHITE);
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setTransparentCircleAlpha(110);

        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setHoleRadius(58f);
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setTransparentCircleRadius(61f);

        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setDrawCenterText(false);

        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setRotationAngle(0);
        // enable rotation of the chart by touch
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setRotationEnabled(true);
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.setOnChartValueSelectedListener(this);

        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setEntryLabelColor(Color.WHITE);
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setEntryLabelTextSize(12f);

        setData(4, 4);
        //endregion

        //region Set Bar Chart Settings and Data
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.setOnChartValueSelectedListener(this);

        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.setDrawBarShadow(false);
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.setDrawValueAboveBar(true);

        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.setPinchZoom(false);

        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView);

        XAxis xAxis = mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        ValueFormatter custom = new MyValueFormatter("$");

        YAxis leftAxis = mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend mLegend = mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.getLegend();
        mLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        mLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        mLegend.setDrawInside(false);
        mLegend.setForm(Legend.LegendForm.SQUARE);
        mLegend.setFormSize(9f);
        mLegend.setTextSize(11f);
        mLegend.setXEntrySpace(4f);

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView); // For bounds control
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.setMarker(mv);

        setBarData(12, 50);
        //endregion

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void setData(int count, float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) ((Math.random() * range) + range / 5),
                    parties[i % parties.length],
                    getResources().getDrawable(R.drawable.star)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.setData(data);

        // undo all highlights
        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.highlightValues(null);

        mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailPieGraphView.invalidate();
    }

    private void setBarData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (Math.random() * (range + 1));

            if (Math.random() * 100 < 25) {
                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                values.add(new BarEntry(i, val));
            }
        }

        BarDataSet set1;

        if (mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.getData() != null &&
                mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.getData().notifyDataChanged();
            mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values, "The year 2017");

            set1.setDrawIcons(false);

//            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            /*int startColor = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor = ContextCompat.getColor(this, android.R.color.holo_blue_bright);
            set1.setGradientColor(startColor, endColor);*/

            int startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light);
            int startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light);
            int endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple);
            int endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark);
            int endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark);
            int endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark);

            List<GradientColor> gradientColors = new ArrayList<>();
            gradientColors.add(new GradientColor(startColor1, endColor1));
            gradientColors.add(new GradientColor(startColor2, endColor2));
            gradientColors.add(new GradientColor(startColor3, endColor3));
            gradientColors.add(new GradientColor(startColor4, endColor4));
            gradientColors.add(new GradientColor(startColor5, endColor5));

            set1.setGradientColors(gradientColors);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);

            mActivityVendorSurveyDetailBinding.activityVendorSurveyDetailBarGraphView.setData(data);
        }
    }

}
