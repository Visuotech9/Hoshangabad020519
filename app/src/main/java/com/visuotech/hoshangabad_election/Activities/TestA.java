package com.visuotech.hoshangabad_election.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.visuotech.hoshangabad_election.R;

import java.util.ArrayList;

import static android.graphics.Color.*;

public class TestA extends AppCompatActivity {
      PieChart pieChart;
      float male=12;
      float female=60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        pieChart=findViewById(R.id.piechart_1);
        setPieChart();

    }

    public void setPieChart() {
        this.pieChart = pieChart;
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleColor(WHITE);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(male,"Male"));
        yValues.add(new PieEntry(female,"Female"));

        PieDataSet dataSet = new PieDataSet(yValues, "Desease PerRegions");
                dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(YELLOW);
        pieChart.setData(pieData);
        //PieChart Ends Here
    }


}
