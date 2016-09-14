package com.kachidoki.me.onenettest.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chinamobile.iot.onenet.OneNetApi;
import com.chinamobile.iot.onenet.OneNetError;
import com.chinamobile.iot.onenet.OneNetResponse;
import com.chinamobile.iot.onenet.ResponseListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        setToolbar(true);
        mLineChart = (LineChart) findViewById(R.id.chart1);

        showChart();

    }

    private void showChart(){
        mLineChart.setDrawBorders(false);  //是否在折线图上添加边框
        mLineChart.setNoDataTextDescription("无数据");

        mLineChart.setDrawGridBackground(false); // 是否显示表格颜色
        mLineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜
        // enable scaling and dragging
        mLineChart.setDragEnabled(false);// 是否可以拖拽
        mLineChart.setScaleEnabled(false);// 是否可以缩放

        getLineData();
        Legend mLegend = mLineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的

        // modify the legend ...
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.BLACK);// 颜色
        mLineChart.animateX(2500); // 立即执行的动画,x轴




    }


    private void getLineData(){

        final ArrayList<Entry> values = new ArrayList<Entry>();

        final Intent intent = getIntent();
        OneNetApi.getInstance(this).getDatapoints(API.APIKey, API.mTempDeviceId , intent.getStringExtra("id"),null, null, "10", null, null, new ResponseListener() {


            @Override
            public void onResponse(OneNetResponse oneNetResponse) {
                final DataPoints.Data data = new Gson().fromJson(oneNetResponse.getData(), DataPoints.Data.class);
                for (int i=0;i<data.getDatastreams()[0].getDataPoints().length;i++){
                    values.add(new Entry(i,Float.parseFloat(data.getDatastreams()[0].getDataPoints()[i].getValue())));
                    Log.i("test",data.getDatastreams()[0].getDataPoints()[i].getValue());
                }
            }

            @Override
            public void onError(OneNetError oneNetError) {

            }
        });

//        values.add(new Entry(1,(float) 3.22 ));
//        values.add(new Entry(2,(float)3.23));
//        values.add(new Entry(3,(float)3.2));
//        values.add(new Entry(4,(float)3.2));
//        values.add(new Entry(5,(float)3.24));





        LineDataSet set1;

        if (mLineChart.getData() != null && mLineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mLineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet");

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);


            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData lineData = new LineData(dataSets);
            mLineChart.setData(lineData);
        }

    }


}
