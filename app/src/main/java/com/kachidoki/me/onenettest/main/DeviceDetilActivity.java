package com.kachidoki.me.onenettest.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
import java.util.zip.Inflater;

/**
 * Created by Frank on 16/6/5.
 */
public class DeviceDetilActivity extends AppCompatActivity {
    private SuperRecyclerView recyclerView;
    TextView tv_devicetitle;
    TextView tv_safe;
    Switch aSwitch;
    final DeviceDetilAdapter adapter = new DeviceDetilAdapter();
    private ImageView back;
    private ImageView more;
    private TextView isOnlineText;
    private String deviceID;
    private boolean isOnline=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil);
        final Intent intent = DeviceDetilActivity.this.getIntent();
        deviceID=intent.getStringExtra("id");
        recyclerView = (SuperRecyclerView) findViewById(R.id.recyclerview_detil);
        tv_safe = (TextView) findViewById(R.id.device_safe);
        tv_devicetitle = (TextView) findViewById(R.id.deviceDetil_title);
        aSwitch = (Switch) findViewById(R.id.swich);
        back = (ImageView) findViewById(R.id.deviceDetil_finsh);
        more = (ImageView) findViewById(R.id.deviceDetil_more);
        isOnlineText= (TextView) findViewById(R.id.deviceDetil_isOnline);
        tv_devicetitle.setText(intent.getStringExtra("title"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop(v,DeviceDetilActivity.this);
            }
        });
        InitRecyclerView();
        SetData();
    }

    private void SetData() {
        adapter.clear();
        OneNetApi.getInstance(this).getDevice(API.APIKey,deviceID, new ResponseListener() {
            @Override
            public void onResponse(OneNetResponse response) {
                final DeviceDetil.DeviceDetilWrapper deviceDetil = new Gson().fromJson(response.getRawResponse(),DeviceDetil.DeviceDetilWrapper.class);
                isOnline=deviceDetil.getData().isOnline();
                OnlineIsChange(isOnline);
                getDataStreams();
            }
            @Override
            public void onError(OneNetError error) {
                Toast.makeText(DeviceDetilActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("DataStreamsTest", error.toString());
            }
        });
    }

    private void getDataStreams(){
        OneNetApi.getInstance(this).getDatastreams(API.APIKey, deviceID, null, new ResponseListener() {
            @Override
            public void onResponse(OneNetResponse oneNetResponse) {
                final Datastreams.datastreamsWraper datastreamsWraper =  new Gson().fromJson(oneNetResponse.getRawResponse(), Datastreams.datastreamsWraper.class);
                if (datastreamsWraper.getdata()!=null){
                    List<Datastreams> newdata = new ArrayList<>();
                    for(Datastreams data:datastreamsWraper.getdata()){
                        if (data.getId().equals("安全指数")){
                            tv_safe.setText(data.getCurrent_value()+"");
                        }else if (data.getId().equals("开关状态")){
                            aSwitch.setChecked(false);
                        }else {
                            newdata.add(data);
                        }
                    }
                    adapter.add(newdata);
                }
            }

            @Override
            public void onError(OneNetError oneNetError) {
                Toast.makeText(DeviceDetilActivity.this, oneNetError.toString(), Toast.LENGTH_SHORT).show();
                Log.i("DataStreamsTest", oneNetError.toString());
            }
        });
    }

    private void OnlineIsChange(boolean isOnline){
        if (!isOnline){
            isOnlineText.setText("离线");
            aSwitch.setChecked(false);
            Toast.makeText(DeviceDetilActivity.this,"请先让设备上线",Toast.LENGTH_LONG).show();
        }else {
            isOnlineText.setText("在线");
            NetcheckSwitch();
            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.e("aSwitchTest",isChecked+"");
                    OneNetApi.getInstance(DeviceDetilActivity.this).sendToEdp(API.APIKey,deviceID,isChecked?API.COMMAND_ON:API.COMMAND_OFF, new ResponseListener() {
                        @Override
                        public void onResponse(OneNetResponse response) {
                            Toast.makeText(DeviceDetilActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
                            NetcheckSwitch();
                        }

                        @Override
                        public void onError(OneNetError error) {
                            Toast.makeText(DeviceDetilActivity.this,"出错了",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    private void NetcheckSwitch(){
        OneNetApi.getInstance(DeviceDetilActivity.this).getDatastream(API.APIKey, deviceID, "开关状态", new ResponseListener() {
            @Override
            public void onResponse(OneNetResponse response) {
                final Datastreams datastreams = new Gson().fromJson(response.getData(), Datastreams.class);
                if (datastreams.getCurrent_value().equals("开")){
                    Log.e("aSwitchTest",datastreams.getCurrent_value()+"");
                    aSwitch.setChecked(true);
                }else {
                    Log.e("aSwitchTest",datastreams.getCurrent_value()+"");
                    aSwitch.setChecked(false);
                }
            }

            @Override
            public void onError(OneNetError error) {

            }
        });
    }

    private void InitRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(DeviceDetilActivity.this));
        recyclerView.setAdapter(adapter);
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SetData();
            }
        });
    }

    private void showPop(View v, final Context context){
        View content=LayoutInflater.from(context).inflate(R.layout.pop_list,null);
        TextView wifi=(TextView)content.findViewById(R.id.pop_wifi);
        TextView clock=(TextView)content.findViewById(R.id.pop_clock);
        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetWifi(context);
            }
        });
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetColck(context);
            }
        });
        PopupWindow popup=new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popup.setTouchable(true);
        popup.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popup.showAsDropDown(v);
    }

    private void showSetColck(final Context context){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("设置定时关");
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_clock,null);
        builder.setView(view);
        final EditText time= (EditText) view.findViewById(R.id.clock_time);
        final TextInputLayout inputLayout= (TextInputLayout) view.findViewById(R.id.clock_input);
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("确定",null);
        builder.setNeutralButton("取消之前定时", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendColckCommand(-1);
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time.getText()!=null&&!String.valueOf(time.getText()).isEmpty()){
                    int settime=Integer.valueOf(String.valueOf(time.getText()));
                    sendColckCommand(settime);
                    dialog.dismiss();
                }else {
                    inputLayout.setError("请输入有效时间");
                }
            }
        });
    }

    private void showSetWifi(final Context context){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("设置Wifi");
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_wifi,null);
        builder.setView(view);
        final TextInputLayout name= (TextInputLayout) view.findViewById(R.id.wifi_name);
        final TextInputLayout psw= (TextInputLayout) view.findViewById(R.id.wifi_psw);
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("确定",null);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(name.getEditText().getText())&&!TextUtils.isEmpty(name.getEditText().getText())){
                    sendWifiCommand(name.getEditText().getText().toString(),psw.getEditText().getText().toString());
                    dialog.dismiss();
                }else {
                    if (TextUtils.isEmpty(name.getEditText().getText())) name.setError("请输入wifi名");
                    if (TextUtils.isEmpty(psw.getEditText().getText())) psw.setError("请输入wifi密码");
                }
            }
        });
    }

    private void sendColckCommand(int time){
        if (deviceID.isEmpty()) return;
        OneNetApi.getInstance(DeviceDetilActivity.this).sendToEdp(API.APIKey,deviceID,time>0?API.COMMAND_TIME(time):API.COMMAND_CANCELTIME, new ResponseListener() {
            @Override
            public void onResponse(OneNetResponse response) {
                Toast.makeText(DeviceDetilActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(OneNetError error) {
                Toast.makeText(DeviceDetilActivity.this,"设置失败",Toast.LENGTH_SHORT).show();
                Toast.makeText(DeviceDetilActivity.this,error+"",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendWifiCommand(String name,String psw){
        if (deviceID.isEmpty()) return;
        OneNetApi.getInstance(DeviceDetilActivity.this).sendToEdp(API.APIKey,deviceID,API.COMMAND_WIFI(name,psw),new ResponseListener() {
            @Override
            public void onResponse(OneNetResponse response) {
                Toast.makeText(DeviceDetilActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(OneNetError error) {
                Toast.makeText(DeviceDetilActivity.this,"设置失败",Toast.LENGTH_SHORT).show();
                Toast.makeText(DeviceDetilActivity.this,error+"",Toast.LENGTH_SHORT).show();
            }
        });
    }



    class DeviceDetilAdapter extends RecyclerView.Adapter<DeviceDetilVH>{
        private ArrayList<Datastreams> data = new ArrayList<>();

        public void clear(){
            data.clear();
        }

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!datastreams.getId().equals("运行状态")){
                        DatastreamChartActivity.goToChart(DeviceDetilActivity.this,deviceID,datastreams.getId());
                    }
                }
            });

        }
    }


}
