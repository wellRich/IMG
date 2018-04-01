package com.digital.util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipException;

/**
 * @Author: zhanghpj
 * @Version 1.0, 16:10 2018/2/27
 * @See
 * @Since
 * @Deprecated
 */
public class ZipDecompressionUtil {

    private static Logger logger = LoggerFactory.getLogger(ZipDecompressionUtil.class);

    /**
     * @description TODO 解压文件
     * @method  decompress
     * @params [zipPath 被压缩文件，请使用绝对路径, targetPath解压路径，解压后的文件将会放入此目录中，请使用绝对路径
     *         默认为压缩文件的路径的父目录为解压路径, encoding 解压编码]
     * @return  boolean
     * @exception
     */
    public static boolean decompress(String zipPath, String targetPath, String encoding)
            throws  IOException {
        OutputStream os = null;
        InputStream is = null;
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zipPath,encoding);
            String directoryPath = "";
            if (null == targetPath || "".equals(targetPath)) {
                directoryPath = zipPath.substring(0,zipPath.lastIndexOf(File.separator));
            } else {
                directoryPath = targetPath;
            }
            Enumeration entryEnum = zipFile.getEntries();
            if (null != entryEnum) {
                ZipEntry zipEntry = null;
                while (entryEnum.hasMoreElements()) {
                    zipEntry = (ZipEntry) entryEnum.nextElement();
                    System.out.println("zipEntry.isDirectory:"+zipEntry.isDirectory());

                    System.out.println("zipEntry.getName:"+zipEntry.getName());

                    System.out.println();
                    if (zipEntry.isDirectory()) {
                        System.out.println(directoryPath);
                        continue;
                    }
                    if (zipEntry.getSize() > 0) {
                        // 文件
                        File targetFile = FileUtil.buildFile(directoryPath + File.separator
                                + zipEntry.getName(), false);
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
                        // 空目录
                        FileUtil.buildFile(directoryPath + File.separator
                                + zipEntry.getName(), true);
                    }
                }
            }
        } catch (IOException ex) {
            logger.info("解压过程中出现未知异常！！");
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
        return true;

    }




}
