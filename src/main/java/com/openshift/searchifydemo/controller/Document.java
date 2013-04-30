package com.openshift.searchifydemo.controller;

public class Document {

	private String id;
	
	private String text;
	
	public Document() {
	}

	public Document(String id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
}
