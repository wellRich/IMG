package com.padis.business.xzqhwh.zxbg.qhdmxz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.padis.business.common.data.xt.D_xt_xzqhfb;
import com.padis.business.xzqhwh.common.FileUtils;
import com.padis.business.xzqhwh.common.ZipUtil;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.XtSvc;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.business.common.sequence.IGeneralSequence;
import ctais.services.data.DataWindow;
import ctais.services.log.LogManager;
import ctais.services.or.ORManager;
import ctais.services.or.UserTransaction;
import ctais.services.sequence.SequenceManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
/**
 * <p> 
 * Description: �����������������ࣨExpXzqhService����manager,�ṩExpXzqhService�ײ㴦��ķ���
 * </p> 
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @since 2009-08-14
 * @author ���
 * @version 1.0
 */

public class QhdmxzManager {

	/**
	 * <p>�������ƣ�querySqbrz()����ѯ��ѯ��������������</p>
	 * <p>����˵������ѯ��ѯ��������������ķ�����������������XT_XZQHFB���м�����������¼��</p>
	 * @param xmldo		��ѯ����
	 * @param args		��ҳ��ѯ����
	 * @since 2009-08-14
	 * @author ���
	 * String ���������ĵ����ʼ��б�
	 * @throws Exception
	 */
	public String queryXzqhfb(XMLDataObject xmldo ,long[] args) throws Exception {
		String bgsjq = StringEx.sNull(xmldo.getItemValue("DCSJQ"));
		String bgsjz = StringEx.sNull(xmldo.getItemValue("DCSJZ"));	
		String pageSize = StringEx.sNull(xmldo.getItemValue("PAGESIZE")); //ÿҳ����������
		String pageIndex = StringEx.sNull(xmldo.getItemValue("PAGEINDEX")); //ҳ������
		pageSize = pageSize.equals("") ? "20" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer sql = new StringBuffer();
		StringBuffer queryBuffer = new StringBuffer("SELECT S.DCXH,S.WJM,S.WJLJ,to_char((S.WJDX/1024),'9999999999.99') WJDX," +
				"to_char(S.DCSJ,'YYYY-MM-DD HH24:mi:ss') DCSJ FROM XT_XZQHFB S");
		if(!bgsjq.equals("")){
			sql.append(" to_char(S.DCSJ,'yyyy-mm-dd')>='");
			sql.append(bgsjq);
			sql.append("'");
		}
		if(!bgsjz.equals("")){
			if(!bgsjq.equals("")){
				sql.append(" AND ");
			}
			sql.append(" to_char(S.DCSJ,'yyyy-mm-dd')<='");
			sql.append(bgsjz);
			sql.append("'");
		}
		if(!bgsjz.equals("")||!bgsjq.equals("")){
			queryBuffer.append(" WHERE");
		}
		queryBuffer.append(sql.toString());
		queryBuffer.append(" order by S.DCSJ desc");
		DataWindow dw = DataWindow.dynamicCreate(queryBuffer.toString(),true);
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rowCount = dw.getRowCount(queryBuffer.toString());
		long ps = Long.parseLong(pageSize);   //һҳ�а������������ݣ����з�ҳ׼��
		long pageCount = dw.getPageCount(ps);  //һ������ҳ
		long cnt = dw.retrieve(ps, Long.parseLong(pageIndex),pageCount, queryBuffer.toString());
		args[0] = pageCount ; 
		args[1] = rowCount ; 
		String resultXml = "";
		if (cnt > 0){
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>�������ƣ�getFilePath()����ȡ�ļ�·��</p>
	 * <p>����˵������ȡ�ļ�·���ķ�����</p>
	 * @param dcxh  ��������
	 * @Date 2009-08-14
	 * @author ���
	 * @return ��
	 * @exception ��
	 */
	public String getFilePath(String dcxh)throws Exception{
		String filePath ="";
		String exportDir = XtSvc.getXtcs("97001", null, null);
		if(exportDir.equals("")){
			return filePath;
		}
		exportDir = exportDir.replaceAll("\\\\", "/");
		String fbSql = "select WJLJ from XT_XZQHFB where DCXH='"+dcxh+"'";
		DataWindow fbDw = DataWindow.dynamicCreate(fbSql);
		fbDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rows = fbDw.retrieve();
		if(rows>0){		
			filePath= exportDir+StringEx.sNull(fbDw.getItemAny(0, "WJLJ"));
		}
		return filePath;
	}
	
	/**
	 * <p>�������ƣ�createQgFile()����ȫ����������д��XZQH_JSW.TXT</p>
	 * <p>����˵������ȫ����������д��XZQH_JSW.TXT�ķ�����������������V_DM_XZQH���м�����ȫ����¼��</p>
	 * @since 2009-07-21
	 * @author ���
	 * @throws Exception
	 */
	private void createQgFile() throws Exception{
		String date = XtDate.getDBdate();
		date = date.replaceAll("-", "");
		String rootDir = XtSvc.getXtcs("97001", null, null);
		if (rootDir == null) {
			LogManager.getLogger().error("ϵͳû�������ļ��ϴ�Ŀ¼");
			return ;
		}
		rootDir = rootDir.trim().replaceAll("\\\\", "/");
		date = date.replaceAll("-", "");
		File fileUploadDir = new File(rootDir+"/exportxzqh",date);
		if (!fileUploadDir.exists()) {
			if (!fileUploadDir.mkdirs()) {
				LogManager.getLogger().error("�޷������ļ��ϴ�Ŀ¼");
				return ;
			}
		}
		//��ȡ������
		DataWindow dw = DataWindow.dynamicCreate("select count(*) ZS from v_dm_xzqh where XZQH_DM<>'000000000000000'");
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rows = dw.retrieve();
		String rowcount ="0";
		if(rows>0){
			rowcount = StringEx.sNull(dw.getItemAny(0L, "ZS"));
		}
		
		String exportPath = fileUploadDir.getAbsolutePath();
		exportPath = exportPath.replaceAll("\\\\", "/");
		String sql="select XZQH_DM,XZQH_MC from v_dm_xzqh where XZQH_DM<>'000000000000000' order by XZQH_DM";
		ResultSet resultSet= null;
		Connection conn = ORManager.getInstance().getConnectionPool("ctais").getConnection("0");
		Statement stmt = conn.createStatement();
		resultSet = stmt.executeQuery(sql);
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(exportPath+"/QGQHDM.TXT"),"UTF-8");
		out.write(rowcount+"\r\n");
		out.write("������������,������������\r\n");
		if(resultSet!=null){
			try {			
				while (resultSet.next()) {
					String xzqh_dm = StringEx.sNull(resultSet.getString("XZQH_DM"));
					String xzqh_mc = StringEx.sNull(resultSet.getString("XZQH_MC"));
					String str = xzqh_dm+","+xzqh_mc+"\r\n";	
					out.write(str);
				}
			}catch (Exception e) {
	            throw e;
	        }
			finally {
	            try {
	            	if(resultSet!=null){
	            		resultSet.close();
	            	}
	            	if(stmt!=null){
	            		stmt.close();
	            	}
	            	if(conn!=null){
	            		conn.close();
	            	}
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
		}
		out.flush();
		out.close();
	}
	
	/**
	 * <p>�������ƣ�createBgFile()�����������������д��XZQH_BGDZ.TXT</p>
	 * <p>����˵�������������������д��XZQH_BGDZ.TXT�ķ�����������������XT_XZQHBGMXB���м�����ȫ����¼��</p>
	 * @since 2009-07-21
	 * @author ���
	 * @throws Exception
	 */
	private void createQggsFile() throws Exception{
		String date = XtDate.getDBdate();
		date = date.replaceAll("-", "");
		String rootDir = XtSvc.getXtcs("97001", null, null);
		if (rootDir == null) {
			LogManager.getLogger().error("ϵͳû�������ļ��ϴ�Ŀ¼");
			return ;
		}
		rootDir = rootDir.trim().replaceAll("\\\\", "/");
		date = date.replaceAll("-", "");
		File fileUploadDir = new File(rootDir+"/exportxzqh",date);
		if (!fileUploadDir.exists()) {
			if (!fileUploadDir.mkdirs()) {
				LogManager.getLogger().error("�޷������ļ��ϴ�Ŀ¼");
				return ;
			}
		}
		
		String exportPath = fileUploadDir.getAbsolutePath();
		exportPath = exportPath.replaceAll("\\\\", "/");
		ResultSet resultSet= null;
		Connection conn = ORManager.getInstance().getConnectionPool("ctais").getConnection("0");
		Statement stmt = conn.createStatement();
		DataWindow dw = DataWindow.dynamicCreate("select substr(x.XZQH_DM,0,2) SJXZQH_DM from v_dm_xzqh x  " +
				"where XZQH_DM<>'000000000000000' group by substr(x.XZQH_DM,0,2) order by substr(x.XZQH_DM,0,2)");
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rows = dw.retrieve();
		if(rows>0){
			for(int i=0; i<rows ;i++){
				String sjxzqh_dm= StringEx.sNull(dw.getItemAny(i, "SJXZQH_DM"));
				String sql="select XZQH_DM,XZQH_MC from v_dm_xzqh where XZQH_DM like '"+sjxzqh_dm+"%' order by XZQH_DM";
				String fileName = "QHDM_"+sjxzqh_dm+"0000.TXT";
				resultSet = stmt.executeQuery(sql);
				File expDir = new File(exportPath,sjxzqh_dm+"0000");
				if (!expDir.exists()) {
					if (!expDir.mkdirs()) {
						LogManager.getLogger().error("�޷������ļ��ϴ�Ŀ¼");
						return ;
					}
				}
				//��ȡ������
				DataWindow dw2 = DataWindow.dynamicCreate("select count(*) ZS from v_dm_xzqh where XZQH_DM like '"+sjxzqh_dm+"%'");
				dw2.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
				long rows2 = dw2.retrieve();
				String rowcounts ="0";
				if(rows2>0){
					rowcounts = StringEx.sNull(dw2.getItemAny(0L, "ZS"));
				}			
				String sjDir = expDir.getAbsolutePath().replaceAll("\\\\", "/");
				OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(sjDir+"/"+fileName),"UTF-8");				
				out.write(rowcounts+"\r\n");
				out.write("������������,������������\r\n");
				if(resultSet!=null){
					try {					
						while (resultSet.next()) {
							String xzqh_dm = StringEx.sNull(resultSet.getString("XZQH_DM"));
							String xzqh_mc = StringEx.sNull(resultSet.getString("XZQH_MC"));
							String qlwXzqh_dm = xzqh_dm.substring(0,6);
							if(xzqh_dm.substring(6).equals("000000000")&&!xzqh_dm.substring(4).equals("00000000000")){							
								String xjFileName = "QHDM_"+qlwXzqh_dm+".TXT";
								String sql1="select XZQH_DM,XZQH_MC from v_dm_xzqh where XZQH_DM like '"+qlwXzqh_dm+"%' order by XZQH_DM";
								DataWindow dw1 = DataWindow.dynamicCreate(sql1);
								dw1.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
								long rowCount = dw1.retrieve();
								if(rowCount>0){
									OutputStreamWriter out1 = new OutputStreamWriter(new FileOutputStream(sjDir+"/"+xjFileName),"UTF-8");
									out1.write(rowCount+"\r\n");
									out1.write("������������,������������\r\n");
									for(int j=0; j<rowCount ;j++){
										String xjxzqh_dm = StringEx.sNull(dw1.getItemAny(j, "XZQH_DM"));
										String xjxzqh_mc = StringEx.sNull(dw1.getItemAny(j, "XZQH_MC"));
										String str1 = xjxzqh_dm+","+xjxzqh_mc+"\r\n";	
										out1.write(str1);
									}
									out1.flush();
									out1.close();
								}							
							}
							String str = xzqh_dm+","+xzqh_mc+"\r\n";	
							out.write(str);
						}
					}catch (Exception e) {
			            throw e;
			        }
					finally {
			            try {
			            	if(resultSet!=null){
			            		resultSet.close();
			            	}
			            } catch (Exception e) {
			            	throw e;
			            }
			        }
				}
				out.flush();
				out.close();
			}
		}
		try {
        	if(stmt!=null){
        		stmt.close();
        	}
        	if(conn!=null){
        		conn.close();
        	}
        } catch (Exception e) {
        	throw e;
        }
	}
	
	/**
	 * <p>�������ƣ�addXzqhfb()�� ���浼����¼</p>
	 * <p>����˵�������浼����¼�ķ�����������������XT_XZQHFB���б���һ����¼��</p>
	 * @param wjm  �ļ���
	 * @param wjlj �ļ�·��
	 * @param wjdx �ļ���С
	 * @since 2009-08-14
	 * @author ���
	 * @throws Exception
	 */
	private void addXzqhfb(String wjm, String wjlj, String wjdx)throws Exception{
		D_xt_xzqhfb xzqhfbDw = new D_xt_xzqhfb();
		xzqhfbDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		xzqhfbDw.insert(-1);
		IGeneralSequence iseq = (IGeneralSequence) SequenceManager.getSequenceFactory();
		String dcxh = iseq.get("SEQ_YWXTWH_XZQHFB_XL");
		xzqhfbDw.setItemString(0, "DCXH", dcxh);
		xzqhfbDw.setItemString(0, "WJM", wjm);
		xzqhfbDw.setItemString(0, "WJLJ", wjlj);
		xzqhfbDw.setItemString(0, "WJDX", wjdx);
		xzqhfbDw.setItemString(0, "DCSJ", XtDate.getDBTime());
		xzqhfbDw.setTransObject(new UserTransaction());
		xzqhfbDw.update(true);
	}
	
	/**
	 * <p>�������ƣ�zipXzqhFile()�� ��������TXT�ļ�ѹ����ZIP�ļ�</p>
	 * <p>����˵������������TXT�ļ�ѹ����ZIP�ļ��ķ�����</p>
	 * @since 2009-08-14
	 * @author ���
	 * @throws Exception
	 */
	public void zipXzqhFile()throws Exception{
		String fbSql = "select to_char(b.DCSJ,'yyyy-mm-dd') DCSJ from XT_XZQHFB b order by b.DCSJ desc";
		DataWindow fbDw = DataWindow.dynamicCreate(fbSql);
		fbDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		String dcsj = "";
		long rows = fbDw.retrieve();
		if(rows>0){
			dcsj= StringEx.sNull(fbDw.getItemAny(0, "DCSJ"));
		}
		String date = XtDate.getDBdate();
		if(dcsj.equals(date)){
			return;
		}
		
		this.createQgFile();
		this.createQggsFile();
		String rootDir = XtSvc.getXtcs("97001", null, null);
		if (rootDir == null) {
			LogManager.getLogger().error("ϵͳû�������ļ��ϴ�Ŀ¼");
			return ;
		}
		rootDir = rootDir.trim().replaceAll("\\\\", "/");
		date = date.replaceAll("-", "");
		File fileUploadDir = new File(rootDir+"/exportxzqh",date);
		if (!fileUploadDir.exists()) {
			if (!fileUploadDir.mkdirs()) {
				LogManager.getLogger().error("�޷������ļ��ϴ�Ŀ¼");
				return ;
			}
		}
		
		String exportPath = fileUploadDir.getAbsolutePath();
		exportPath = exportPath.replaceAll("\\\\", "/");
		//ɾ���ɵ�ZIP�ļ�
		File oldZipfile = new File(exportPath+"/QGQHDM_"+date+".ZIP");
		if(oldZipfile.exists())
			oldZipfile.delete();
		//��ӵ�ѹ���ļ�
		ZipUtil zip = new ZipUtil(rootDir+"/exportxzqh/QGQHDM_"+date+".ZIP");
		zip.addFile(exportPath,"",false);
		zip.zipDone();
		FileUtils.moveFile(rootDir+"/exportxzqh/QGQHDM_"+date+".ZIP",exportPath+"/QGQHDM_"+date+".ZIP");
		File newZipFile = new File(exportPath+"/QGQHDM_"+date+".ZIP");
		String wjdx="0";
		if(newZipFile.exists()){
			wjdx = String.valueOf(newZipFile.length());
		}
		String savePath = exportPath.substring(rootDir.length(), exportPath.length());			
		//���浼����¼
		addXzqhfb(date+"ȫ�����������ͱ�����ձ�",savePath+"/QGQHDM_"+date+".ZIP",wjdx);
	}
	
}
