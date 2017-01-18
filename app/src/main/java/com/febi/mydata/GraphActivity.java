package com.febi.mydata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    protected HorizontalBarChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        mChart = (HorizontalBarChart) findViewById(R.id.id_chart_view);

        initializeGraph(getIntent().getFloatArrayExtra("values"));
    }

    private void initializeGraph(float[] values) {
        mChart.setDrawValueAboveBar(true);
        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setVisibleXRangeMaximum(12);
        mChart.getDescription().setEnabled(false);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);

        YAxis yl = mChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f);

        YAxis yr = mChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f);

        IAxisValueFormatter xAxisFormatter = new MyCustomXAxisValueFormatter();

        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setValueFormatter(xAxisFormatter);

        ArrayList<BarEntry> barEntries      = new ArrayList<>();
        for(int i = 0; i < 12; i++) {
            barEntries.add(new BarEntry(i, values[i]));
        }

        BarDataSet barDataSet               = new BarDataSet(barEntries, "Petrol Consumption");
        ArrayList<IBarDataSet> iBarDataSets = new ArrayList<>();
        iBarDataSets.add(barDataSet);

        BarData barData                     = new BarData(barDataSet);
        barData.setBarWidth(0.9f);
        mChart.setData(barData);
    }

    public class MyCustomXAxisValueFormatter implements IAxisValueFormatter {
        private String[] mMonths = new String[]{"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December"};

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mMonths[(int) value];
        }
    }
}
