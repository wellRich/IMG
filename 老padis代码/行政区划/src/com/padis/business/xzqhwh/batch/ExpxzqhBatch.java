package com.padis.business.xzqhwh.batch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.padis.business.common.data.xt.D_xt_xzqhfb;
import com.padis.business.common.data.xzqh.D_xzqh_bgsqd;
import com.padis.business.xzqhwh.common.Common;
import com.padis.business.xzqhwh.common.FileUtils;
import com.padis.business.xzqhwh.common.ZipUtil;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.XtSvc;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.business.common.sequence.IGeneralSequence;
import ctais.business.common.xtservice.batch.BatchFactory;
import ctais.business.common.xtservice.batch.BatchTask;
import ctais.business.common.xtservice.batch.DbHelper;
import ctais.business.common.xtservice.batch.IBatchable;
import ctais.business.common.xtservice.batch.IManagerCommand;
import ctais.business.common.xtservice.batch.IScheduleable;
import ctais.services.data.DataWindow;
import ctais.services.log.LogManager;
import ctais.services.or.ORManager;
import ctais.services.or.UserTransaction;
import ctais.services.sequence.SequenceManager;
import ctais.services.xml.Response;
import ctais.services.xml.XMLDataObject;
import ctais.services.xml.XMLDataObjectHelper;
import ctais.services.xml.XMLParser;
import ctais.util.StringEx;

/**
 * 
 * <p>
 * Title:������������ɨ�����
 * </P>
 * <p>
 * Description:ɨ��ȫ�������������Լ�������������������ɨ��󵼳���������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: DigitalChina Co.Ltd
 * </p>
 * 
 * @auther lijl
 * @version 1.0
 * @since 2009-08-14 �޸��ˣ� �޸�ʱ�䣺 �޸����ݣ� �޸İ汾�ţ�
 */

public class ExpxzqhBatch implements IManagerCommand, IBatchable {


	private int progressPercent = 0;

	private StringBuffer sbProcessMsg = new StringBuffer();

	/**
	 * ������ĵ��ȵ�Ԫ
	 */
	private IScheduleable schedUnit;

	public ExpxzqhBatch() {
	}

	/**
	 * ȡ�õ�ǰ����״̬
	 * 
	 * @return ����״̬��Ϣ
	 */
	public String getProcessMsg() {
		return this.sbProcessMsg.toString();
	}

	/**
	 * ���ش�����Ȱٷֱ�
	 * 
	 * @return �ٷֱ�(0-100)
	 */
	public int getProgressPercent() {
		return this.progressPercent;
	}

	public void setSchedUnit(IScheduleable forSchedUnit) {
		this.schedUnit = forSchedUnit;
	}

