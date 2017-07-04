package com.lwz.pie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * hellochart饼状图的使用
 */
public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    /**
     * 简单饼状图
     *
     * @param view
     */
    public void simplePie(View view) {
        startActivity(new Intent(this, SimplePieActivity.class).putExtra("title", "简单饼状图"));
    }

    /**
     * 炸开的饼状图
     *
     * @param view
     */
    public void explodedPie(View view) {
        startActivity(new Intent(this, ExplodedPieActivity.class).putExtra("title", "炸开的饼状图"));
    }

    /**
     * 有中心圆的饼状图
     *
     * @param view
     */
    public void centerCirclePie(View view) {
        startActivity(new Intent(this, CenterCirclePieActivity.class).putExtra("title", "有中心圆饼状图"));
    }


}
