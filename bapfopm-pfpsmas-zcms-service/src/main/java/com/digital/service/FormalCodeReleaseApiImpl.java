package com.digital.service;

import com.digital.api.FormalCodeReleaseApi;
import com.digital.dao.FormalCodeReleaseMapper;
import com.digital.entity.FormalCodeReleaseInfo;
import com.digital.util.resultData.Constants;
import com.digital.util.resultData.RtnData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * @Description: TODO
 * @Author: zhanghpj
 * @Version 1.0, 16:52 2018/4/24
 * @See
 * @Since
 * @Deprecated
 */
@Service
@Transactional
public class FormalCodeReleaseApiImpl implements FormalCodeReleaseApi{

    @Autowired
    private FormalCodeReleaseMapper formalCodeReleaseMapper;

    /**
     * @description 查询所有发布区划的信息 ||按照时间查询发布区划的信息
     * @method  selectFormalReleaseByExportNum
     * @params exportDate ： 导入时间；deadline : 截至时间
     * @return
     * @exception
     */
    @Override
    public List<FormalCodeReleaseInfo> selectFormalReleaseByExportNum(String exportDate,String deadline) {
        List<FormalCodeReleaseInfo> formalCodeReleaseInfos = formalCodeReleaseMapper.selectFormalReleaseByExportNum(exportDate, deadline);
        return formalCodeReleaseInfos;
    }


    /**
     * @description 下载全国区划代码zip
     * @method downloadFormalRelease
     * @params response 下载；zipPath： 下载路径
     * @return
     * @exception
     */
    @Override
    public void downloadFormalRelease(HttpServletResponse response, String zipPath) {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=" + zipPath.substring(zipPath.lastIndexOf("/")));
        byte[] buff = new byte[1024];
        OutputStream out = null;
        FileInputStream in = null;
        BufferedInputStream bis = null;
        try {
            File file = new File(zipPath);
            out = response.getOutputStream();
            in = new FileInputStream(file);
            bis = new BufferedInputStream(in);
            while (true){
                int result = bis.read(buff);
                if (result==-1){
                    break;
                }
                out.write(buff,0,buff.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("获取输入流出错！！！");
        }finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (in!=null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭流 出现异常！");
            }

        }

    }

}
