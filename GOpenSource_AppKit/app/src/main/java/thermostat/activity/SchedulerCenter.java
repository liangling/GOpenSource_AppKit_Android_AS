package thermostat.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.gizwits.gizwifisdk.api.GizDeviceScheduler;
import com.gizwits.gizwifisdk.api.GizDeviceSchedulerCenter;
import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.enumration.GizScheduleWeekday;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.gizwits.gizwifisdk.listener.GizDeviceSchedulerCenterListener;
import com.gizwits.opensource.appkit.CommonModule.GosBaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ll on 2017/3/6.
 */

public class SchedulerCenter {
    private  static void addSingleTimer(String uid, String token, GizWifiDevice gizWifiDevice, ConcurrentHashMap<String, Object> concurrentHashMap,
                                String date, String time, GizDeviceSchedulerCenterListener listener) {

        // 设置定时任务监听
        GizDeviceSchedulerCenter.setListener(listener);

        // 一次性定时任务，在2017年1月16日早上6点30分开灯
        GizDeviceScheduler scheduler = new GizDeviceScheduler();
        scheduler.setDate(date);
        scheduler.setTime(time);
        scheduler.setRemark("不重复的定时任务");
//        ConcurrentHashMap<String, Object> attrs = new ConcurrentHashMap<String, Object>();
//        attrs.put(FANSWITCH, true);
//        attrs.put(KEY_LOCK, 1);

        scheduler.setAttrs(concurrentHashMap);

//        SharedPreferences spf = context.getSharedPreferences(GosBaseActivity.SPF_Name, Context.MODE_PRIVATE);
//        String uid = spf.getString("Uid", "");
//        String token = spf.getString("Token", "");

        // 创建设备的定时任务，mDevice为在设备列表中得到的设备对象
        GizDeviceSchedulerCenter.createScheduler(uid, token, gizWifiDevice, scheduler);
    }

    private static void addRepeatWeeksTimer(String uid, String token, GizWifiDevice gizWifiDevice, ConcurrentHashMap<String, Object> concurrentHashMap, String date, String time,
                                     List<GizScheduleWeekday> weekdayList, GizDeviceSchedulerCenterListener listener) {

        // 设置定时任务监听
        GizDeviceSchedulerCenter.setListener(listener);

        // 一次性定时任务，在2017年1月16日早上6点30分开灯
        GizDeviceScheduler scheduler = new GizDeviceScheduler();
        scheduler.setDate(date);
        scheduler.setTime(time);
        scheduler.setWeekdays(weekdayList);
        scheduler.setRemark("重复的定时任务");

        scheduler.setAttrs(concurrentHashMap);

        // 创建设备的定时任务，mDevice为在设备列表中得到的设备对象
        GizDeviceSchedulerCenter.createScheduler(uid, token, gizWifiDevice, scheduler);
    }

    public static void editTimer(String uid, String token, GizDeviceScheduler scheduler, GizWifiDevice device, GizDeviceSchedulerCenterListener listener) {
// 设置定时任务监听
        GizDeviceSchedulerCenter.setListener(listener);
// 把之前创建好的一次性定时任务修改成每月1号和15号重复执行的定时任务，scheduler是定时任务列表中要修改的定时任务对象
//        scheduler.setTime("06:30");
//        scheduler.setRemark("开灯任务");
//        ConcurrentHashMap<String, Object> attrs = new ConcurrentHashMap<String, Object>();
//        attrs.put("LED_OnOff", true);
//        scheduler.setAttrs(attrs);
//        List<Integer> monthDays = new ArrayList<Integer>();
//        monthDays.add(1);
//        monthDays.add(15);
//        scheduler.setMonthDays(monthDays);
// 修改设备的定时任务，mDevice是设备列表中要创建定时任务的设备对象
        GizDeviceSchedulerCenter.editScheduler(uid, token, device, scheduler);

    }

    public static void deleteTime(String uid, String token, GizWifiDevice gizWifiDevice, String scheduleId, GizDeviceSchedulerCenterListener listener) {
        // 设置定时任务监听
        GizDeviceSchedulerCenter.setListener(listener);
// 删除设备的定时任务列表，mDevice为在设备列表中得到的设备对象，your_scheduler_id是要删除的定时任务ID
        GizDeviceSchedulerCenter.deleteScheduler(uid, token, gizWifiDevice, scheduleId);
// 实现回调
//        GizDeviceSchedulerCenterListener mListener = new GizDeviceSchedulerCenterListener() {
//            @Override
//            public void didUpdateSchedulers(GizWifiErrorCode result, GizWifiDevice schedulerOwner, List<GizDeviceScheduler> schedulerList) {
//                if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {
//                    // 定时任务删除成功
//                } else {
//                    // 删除失败
//                }
//            }
//        };
    }

    public static void getTimerList(String uid, String token, GizWifiDevice gizWifiDevice, GizDeviceSchedulerCenterListener listener) {
        // 同步更新设备的定时任务列表，mDevice为在设备列表中得到的设备对象
        GizDeviceSchedulerCenter.updateSchedulers(uid, token, gizWifiDevice);
        GizDeviceSchedulerCenter.setListener(listener);
    }


}
