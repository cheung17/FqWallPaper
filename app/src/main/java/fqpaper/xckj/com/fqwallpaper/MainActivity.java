package fqpaper.xckj.com.fqwallpaper;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;
import fqpaper.xckj.com.adapter.SlideAdapter;
import fqpaper.xckj.com.fragment.AppFragment;
import fqpaper.xckj.com.fragment.WallPaperFragment;


public class MainActivity extends InstrumentedActivity implements OnClickListener {
    private Button button;
    private LinearLayout mFangdaoLayout, mInfoLayout;
    private Fragment mFangdao, mInfo;
    private ImageView wallpaperIv, appIv;
    private DrawerLayout drawerLayout;
    private ImageView menuIv;
    private TextView titleTv, statusBar;
    private RelativeLayout homeItem;
    private ListView listView;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        //JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //  JPushInterface.onPause(this);
        MobclickAgent.onPause(this
        );
    }

    private void initViews() {
        // 超时10分钟 session过期 友盟用户统计
        MobclickAgent.setSessionContinueMillis(1000 * 60 * 10);
        // 魔晶
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mFangdaoLayout = (LinearLayout) findViewById(R.id.ll_fangdao);
        mInfoLayout = (LinearLayout) findViewById(R.id.ll_info);
        mFangdaoLayout.setOnClickListener(this);
        mInfoLayout.setOnClickListener(this);
        wallpaperIv = (ImageView) findViewById(R.id.iv_fangdao);
        appIv = (ImageView) findViewById(R.id.iv_info);

        homeItem = (RelativeLayout) findViewById(R.id.home_layout);
        homeItem.setOnClickListener(this);
        initSlideMenu();
        selectFrag(0);

    }

    private void initSlideMenu() {
        //  homeItem.setBackgroundColor(Color.parseColor("#DDDDDD"));
        titleTv = (TextView) findViewById(R.id.tv_title);
        menuIv = (ImageView) findViewById(R.id.iv_back);

        menuIv.setOnClickListener(this);
        menuIv.setImageResource(R.drawable.menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        listView = (ListView) findViewById(R.id.listview);
        SlideAdapter adapter = new SlideAdapter(getApplicationContext(), 2000);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 4) {
                    startActivity(new Intent(MainActivity.this, AboutActivity.class));
                    return;
                }
                Intent intent = new Intent(MainActivity.this, WatchAllActivity.class);
                intent.putExtra("index", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (v.getId()) {
            case R.id.ll_fangdao:
                selectFrag(0);
                break;
            case R.id.ll_info:
                selectFrag(1);
                break;
            case R.id.iv_back:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.home_layout:
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private long firstTime = 0;

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }
        if (secondTime - firstTime > 2000) { // 如果两次按键时间间隔大于2秒，则不退出
            Toast.makeText(this, "click again will be finished", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;// 更新firstTime
        } else { // 两次按键小于2秒时，退出应用
            finish();
            System.exit(0);
        }
    }

    public void selectFrag(int i) {
        FragmentManager fm = getFragmentManager(); // 得到管理
        FragmentTransaction transaction = fm.beginTransaction(); // 开启失误
        hideFragment(transaction);
        switch (i) {
            case 0:
                titleTv.setText("SEXYBELLA");
                wallpaperIv.setImageResource(R.drawable.home_focus);
                appIv.setImageResource(R.drawable.personal_dark);
                if (mFangdao == null) {
                    mFangdao = new WallPaperFragment();
                    transaction.add(R.id.framelayout_main, mFangdao);
                } else {
                    transaction.show(mFangdao);
                }
                break;
            case 1:
                titleTv.setText("APP");
                appIv.setImageResource(R.drawable.personal_focus);
                wallpaperIv.setImageResource(R.drawable.home_dark);
                if (mInfo == null) {
                    mInfo = new AppFragment();
                    transaction.add(R.id.framelayout_main, mInfo);
                } else {
                    transaction.show(mInfo);
                }
                break;

            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {

        if (mFangdao != null) {
            //fuckwo
            wallpaperIv.setImageResource(R.drawable.home_dark);
            transaction.hide(mFangdao);
        }
        if (mInfo != null) {
            appIv.setImageResource(R.drawable.personal_dark);
            transaction.hide(mInfo);
        }

    }

}
