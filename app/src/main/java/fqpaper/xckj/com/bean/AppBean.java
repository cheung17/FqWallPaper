package fqpaper.xckj.com.bean;

public class AppBean {
	private String url;
	private String name;
	private String imgUrl;
	private String detail;   
	public AppBean() {
		super();
	}
	public AppBean(String url, String name, String imgUrl, String detail) {
		this.url = url;
		this.name = name;
		this.imgUrl = imgUrl;
		this.detail = detail;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
