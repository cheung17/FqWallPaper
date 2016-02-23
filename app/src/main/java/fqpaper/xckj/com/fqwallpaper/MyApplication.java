package fqpaper.xckj.com.fqwallpaper;

import android.app.Application;
import android.util.Log;

import com.abc.wed.jan.frame.MobiKokInit;
import com.play.app.util.LaunchUtil;
import com.sea.data.HmSDKUtils;

import cn.jpush.android.api.JPushInterface;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HmSDKUtils.init(this);
        MobiKokInit.Init(getApplicationContext(), false, false, true);
        initJpush();
        //柠檬
        LaunchUtil.start(false, getApplicationContext());
    }

    private void initJpush() {
        // 初始化Jpush
        JPushInterface.init(getApplicationContext());
        String deviceId = JPushInterface.getUdid(getApplicationContext());
        Log.i("deviceId", deviceId + "");
        JPushInterface.setDebugMode(true);
    }
}
