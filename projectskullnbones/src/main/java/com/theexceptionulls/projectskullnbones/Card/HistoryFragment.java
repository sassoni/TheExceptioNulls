package com.theexceptionulls.projectskullnbones.Card;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.theexceptionulls.projectskullnbones.R;

import java.util.ArrayList;
import java.util.Arrays;

public class HistoryFragment extends Fragment {

    private LineChart lineChart;
    private BarChart barChart;
    private PieChart pieChart;
    private BarChart barChartMonth;
    private Button back;
    private TextView barChartMonthHelpText;

    public HistoryFragment() {

    }

    public static final HistoryFragment newInstance(Bundle bundle) {
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);

        lineChart = (LineChart) view.findViewById(R.id.line_chart);
        barChart = (BarChart) view.findViewById(R.id.bar_chart);
        barChartMonth = (BarChart) view.findViewById(R.id.bar_chart_month);
        pieChart = (PieChart) view.findViewById(R.id.pie_chart);
        barChartMonthHelpText = (TextView) view.findViewById(R.id.bar_chart_month_help);

        back = (Button) view.findViewById(R.id.bar_chart_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setVisibility(View.GONE);
                barChartMonth.setVisibility(View.GONE);
                barChart.setVisibility(View.VISIBLE);
                barChartMonthHelpText.setVisibility(View.VISIBLE);
            }
        });

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i) {
                back.setVisibility(View.VISIBLE);
                barChartMonth.setVisibility(View.VISIBLE);
                barChartMonth.setDescription(getResources().getStringArray(R.array.months)[i]+" Purchase");
                barChart.setVisibility(View.GONE);
                barChartMonthHelpText.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        setBarChart();
        setBarChartMonth();
        setLineChart();
        setPieChart();

        return view;
    }

    private void setLineChart(){
        lineChart.setDescription("Purchase Across 12 Months");
        lineChart.setDrawYValues(true);
        lineChart.setValueTypeface(Typeface.DEFAULT);
        lineChart.setUnit(" $");

        ArrayList<String> xValues = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.months)));

        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(598, 0));
        yValues.add(new Entry(567, 1));
        yValues.add(new Entry(500, 2));
        yValues.add(new Entry(621, 3));
        yValues.add(new Entry(569, 4));
        yValues.add(new Entry(354, 5));
        yValues.add(new Entry(422, 6));
        yValues.add(new Entry(254, 7));
        yValues.add(new Entry(287, 8));
        yValues.add(new Entry(460, 9));
        yValues.add(new Entry(578, 10));
        yValues.add(new Entry(568, 11));

        LineDataSet lineDataSet = new LineDataSet(yValues, "Expenditure/Month");
        lineDataSet.setCircleColor(Color.BLUE);
        lineDataSet.setCircleSize(5);
        lineDataSet.setLineWidth(2);
        lineDataSet.setColor(Color.GREEN);
        LineData lineData = new LineData(xValues, lineDataSet);
        lineChart.setData(lineData);
    }

    private void setBarChart(){
        barChart.setDescription("Purchase Across 12 Months");
        barChart.setDrawYValues(true);
        barChart.setValueTypeface(Typeface.DEFAULT);
        barChart.setUnit(" $");

        ArrayList<String> xValues = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.months)));

        ArrayList<BarEntry> yValues = new ArrayList<>();
        yValues.add(new BarEntry(598, 0));
        yValues.add(new BarEntry(567, 1));
        yValues.add(new BarEntry(500, 2));
        yValues.add(new BarEntry(621, 3));
        yValues.add(new BarEntry(569, 4));
        yValues.add(new BarEntry(354, 5));
        yValues.add(new BarEntry(422, 6));
        yValues.add(new BarEntry(254, 7));
        yValues.add(new BarEntry(287, 8));
        yValues.add(new BarEntry(460, 9));
        yValues.add(new BarEntry(578, 10));
        yValues.add(new BarEntry(568, 11));

        BarDataSet barDataSet = new BarDataSet(yValues, "Expenditure/Month");
        barDataSet.setBarShadowColor(Color.TRANSPARENT);
        barDataSet.setColor(Color.GREEN);

        BarData barData = new BarData(xValues, barDataSet);
        barChart.setData(barData);
    }

    private void setBarChartMonth(){
        barChartMonth.setDrawYValues(true);
        barChartMonth.setValueTypeface(Typeface.DEFAULT);
        barChartMonth.setUnit(" $");

        ArrayList<String> xValues = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.days)));

        ArrayList<BarEntry> yValues = new ArrayList<>();
        yValues.add(new BarEntry(132, 5));
        yValues.add(new BarEntry(265, 16));
        yValues.add(new BarEntry(165, 25));

        BarDataSet barDataSet = new BarDataSet(yValues, "Expenditure this Month");
        barDataSet.setBarShadowColor(Color.TRANSPARENT);
        barDataSet.setColor(Color.MAGENTA);

        BarData barData = new BarData(xValues, barDataSet);
        barChartMonth.setData(barData);
    }

    private void setPieChart(){
        pieChart.setDescription("Purchase by category");
        pieChart.setDrawYValues(true);
        pieChart.setValueTypeface(Typeface.DEFAULT);
        pieChart.animateXY(1500, 1500);
     
        ArrayList<String> xValues = new ArrayList<>();
        xValues.add("Baking");
        xValues.add("Produce");
        xValues.add("Diary");
        xValues.add("Beverages");
        xValues.add("Frozen");

        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(20,0));
        yValues.add(new Entry(40,1));
        yValues.add(new Entry(20,2));
        yValues.add(new Entry(10,3));
        yValues.add(new Entry(10,4));

        PieDataSet pieDataSet = new PieDataSet(yValues, "Categories");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(xValues, pieDataSet);
        pieChart.setData(pieData);
    }

}
