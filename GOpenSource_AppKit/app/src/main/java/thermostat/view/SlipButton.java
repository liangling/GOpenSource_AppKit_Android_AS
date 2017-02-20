package thermostat.view;

/**
 * Created by ljy on 2017/2/17.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.gizwits.opensource.appkit.R;

/**
 * @author xiaanming
 */
public class SlipButton extends View implements View.OnTouchListener {
    private Bitmap bg_on, bg_off, slipper_btn_on, slipper_btn_off;

    /**
     * 按下时的x和当前的x
     */
    private float downX, nowX;

    /**
     * 记录用户是否在滑动
     */
    private boolean onSlip = false;

    /**
     * 当前的状态
     */
    private boolean nowStatus = false;

    /**
     * 监听接口
     */
    private OnChangedListener listener;

    public SlipButton(Context context) {
        super(context);
    }

    public SlipButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        // 载入图片资源
        bg_on = BitmapFactory.decodeResource(getResources(),
                R.drawable.on_drawable);
        bg_off = BitmapFactory.decodeResource(getResources(),
                R.drawable.off_drawable);
        slipper_btn_on = BitmapFactory.decodeResource(getResources(),
                R.drawable.on_thumb);
        slipper_btn_off = BitmapFactory.decodeResource(getResources(),
                R.drawable.off_thumb);

        bg_on = ToHalfBig(bg_on, R.dimen.width20);
        bg_off = ToHalfBig(bg_off, R.dimen.width20);
        slipper_btn_on = ToHalfBig(slipper_btn_on, R.dimen.width17);
        slipper_btn_off = ToHalfBig(slipper_btn_off, R.dimen.width17);

        setOnTouchListener(this);
    }

    private Bitmap ToHalfBig(Bitmap bitmap, int resId) {
        Matrix matrix = new Matrix();

        matrix.postScale(
                getResources().getDimension(resId) / bitmap.getHeight(),
                getResources().getDimension(resId) / bitmap.getHeight()); // 按照高度拉伸
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        float x = 0;
        init();
        // 根据nowX设置背景，开或者关状态
        if (nowX < (bg_on.getWidth() / 2)) {
            canvas.drawBitmap(bg_off, matrix, paint);// 画出关闭时的背景
        } else {
            canvas.drawBitmap(bg_on, matrix, paint);// 画出打开时的背景
        }

        if (onSlip) {// 是否是在滑动状态,
            if (nowX >= bg_on.getWidth())// 是否划出指定范围,不能让滑块跑到外头,必须做这个判断
                x = bg_on.getWidth() - slipper_btn_on.getWidth() - 2;// 减去滑块1/2的长度
            else
                x = nowX - slipper_btn_on.getWidth() / 2;
        } else {
            if (nowStatus) {// 根据当前的状态设置滑块的x值
                x = bg_on.getWidth() - slipper_btn_on.getWidth() - 2;
            } else {
                x = 2;
            }
        }

        // 对滑块滑动进行异常处理，不能让滑块出界
        if (x < 0) {
            x = 2;
        } else if (x > bg_on.getWidth() - slipper_btn_on.getWidth()) {
            x = bg_on.getWidth() - slipper_btn_on.getWidth() - 2;
        }
        int y = (bg_on.getHeight() - slipper_btn_on.getHeight()) / 2;

        if (nowX < (bg_on.getWidth() / 2)) {
            canvas.drawBitmap(slipper_btn_off, x, y, paint);// 画出关闭时滑块
        } else {
            canvas.drawBitmap(slipper_btn_on, x, y, paint);// 画出打开时滑块
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (event.getX() > bg_off.getWidth()
                        || event.getY() > bg_off.getHeight()) {
                    return false;
                } else {
                    onSlip = true;
                    downX = event.getX();
                    nowX = downX;
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                nowX = event.getX();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }
            case MotionEvent.ACTION_UP: {
                boolean oldStatus = nowStatus;
                onSlip = false;
                if (event.getX() >= (bg_on.getWidth() / 2)) {
                    nowStatus = true;
                    nowX = bg_on.getWidth();
                } else {
                    nowStatus = false;
                    nowX = 0;
                }

                if ((nowStatus != oldStatus) && listener != null) {
                    listener.OnChanged(SlipButton.this, nowStatus);
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }
        }
        // 刷新界面
        invalidate();
        return true;
    }

    /**
     * 为WiperSwitch设置一个监听，供外部调用的方法
     *
     * @param listener
     */
    public void setOnChangedListener(OnChangedListener listener) {
        this.listener = listener;
    }

    /**
     * 设置滑动开关的初始状态，供外部调用
     *
     * @param checked
     */
    public void setChecked(boolean checked) {
        if (checked) {
            nowX = bg_off.getWidth();
        } else {
            nowX = 0;
        }
        nowStatus = checked;
        invalidate();
    }

    /**
     * 设置判断checked状态，供外部函数调用
     * added by wangxibin
     */
    public boolean isChecked() {
        return nowStatus;
    }

    /**
     * 回调接口
     *
     * @author len
     */
    public interface OnChangedListener {
        public void OnChanged(SlipButton wiperSwitch, boolean checkState);
    }

}
