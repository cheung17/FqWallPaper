package fqpaper.xckj.com.adapter;

import   java.io.File;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import fqpaper.xckj.com.bean.AppBean;
import fqpaper.xckj.com.fqwallpaper.R;


public class LvAdapter extends BaseAdapter {
	private List<AppBean> dataList;
	private LayoutInflater mInflater;
	private Context mContext;

	@Override
	public int getCount() {
		return dataList.size();
	}

	public LvAdapter(List<AppBean> dataList, Context context) {
		this.dataList = dataList;
		mInflater = LayoutInflater.from(context);
		mContext = context;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new MyViewHolder();
			convertView = mInflater.inflate(R.layout.item_app, parent, false);
			viewHolder.avatarIv = (ImageView) convertView
					.findViewById(R.id.iv_avatar);
			viewHolder.detailTv = (TextView) convertView
					.findViewById(R.id.tv_detail);
			viewHolder.nameTv = (TextView) convertView
					.findViewById(R.id.tv_name);
			viewHolder.installBtn = (Button) convertView
					.findViewById(R.id.btn_install);
			viewHolder.statusTv = (TextView) convertView
					.findViewById(R.id.tv_status);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (MyViewHolder) convertView.getTag();
		}
		fillData(position, viewHolder);
		return convertView;
	}

	private void fillData(final int position, final MyViewHolder viewHolder) {
        Glide.with(mContext).load(dataList.get(position).getImgUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.avatarIv);
		viewHolder.detailTv.setText(dataList.get(position).getDetail());
		viewHolder.nameTv.setText(dataList.get(position).getName());
		viewHolder.installBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, "INSTALL", Toast.LENGTH_LONG).show();
				downloadFile(position, viewHolder);
			}
		});

	}

	protected void downloadFile(int positon, final MyViewHolder holder) {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/download/szbz/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		} 
		FinalHttp finalHttp = new FinalHttp();
		String url = dataList.get(positon).getUrl();
		path = path + dataList.get(positon).getName();
		file=new File(path);
		if (file.exists()) {
			holder.installBtn.setVisibility(View.INVISIBLE);
			holder.statusTv.setVisibility(View.VISIBLE);
			holder.statusTv.setText("downloaded");
			return;
		}
		final String pathUri = path;
		Log.i("path", path);
		finalHttp.download(url, path, new AjaxCallBack<File>() {
			@Override
			public void onStart() {
				super.onStart();
				holder.installBtn.setVisibility(View.INVISIBLE);
				holder.statusTv.setVisibility(View.VISIBLE);
				holder.statusTv.setText("0%");
			}

			@Override
			public void onSuccess(File t) {
				super.onSuccess(t);
				holder.statusTv.setText("downloaded");
				inStallApk(mContext, pathUri);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				holder.installBtn.setVisibility(View.VISIBLE);
				holder.statusTv.setVisibility(View.INVISIBLE);
				Log.i("info", t.toString());
			}

			@Override
			public void onLoading(long count, long current) {
				super.onLoading(count, current);
				Log.i(count + "", current + "");
				holder.statusTv.setText(current / count + " %");
			}
		});
	}

	public void inStallApk(Context context, String path) {
		try {
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(new File(path)),
					"application/vnd.android.package-archive");
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class MyViewHolder {
		TextView nameTv, detailTv, statusTv;
		ImageView avatarIv;
		Button installBtn;
	}
}
