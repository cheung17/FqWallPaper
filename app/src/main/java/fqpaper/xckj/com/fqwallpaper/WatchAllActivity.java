package fqpaper.xckj.com.fqwallpaper;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import fqpaper.xckj.com.adapter.WallPaperAdapter;
import fqpaper.xckj.com.bean.BzBean;
import fqpaper.xckj.com.util.Datainfo;

public class WatchAllActivity extends Activity implements
        WallPaperAdapter.OnRecyclerViewItemClickListener, OnClickListener {
    private RecyclerView recyclerView;
    private ImageView backIv;
    private TextView titieTv;
    private List<BzBean> dataList = new ArrayList<BzBean>();
    private WallPaperAdapter adapter = null;
    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.acitivyt_recy);
        initViews();
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this
        );
    }
    private void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        backIv = (ImageView) findViewById(R.id.iv_back);
        backIv.setOnClickListener(this);
        titieTv = (TextView) findViewById(R.id.tv_title);
        mIndex = this.getIntent().getIntExtra("index", 0);
        titieTv.setText(Datainfo.categories[mIndex].toUpperCase());
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2,
                StaggeredGridLayoutManager.VERTICAL, false));
        initData();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        adapter = new WallPaperAdapter(dataList, this, screenWidth / 2);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        for (int i = 1; i < 20; i++) {
            BzBean bzBean = new BzBean();
            Log.i("url", Datainfo.URL + Datainfo.categories[mIndex] + "/" + i + ".jpg");
            bzBean.setBzUrl(Datainfo.URL + Datainfo.categories[mIndex] + "/" + i + ".jpg");
            bzBean.setBzName("");
            dataList.add(bzBean);
        }
    }


    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(WatchAllActivity.this,
                SetWallPaperActivity.class);
        intent.putExtra("url", dataList.get(position).getBzUrl());
        startActivity(intent);
        //
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            default:
                break;
        }
    }
}
