package thermostat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gizwits.opensource.appkit.R;

/**
 * Created by ljy on 2017/2/16.
 */

public class TempControlActivity extends Activity implements View.OnClickListener {

    private RelativeLayout topRel;
    private RelativeLayout popRel;
    private View view1, view2, view3;
    private TextView mHumidity, mTemp, mMode, mSpeed, mSTemp;
    private ImageView minusBt, plusBt, modeBt, speedBt, switchBt, efficintBt, lockBt, timerBt;

    private boolean isOnline = true;

    private PopupWindow mModePop;

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
                break;
            case R.id.efficint_bt:
                break;
            case R.id.lock_bt:
                break;
            case R.id.timer_bt:
                if (isOnline) {
                    startActivity(new Intent(TempControlActivity.this, TimerSettingActivity.class));
                }
                break;
        }
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
