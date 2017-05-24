package com.kachidoki.me.onenettest.main;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.iot.onenet.OneNetApi;
import com.chinamobile.iot.onenet.OneNetError;
import com.chinamobile.iot.onenet.OneNetResponse;
import com.chinamobile.iot.onenet.ResponseListener;
import com.google.gson.Gson;
import com.kachidoki.me.onenettest.R;
import com.kachidoki.me.onenettest.app.BaseActivity;
import com.kachidoki.me.onenettest.config.API;
import com.kachidoki.me.onenettest.model.bean.Datastreams;
import com.kachidoki.me.onenettest.model.bean.DeviceList;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

    private SuperRecyclerView recyclerView;
    private TextView deviceCount;
    final DeviceAdapther mAdapter = new DeviceAdapther();
    final Gson gson=new Gson();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (SuperRecyclerView) findViewById(R.id.recyclerview);
        deviceCount= (TextView) findViewById(R.id.Main_deviceCount);
        InitRecyclerView();
    }

    private void InitRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(mAdapter);
        OneNetApi.getInstance(this).getDevices(API.APIKey, null, null, null, null, null, null, new ResponseListener() {

            @Override
            public void onResponse(OneNetResponse response) {
                final DeviceList.DeviceListWrapper deviceListWrapper = gson.fromJson(response.getData(), DeviceList.DeviceListWrapper.class);
                mAdapter.clear();
                mAdapter.add(deviceListWrapper.getDevices());
                if (deviceListWrapper.getDevices()!=null){
                    deviceCount.setText(""+deviceListWrapper.getTotal_count());
                }else {
                    deviceCount.setText("0");
                }
            }

            @Override
            public void onError(OneNetError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("test", error.toString());
                // 网络或服务器错误
            }
        });

        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                OneNetApi.getInstance(MainActivity.this).getDevices(API.APIKey,null, null, null, null, null, null, new ResponseListener() {

                    @Override
                    public void onResponse(OneNetResponse response) {
                        final DeviceList.DeviceListWrapper deviceListWrapper = gson.fromJson(response.getData(), DeviceList.DeviceListWrapper.class);
//                        recyclerView.showRecycler();
                        mAdapter.clear();
                        mAdapter.add(deviceListWrapper.getDevices());
                        if (deviceListWrapper.getDevices()!=null){
                            deviceCount.setText(""+deviceListWrapper.getTotal_count());
                        }else {
                            deviceCount.setText("0");
                        }
                    }

                    @Override
                    public void onError(OneNetError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.i("test", error.toString());
                        // 网络或服务器错误
                    }
                });
            }
        });

    }



    class DeviceAdapther extends RecyclerView.Adapter<DeviceVH>{
        private List<DeviceList> deviceLists = new ArrayList<>();
        public void add(DeviceList[] deviceList){
            deviceLists.addAll(Arrays.asList(deviceList));
            notifyDataSetChanged();
        }

        public void clear(){
            deviceLists.clear();
        }

        @Override
        public DeviceVH onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.devicecard_view, viewGroup, false);
            return new DeviceVH(view);
        }

        @Override
        public void onBindViewHolder(DeviceVH deviceVH, int i) {
            deviceVH.setData(deviceLists.get(i));
        }

        @Override
        public int getItemCount() {
            return deviceLists.size();
        }
    }

    class DeviceVH extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_time;
        TextView tv_isOnlion;
        TextView tv_Socre;
        Context context;

        public DeviceVH(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.deviceList_title);
            tv_time = (TextView) itemView.findViewById(R.id.deviceList_create_time);
            tv_isOnlion = (TextView) itemView.findViewById(R.id.deviceList_isOnline);
            tv_Socre = (TextView) itemView.findViewById(R.id.deviceList_score);
            context=itemView.getContext();
        }

        public void setData(final DeviceList deviceList){
            tv_title.setText(deviceList.getTitle());
            tv_time.setText(deviceList.getCreate_time());
            if (deviceList.getOnline()){
                tv_isOnlion.setVisibility(View.VISIBLE);
            }else {
                tv_isOnlion.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeviceDetilActivity.GoDeviceDetil(deviceList.getId(),deviceList.getTitle(),MainActivity.this);
                }
            });
            OneNetApi.getInstance(context).getDatastream(API.APIKey, deviceList.getId(), "安全指数", new ResponseListener() {
                @Override
                public void onResponse(OneNetResponse oneNetResponse) {
                    Datastreams.dataSingleWraper datastreamsWraper=gson.fromJson(oneNetResponse.getRawResponse(),Datastreams.dataSingleWraper.class);
                    if (datastreamsWraper.getData()!=null){
                        tv_Socre.setText(""+datastreamsWraper.getData().getCurrent_value());
                    }
                }

                @Override
                public void onError(OneNetError oneNetError) {

                }
            });
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
