package com.padis.business.xzqhwh.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.padis.common.zip.ZipEntry;
import com.padis.common.zip.ZipFile;
import com.padis.common.zip.ZipOutputStream;


public class ZipUtil {
	private String zipFileName ;
	private List fileList;
	public ZipUtil(){
		super();
	}
	
	public ZipUtil(String zipFileName){
		this.zipFileName = zipFileName; 
	}
	
	private void init(){
		if(fileList == null){
			fileList = new ArrayList();
		}
	}
	
	/**
	 * <p>Title: addFile</p>
	 * <p>Description: ����ļ����ļ���</p>
	 * @param directory ��ӵ��ļ������ļ�����-��������·��
	 * @param zKeep �Ƿ���Դ�ļ�����Դ�ļ���
	 * @author wangjz
	 * @since 2009-2-28
	 */
	public void addFile(String directory, boolean zKeep){
		addFile(directory, "", zKeep);
	}
	
	/**
	 * <p>Title: addFile</p>
	 * <p>Description: ����ļ����ļ���</p>
	 * @param directory ��ӵ��ļ������ļ�����-��������·��
	 * @param relativePath ���·��
	 * @param zKeep �Ƿ���Դ�ļ�����Դ�ļ���
	 * @author wangjz
	 * @since 2009-2-28
	 */
	public void addFile(String directory, String relativePath, boolean zKeep){
		init();
		FileItem item = new FileItem(this.zipFileName,relativePath,directory,zKeep);
		if(fileList.contains(item)) return;
		fileList.add(item);
	}
	
	/**
	 * <p>Title: addFile</p>
	 * <p>Description: ����ļ����ļ���</p>
	 * @param directory ��ӵ��ļ������ļ�����-��������·��
	 * @author wangjz
	 * @since 2009-2-28
	 */
	public void addFile(String directory){
		addFile(directory, "");
	}
	
	/**
	 * <p>Title: addFile</p>
	 * <p>Description: ����ļ����ļ���</p>
	 * @param directory ��ӵ��ļ������ļ�����-��������·��
	 * @param relativePath ���·��
	 * @author wangjz
	 * @since 2009-2-28
	 */
	public void addFile(String directory, String relativePath){
		init();
		FileItem item = new FileItem(this.zipFileName, relativePath, directory, true);
		if(fileList.contains(item)) return;
		fileList.add(item);
	}
	
	
	/**
	 * <p>Title: zipDone</p>
	 * <p>Description: ѹ����ӵ��ļ�</p>
	 * @throws Exception
	 * @author wangjz
	 * @since 2009-2-28
	 */
	public void zipDone() throws Exception{
		if(fileList == null || fileList.size() == 0) return;
		String fileName = zipFileName;
		FileItem fileItem = (FileItem)fileList.get(0);
		if (fileName == null || fileName.trim().equals("")) {  
			File temp = new File(fileItem.getFileName());
			if (temp.isDirectory()) {
				fileName = fileItem.getFileName() + ".zip";
			} else {
				if (fileItem.getFileName().indexOf(".") > 0) {
					fileName = fileItem.getFileName().substring(0, fileItem.getFileName().lastIndexOf("."))+ ".zip";
				} else {
					fileName = fileItem.getFileName() + ".zip";
				}
			}
		}
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(fileName));
		try {
			for(int i = 0; i < fileList.size(); i++){
				FileItem fileItems = (FileItem)fileList.get(i);
				zip(zos, fileItems.getRelativePath(), fileItems.getFileName());
				if(!fileItems.isZKeep()){
					FileUtils.deleteDir(fileItems.getFileName());
				}
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (null != zos) {
				zos.close();
			}
		}
	}
	
