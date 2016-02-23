package fqpaper.xckj.com.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fqpaper.xckj.com.bean.AppBean;
import fqpaper.xckj.com.fqwallpaper.R;
import fqpaper.xckj.com.util.Datainfo;

/**
 * Created by Administrator on 2016/2/1.
 */
public class SlideAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;

    public SlideAdapter(Context context, int index) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return 5;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MyViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyViewHolder();
            convertView = mInflater.inflate(R.layout.item_slide, viewGroup, false);
            viewHolder.nameTv = (TextView) convertView
                    .findViewById(R.id.tv_menu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        if (i == 4) {
            viewHolder.nameTv.setText("ABOUT");
        } else {
            viewHolder.nameTv.setText(Datainfo.categories[i].toUpperCase());
        }
        return convertView;
    }

    class MyViewHolder {
        TextView nameTv;
    }
}
