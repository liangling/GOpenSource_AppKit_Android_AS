package thermostat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gizwits.opensource.appkit.R;

import thermostat.view.SlipButton;

/**
 * Created by ljy on 2017/2/17.
 */

public class TimerListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
//    private List<Building> mList;

    public TimerListAdapter(Context context/*, List<Building> list*/) {
        this.mContext = context;
//        this.mList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
//        return mList.size();
        return 3;
    }

    @Override
    public Object getItem(int position) {
//        return mList.get(position);
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_timer_list, null);
            viewHolder.time = (TextView) convertView.findViewById(R.id.item_time_txt);
            viewHolder.temp = (TextView) convertView.findViewById(R.id.item_temp_txt);
            viewHolder.repeat = (TextView) convertView.findViewById(R.id.item_repeat_txt);
            viewHolder.open = (SlipButton) convertView.findViewById(R.id.item_slip_button);
            viewHolder.delete = (ImageView) convertView.findViewById(R.id.item_delete_bt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        Building building = mList.get(position);
//        viewHolder.buildName.setText(building.building_name);
        return convertView;
    }

    private class ViewHolder {
        TextView time;
        TextView temp;
        TextView repeat;
        SlipButton open;
        ImageView delete;
    }
}
