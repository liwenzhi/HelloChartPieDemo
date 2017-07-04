package com.lwz.pie;

import android.app.Activity;
import android.graphics.Color;
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
 * 有中心圆的饼状图的展示
 * 和普通的饼状图差不多，改变一个属性就可以了
 * 但是这里使用的自己定义的一组数据
 */
public class CenterCirclePieActivity extends Activity {

    private PieChartView pieChart;     //饼状图View
    private PieChartData data;         //存放数据

    private boolean hasLabels = true;                   //是否有标语
    private boolean hasLabelsOutside = false;           //扇形外面是否有标语
    private boolean hasCenterCircle = true;            //是否有中心圆
    private boolean hasCenterText1 = true;             //是否有中心的文字，大字体
    private boolean hasCenterText2 = false;             //是否有中心的文字2，和第一种文字的大小不一样，小字体，但是好像没有用！
    private boolean isExploded = false;                  //是否是炸开的图像
    private boolean hasLabelForSelected = false;         //选中的扇形显示标语

    private List<Integer> listRange = new ArrayList<Integer>();    //每个扇形范围的数量
    private String[] rangeString = {"60以下", "60-80", "80-90", "90以上"};//每个范围的标签显示

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
        //数据
        int[] num = {88, 44, 66, 98, 45, 65, 65, 45, 32, 99, 89, 100, 2, 33, 44, 12, 33, 65, 45, 32, 99, 88,};
        int under_60 = 0;
        int be60_80 = 0;
        int be80_90 = 0;
        int above_90 = 0;
        //判断各个数据的范围
        for (int i = 0; i < num.length; i++) {
            if (num[i] < 60) {
                under_60++;
            } else if (num[i] < 80) {
                be60_80++;
            } else if (num[i] < 90) {
                be80_90++;
            } else if (num[i] <= 100) {
                above_90++;
            }
        }


        listRange.add(under_60);
        listRange.add(be60_80);
        listRange.add(be80_90);
        listRange.add(above_90);

        int numValues = 4;   //扇形的数量
        int[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};   //各个扇形显示的颜色
        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            //判断各个数据范围占360度的多少度
            SliceValue sliceValue = new SliceValue(360f * listRange.get(i) / num.length, colors[i]);
            sliceValue.setLabel("范围 " + rangeString[i] + ":" + listRange.get(i) + "人"); //设置扇形显示的文字
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
            data.setCenterText1("学生成绩统计");

            // Get roboto-italic font.    //Typeface是用来设置字体的!
            Typeface tf = Typeface.createFromAsset(getAssets(), "Roboto-Italic.ttf");
            data.setCenterText1Typeface(tf);

            // Get font size from dimens.xml and convert it to sp(library uses sp values).
            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
//                    (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));  //大字体
                    (int) getResources().getDimension(R.dimen.pie_chart_text2_size)));    //小字体
        }

        if (hasCenterText2) {
            data.setCenterText2("学生成绩统计");
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
            showToast(rangeString[arcIndex] + "的人数是：" + listRange.get(arcIndex) + "，占的比重是：" + (value.getValue() / 360f) + "%");
        }

        @Override
        public void onValueDeselected() {

        }

    }

    Toast toast;

    /**
     * 显示对话框
     *
     * @param msg
     */
    private void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

}
