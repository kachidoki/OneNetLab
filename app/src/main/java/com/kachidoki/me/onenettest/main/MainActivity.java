package com.kachidoki.me.onenettest.main;

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
import com.kachidoki.me.onenettest.model.bean.DeviceList;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends BaseActivity {

    private SuperRecyclerView recyclerView;
    final DeviceAdapther mAdapter = new DeviceAdapther();
    private int page = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar(true);
        recyclerView = (SuperRecyclerView) findViewById(R.id.recyclerview);
        InitRecyclerView();
    }

    private void InitRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(mAdapter);
        OneNetApi.getInstance(this).getDevices(API.APIKey, null, null, null, null, null, null, new ResponseListener() {

            @Override
            public void onResponse(OneNetResponse response) {
                final DeviceList.DeviceListWrapper deviceListWrapper = new Gson().fromJson(response.getData(), DeviceList.DeviceListWrapper.class);
                mAdapter.add(deviceListWrapper.getDevices());
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
                OneNetApi.getInstance(MainActivity.this).getDevices(API.APIKey, page+"", null, null, null, null, null, new ResponseListener() {

                    @Override
                    public void onResponse(OneNetResponse response) {
                        final DeviceList.DeviceListWrapper deviceListWrapper = new Gson().fromJson(response.getData(), DeviceList.DeviceListWrapper.class);
                        page = 1;
                        recyclerView.showRecycler();
                        mAdapter.deviceLists.clear();
                        mAdapter.add(deviceListWrapper.getDevices());
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

        recyclerView.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int i, int i1, int i2) {
                page++;
                OneNetApi.getInstance(MainActivity.this).getDevices(API.APIKey, page+"", null, null, null, null, null, new ResponseListener() {

                    @Override
                    public void onResponse(OneNetResponse response) {
                        final DeviceList.DeviceListWrapper deviceListWrapper = new Gson().fromJson(response.getData(), DeviceList.DeviceListWrapper.class);
                        recyclerView.showRecycler();
                        recyclerView.hideMoreProgress();
                        if (deviceListWrapper.getDevices()!=null){
                            mAdapter.add(deviceListWrapper.getDevices());
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
        private ArrayList<DeviceList> deviceLists = new ArrayList<>();
        public void add(DeviceList[] deviceList){
            deviceLists.addAll(Arrays.asList(deviceList));
            notifyDataSetChanged();
        }
        @Override
        public DeviceVH onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.devicerecycle_view, viewGroup, false);
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
        TextView tv_describe;
        ImageView img_device;

        public DeviceVH(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.device_title);
            tv_describe = (TextView) itemView.findViewById(R.id.device_describe);
        }

        public void setData(final DeviceList deviceList){
            tv_title.setText(deviceList.getTitle());
            tv_describe.setText(deviceList.getDesc());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this,DeviceDetilActivity.class);
                    intent.putExtra("id",deviceList.getId());
                    intent.putExtra("title",deviceList.getTitle());
                    startActivity(intent);
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
