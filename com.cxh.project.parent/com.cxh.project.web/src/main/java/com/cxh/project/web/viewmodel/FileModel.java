package com.cxh.project.web.viewmodel; 
/** 
* @author 作者 E-mail: 
* @version 创建时间：2021年7月16日 上午9:56:15 
* 类说明 
*/
public class FileModel {

	private String text;
	
	private String status;
	
	private Boolean successful;

	
	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the successful
	 */
	public Boolean getSuccessful() {
		return successful;
	}

	/**
	 * @param successful the successful to set
	 */
	public void setSuccessful(Boolean successful) {
		this.successful = successful;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
 