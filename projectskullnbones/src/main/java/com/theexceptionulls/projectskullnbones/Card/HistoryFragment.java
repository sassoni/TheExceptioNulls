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
    private PieChart savingsPieChart;

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
        savingsPieChart = (PieChart) view.findViewById(R.id.savings_pie_chart);
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
                barChartMonth.setDescription("");
                barChart.setVisibility(View.GONE);
                barChartMonthHelpText.setText(getResources().getStringArray(R.array.months)[entry.getXIndex()]+" Purchase");
            }

            @Override
            public void onNothingSelected() {

            }
        });

        setBarChart();
        setBarChartMonth();
        setLineChart();
        setPieChart();
        setSavingsPieChart();

        return view;
    }

    private void setLineChart(){
        lineChart.setDescription("Savings Across 12 Months");
        lineChart.setDescriptionTextSize(20);
        lineChart.setDrawYValues(true);
        lineChart.setValueTypeface(Typeface.DEFAULT);
        lineChart.setUnit(" $");

        ArrayList<String> xValues = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.months)));

        ArrayList<Entry> userValues = new ArrayList<>();
        userValues.add(new Entry(29.69f, 0));
        userValues.add(new Entry(5.47f, 1));
        userValues.add(new Entry(26.95f, 2));
        userValues.add(new Entry(46.02f, 3));
        userValues.add(new Entry(32.3f, 4));
        userValues.add(new Entry(6.08f, 5));
        userValues.add(new Entry(71.46f, 6));
        userValues.add(new Entry(39.53f, 7));
        userValues.add(new Entry(31.68f, 8));
        userValues.add(new Entry(64.42f, 9));
        userValues.add(new Entry(46.02f, 10));
        userValues.add(new Entry(39.85f, 11));

        ArrayList<Entry> otherValues = new ArrayList<>();
        otherValues.add(new Entry(22.46f, 0));
        otherValues.add(new Entry(4.25f, 1));
        otherValues.add(new Entry(20.98f, 2));
        otherValues.add(new Entry(35.74f, 3));
        otherValues.add(new Entry(28.55f, 4));
        otherValues.add(new Entry(2.11f, 5));
        otherValues.add(new Entry(56.33f, 6));
        otherValues.add(new Entry(35.74f, 7));
        otherValues.add(new Entry(24.85f, 8));
        otherValues.add(new Entry(42.42f, 9));
        otherValues.add(new Entry(39.14f, 10));
        otherValues.add(new Entry(29.82f, 11));

        ArrayList<LineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet userDataSet = new LineDataSet(userValues, "Your Savings");
        userDataSet.setCircleColor(Color.parseColor("#FE1930"));
        userDataSet.setCircleSize(5);
        userDataSet.setLineWidth(2);
        userDataSet.setColor(Color.parseColor("#F5AB2A"));
        lineDataSets.add(userDataSet);

        LineDataSet othersDataSet = new LineDataSet(otherValues, "Others Savings");
        othersDataSet.setCircleColor(Color.parseColor("#68F3E8"));
        othersDataSet.setCircleSize(5);
        othersDataSet.setLineWidth(2);
        othersDataSet.setColor(Color.parseColor("#3FFE19"));
        lineDataSets.add(othersDataSet);

        LineData lineData = new LineData(xValues, lineDataSets);
        lineChart.setData(lineData);
    }

    private void setBarChart(){
        barChart.setUnit(" $");
        barChart.setDrawYValues(false);
        barChart.setDescription("");

        ArrayList<String> xValues = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.months)));

        ArrayList<BarEntry> yValues = new ArrayList<>();
        yValues.add(new BarEntry(new float[]{29.69f, 166.81f}, 0));
        yValues.add(new BarEntry(new float[]{5.47f, 68.97f}, 1));
        yValues.add(new BarEntry(new float[]{26.95f, 207.63f}, 2));
        yValues.add(new BarEntry(new float[]{46.02f, 309.62f}, 3));
        yValues.add(new BarEntry(new float[]{32.30f, 294.16f}, 4));
        yValues.add(new BarEntry(new float[]{6.08f, 54.11f}, 5));
        yValues.add(new BarEntry(new float[]{71.46f, 417.9f}, 6));
        yValues.add(new BarEntry(new float[]{39.53f, 295.68f}, 7));
        yValues.add(new BarEntry(new float[]{31.68f, 246.76f}, 8));
        yValues.add(new BarEntry(new float[]{64.42f, 397.20f}, 9));
        yValues.add(new BarEntry(new float[]{46.02f, 326.75f}, 10));
        yValues.add(new BarEntry(new float[]{39.85f, 217.38f}, 11));

        BarDataSet barDataSet = new BarDataSet(yValues, "Expenditure per Month");
        barDataSet.setBarShadowColor(Color.TRANSPARENT);
        barDataSet.setStackLabels(new String[]{"Without Savings","With Savings"});

        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(Color.parseColor("#FF6900"));
        integers.add(Color.parseColor("#F1D54E"));
        barDataSet.setColors(integers);

        BarData barData = new BarData(xValues, barDataSet);
        barChart.setData(barData);
    }

    private void setBarChartMonth(){
        barChartMonth.setDescriptionTextSize(20);
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
        barDataSet.setColor(Color.parseColor("#2AE8F5"));

        BarData barData = new BarData(xValues, barDataSet);
        barChartMonth.setData(barData);
    }

    private void setPieChart(){
        pieChart.setDescription("");
        pieChart.setUnit(" %");
        pieChart.setDescriptionTextSize(16);
        pieChart.setDrawYValues(true);
        pieChart.setValueTypeface(Typeface.DEFAULT);
        pieChart.setRotationEnabled(false);
     
        final ArrayList<String> xValues = new ArrayList<>();
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
        pieDataSet.setSliceSpace(3);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#008245"));
        colors.add(Color.parseColor("#E6266C"));
        colors.add(Color.parseColor("#B1262D"));
        colors.add(Color.parseColor("#274A80"));
        colors.add(Color.parseColor("#FF6900"));
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(xValues, pieDataSet);
        pieChart.setData(pieData);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i) {
                pieChart.setCenterText(xValues.get(entry.getXIndex())+" - "+getResources().getStringArray(R.array.category_purchase_piechart_data)[entry.getXIndex()]);
            }

            @Override
            public void onNothingSelected() {
                pieChart.setCenterText("");
            }
        });
    }

    private void setSavingsPieChart(){

        savingsPieChart.setDescription("Used vs Un-used savings");
        savingsPieChart.setDescriptionTextSize(20);
        savingsPieChart.setDrawYValues(true);
        savingsPieChart.setValueTypeface(Typeface.DEFAULT);
        savingsPieChart.setHoleColor(Color.rgb(235, 235, 235));
        savingsPieChart.setUnit(" %");
        savingsPieChart.setDrawUnitsInChart(true);
        savingsPieChart.setRotationEnabled(false);

        final ArrayList<String> xValues = new ArrayList<>();
        xValues.add("Used");
        xValues.add("Un-used");

        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(82,0));
        yValues.add(new Entry(18,1));

        PieDataSet pieDataSet = new PieDataSet(yValues, "Categories");
        pieDataSet.setSliceSpace(3);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#A5DA00"));
        colors.add(Color.parseColor("#FF6900"));
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(xValues, pieDataSet);
        savingsPieChart.setData(pieData);

        savingsPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i) {
                savingsPieChart.setCenterText(xValues.get(entry.getXIndex())+" - "+"$250");
            }

            @Override
            public void onNothingSelected() {
                savingsPieChart.setCenterText("");
            }
        });

    }

}
