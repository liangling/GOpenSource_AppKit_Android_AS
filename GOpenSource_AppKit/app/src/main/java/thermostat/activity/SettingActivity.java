package thermostat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gizwits.opensource.appkit.R;

/**
 * Created by ljy on 2017/2/17.
 */

public class SettingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("设 置");
        ImageButton back = (ImageButton) findViewById(R.id.title_left_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
