package com.shunyin.common.upload;

/**
 * 上传
 * @author chenwenjun
 *
 */
public class UpResult {
	
	public UpResult() {
		super();
	}
	

	public UpResult(String fileName, String uuidName, String destDir, String filePath) {
		super();
		this.fileName = fileName;
		this.uuidName = uuidName;
		this.destDir = destDir;
		this.filePath = filePath;
	}


	// 原文件名
	private String fileName;
	// 新文件名
	private String uuidName;
	// 文件目录
	private String destDir;
	// 文件地址
	private String filePath;
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDestDir() {
		return destDir;
	}
	public void setDestDir(String destDir) {
		this.destDir = destDir;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getUuidName() {
		return uuidName;
	}
	public void setUuidName(String uuidName) {
		this.uuidName = uuidName;
	}
	
	
	
}
