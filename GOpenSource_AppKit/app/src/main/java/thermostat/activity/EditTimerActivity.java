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

public class EditTimerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_time);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("编辑定时");
        ImageButton cancle = (ImageButton) findViewById(R.id.title_left_btn);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ImageButton confirm = (ImageButton) findViewById(R.id.title_right_btn);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
