package com.kachidoki.me.onenettest.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chinamobile.iot.onenet.OneNetApi;
import com.chinamobile.iot.onenet.OneNetError;
import com.chinamobile.iot.onenet.OneNetResponse;
import com.chinamobile.iot.onenet.ResponseListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.kachidoki.me.onenettest.R;
import com.kachidoki.me.onenettest.app.BaseActivity;
import com.kachidoki.me.onenettest.config.API;
import com.kachidoki.me.onenettest.model.bean.DataPoints;

import java.util.ArrayList;

/**
 * Created by Frank on 16/8/15.
 */

public class DatastreamChartActivity extends BaseActivity {

    private LineChart mLineChart;
    ArrayList<Entry> y = new ArrayList<Entry>();

    private  String DeviceId ;

    private Handler handler;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        mLineChart = (LineChart) findViewById(R.id.chart1);
        imageView = (ImageView) findViewById(R.id.finsh);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Intent intent = getIntent();
        DeviceId= intent.getStringExtra("id");

        Log.e("Thread","OnCreate thread "+Thread.currentThread().getId());

//        new BackAsync().execute();
        setChartStyle(mLineChart);

        OneNetApi.getInstance(DatastreamChartActivity.this).getDatapoints(API.APIKey, API.mTempDeviceId ,DeviceId ,null, null, "10", null, null, new ResponseListener() {


            @Override
            public void onResponse(OneNetResponse oneNetResponse) {

                Log.e("Thread","OnResponse thread "+Thread.currentThread().getId());

                final DataPoints.Data data = new Gson().fromJson(oneNetResponse.getData(), DataPoints.Data.class);
                for (int i=0;i<data.getDatastreams()[0].getDataPoints().length;i++){
                    y.add(new Entry(i,Float.parseFloat(data.getDatastreams()[0].getDataPoints()[i].getValue())));
                    Log.i("test","doInBackGround:--> x = "+y.get(i).getX()+"y = "+y.get(i).getY());
                }
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onError(OneNetError oneNetError) {

            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                makeLineData();
            }
        };

    }

    // 设置chart显示的样式
    private void setChartStyle(LineChart mLineChart) {
        // 是否在折线图上添加边框
        mLineChart.setDrawBorders(false);
        mLineChart.setDescription("最近数据");// 数据描述
        mLineChart.getAxisRight().setEnabled(false);
        mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mLineChart.getXAxis().setGridColor(Color.TRANSPARENT);
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        mLineChart.setNoDataTextDescription("暂时无数据");
        // 是否绘制背景颜色。
        // 如果mLineChart.setDrawGridBackground(false)，
        // 那么mLineChart.setGridBackgroundColor(Color.CYAN)将失效;
        mLineChart.setDrawGridBackground(false);
        mLineChart.setGridBackgroundColor(Color.CYAN);
        // 触摸
        mLineChart.setTouchEnabled(true);
        // 拖拽
        mLineChart.setDragEnabled(false);
        // 缩放
        mLineChart.setScaleEnabled(false);
        mLineChart.setPinchZoom(false);
        // 设置背景
        mLineChart.setBackgroundColor(Color.TRANSPARENT);



        // 设置比例图标示，就是那个一组y的value的
        Legend mLegend = mLineChart.getLegend();
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(15.0f);// 字体
        mLegend.setTextColor(Color.BLUE);// 颜色
        // 沿x轴动画，时间2000毫秒。
       mLineChart.animateX(2000);
    }

    private void makeLineData() {

        // y轴数据集
        LineDataSet mLineDataSet = new LineDataSet(y, "测试数据集");
        for (int i =0;i<y.size();i++){
            Log.i("test","makeLineData:--> x = "+y.get(i).getX()+"y = "+y.get(i).getY());
        }


        // 用y轴的集合来设置参数
        // 线宽
        mLineDataSet.setLineWidth(1.0f);
        // 显示的圆形大小
        mLineDataSet.setCircleSize(2.0f);
        // 折线的颜色
        mLineDataSet.setColor(Color.WHITE);
        // 圆球的颜色
        mLineDataSet.setCircleColor(Color.WHITE);
        // 设置mLineDataSet.setDrawHighlightIndicators(false)后，
        // Highlight的十字交叉的纵横线将不会显示，
        // 同时，mLineDataSet.setHighLightColor(Color.CYAN)失效。
        mLineDataSet.setDrawHighlightIndicators(false);
        // 按击后，十字交叉线的颜色
//        mLineDataSet.setHighLightColor(Color.CYAN);
        // 设置这项上显示的数据点的字体大小。
        mLineDataSet.setValueTextSize(10.0f);
        mLineDataSet.setValueTextColor(Color.GRAY);
        // mLineDataSet.setDrawCircleHole(true);
        // 改变折线样式，用曲线。
        // mLineDataSet.setDrawCubic(true);
        // 默认是直线
        // 曲线的平滑度，值越大越平滑。
        // mLineDataSet.setCubicIntensity(0.2f);
//         填充曲线下方的区域，红色，半透明。
         mLineDataSet.setDrawFilled(true);
         mLineDataSet.setFillAlpha(70);
         mLineDataSet.setFillColor(Color.WHITE);
        // 填充折线上数据点、圆球里面包裹的中心空白处的颜色。
        mLineDataSet.setCircleColorHole(Color.WHITE);


        ArrayList<ILineDataSet> mLineDataSets = new ArrayList<ILineDataSet>();
        mLineDataSets.add(mLineDataSet);

        LineData mLineData = new LineData(mLineDataSets);
        // 设置x,y轴的数据
        mLineChart.setData(mLineData);
        mLineChart.notifyDataSetChanged();
        mLineChart.invalidate();
    }


}
