package thermostat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gizwits.gizwifisdk.api.GizDeviceScheduler;
import com.gizwits.gizwifisdk.api.GizDeviceSchedulerCenter;
import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.gizwits.gizwifisdk.listener.GizDeviceSchedulerCenterListener;
import com.gizwits.opensource.appkit.CommonModule.GosBaseActivity;
import com.gizwits.opensource.appkit.R;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ljy on 2017/2/16.
 */

public class TempControlActivity extends Activity implements View.OnClickListener {

   	/*
	 * ===========================================================
	 * 以下key值对应http://site.gizwits.com/v2/datapoint?product_key={productKey}
	 * 中显示的数据点名称，sdk通过该名称作为json的key值来收发指令，demo中使用的key都是对应机智云实验室的微信宠物屋项目所用数据点
	 * ===========================================================
	 */
    /** led红灯开关 0=关 1=开. */
    //private static final String KEY_RED_SWITCH = "LED_OnOff";

    /** 温控器 0=关 1=开. */
    private static final String FANSWITCH = "switch";

    /** 按键锁定 0=未锁定 1=锁定. */
    private static final String KEY_LOCK = "lock_key";

    /** 空调模式 .制冷 、送风、制热 */
    private static final String KEY_MODE = "mode";

    /**  风速 0.低风 1.中风 2.高风 3.自动. */
    private static final String  KEY_SPEED = "fan_speed";

    /** 室内温度  0~45. */
    private static final String ROOM_TEMP = "room_temp";

    /** 室内温度设定  5~30. */
    private static final String SET_TEMP = "set_temp";



    /** 红外探测 0无障碍 1有障碍. */
    private static final String KEY_INFRARED = "Infrared";

    /** 环境温度. */
    private static final String KEY_TEMPLATE = "Temperature";

    /** 环境湿度. */
    private static final String KEY_HUMIDITY = "Humidity";


    private RelativeLayout topRel;
    private RelativeLayout popRel;
    private View view1, view2, view3;
    private TextView mHumidity, mTemp, mMode, mSpeed, mSTemp;
    private ImageView minusBt, plusBt, modeBt, speedBt, switchBt, efficintBt, lockBt, timerBt;

    private boolean isOnline = true;
    //开关状态
    private boolean switch_on = false;

