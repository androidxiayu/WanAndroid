package com.hy.wanandroid.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by huyin on 2018/4/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

     @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView();

          viewInit();

          dataInit();
     }

     /**
      * 设置布局
      */
     public abstract void setContentView();

     /**
      * 控件初始化
      */
     public abstract void viewInit();

     /**
      * 数据初始化
      */
     public abstract void dataInit();

     @Override
     protected void onSaveInstanceState(Bundle outState) {
          super.onSaveInstanceState(outState);
     }

     @Override
     protected void onRestoreInstanceState(Bundle savedInstanceState) {
          super.onRestoreInstanceState(savedInstanceState);
     }

     @Override
     public boolean dispatchTouchEvent(MotionEvent ev) {
          if (ev.getAction() == MotionEvent.ACTION_DOWN) {
               View v = getCurrentFocus();
               if (isShouldHideKeyboard(v, ev)) {

                    hideKeyboard(v.getWindowToken());
               }
          }
          return super.dispatchTouchEvent(ev);
     }

     /**
      * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
      *
      * @param v
      * @param event
      * @return
      */
     private boolean isShouldHideKeyboard(View v, MotionEvent event) {
          if (v != null && (v instanceof EditText)) {
               int[] l = {0, 0};
               v.getLocationInWindow(l);
               int left = l[0],
                       top = l[1],
                       bottom = top + v.getHeight(),
                       right = left + v.getWidth();
               if (event.getX() > left && event.getX() < right
                       && event.getY() > top && event.getY() < bottom) {
                    // 点击EditText的事件，忽略它。
                    return false;
               } else {
                    return true;
               }
          }
          // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
          return false;
     }

     /**
      * 获取InputMethodManager，隐藏软键盘
      *
      * @param token
      */
     private void hideKeyboard(IBinder token) {
          if (token != null) {
               InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
               im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
          }
     }
}
