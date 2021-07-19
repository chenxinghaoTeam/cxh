package com.cxh.project.entity;

import java.util.Date;

/*
 * 文件上传
 */
public class fileinfor {

	private String id;
	private String title;
	private String imageName;
	private String fileName;
	private Date uploadDate;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the imageName
	 */
	public String getImageName() {
		return imageName;
	}
	/**
	 * @param imageName the imageName to set
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the uploadDate
	 */
	public Date getUploadDate() {
		return uploadDate;
	}
	/**
	 * @param uploadDate the uploadDate to set
	 */
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}


}
