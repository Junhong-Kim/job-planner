package com.kimjunhong.jobplanner.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kimjunhong.jobplanner.R;
import com.kimjunhong.jobplanner.activity.ChartActivity;
import com.kimjunhong.jobplanner.item.ChartDetailItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by INMA on 2017. 6. 20..
 */

public class DocumentChartFragment extends Fragment implements OnChartValueSelectedListener {
    @BindView(R.id.pieChart_document) PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_document_chart, container, false);

        ButterKnife.bind(this, view);

        initChart();

        return view;
    }

    private void initChart() {
        // 차트 데이터
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(40, "합격"));
        entries.add(new PieEntry(60, "불합격"));

        // 차트 설정
        int[] colors = {ContextCompat.getColor(getActivity(), R.color.positive),
                ContextCompat.getColor(getActivity(), R.color.negative)};

        PieDataSet dataSet = new PieDataSet(entries, "서류");
        dataSet.setColors(ColorTemplate.createColors(colors));

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterText(generateCenterSpannableText());
        pieChart.setRotationEnabled(false);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setOnChartValueSelectedListener(this);
    }

    private List<ChartDetailItem> dummyData(String result) {
        List<ChartDetailItem> items = new ArrayList<>();
        ChartDetailItem[] item = new ChartDetailItem[1];

        item[0] = new ChartDetailItem(R.drawable.icon_company_logo, "Company", "Android Developer", result, "17.06.20");

        for(int i = 0; i < item.length; i++) {
            items.add(item[i]);
        }

        return items;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null) {
            return;
        } else if (e.getY() == 40) {
            Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_LONG).show();
            ((ChartActivity) ChartActivity.context).initRecyclerView(dummyData("합격"));
        } else {
            Toast.makeText(getActivity(), String.valueOf(e), Toast.LENGTH_LONG).show();
            ((ChartActivity) ChartActivity.context).initRecyclerView(dummyData("불합격"));
        }

        Log.i("VAL SELECTED", "Value: " + e.getY() + ", index: " + h.getX() + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("JobPlanner\ndeveloped by Junhong Kim");

        s.setSpan(new RelativeSizeSpan(1.5f), 0, 10, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 10, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 10, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.65f), 10, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 15, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 15, s.length(), 0);

        return s;
    }
}
