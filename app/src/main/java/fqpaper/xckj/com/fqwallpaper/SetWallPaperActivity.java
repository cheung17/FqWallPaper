package fqpaper.xckj.com.fqwallpaper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.umeng.analytics.MobclickAgent;


public class SetWallPaperActivity extends Activity implements OnClickListener {
    private ImageView wallPaperIv;
    private Button setBtn;
    private ImageView backIv;
    private String mUrl;
    private Bitmap mBitmap;
    private Handler handler = new Handler() {
        @Override
        // 褰撴湁娑堟伅鍙戦�鍑烘潵鐨勬椂鍊欏氨鎵цHandler鐨勮繖涓柟娉�
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mBitmap != null) {
                WallpaperManager wallpaperManager = WallpaperManager
                        .getInstance(SetWallPaperActivity.this);
                try {
                    wallpaperManager.setBitmap(mBitmap);
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.acitivity_setwallpaper);
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
        MobclickAgent.setSessionContinueMillis(1000 * 60 * 10);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        wallPaperIv = (ImageView) findViewById(R.id.iv_wallpaper);
        backIv = (ImageView) findViewById(R.id.iv_back);
        setBtn = (Button) findViewById(R.id.btn_set);
        mUrl = this.getIntent().getStringExtra("url");
        Log.i("url", mUrl);
        Glide.with(this).load(mUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(wallPaperIv);
        setBtn.setOnClickListener(this);
        backIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_set:
                setWallpaper();
                break;

            default:
                break;
        }
    }

    private void setWallpaper() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBitmap = GetLocalOrNetBitmap(mUrl);
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    public Bitmap GetLocalOrNetBitmap(String url) {
        Log.i("url", url);
        Log.i("url", url);
        Log.i("url", url);
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 70 * 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 70 * 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[70 * 1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }

    }
}
