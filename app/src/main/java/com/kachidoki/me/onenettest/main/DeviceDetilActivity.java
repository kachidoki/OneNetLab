package com.kachidoki.me.onenettest.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.iot.onenet.OneNetApi;
import com.chinamobile.iot.onenet.OneNetError;
import com.chinamobile.iot.onenet.OneNetResponse;
import com.chinamobile.iot.onenet.ResponseListener;
import com.google.gson.Gson;
import com.kachidoki.me.onenettest.R;
import com.kachidoki.me.onenettest.app.App;
import com.kachidoki.me.onenettest.config.API;
import com.kachidoki.me.onenettest.model.bean.Datastreams;
import com.kachidoki.me.onenettest.model.bean.DeviceDetil;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Frank on 16/6/5.
 */
public class DeviceDetilActivity extends AppCompatActivity {
    private SuperRecyclerView recyclerView;
    TextView tv_devicetitle;
    TextView tv_safe;
    Switch aSwitch;
    final DeviceDetilAdapter adapter = new DeviceDetilAdapter();
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil);
        recyclerView = (SuperRecyclerView) findViewById(R.id.recyclerview_detil);
        tv_safe = (TextView) findViewById(R.id.device_safe);
        tv_devicetitle = (TextView) findViewById(R.id.deviceDetil_title);
        aSwitch = (Switch) findViewById(R.id.swich);
        imageView = (ImageView) findViewById(R.id.finsh);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SetData();
        InitRecyclerView();
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final Intent intent = DeviceDetilActivity.this.getIntent();
                if (isChecked){
                    OneNetApi.getInstance(DeviceDetilActivity.this).sendToEdp(API.APIKey,intent.getStringExtra("id"),"{SWITCH0}", new ResponseListener() {
                        @Override
                        public void onResponse(OneNetResponse response) {
                            Toast.makeText(DeviceDetilActivity.this,"设备打开",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(OneNetError error) {
                            Toast.makeText(DeviceDetilActivity.this,"出错啦",Toast.LENGTH_SHORT).show();
                            Toast.makeText(DeviceDetilActivity.this,error+"",Toast.LENGTH_SHORT).show();
                            aSwitch.setChecked(false);

                        }
                    });
                }else {
                    OneNetApi.getInstance(DeviceDetilActivity.this).sendToEdp(API.APIKey,intent.getStringExtra("id"),"{SWITCH1}", new ResponseListener() {
                        @Override
                        public void onResponse(OneNetResponse response) {
                            Toast.makeText(DeviceDetilActivity.this,"设备打开",Toast.LENGTH_SHORT).show();
                            Toast.makeText(DeviceDetilActivity.this,response+"",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(OneNetError error) {
                            Toast.makeText(DeviceDetilActivity.this,"出错啦",Toast.LENGTH_SHORT).show();
                            Toast.makeText(DeviceDetilActivity.this,error.getMessage()+"",Toast.LENGTH_SHORT).show();
                            aSwitch.setChecked(true);
                        }
                    });
                }
            }
        });

    }

    private void InitRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(DeviceDetilActivity.this));
        recyclerView.setAdapter(adapter);
        Intent intent = this.getIntent();

        OneNetApi.getInstance(this).getDatastreams(API.APIKey, intent.getStringExtra("id"), null, new ResponseListener() {
            @Override
            public void onResponse(OneNetResponse response) {
                final Datastreams.datastreamsWraper datastreams =  new Gson().fromJson(response.getRawResponse(), Datastreams.datastreamsWraper.class);
                if (datastreams.getdata()!=null){
                    List<Datastreams> newdata = new ArrayList<Datastreams>();
                    for(int i=0;i<datastreams.getdata().length;i++){
                        if (!datastreams.getdata()[i].getId().equals("安全指数")){
                            newdata.add(datastreams.getdata()[i]);
//                            Log.i("test",datastreams.getdata()[i].getId());
                        }

                    }
                    adapter.add(newdata);
                }
            }

            @Override
            public void onError(OneNetError error) {
                Toast.makeText(DeviceDetilActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("test", error.toString());
            }
        });


    }

    private void SetData() {
        final Intent intent = this.getIntent();

        OneNetApi.getInstance(this).getDatastream(API.APIKey, intent.getStringExtra("id"), "安全指数", new ResponseListener() {
            @Override
            public void onResponse(OneNetResponse response) {
                final DeviceDetil.Datastreams datastreams = new Gson().fromJson(response.getData(), DeviceDetil.Datastreams.class);
                tv_devicetitle.setText(intent.getStringExtra("title"));
                tv_safe.setText(datastreams.getCurrent_value());
            }

            @Override
            public void onError(OneNetError error) {

            }
        });


    }

    class DeviceDetilAdapter extends RecyclerView.Adapter<DeviceDetilVH>{
        private ArrayList<Datastreams> data = new ArrayList<>();
        public void add(List<Datastreams> datastreamses){
            data.addAll(datastreamses);
            notifyDataSetChanged();
        }
        @Override
        public DeviceDetilVH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(DeviceDetilActivity.this).inflate(R.layout.datastreams_view, parent, false);
            return new DeviceDetilVH(view);
        }

        @Override
        public void onBindViewHolder(DeviceDetilVH holder, int position) {
                if (!data.get(position).getId().equals("安全指数")){
                    holder.setData(data.get(position));
                }


        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }


    class DeviceDetilVH extends RecyclerView.ViewHolder{
        ImageView img_icon;
        TextView tv_id;
        TextView tv_current;
        ImageView img_go;
        public DeviceDetilVH(View itemView) {
            super(itemView);
            img_icon = (ImageView) itemView.findViewById(R.id.datastream_Img);
            tv_id = (TextView) itemView.findViewById(R.id.datastream_id);
            tv_current = (TextView) itemView.findViewById(R.id.datastream_current);
            img_go = (ImageView) itemView.findViewById(R.id.datastream_go);
        }

        public void setData(final Datastreams datastreams){
            tv_id.setText(datastreams.getId());
            tv_current.setText(datastreams.getCurrent_value());
            if (datastreams.getId().equals("温度值")){
                img_icon.setImageResource(R.drawable.temp);
            }
            if (datastreams.getId().equals("运行时间")){
                img_icon.setImageResource(R.drawable.runtime);
            }
            if (datastreams.getId().equals("设备状态")){
                img_icon.setImageResource(R.drawable.workstatue);
                if (datastreams.getCurrent_value().equals("1")){
                    tv_current.setText("已打开");
                    aSwitch.setChecked(true);
                }else {
                    tv_current.setText("已关闭");
                    aSwitch.setChecked(false);
                }
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DeviceDetilActivity.this,DatastreamChartActivity.class);
                    intent.putExtra("id",datastreams.getId());
                    startActivity(intent);
                }
            });

        }
    }


}
