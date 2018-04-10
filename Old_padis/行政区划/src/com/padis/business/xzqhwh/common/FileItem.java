package com.padis.business.xzqhwh.common;

/**
 * <p>Title: FileItem</P>
 * <p>Description: 加入压缩包的文件</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @auther wangjz
 * @version 1.0
 * @since 2009-2-28
 */
public class FileItem {
	private String zipFileName;   //压缩后的zip文件名-包含绝对路径
	private String relativePath;  //压缩包内的相对路径
	private String fileName;      //加入压缩包的文件名-包含绝对路径
	private boolean zKeep;        //是否保留源文件
	

	/**
	 * @param zipFileName
	 * @param relativePath
	 * @param keep
	 */
	public FileItem(String zipFileName, String relativePath, String fileName, boolean keep) {
		super();
		this.zipFileName = zipFileName;
		this.relativePath = relativePath;
		this.fileName = fileName;
		zKeep = keep;
	}
	
	/**
	 * @return the zipFileName
	 */
	public String getZipFileName() {
		return zipFileName;
	}
	/**
	 * @param zipFileName the zipFileName to set
	 */
	public void setZipFileName(String zipFileName) {
		this.zipFileName = zipFileName;
	}
	/**
	 * @return the relativePath
	 */
	public String getRelativePath() {
		return relativePath;
	}
	/**
	 * @param relativePath the relativePath to set
	 */
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
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
	 * @return the zKeep
	 */
	public boolean isZKeep() {
		return zKeep;
	}
	/**
	 * @param keep the zKeep to set
	 */
	public void setZKeep(boolean keep) {
		zKeep = keep;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	
	public boolean equals(Object obj) {
		if(this.fileName.toLowerCase().equals(((FileItem)obj).fileName.toLowerCase())){
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	public int hashCode() {
		return this.fileName.hashCode();
	}
}
