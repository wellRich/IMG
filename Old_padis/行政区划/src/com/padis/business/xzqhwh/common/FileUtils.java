package com.padis.business.xzqhwh.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {
	
	 public static File buildFile(String fileName, boolean isDirectory) {
		File target = new File(fileName);
		if (isDirectory) {
			target.mkdirs();
		} else {
			if (!target.getParentFile().exists()) {
				target.getParentFile().mkdirs();
				target = new File(target.getAbsolutePath());
			}
		}
		return target;
	} 
	public static void writeFile(String fn, String content) throws Exception {
		writeFile(fn, content, "UTF-8");
	}

	public static void writeFile(String fn, String content, String charset)
			throws Exception {
		OutputStream fso = null;
		OutputStreamWriter writer = null;
		if (charset == null || charset.length() == 0)
			charset = "UTF-8";
		try {
			File f = new File(fn);
			fso = new FileOutputStream(f);
			writer = new OutputStreamWriter(fso, charset);
			writer.write(content);
		} finally {
			try {
				if (writer != null) {
					writer.flush();
					writer.close();
				}
				if (fso != null)
					fso.close();
			} catch (Exception e) {
			}
		}
		return;
	}

	public static void mksubdir(String dir) {
		File f = new File(dir);
		if (f.isDirectory() && f.exists())
			f.mkdirs();
	}

	public static String extractFileName(String fn) {
		if (!StringEx.isEmpty(fn)) {
			String temp = fn.replace('\\', '/');
			int ix = temp.lastIndexOf('/');
			if (ix != -1)
				fn = temp.substring(ix + 1);
		}
		return fn;
	}

	public static String extractFileExt(String fn) {
		if (!StringEx.isEmpty(fn))
			return fn.substring(fn.lastIndexOf('.') + 1);
		else
			return fn;
	}

	public static boolean fileExists(String fn) {
		File f = new File(fn);
		return f.exists();
	}

	public static void copyFile(String src, String target) throws Exception {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(src);
			fos = new FileOutputStream(target);
			byte buff[] = new byte[4096];
			int c;
			while ((c = fis.read(buff, 0, 4096)) > 0)
				fos.write(buff, 0, c);
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (Exception exception1) {
				}
			if (fis != null)
				try {
					fis.close();
				} catch (Exception ex) {
				}
		}
		return;
	}
	
	public static boolean moveFile(String src, String target) throws Exception {
		return moveFile(src, target, false);
	}

	public static boolean moveFile(String src, String target,
			boolean overwriteable) throws Exception {
		File fSrc = new File(src);
		File fDest = new File(target);
		boolean zOk = fSrc.exists();
		if (zOk && fDest.exists())
			if (overwriteable)
				zOk = fDest.delete();
			else
				throw new Exception("File[" + fDest + "] exists!");
		File fDestDir = fDest.getParentFile();
		if (zOk && !fDestDir.exists())
			zOk = fDest.getParentFile().mkdirs();
		if (zOk)
			zOk = fSrc.renameTo(fDest);
		return zOk;
	}

	public static boolean deleteFile(String fn) {
		File f = new File(fn);
		return f.exists() && f.delete();
	}

	public static boolean deleteDir(String dir) {
		File f = new File(dir);
		if(f.isDirectory()){
			File[] files = f.listFiles();
			
			for (int i = 0; i < files.length; i++) {
				File tempFile = files[i];
				if (tempFile.isDirectory()) {
					deleteDir(tempFile.getAbsolutePath());
				} else {
					deleteFile(tempFile.getAbsolutePath());
				}
			}
		}
		return f.delete();
	}
	
	public static byte[] readFile(String fn) throws Exception {
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		byte abyte0[];
		try {
			fis = new FileInputStream(fn);
			bos = new ByteArrayOutputStream(4096);
			byte buff[] = new byte[4096];
			int len;
			while ((len = fis.read(buff)) > 0)
				bos.write(buff, 0, len);
			abyte0 = bos.toByteArray();
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (Exception exception1) {
				}
			if (bos != null)
				try {
					fis.close();
				} catch (Exception e) {
					bos.close();
				}
		}
		return abyte0;
	}

	/**
	 * <p>方法名称：getNotIterantFileName</p>
	 * <p>方法描述：获取文件的唯一命名</p>
	 * @param file  文件路径
	 * @return 不重复的文件名
	 * @author wangjz
	 * @since Jan 19, 2008
	 */
	public static String getNotIterantFileName(String path) {
		File file = new File(path);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String datestr = "";
		File newFile = file;
		if (file.exists()) {
			sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			datestr = sdf.format(new Date());
			StringBuffer sb = new StringBuffer("");
			String fileName = file.getPath();
			int pos = fileName.lastIndexOf(".");
			if(pos<0){
				pos=fileName.length()-1;
			}
			sb.append(fileName.substring(0, pos));
			sb.append(datestr);
			sb.append(fileName.substring(pos));
			newFile = new File(sb.toString());
		}
		return newFile.getAbsolutePath();
	}
	
	public static String modidyFileName(String path, String destFileName){
		File sourceFile = new File(path);
		File destFile = new File(destFileName);
		
		if (sourceFile.exists()) {
			sourceFile.renameTo(destFile);
		}
		return destFile.getName();
	}
	
	public static synchronized String getNextFileName(String filePath,String column, String fileExt)
			throws Exception {

		if (filePath == null || filePath == "")
			throw new Exception("文件路径不存在");
		filePath = filePath.trim().toUpperCase();

		//String sDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String sFilePath = filePath;
		File f = new File(sFilePath);

		if (!f.exists()) {
			try {
				f.mkdirs();
			} catch (Exception ex) {
				throw new Exception("无法创建目录：" + sFilePath);
			}
		}
		long lTime = System.currentTimeMillis();

		String sTime = StringEx.numberToStr(lTime, 8, '0');
		String sRandom = StringEx.numberToStr(Math.round(Math.random() * 10000D),
				4, '0');
		String sFileExt = fileExt.trim();
		if (sFileExt.length() > 0 && sFileExt.charAt(0) != '.')
			sFileExt = "." + sFileExt;
		String sFileName = sFilePath + File.separator + column+ sTime + sRandom + sFileExt;

		return sFileName;
	}
}