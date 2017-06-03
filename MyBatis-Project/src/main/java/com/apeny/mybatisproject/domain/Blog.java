package com.apeny.mybatisproject.domain;

public class Blog {
	
	private Integer id;
	private String title;
	private String digest;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	
	@Override
	public String toString() {
		return "Blog [id=" + id + ", title=" + title + ", digest=" + digest + "]";
	}
}
