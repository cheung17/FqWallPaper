package fqpaper.xckj.com.fragment;

import  java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import fqpaper.xckj.com.adapter.LvAdapter;
import fqpaper.xckj.com.bean.AppBean;
import fqpaper.xckj.com.fqwallpaper.R;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AppFragment extends Fragment implements OnRefreshListener {
	private ListView listView;
	private SwipeRefreshLayout swipeRefreshLayout;
	private List<AppBean> dataList = new ArrayList<AppBean>();
	private LvAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_app, container, false);
		listView = (ListView) view.findViewById(R.id.listview);
       // swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        //swipeRefreshLayout.setOnRefreshListener(this);
        //swipeRefreshLayout.setColorSchemeResources(R.color.title_bg);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		LoadData();
	}
	private void LoadData() {
		if (swipeRefreshLayout!=null) {
			swipeRefreshLayout.setRefreshing(true);
		}
		
		String url = "http://www.mtt125.com/Bt/GetPushAppDownloadData"
				+ "?mid=929062ee-1e4e-439f-a68a-b1106ea51e7d&appsid=a84aedcf"
				+ "-4ec2-4244-829e-fe52cd2f85b9&imeino=358313042578059_D4206D48EA5F&isinner="
				+ "0&mobile=13000000000&mobiletype=HTC_Sensation_Z710e&appskey="
				+ "c5ce45d2-505c-4463-9b61-e41aa53a32c3&netConnectType=wifi&leftspace=3166044160&version=2.14.01.22&"
				+ "imsino=null&local=zh_CN&showtime=0&notshowtime=0&todayshowtime=0&todaynotshowtime=0&uLeftShowtotal=0&urightShowtotal=0"
				+ "&udownShowtotal=0&uLeftShow=0&urightShow=0&udownShow=0&errtime=0&errcode=&appcnt=10&debug=true";
		FinalHttp finalHttp = new FinalHttp();
		finalHttp.get(url, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				parseXml(t.toString());
				adapter = new LvAdapter(dataList, getActivity());
				listView.setAdapter(adapter);
				if (swipeRefreshLayout!=null) {
					swipeRefreshLayout.setRefreshing(false);
				}
				
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				if (swipeRefreshLayout!=null) {
					swipeRefreshLayout.setRefreshing(false);
				}
				
			}
		});
	}
	@SuppressWarnings("unchecked")
	protected void parseXml(String xml) {
		dataList.clear();
		InputStream xmlIs;
		try {
			xmlIs = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			SAXReader saxReader = new SAXReader();
			Element root = saxReader.read(xmlIs).getDocument().getRootElement();
			List<Element> elmList = root.elements("app");
			// Toast.makeText(getActivity(), elmList.size()+"", 1).show();
			for (int i = 0; i < elmList.size(); i++) {
				AppBean app = new AppBean();
				Element element = elmList.get(i);
				app.setName(element.element("appname").getStringValue() + "");
				app.setImgUrl(element.element("iconUrl").getStringValue() + "");
				app.setUrl(element.element("url").getStringValue() + "");
				app.setDetail(element.element("description").getStringValue()
						+ "");
				dataList.add(app);
			}
			adapter=new LvAdapter(dataList, getActivity());
			listView.setAdapter(adapter);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		 
	}

	@Override
	public void onRefresh() {
        LoadData();		
	}
}
