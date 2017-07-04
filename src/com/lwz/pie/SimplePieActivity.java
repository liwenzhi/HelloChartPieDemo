package com.lwz.pie;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.lwz.chart.hellocharts.listener.PieChartOnValueSelectListener;
import com.lwz.chart.hellocharts.model.PieChartData;
import com.lwz.chart.hellocharts.model.SliceValue;
import com.lwz.chart.hellocharts.util.ChartUtils;
import com.lwz.chart.hellocharts.view.PieChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通饼状图的展示
 */
public class SimplePieActivity extends Activity {

    private PieChartView pieChart;     //饼状图View
    private PieChartData data;         //存放数据

    private boolean hasLabels = true;                   //是否有标语
    private boolean hasLabelsOutside = false;           //扇形外面是否有标语
    private boolean hasCenterCircle = false;            //是否有中心圆
    private boolean hasCenterText1 = false;             //是否有中心的文字
    private boolean hasCenterText2 = false;             //是否有中心的文字2
    private boolean isExploded = false;                  //是否是炸开的图像
    private boolean hasLabelForSelected = false;         //选中的扇形显示标语

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_pie_activity);
        String title = getIntent().getStringExtra("title");
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("" + title);
        initView();
        initEvent();
        initData();
    }

    private void initView() {
        pieChart = (PieChartView) findViewById(R.id.pieChart);

    }

    private void initEvent() {
        pieChart.setOnValueTouchListener(new ValueTouchListener());

    }

    private void initData() {
        generateData();

    }


    /**
     * 配置数据
     */
    private void generateData() {
        int numValues = 6;   //扇形的数量

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
            values.add(sliceValue);
        }

        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);

        if (isExploded) {
            data.setSlicesSpacing(24);
        }

        if (hasCenterText1) {
            data.setCenterText1("Hello!");

            // Get roboto-italic font.    //Typeface是用来设置字体的!
            Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Italic.ttf");
            data.setCenterText1Typeface(tf);

            // Get font size from dimens.xml and convert it to sp(library uses sp values).
            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
        }

        if (hasCenterText2) {
            data.setCenterText2("Charts (Roboto Italic)");

            Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Italic.ttf");

            data.setCenterText2Typeface(tf);
            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    (int) getResources().getDimension(R.dimen.pie_chart_text2_size)));
        }

        pieChart.setPieChartData(data);
    }


    /**
     * 点击监听
     */
    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            Toast.makeText(SimplePieActivity.this, "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {

        }

    }

}
