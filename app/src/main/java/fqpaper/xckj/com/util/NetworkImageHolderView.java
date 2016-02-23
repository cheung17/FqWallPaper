package fqpaper.xckj.com.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.koushikdutta.ion.Ion;

/**
 * Created by Administrator on 2015-12-08.
 */
public class NetworkImageHolderView implements CBPageAdapter.Holder<String> {
    protected ImageView imageView;

    public View createView(final Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("view", "onClick");
            }
        });
        return imageView;
    }

    public void UpdateUI(Context context, int position, String data) {
        Ion.with(imageView).load(data);
    }
}