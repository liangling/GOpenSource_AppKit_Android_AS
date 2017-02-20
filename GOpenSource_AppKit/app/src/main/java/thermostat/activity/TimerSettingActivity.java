package thermostat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.gizwits.opensource.appkit.R;

import thermostat.adapter.TimerListAdapter;

/**
 * Created by ljy on 2017/2/16.
 */

public class TimerSettingActivity extends Activity {

    private ListView timerListView;
    private TimerListAdapter timerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_setting);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("定时设置");
        ImageButton back = (ImageButton) findViewById(R.id.title_left_btn);
        back.setBackgroundResource(R.drawable.icon_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageButton add = (ImageButton) findViewById(R.id.title_right_btn);
        add.setBackgroundResource(R.drawable.icon_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        initViews();
        initData();
    }

    private void initViews() {
        timerListView = (ListView) findViewById(R.id.timer_list);
    }

    private void initData() {
        timerListAdapter = new TimerListAdapter(this);
        timerListView.setAdapter(timerListAdapter);
    }
}