	/**
	 * <p>Title: zip</p>
	 * <p>Description: ѹ��</p>
	 * @param zos ѹ�������
	 * @param relativePath   ���·��
	 * @param absolutPath  �ļ����ļ��о���·��
	 * @throws IOException
	 * @author wangjz
	 * @since 2009-2-26
	 */
	private void zip(ZipOutputStream zos, String relativePath,
			String absolutPath) throws IOException {
		File file = new File(absolutPath);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				File tempFile = files[i];
				if (tempFile.isDirectory()) {
					String newRelativePath = relativePath + tempFile.getName()
							+ File.separator;
					//createZipNode(zos, newRelativePath);
					zip(zos, newRelativePath, tempFile.getPath());
				} else {
					zipFile(zos, tempFile, relativePath);
				}
			}
		} else {
			zipFile(zos, file, relativePath);
		}
	}

	/**
	 * <p>Title: zipFile</p>
	 * <p>Description: ѹ���ļ�</p>
	 * @param zos  ѹ�������
	 * @param file  �ļ�����
	 * @param relativePath  ���·��
	 * @throws IOException
	 * @author wangjz
	 * @since 2009-2-26
	 */
	private void zipFile(ZipOutputStream zos, File file,
			String relativePath) throws IOException {
		ZipEntry entry = new ZipEntry(relativePath + file.getName());
		zos.putNextEntry(entry);
		
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			int BUFFERSIZE = 2 << 10;
			int length = 0;
			byte[] buffer = new byte[BUFFERSIZE];
			while ((length = is.read(buffer, 0, BUFFERSIZE)) >= 0) {
				zos.write(buffer, 0, length);
			}
			zos.flush();
			zos.closeEntry();
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (null != is) {
				is.close();
			}
		}
	}

	/**
	 * <p>Title: createZipNode</p>
	 * <p>Description: ����Ŀ¼</p>
	 * @param zos  zip�����
	 * @param relativePath   ���·��
	 * @throws IOException
	 * @author wangjz
	 * @since 2009-2-26
	 */
	private void createZipNode(ZipOutputStream zos, String relativePath)
			throws IOException {
		ZipEntry zipEntry = new ZipEntry(relativePath);
		zos.putNextEntry(zipEntry);
		zos.closeEntry();
	}


	/**
	 * <p>Title: unzip</p>
	 * <p>Description: ��ѹ��zip��</p>
	 * @param zipFilePath
	 * 				zip�ļ�·��
	 * @param targetPath
	 * 				��ѹ������λ�ã����Ϊnull����ַ�����Ĭ�Ͻ�ѹ������zip��ͬĿ¼��zip��ͬ�����ļ�����
	 * @throws IOException
	 * @author wangjz
	 * @since 2009-2-26
	 */
	public static void unzip(String zipFilePath, String targetPath)
			throws IOException {
		OutputStream os = null;
		InputStream is = null;
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(zipFilePath);
			String directoryPath = "";
			if (null == targetPath || "".equals(targetPath)) {
				directoryPath = zipFilePath.substring(0, zipFilePath
						.lastIndexOf("."));
			} else {
				directoryPath = targetPath;
			}
			Enumeration entryEnum = zipFile.getEntries();
			if (null != entryEnum) {
				ZipEntry zipEntry = null;
				while (entryEnum.hasMoreElements()) {
					zipEntry = (ZipEntry) entryEnum.nextElement();
					if (zipEntry.isDirectory()) {
						directoryPath = directoryPath + File.separator
								+ zipEntry.getName();
						System.out.println(directoryPath);
						continue;
					}
					if (zipEntry.getSize() > 0) {
						// �ļ�
						File targetFile = FileUtils.buildFile(directoryPath
								+ File.separator + zipEntry.getName(), false);
						os = new BufferedOutputStream(new FileOutputStream(
								targetFile));
						is = zipFile.getInputStream(zipEntry);
						byte[] buffer = new byte[4096];
						int readLen = 0;
						while ((readLen = is.read(buffer, 0, 4096)) >= 0) {
							os.write(buffer, 0, readLen);
						}

						os.flush();
						os.close();
					} else {
						// ��Ŀ¼
						FileUtils.buildFile(directoryPath + File.separator
								+ zipEntry.getName(), true);
					}
				}
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (null != zipFile) {
				zipFile.close();
				zipFile = null;
			}
			if (null != is) {
				is.close();
			}
			if (null != os) {
				os.close();
			}
		}
	}
}
