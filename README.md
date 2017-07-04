#hellochart详细讲解（二）饼状图


之前有介绍HelloChart图形绘制框架的使用，还有各种效果图：

http://blog.csdn.net/wenzhi20102321/article/details/73133718



之前没有对各个图形设计做详细介绍。

本文重点hellochart饼状图的设计。


##效果：


![1](http://i.imgur.com/YBy4rFZ.gif)

有点卡顿！请看下面的图片介绍。



##总览图：


![2](http://i.imgur.com/5bdAvQo.png)


一共显示3种基本图形，其实通过设置属性可以变成多种图形样式！

第一种简单饼状图，设置很少的属性：


##简单饼状图

设置几个随机数字进去，然后按照数值的大小显示饼状图的比例大小。

![3](http://i.imgur.com/dCHKTch.png)





##炸开的饼状图


![4](http://i.imgur.com/AMb8moX.png)


代码和简单饼状图的一样，就修改一个属性，就可以然线条变成炸开的图形


##有中心圆的饼状图

这里传人的数据是一组学生成绩：

int[] num = {88, 44, 66, 98, 45, 65, 65, 45, 32, 99, 89, 100, 2, 33, 44, 12, 33, 65, 45, 32, 99, 88,0};

扇形显示的区域范围的文字：

String[] rangeString = {"60以下", "60-80", "80-90", "90以上"};//每个范围的标签显示

效果：

![5](http://i.imgur.com/FfO4elG.png)


#饼状图使用讲解

##（一）依赖hellochart，或导入jar包

依赖和jar包都可以上官网找，我的项目中也有jar包（在后面）。


##（二）布局文件

```
//这是我自己打包的jar包，包名和官网不一样，但是类名完全一样的
  <com.lwz.chart.hellocharts.view.PieChartView
            android:layout_height="match_parent"
            android:layout_width="match_parent"
            android:id="@+id/pieChart"
            />


```


##（三）代码

//通过简单饼状图代码来学会使用

```
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
		
		//存放扇形数据的集合
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




```

上面有些属性的意思看，上面的注解就差不多知道了。

但是有些人对弧度的分配，和弧度上面显示的文字显示还不清楚



```
	//设置弧度数和弧度显示文字的关键代码
 	 List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            //判断各个数据范围占360度的多少度
            SliceValue sliceValue = new SliceValue(value, colors[i]);//SliceValue构造方法中传人两个参数，第一个是角度数，第二个是弧形显示的颜色
            sliceValue.setLabel("****"); //设置扇形显示的文字
            values.add(sliceValue);
        }

```


饼状图是hellochart中，使用最简单容易的一个图形了。

也可以先运行下，我的代码看看效果，再替换下数据。



共勉：生命就掌握你的手中，你可以对别人说“no”，也可以对自己说“yes”！
