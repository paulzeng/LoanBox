package com.xdroid.loanbox.module.bean;

import com.google.gson.Gson;

public class BannerBean {
	private String img_url;
	private String link;
	private String sort_id;
	private String type;
	private int resource;

	public String getType() {
		return type;
	}

	public String getImg_url() {
		return img_url;
	}

	public String getLink() {
		return link;
	}

	public String getSort_id() {
		return sort_id;
	}

	public int getResource() {
		return resource;
	}

	public void setResource(int resource) {
		this.resource = resource;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
