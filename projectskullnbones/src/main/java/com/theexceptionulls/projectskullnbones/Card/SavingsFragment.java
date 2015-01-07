package com.theexceptionulls.projectskullnbones.Card;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.theexceptionulls.projectskullnbones.R;

import java.util.ArrayList;

public class SavingsFragment extends Fragment {

    private LineChart lineChart;
    private BarChart barChart;
    private PieChart pieChart;

    private LineData lineData;

    public SavingsFragment() {

    }

    public static final SavingsFragment newInstance(Bundle bundle) {
        SavingsFragment fragment = new SavingsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.savings_fragment, container, false);

        lineChart = (LineChart) view.findViewById(R.id.line_chart);
        lineChart.setDescription("Line Chart");
        lineChart.setDrawYValues(true);
        lineChart.setValueTypeface(Typeface.DEFAULT);

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("1");
        xVals.add("2");
        xVals.add("3");
        xVals.add("4");

        ArrayList<Entry> entryListOne = new ArrayList<>();
        ArrayList<Entry> entryListTwo = new ArrayList<>();

        Entry entryOne = new Entry(100.00f, 0);
        Entry entryTwo = new Entry(50.00f, 1);
        entryListOne.add(entryOne);
        entryListOne.add(entryTwo);

        Entry entryOneOne = new Entry(120.00f, 0);
        Entry entryTwoTwo = new Entry(110.00f, 1);
        entryListTwo.add(entryOneOne);
        entryListTwo.add(entryTwoTwo);

        ArrayList<LineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(new LineDataSet(entryListOne, "Company 1"));
        lineDataSets.add(new LineDataSet(entryListTwo, "Company 2"));

        lineData = new LineData(xVals, lineDataSets);
        lineChart.setData(lineData);

        barChart = (BarChart) view.findViewById(R.id.bar_chart);
        barChart.setDescription("Bar Chart");
        barChart.setDrawYValues(true);
        barChart.setValueTypeface(Typeface.DEFAULT);

        pieChart = (PieChart) view.findViewById(R.id.pie_chart);
        pieChart.setDescription("Pie Chart");
        pieChart.setDrawYValues(true);
        pieChart.setValueTypeface(Typeface.DEFAULT);

        return view;
    }
}