	public void execute() throws Exception {
		String argXml = null; // �������
		try {
			Object[] enviroment = (Object[]) this.schedUnit.getEnviroment();
			if ((enviroment != null) && (enviroment.length >= 2)) {
				argXml = (String) enviroment[1];
			}

			argXml = StringEx.sNull(argXml.trim());
			if (argXml.length() > 0) {
				XMLDataObject arg;
				if (!argXml.startsWith("<?xml")) {
					arg = new XMLDataObject(
							(new XMLParser()).parseXMLStr("<?xml version=\"1.0\" encoding=\"GBK\"?>"+ argXml));
				} else {
					arg = new XMLDataObject((new XMLParser()).parseXMLStr(argXml));
				}

				// ����XMLDataObject�ĵ�ǰλ��
				arg.rootScrollTo("Service");
				arg.retrieve();
				System.out.println("��ʼִ������[" + (String) enviroment[0]+ "]......");
				String endtime = XMLDataObjectHelper.getXDOValue(arg,"COMMAND", "");
				LogManager.getLogger().info("����ִ�еĽ���ʱ�䡾" + endtime + "��");
				this.zipXzqhFile();

				System.out.println("ִ���������[" + (String) enviroment[0] + "]��");

				this.progressPercent = 100;
				this.sbProcessMsg.append("ִ������[" + StringEx.sNull(argXml)
						+ "]�ɹ���");
			} else {
				throw new IllegalArgumentException("���������ӦΪ��");
			}
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			String strMsg = "ִ������[" + StringEx.sNull(argXml) + "]ʧ�ܣ�"
					+ sw.toString();
			this.sbProcessMsg.append(strMsg);
			LogManager.getLogger().error(strMsg);
			throw ex;
		}
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
		File fileUploadDir = new File(rootDir+"/exportxzqh/bgsj",date);
		if (!fileUploadDir.exists()) {
			if (!fileUploadDir.mkdirs()) {
				LogManager.getLogger().error("�޷������ļ��ϴ�Ŀ¼");
				return ;
			}
		}
		//��ȡ������
		DataWindow dw = DataWindow.dynamicCreate("select count(*) ZS from v_dm_xzqh_ylsj where XZQH_DM<>'000000000000000' AND JCDM<6");
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rows = dw.retrieve();
		String rowcount ="0";
		if(rows>0){
			rowcount = StringEx.sNull(dw.getItemAny(0L, "ZS"));
		}
		
		String exportPath = fileUploadDir.getAbsolutePath();
		exportPath = exportPath.replaceAll("\\\\", "/");
		String sql="select XZQH_DM,XZQH_MC from v_dm_xzqh_ylsj where XZQH_DM<>'000000000000000' AND JCDM<6 order by XZQH_DM";
		ResultSet resultSet= null;
		Connection conn = ORManager.getInstance().getConnectionPool("ctais").getConnection("0");
		Statement stmt = conn.createStatement();
		resultSet = stmt.executeQuery(sql);
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(exportPath+"/QGQHDM_"+date+".TXT"),"UTF-8");
		out.write(rowcount+"\r\n");
		out.write("��������,��������\r\n");
		if(resultSet!=null){
			try {			
				while (resultSet.next()) {
					String xzqh_dm = StringEx.sNull(resultSet.getString("XZQH_DM"));
					String xzqh_mc = StringEx.sNull(resultSet.getString("XZQH_MC"));
					String str = xzqh_mc+","+xzqh_dm+"\r\n";	
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
	private void createQggsFile(String qlwxzqh_dm) throws Exception{
		String date = XtDate.getDBdate();
		date = date.replaceAll("-", "");
		String rootDir = XtSvc.getXtcs("97001", null, null);
		if (rootDir == null) {
			LogManager.getLogger().error("ϵͳû�������ļ��ϴ�Ŀ¼");
			return ;
		}
		rootDir = rootDir.trim().replaceAll("\\\\", "/");
		date = date.replaceAll("-", "");
		File fileUploadDir = new File(rootDir+"/exportxzqh/bgsj",date);
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
		String sql="select XZQH_DM,XZQH_MC from v_dm_xzqh_ylsj where XZQH_DM like '"+qlwxzqh_dm+"%' AND JCDM<6 order by XZQH_DM";
		String fileName = "QHDM_"+qlwxzqh_dm+"0000_"+date+".TXT";
		resultSet = stmt.executeQuery(sql);
		File expDir = new File(exportPath,qlwxzqh_dm+"0000");
		if (!expDir.exists()) {
			if (!expDir.mkdirs()) {
				LogManager.getLogger().error("�޷������ļ��ϴ�Ŀ¼");
				return ;
			}
		}
		//��ȡ������
		DataWindow dw2 = DataWindow.dynamicCreate("select count(*) ZS from v_dm_xzqh_ylsj where XZQH_DM like '"+qlwxzqh_dm+"%' AND JCDM<6");
		dw2.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rows2 = dw2.retrieve();
		String rowcounts ="0";
		if(rows2>0){
			rowcounts = StringEx.sNull(dw2.getItemAny(0L, "ZS"));
		}			
		String sjDir = expDir.getAbsolutePath().replaceAll("\\\\", "/");
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(sjDir+"/"+fileName),"UTF-8");				
		out.write(rowcounts+"\r\n");
		out.write("��������,��������\r\n");
		if(resultSet!=null){
			try {					
				while (resultSet.next()) {
					String xzqh_dm = StringEx.sNull(resultSet.getString("XZQH_DM"));
					String xzqh_mc = StringEx.sNull(resultSet.getString("XZQH_MC"));
					String qlwXzqh_dm = xzqh_dm.substring(0,6);
					if(xzqh_dm.substring(6).equals("000000000")&&!xzqh_dm.substring(4).equals("00000000000")){					
						String xjFileName = "QHDM_"+qlwXzqh_dm+"_"+date+".TXT";
						String sql1="select XZQH_DM,XZQH_MC from v_dm_xzqh_ylsj where XZQH_DM like '"+qlwXzqh_dm+"%' AND JCDM<6 order by XZQH_DM";
						DataWindow dw1 = DataWindow.dynamicCreate(sql1);
						dw1.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
						long rowCount = dw1.retrieve();
						if(rowCount>0){
							OutputStreamWriter out1 = new OutputStreamWriter(new FileOutputStream(sjDir+"/"+xjFileName),"UTF-8");
							out1.write(rowCount+"\r\n");
							out1.write("��������,��������\r\n");
							for(int j=0; j<rowCount ;j++){
								String xjxzqh_dm = StringEx.sNull(dw1.getItemAny(j, "XZQH_DM"));
								String xjxzqh_mc = StringEx.sNull(dw1.getItemAny(j, "XZQH_MC"));
								String str1 = xjxzqh_mc+","+xjxzqh_dm+"\r\n";	
								out1.write(str1);
							}
							out1.flush();
							out1.close();
						}							
					}
					String str = xzqh_mc+","+xzqh_dm+"\r\n";	
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
				
		try {
        	if(stmt!=null){
        		stmt.close();
        	}
        	if(conn!=null){
        		conn.close();
        	}
        	if(out!=null){
	        	out.flush();
	    		out.close();
        	}
        } catch (Exception e) {
        	throw e;
        }
	}
	
	/**
	 * <p>�������ƣ�createGsBgdzbFile()�����������������д��XZQH_BGDZ.TXT</p>
	 * <p>����˵�������������������д��XZQH_BGDZ.TXT�ķ�����������������XT_XZQHBGMXB���м�����ȫ����¼��</p>
	 * @since 2009-07-21
	 * @author ���
	 * @throws Exception
	 */
	private boolean createBgdzbFile(String sjxzqh_dm) throws Exception{
		boolean flag=true;
		String date = XtDate.getDBdate();
		date = date.replaceAll("-", "");
		String rootDir = XtSvc.getXtcs("97001", null, null);
		if (rootDir == null) {
			LogManager.getLogger().error("ϵͳû�������ļ��ϴ�Ŀ¼");
			return false;
		}
		rootDir = rootDir.trim().replaceAll("\\\\", "/");
		date = date.replaceAll("-", "");
		File fileUploadDir = new File(rootDir+"/exportxzqh/bgsj",date);
		if (!fileUploadDir.exists()) {
			if (!fileUploadDir.mkdirs()) {
				LogManager.getLogger().error("�޷������ļ��ϴ�Ŀ¼");
				return false;
			}
		}	
		String exportPath = fileUploadDir.getAbsolutePath();
		exportPath = exportPath.replaceAll("\\\\", "/");
		ResultSet resultSet= null;
		StringBuffer sb = new StringBuffer("SELECT G.BH,G.GROUPMC,M.YSXZQH_MC,M.YSXZQH_DM,M.BGLX_DM,M.MBXZQH_MC,M.MBXZQH_DM " +
				"FROM XZQH_BGGROUP G,XZQH_BGMXB M,XZQH_BGSQD S WHERE S.SQDXH=G.SQDXH AND G.GROUPXH=M.GROUPXH" +
				" AND S.SQDZT_DM='40'");
		if(sjxzqh_dm!=null&&!sjxzqh_dm.equals("")){
			sb.append(" AND S.SBXZQH_DM LIKE '").append(sjxzqh_dm).append("%'");
		}
		sb.append(" ORDER BY G.PXH,M.PXH");
		
//		��ȡ������
		StringBuffer sb1 = new StringBuffer("SELECT COUNT(*) ZS " +
				"FROM XZQH_BGGROUP G,XZQH_BGMXB M,XZQH_BGSQD S WHERE S.SQDXH=G.SQDXH AND G.GROUPXH=M.GROUPXH" +
				" AND S.SQDZT_DM='40'");
		if(sjxzqh_dm!=null&&!sjxzqh_dm.equals("")){
			sb1.append(" AND S.SBXZQH_DM LIKE '").append(sjxzqh_dm).append("%'");
		}
		DataWindow dw = DataWindow.dynamicCreate(sb1.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rows = dw.retrieve();
		String rowcount ="0";
		if(rows>0){
			rowcount = StringEx.sNull(dw.getItemAny(0L, "ZS"));
		}
		
		Connection conn = ORManager.getInstance().getConnectionPool("ctais").getConnection("0");
		Statement stmt = conn.createStatement();
		resultSet = stmt.executeQuery(sb.toString());
		OutputStreamWriter out = null;
		if(resultSet!=null){
			try {
				String sjDir="";
				String fileName = "QHDM_BGDZB_"+sjxzqh_dm+"0000_"+date+".TXT";
				if(sjxzqh_dm==null||sjxzqh_dm.equals("")){
					fileName = "QGQHDM_BGDZB_"+date+".TXT";
					sjDir = exportPath;
				}else{
					File expDir = new File(exportPath,sjxzqh_dm+"0000");
					if (!expDir.exists()) {
						if (!expDir.mkdirs()) {
							LogManager.getLogger().error("�޷������ļ��ϴ�Ŀ¼");
							return false;
						}
					}
					sjDir = expDir.getAbsolutePath().replaceAll("\\\\", "/");
				}
				out = new OutputStreamWriter(new FileOutputStream(sjDir+"/"+fileName),"UTF-8");				
				out.write(rowcount+"\r\n");
				out.write("�������,����˵��,ԭ��������,ԭ��������,��������,����������,����������\r\n");	
				while (resultSet.next()) {
					String bh = StringEx.sNull(resultSet.getString("BH"));
					String groupmc = StringEx.sNull(resultSet.getString("GROUPMC"));
					String ysxzqh_mc = StringEx.sNull(resultSet.getString("YSXZQH_MC"));
					String ysxzqh_dm = StringEx.sNull(resultSet.getString("YSXZQH_DM"));
					String bglx_dm = StringEx.sNull(resultSet.getString("BGLX_DM"));
					String mbxzqh_mc = StringEx.sNull(resultSet.getString("MBXZQH_MC"));
					String mbxzqh_dm = StringEx.sNull(resultSet.getString("MBXZQH_DM"));
					out.write(bh+","+groupmc+","+ysxzqh_mc+","+ysxzqh_dm+","+bglx_dm+","+mbxzqh_mc+","+mbxzqh_dm+"\r\n");
				}
				out.flush();
				out.close();
			}catch (Exception e) {
				out.flush();
				out.close();
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
	                throw e;
	            }
	        }
		}
		return flag;
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
	private void addXzqhfb(String wjm, String wjlj, String wjdx,UserTransaction ut)throws Exception{
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
		xzqhfbDw.setTransObject(ut);
		xzqhfbDw.update(false);
	}
	
	/**
	 * <p>�������ƣ�zipXzqhFile()�� ��������TXT�ļ�ѹ����ZIP�ļ�</p>
	 * <p>����˵������������TXT�ļ�ѹ����ZIP�ļ��ķ�����</p>
	 * @since 2009-08-14
	 * @author ���
	 * @throws Exception
	 */
	public void zipXzqhFile()throws Exception{
		
		String date = XtDate.getDBdate();
		String rootDir = XtSvc.getXtcs("97001", null, null);
		if (rootDir == null) {
			LogManager.getLogger().error("ϵͳû�������ļ��ϴ�Ŀ¼");
			return ;
		}
		rootDir = rootDir.trim().replaceAll("\\\\", "/");
		date = date.replaceAll("-", "");
		File fileUploadDir = new File(rootDir+"/exportxzqh/bgsj",date);
		if (!fileUploadDir.exists()) {
			if (!fileUploadDir.mkdirs()) {
				LogManager.getLogger().error("�޷������ļ��ϴ�Ŀ¼");
				return ;
			}
		}else{
			throw new Exception("�����"+date+"��Ŀ¼�Ѵ��ڣ��޷��������ļ���");
		}

		String exportPath = fileUploadDir.getAbsolutePath();
		exportPath = exportPath.replaceAll("\\\\", "/");
		
		String fbSql = "select to_char(b.DCSJ,'yyyy-mm-dd') DCSJ from XT_XZQHFB b order by b.DCSJ desc";
		DataWindow fbDw = DataWindow.dynamicCreate(fbSql);
		fbDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		String dcsj = "";
		long rows = fbDw.retrieve();
		if(rows>0){
			dcsj= StringEx.sNull(fbDw.getItemAny(0, "DCSJ"));
		}
		if(dcsj.replaceAll("-", "").equals(date)){
			throw new Exception("ÿ��ֻ�ܷ���һ�Σ����跢����Σ�����������ݿ��¼��"); 
		}
		
		//�鿴�Ƿ���δ�ύ���뵥��ʡ��
		if(this.queryWtj()){
			throw new Exception("����δ�ύ���뵥��ʡ�ݣ���鿴��");
		}
		
		//�鿴�Ƿ���δ��˵�����
		DataWindow sqdDw = DataWindow.dynamicCreate("select count(*) WSHSJ from XZQH_BGSQD where SQDZT_DM<40");
		sqdDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		String wshsj = "";
		long rows1 = sqdDw.retrieve();
		if(rows1>0){
			wshsj= StringEx.sNull(sqdDw.getItemAny(0, "WSHSJ"));
			if(!wshsj.equals("0")&&!wshsj.equals("")){
				throw new Exception("����δ��˵����뵥��");
			}
		}
		//�鿴��˵�����
		DataWindow sqdDw1 = DataWindow.dynamicCreate("select SQDXH,SBXZQH_DM from XZQH_BGSQD where SQDZT_DM='40'");
		sqdDw1.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rows2 = sqdDw1.retrieve();
		if(rows2<=0){
			throw new Exception("����������˵����뵥��");
		}
		

		//������ʡ�����Ͷ��ձ�
		DataWindow sqdDw2 = DataWindow.dynamicCreate("select substr(s.SBXZQH_DM,0,2) SJXZQH_DM from XZQH_BGSQD s where SQDZT_DM='40' group by substr(s.SBXZQH_DM,0,2) order by  substr(s.SBXZQH_DM,0,2)");
		sqdDw2.setConnectionName(ConnConfig.getConnectionName(this.getClass()));//�������ӳ�
		long rows3 = sqdDw2.retrieve();
		UserTransaction ut = new UserTransaction();
		try{
			ut.begin();
			//����ȫ�������Ͷ��ձ�
			if(this.createBgdzbFile("")){
				this.createQgFile();
			}			
			for(int i=0; i<rows3; i++){
				String sjxzqh_dm = StringEx.sNull(sqdDw2.getItemAny(i, "SJXZQH_DM"));
				if(this.createBgdzbFile(sjxzqh_dm)){
					this.createQggsFile(sjxzqh_dm);
				}
				//�����뵥״̬��Ϊ�ѷ���
				D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
				d_xzqh_bgsqd.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
				StringBuffer sql = new StringBuffer();
				sql.append("SBXZQH_DM like '").append(sjxzqh_dm).append("%' AND SQDZT_DM='40'");
				d_xzqh_bgsqd.setFilter(sql.toString());
				long counts = d_xzqh_bgsqd.retrieve();	
				for(int j=0; j<counts; j++){
					d_xzqh_bgsqd.setItemString(j, "SQDZT_DM","50");
					d_xzqh_bgsqd.setTransObject(ut);
					d_xzqh_bgsqd.update(false);
				}
			}
	
		
			//ɾ���ɵ�ZIP�ļ�
			File oldZipfile = new File(exportPath+"/QGQHDM_"+date+".ZIP");
			if(oldZipfile.exists())
				oldZipfile.delete();
			//��ӵ�ѹ���ļ�
			ZipUtil zip = new ZipUtil(rootDir+"/exportxzqh/bgsj/QGQHDM_"+date+".ZIP");
			zip.addFile(exportPath,"",false);
			zip.zipDone();
			FileUtils.moveFile(rootDir+"/exportxzqh/bgsj/QGQHDM_"+date+".ZIP",exportPath+"/QGQHDM_"+date+".ZIP");
			File newZipFile = new File(exportPath+"/QGQHDM_"+date+".ZIP");
			String wjdx="0";
			if(newZipFile.exists()){
				wjdx = String.valueOf(newZipFile.length());
			}
			String savePath = exportPath.substring(rootDir.length(), exportPath.length());			
			//���浼����¼
			addXzqhfb(date+"ȫ����ʡ�������ݺͱ�����ձ�",savePath+"/QGQHDM_"+date+".ZIP",wjdx,ut);
			ut.commit();
		}catch(Exception e){
			ut.rollback();
			FileUtils.deleteDir(exportPath);
			throw e;
		}	
	}
	
	/**
	 * <p>�������ƣ�queryWtj</p>
	 * <p>������������ѯδ�ύ���뵥��ʡ��</p>
	 * @return
	 * @throws Exception
	 * @author pengld
	 * @since 2009-12-02
	 */
	private boolean queryWtj() throws Exception
	{
		boolean flag = false;
//		��ȡϵͳ����:���ύ����ĵ�ʡ������
		String xzqh = StringEx.sNull(XtSvc.getXtcs("97002", null, null));
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  XZQH_DM,XZQH_MC FROM V_DM_XZQH ");
		sql.append(" WHERE XZQH_DM NOT IN (SELECT DISTINCT(SUBSTR(SBXZQH_DM,1,2))||'0000000000000' FROM XZQH_BGSQD XZQH_BGSQD WHERE SQDZT_DM<"+Common.XZQH_SQDZT_YFB+") AND JCDM ='1' ");
		if(!xzqh.equals(""))
		{
			sql.append(" AND SUBSTR(XZQH_DM,1,2) NOT IN ("+xzqh+") ");
		}
		sql.append(" ORDER BY XZQH_DM ");
		
		DataWindow dw = DataWindow.dynamicCreate(sql.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long rows = dw.retrieve();
		if (rows > 0){	
			flag = true;
		}
		return flag;
	}
	
	/**
	 * �¼�:����������
	 * 
	 * @throws Exception
	 *             ����
	 */
	public void queueEntered() throws Exception {
		LogManager.getLogger().debug(
				this.schedUnit.getTaskDescription() + "�� ���������С�");
	}

	/**
	 * ����ȡ��
	 * 
	 * @throws Exception
	 *             ȡ��ʧ��
	 */
	public void canceled() throws Exception {
		LogManager.getLogger().debug(
				this.schedUnit.getTaskDescription() + "�� ����ȡ����");
	}

	/**
	 * ����ִ�йر�
	 */
	public void closed() {
		LogManager.getLogger().debug(
				this.schedUnit.getTaskDescription() + "�� ����ִ�йرա�");
	}

	/**
	 * ���������
	 * 
	 * @throws Exception
	 *             ȡ��ʧ��
	 */
	public void queueOut() throws Exception {
		LogManager.getLogger().debug(
				this.schedUnit.getTaskDescription() + "�� ��������С�");
	}

	XMLDataObject xdoArg = null; // �������

	/**
	 * initCommand
	 * 
	 * @param forArgXdo
	 *            XMLDataObject
	 */
	public void initCommand(XMLDataObject xdoArg) {
		this.xdoArg = xdoArg;
	}

	/**
	 * doCommand
	 * 
	 * @return Response
	 */
	public Response doCommand() {
		try {
			// ����XMLDataObject�ĵ�ǰλ��
			this.xdoArg.rootScrollTo("Service");
			this.xdoArg.retrieve();

			String cmd = XMLDataObjectHelper.getXDOValue(this.xdoArg,
					"COMMAND", "");

			BatchTask bt = new BatchTask();
			bt.setId(this.getClass().getName());
			bt.setGroupTitle("������������");
			bt.setGroupId(this.getClass().getName() + "_GROUP");
			bt.setGroupYxjb(IScheduleable.DEFAULT_GROUP_PRIORITY);
			bt.setPriority(1);
			bt.setBz(this.getClass().getName());
			bt.setRedo("N");
			bt.setBPersistent(true);
			bt.setPh(DbHelper.newSequence());
			bt.setDdh(DbHelper.newSequence());
			bt.setTaskDescription("������������[" + this.getClass().getName()
					+ "]");
			bt.setTaskExecutor(this.getClass().getName());

			// �������������
			StringBuffer sbXml = new StringBuffer("<CtaisSession><Service>");
			sbXml.append("<COMMAND>" + cmd + "</COMMAND>");
			sbXml.append("</Service></CtaisSession>");
			bt.setEnvironment(new Object[] { this.getClass().getName(),
					sbXml.toString() });

			BatchFactory.getTaskScheduler().addTask(bt);

			return new Response(0, 2000, "��ӵ�������гɹ���", "");
		} catch (Exception ex) {
			return new Response(1, 2005, "��ӵ����������ʧ�ܣ�", "");
		}
	}

}
