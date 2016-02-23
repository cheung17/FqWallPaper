package fqpaper.xckj.com.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import fqpaper.xckj.com.bean.BzBean;
import fqpaper.xckj.com.fqwallpaper.R;


public class WallPaperAdapter extends
        RecyclerView.Adapter<WallPaperAdapter.MasonryView> {
    private List<BzBean> BzBeans;
    private Context mContext;
    private LayoutInflater mInflater;
    private int mWidth;
    private OnRecyclerViewItemClickListener listener;

    public interface OnRecyclerViewItemClickListener {
        void onClick(View view, int position);
    }

    ;

    public void setListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public WallPaperAdapter(List<BzBean> list, Context context, int width) {
        BzBeans = list;
        mContext = context;
        mWidth = width;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup viewGroup, int i) {
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.from(mContext).inflate(R.layout.bz_grid_item,
                viewGroup, false);
        return new MasonryView(view);
    }

    @Override
    public void onBindViewHolder(final MasonryView masonryView,
                                 final int position) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mWidth,
                (int) (mWidth * 1.5));
        masonryView.imageView.setLayoutParams(lp);
        Log.i("url ", BzBeans.get(position).getBzUrl());
        Glide.with(mContext).load(BzBeans.get(position).getBzUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(masonryView.imageView);
        masonryView.itemView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(masonryView.itemView, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return BzBeans.size();
    }

    public static class MasonryView extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MasonryView(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_bz);
        }

    }

}