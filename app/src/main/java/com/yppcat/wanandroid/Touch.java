package com.yppcat.wanandroid;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class Touch implements GestureDetector.OnGestureListener {


    //一个ACTION_DOWN触发
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    //单击事件
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    //一个DOWN和多个MOVE事件，拖动行为
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    //长按事件
    @Override
    public void onLongPress(MotionEvent e) {

    }

    //快速滑动事件
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


}