    private PopupWindow mModePop;
    /** The GizWifiDevice device */
    private GizWifiDevice device;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_control);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.setting_rel);
        relativeLayout.setVisibility(View.VISIBLE);
        initViews();
        clickListener();
        if (isOnline) {
            changeToOnline();
        } else {
            changeToOffline();
        }
        initDevice();
    }
    private void initDevice() {
        Intent intent = getIntent();
        device = (GizWifiDevice) intent.getParcelableExtra("GizWifiDevice");
//        deviceStatu = new HashMap<String, Object>();

//        if (TextUtils.isEmpty(device.getAlias())) {
//            title = device.getProductName();
//        } else {
//            title = device.getAlias();
//        }
    }

    private void initViews() {
        topRel = (RelativeLayout) findViewById(R.id.top_rel);
        popRel = (RelativeLayout) findViewById(R.id.pop_rel);
        view1 = findViewById(R.id.temp_view_1);
        view2 = findViewById(R.id.temp_view_2);
        view3 = findViewById(R.id.temp_view_3);
        mHumidity = (TextView) findViewById(R.id.humidity_text);
        mTemp = (TextView) findViewById(R.id.temp_text);
        mMode = (TextView) findViewById(R.id.mode_text);
        mSpeed = (TextView) findViewById(R.id.speed_text);
        mSTemp = (TextView) findViewById(R.id.small_temp_text);
        minusBt = (ImageView) findViewById(R.id.minus_bt);
        plusBt = (ImageView) findViewById(R.id.plus_bt);
        modeBt = (ImageView) findViewById(R.id.mode_bt);
        speedBt = (ImageView) findViewById(R.id.speed_bt);
        switchBt = (ImageView) findViewById(R.id.switch_bt);
        efficintBt = (ImageView) findViewById(R.id.efficint_bt);
        lockBt = (ImageView) findViewById(R.id.lock_bt);
        timerBt = (ImageView) findViewById(R.id.timer_bt);
    }

    private void clickListener() {
        minusBt.setOnClickListener(this);
        plusBt.setOnClickListener(this);
        modeBt.setOnClickListener(this);
        speedBt.setOnClickListener(this);
        switchBt.setOnClickListener(this);
        efficintBt.setOnClickListener(this);
        lockBt.setOnClickListener(this);
        timerBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.minus_bt:
                break;
            case R.id.plus_bt:
                break;
            case R.id.mode_bt:
                if (isOnline) {
                    showModeChoosePopWindow();
                }
                break;
            case R.id.speed_bt:
                break;
            case R.id.switch_bt:
                sendSwitch();
                break;
            case R.id.efficint_bt:
                break;
            case R.id.lock_bt:

                break;
            case R.id.timer_bt:
                addTimer();
//                if (isOnline) {
//                    startActivity(new Intent(TempControlActivity.this, TimerSettingActivity.class));
//                }
                break;
        }
    }
    public void sendSwitch(){
        try {
            sendJson(FANSWITCH, false);
            switch_on=!switch_on;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    private void getUserinfo(){
//        SharedPreferences spf = getSharedPreferences(GosBaseActivity.SPF_Name, Context.MODE_PRIVATE);
//        uid = spf.getString("Uid", "");
//        token = spf.getString("Token", "");
//    }

    private void addTimer() {
        GizDeviceSchedulerCenterListener mListener = new GizDeviceSchedulerCenterListener() {
            @Override
            public void didUpdateSchedulers(GizWifiErrorCode result, GizWifiDevice schedulerOwner, List<GizDeviceScheduler> schedulerList) {
                if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {
                    Log.d("timer---->","success");
                    // 定时任务创建成功
                } else {
                    Log.d("timer---->","failure");

                    // 创建失败
                }
            }
        };

        // 设置定时任务监听
        GizDeviceSchedulerCenter.setListener(mListener);

        // 一次性定时任务，在2017年1月16日早上6点30分开灯
        GizDeviceScheduler scheduler = new GizDeviceScheduler();
        scheduler.setDate("2017-02-21");
        scheduler.setTime("18:30");
        scheduler.setRemark("开灯任务");
        ConcurrentHashMap<String, Object> attrs = new ConcurrentHashMap<String, Object>();
        attrs.put(FANSWITCH, true);
        attrs.put(KEY_LOCK, 1);

        scheduler.setAttrs(attrs);

        SharedPreferences spf = getSharedPreferences(GosBaseActivity.SPF_Name, Context.MODE_PRIVATE);
        String uid = spf.getString("Uid", "");
        String token = spf.getString("Token", "");

        // 创建设备的定时任务，mDevice为在设备列表中得到的设备对象
        GizDeviceSchedulerCenter.createScheduler(uid,token , device, scheduler);



    }

    private void sendJson(String key, Object value) throws JSONException {
        ConcurrentHashMap<String, Object> hashMap = new ConcurrentHashMap<String, Object>();
        hashMap.put(key, value);
        device.write(hashMap, 0);
        Log.i("Apptest", hashMap.toString());
    }


    /**
     * 设备掉电后显示状态
     */
    private void changeToOffline() {
        isOnline = false;
        findViewById(R.id.title_setting).setBackgroundResource(R.drawable.icon_setting_off);
        findViewById(R.id.title_view).setBackgroundColor(getResources().getColor(R.color.title_right_view_default));
        findViewById(R.id.title_share).setBackgroundResource(R.drawable.icon_share_off);
        topRel.setBackgroundColor(getResources().getColor(R.color.title_right_view_default));
        view1.setBackgroundColor(getResources().getColor(R.color.title_right_view_default));
        view2.setBackgroundColor(getResources().getColor(R.color.title_right_view_default));
        view3.setBackgroundColor(getResources().getColor(R.color.title_right_view_default));
        mSTemp.setTextColor(getResources().getColor(R.color.title_right_view_default));
        minusBt.setImageResource(R.drawable.icon_minus_off);
        plusBt.setImageResource(R.drawable.icon_plus_off);
        modeBt.setImageResource(R.drawable.mode_cool);
        speedBt.setImageResource(R.drawable.mode_heat);
        switchBt.setImageResource(R.drawable.icon_switch_off);
        efficintBt.setImageResource(R.drawable.icon_efficient_off);
        lockBt.setImageResource(R.drawable.icon_lock_off);
        timerBt.setImageResource(R.drawable.icon_time_off);
    }

    /**
     * 设备上电后显示状态
     */
    private void changeToOnline() {
        isOnline = true;
        findViewById(R.id.title_setting).setBackgroundResource(R.drawable.icon_setting_on);
        findViewById(R.id.title_view).setBackgroundColor(getResources().getColor(R.color.title_right_view_pressed));
        findViewById(R.id.title_share).setBackgroundResource(R.drawable.icon_share_on);
        topRel.setBackgroundColor(getResources().getColor(R.color.title_right_view_pressed));
        view1.setBackgroundColor(getResources().getColor(R.color.title_right_view_pressed));
        view2.setBackgroundColor(getResources().getColor(R.color.title_right_view_pressed));
        view3.setBackgroundColor(getResources().getColor(R.color.title_right_view_pressed));
        mSTemp.setTextColor(getResources().getColor(R.color.title_right_view_pressed));
        minusBt.setImageResource(R.drawable.icon_minus_on);
        plusBt.setImageResource(R.drawable.icon_plus_on);
        modeBt.setImageResource(R.drawable.mode_cool);
        speedBt.setImageResource(R.drawable.mode_heat);
        switchBt.setImageResource(R.drawable.icon_switch_on);
        efficintBt.setImageResource(R.drawable.icon_efficient_on);
        lockBt.setImageResource(R.drawable.icon_lock_on);
        timerBt.setImageResource(R.drawable.icon_time_on);
    }

    private void showModeChoosePopWindow() {
        LayoutInflater mLayoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mLayoutInflater.inflate(R.layout.popwindow_mode, null);
        mModePop = new PopupWindow(view, popRel.getWidth(), (int) getResources().getDimension(R.dimen.width75));
        mModePop.setFocusable(true);
        mModePop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mModePop.showAsDropDown(popRel);
        setAlpha(0.6f);

        ImageView cool = (ImageView) view.findViewById(R.id.pop_cool);
        ImageView wind = (ImageView) view.findViewById(R.id.pop_wind);
        ImageView heat = (ImageView) view.findViewById(R.id.pop_heat);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (mModePop != null && mModePop.isShowing()) {
                    setAlpha(1f);
                    mModePop.dismiss();
                    mModePop = null;
                }
                return false;
            }
        });
    }

    private void setAlpha(float alpha) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = alpha;
        this.getWindow().setAttributes(params);
    }
}
