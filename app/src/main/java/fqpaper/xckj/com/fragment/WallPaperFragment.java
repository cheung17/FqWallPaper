package fqpaper.xckj.com.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.OnItemClickListener;
import com.bigkoo.convenientbanner.transforms.DefaultTransformer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.google.gson.JsonArray;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fqpaper.xckj.com.adapter.WallPaperAdapter;
import fqpaper.xckj.com.bean.BzBean;
import fqpaper.xckj.com.fqwallpaper.R;
import fqpaper.xckj.com.fqwallpaper.SetWallPaperActivity;
import fqpaper.xckj.com.fqwallpaper.WatchAllActivity;
import fqpaper.xckj.com.util.Datainfo;
import fqpaper.xckj.com.util.NetworkImageHolderView;
import fqpaper.xckj.com.util.SpacesItemDecoration;


@SuppressLint("NewApi")
public class WallPaperFragment extends Fragment implements
        WallPaperAdapter.OnRecyclerViewItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerViewHeader bannerHeader;
    private ConvenientBanner convenientBanner;
    private List<BzBean> dataList = new ArrayList<BzBean>();
    private TextView titieTv;
    private WallPaperAdapter adapter = null;
    private String[] urls = {

            // http://szhp2010.web8.026idc.com/yy_bz/banner/1.jpg
            //flowers", "nature", "ocean", "urban culture", "universe", "beauty"
            "http://szhp2010.web8.026idc.com/yy_bz/beauty/1.jpg",
            "http://szhp2010.web8.026idc.com/yy_bz/flower/1.jpg",
            "http://szhp2010.web8.026idc.com/yy_bz/urban/1.jpg",
            "http://szhp2010.web8.026idc.com/yy_bz/natural/1.jpg",
            "http://szhp2010.web8.026idc.com/yy_bz/ocean/1.jpg",
            "http://szhp2010.web8.026idc.com/yy_bz/universe/1.jpg"};
    private String[] names =
            //{"beauty", "flower", "urban", "natural ", "ocean", "universe"};
            {"beauty", "flower", "urban", "natural", "ocean", "universe"};

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallpaper, container,
                false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        bannerHeader = RecyclerViewHeader.fromXml(getActivity(), R.layout.banner);
        convenientBanner = (ConvenientBanner) bannerHeader.findViewById(R.id.convenientBanner);
        initViews();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initViews() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity()
                .getApplicationContext(), 2,
                StaggeredGridLayoutManager.VERTICAL, false));
        // 2016/02/23 不要banner了
        // bannerHeader.attachTo(recyclerView);
        //加载banner
        //initBanner();
        //加载分类
        initData();
        // 设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(1);
        recyclerView.addItemDecoration(decoration);
        //ReCyclerViewHea

    }
    //加载banner图片

    /**
     * 加载分类
     */
    private void initData() {
        for (int j = 0; j < Datainfo.categories.length; j++) {
            for (int i = 0; i < 30; i++) {
                BzBean bzBean = new BzBean();
                bzBean.setBzName("");
                bzBean.setBzUrl(Datainfo.URL + Datainfo.categories[j] + "/" + (i + 1)
                        + ".jpg");
                dataList.add(bzBean);
            }
        }
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        adapter = new WallPaperAdapter(dataList, getActivity(), screenWidth / 2);
        adapter.setListener((WallPaperAdapter.OnRecyclerViewItemClickListener) this);
        recyclerView.setAdapter(adapter);
    }

    public void onClick(View view, int position) {

        Intent intent = new Intent(getActivity(), SetWallPaperActivity.class);
        intent.putExtra("url", dataList.get(position).getBzUrl());
        startActivity(intent);
    }


    private void initBanner() {
        final List<String> list = new ArrayList<>();
        list.add("http://szhp2010.web8.026idc.com/yy_bz/banner/1.jpg");
        list.add("http://szhp2010.web8.026idc.com/yy_bz/banner/2.jpg");
        list.add("http://szhp2010.web8.026idc.com/yy_bz/banner/3.jpg");
        list.add("http://szhp2010.web8.026idc.com/yy_bz/banner/4.jpg");
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, list).setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused}).setOnItemClickListener(
                new OnItemClickListener() {
                    public void onItemClick(int position) {
                        startActivity(new Intent(getActivity(), SetWallPaperActivity.class).putExtra("url", list.get(position)));
                    }
                }).setPageTransformer(new DefaultTransformer()).startTurning(2000);

    }
}
