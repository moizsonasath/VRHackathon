package org.gearvrf.gvrbullet;


import org.gearvrf.GVRActivity;
import org.gearvrf.utility.Log;
import org.gearvrf.gvrbullet.VRTouchPadGestureDetector;
import org.gearvrf.gvrbullet.VRTouchPadGestureDetector.OnTouchPadGestureListener;
import org.gearvrf.gvrbullet.VRTouchPadGestureDetector.SwipeDirection;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class BulletSampleActivity extends GVRActivity implements
        OnTouchPadGestureListener {
    private long lastDownTime;
    BulletSampleMain viewManager;
    private long mLatestTap = 0;
    private static final int TAP_INTERVAL = 300;
    private VRTouchPadGestureDetector mDetector = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewManager = new BulletSampleMain();
        mDetector = new VRTouchPadGestureDetector(this);
        setMain(viewManager, "gvr.xml");
    }
/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            lastDownTime = event.getDownTime();
        }

        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            if (event.getEventTime() - lastDownTime < 200) {
                viewManager.onTap();
            }
        }
        return true;
    }
*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTap(MotionEvent e) {
        Log.v("", "onSingleTap");
        if (System.currentTimeMillis() > mLatestTap + TAP_INTERVAL) {
            mLatestTap = System.currentTimeMillis();
            //viewManager.onTap();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onSwipe(MotionEvent e, SwipeDirection swipeDirection,
                           float velocityX, float velocityY) {
        Log.e("", "onSwipe");

        if (swipeDirection.equals(SwipeDirection.Forward)) {
            viewManager.onSwipe(velocityX);
        }
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    viewManager.moveLeft();
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    viewManager.moveRight();
                }
                return true;
            case KeyEvent.KEYCODE_BACK:
                if (action == KeyEvent.ACTION_DOWN) {
                    viewManager.onTap();
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
}
