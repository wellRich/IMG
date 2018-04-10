package com.padis.business.xzqhwh.sjjzbg.xzqhsjsc;

import java.io.File;

import com.padis.business.common.data.xzqh.D_xzqh_jzbgzip;
import com.padis.common.dmservice.Dmmc;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.business.common.sequence.IGeneralSequence;
import ctais.services.data.DataWindow;
import ctais.services.or.UserTransaction;
import ctais.services.sequence.SequenceManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;

/**
 * <p>Title: XzqhsjscManager</P>
 * <p>Description: ʡ���ϱ����ݵ�Manager��</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @auther lijl
 * @version 1.0
 * @since 2009-09-10
 * �޸��ˣ�
 * �޸�ʱ�䣺
 * �޸����ݣ�
 * �޸İ汾�ţ�
 */
public class XzqhsjscManager {
	
	/**
	 * 
	 * <p>
	 * �������ƣ�addBgrzb
	 * </p>
	 * <p>
	 * ������������һ��ҵ���¼ת����ָ�����A�ر��B�أ������Ҫ��A�����е���ʹ嶼Ҫ�оٳ�������
	 * </p>
	 * 
	 * @param xb
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2009-09-10
	 */
	public void uploadXzqhjzbgzip(XMLDataObject xmldo, String czry_dm, String qxjg_dm) throws Exception {
		xmldo.rootScrollTo("FileInfo");
		String filePath = StringEx.sNull(xmldo.getItemAny(0L,"realname"));
		String fileName = StringEx.sNull(xmldo.getItemAny(0L,"filename"));
		D_xzqh_jzbgzip zipDw = new D_xzqh_jzbgzip();
		zipDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		zipDw.insert(-1);
		IGeneralSequence iseq = (IGeneralSequence) SequenceManager.getSequenceFactory();
		String name="";
		if(!filePath.equals("")){
			filePath = filePath.replaceAll("\\\\", "/").replaceAll("//", "/");			
		}else{
			return ;
		}
		int index = fileName.lastIndexOf(".");
		if(index>-1){
			name = fileName.substring(0,index);
		}	
		String[] args =name.split("_");
		if(args==null||args.length!=3){
			return ;
		}
		if(args[1]==null||args[1].equals("")||args[2]==null||args[2].equals("")){
			return ;
		}
		String zipxh = iseq.get("SEQ_XZQH_JZBGZIP_XL");
		zipDw.setItemString(0L, "ZIPXH", zipxh);
		zipDw.setItemString(0L, "XZQH_DM", args[1]);
		zipDw.setItemString(0L, "RQ",args[2]);
		zipDw.setItemString(0L, "JZBGZT_DM","10");
		zipDw.setItemString(0L, "BGZL_DM","0");
		zipDw.setItemString(0L, "WJM", fileName);
		zipDw.setItemString(0L, "WJLJ", filePath);
		zipDw.setItemString(0, "LRR_DM", czry_dm);
		zipDw.setItemString(0, "LRSJ", XtDate.getDBTime());
		zipDw.setItemString(0, "LRJG_DM", qxjg_dm);
		zipDw.setTransObject(new UserTransaction());
		zipDw.update(true);
		
	}	

	/**
	 * <p>
	 * �������ƣ�queryXzqhZip
	 * </p>
	 * <p>
	 * ������������ѯ������������������Ƿ��Ѵ���
	 * </p>
	 * 
	 * @param xzqh_dm
	 *            ������������
	 * @param xzqh_mc
	 *            ������������
	 * @return Ŀ¼�ڵ���Ϣ
	 * @throws Exception
	 * @author lijl
	 * @since 2009-09-10
	 */
	public boolean queryXzqhZip(String xzqh_dm, String rq)throws Exception {
		boolean flag = false;
		StringBuffer sql = new StringBuffer("SELECT Z.WJM FROM XZQH_JZBGZIP Z WHERE Z.XZQH_DM='");
		sql.append(xzqh_dm);
		sql.append("'");
		sql.append(" AND Z.RQ='");
		sql.append(rq);
		sql.append("'");
		DataWindow xzqhDw = DataWindow.dynamicCreate(sql.toString());
		xzqhDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long count = xzqhDw.retrieve();
		if (count >0) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * <p>�������ƣ�queryXzqhjzbgzip()��ʡ�������������б���ļ���¼</p>
	 * <p>����˵����ʡ�������������б���ļ���¼�ķ�����������������XT_XZQHFB���м�����������¼��</p>
	 * @param xmldo		��ѯ����
	 * @param args		��ҳ��ѯ����
	 * @since 2009-09-10
	 * @author lijl
	 * String ���������ĵ����ʼ��б�
	 * @throws Exception
	 */
	public String queryXzqhjzbgzip(XMLDataObject xmldo ,long[] args,String xzqh_dm) throws Exception {
		String pageSize = StringEx.sNull(xmldo.getItemValue("PAGESIZE")); //ÿҳ����������
		String pageIndex = StringEx.sNull(xmldo.getItemValue("PAGEINDEX")); //ҳ������
		pageSize = pageSize.equals("") ? "10" : pageSize;
		pageIndex = pageIndex.equals("") ? "1" : pageIndex;
		StringBuffer queryBuffer = new StringBuffer("SELECT Z.ZIPXH,Z.XZQH_DM,substr(Z.XZQH_DM,0,2) SJXZQH_DM,Z.RQ,Z.WJM,Z.JZBGZT_DM," +
				"to_char(Z.LRSJ,'YYYY-MM-DD HH24:mi:ss') LRSJ FROM XZQH_JZBGZIP Z");
		if(!xzqh_dm.equals("00")){
			queryBuffer.append(" where substr(Z.XZQH_DM,0,2)='").append(xzqh_dm).append("'");
		}
		queryBuffer.append(" order by Z.LRSJ desc");
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
			Dmmc.convDmmc(dw,"JZBGZT_DM", "V_DM_XZQH_JZBGZT");
			resultXml = dw.toXML().toString();
		}
		return resultXml;
	}
	
	/**
	 * <p>�������ƣ�deleteMxb()��ɾ����ϸ������</p>
	 * <p>����˵����ɾ����ϸ�����ݵķ���������������ӱ�XT_XZQHBGMXB��ɾ��������¼��</p>
	 * @param mxbxhs
	 *             ��ϸ�����
	 * @since 2009-09-10
	 * @author lijl
	 * @return ��
	 * @exception Exception
	 */
	public void deleteZip(String zipxhs) throws Exception{
		String[] zipxh = zipxhs.split(",");
		UserTransaction ut = new UserTransaction();
		try {
			ut.begin();
			for(int i=0; i<zipxh.length; i++){
				//ɾ��ZIP�ļ���¼				
				D_xzqh_jzbgzip zipDw = new D_xzqh_jzbgzip();
				zipDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
				StringBuffer sql1 = new StringBuffer();
				sql1.append("ZIPXH = '").append(zipxh[i]).append("'");
				zipDw.setFilter(sql1.toString());
				long count1 = zipDw.retrieve();
				if(count1>0){
					String wjlj = StringEx.sNull(zipDw.getItemAny(0, "WJLJ"));
					File file = new File(wjlj);
					if(file.exists()){
						file.delete();
					}
					zipDw.deleteRow(0L);
					zipDw.setTransObject(ut);
					zipDw.update(false);
				}
				
				//ɾ��ʡ������
				StringBuffer sqlSb2 = new StringBuffer("delete XZQH_JZBGSJ_TEMP t where ");
				sqlSb2.append(" t.ZIPXH='");
				sqlSb2.append(zipxh[i]);
				sqlSb2.append("'");
				DataWindow deleteDw = DataWindow.dynamicCreate(sqlSb2.toString());
				deleteDw.setTransObject(ut);
				deleteDw.update(false);
				//ɾ�����ձ�����
				
				StringBuffer sqlSb3 = new StringBuffer("delete XZQH_JZBGDZB_TEMP t where ");
				sqlSb3.append(" t.ZIPXH='");
				sqlSb3.append(zipxh[i]);
				sqlSb3.append("'");
				DataWindow dzbDw = DataWindow.dynamicCreate(sqlSb3.toString());
				dzbDw.setTransObject(ut);
				dzbDw.update(false);
				
				//ɾ�����������ݶ��ձ���ձ�����
				
				StringBuffer sqlSb4 = new StringBuffer("delete XZQH_JZBGKLJDZB_TEMP t where ");
				sqlSb4.append(" t.ZIPXH='");
				sqlSb4.append(zipxh[i]);
				sqlSb4.append("'");
				DataWindow kljdzbDw = DataWindow.dynamicCreate(sqlSb4.toString());
				kljdzbDw.setTransObject(ut);
				kljdzbDw.update(false);
				
			}
			ut.commit();
		} catch (Exception e) {
			ut.rollback();
			throw e;
		}
	}
	
	/**
	 * 
	 * <p>
	 * �������ƣ�getSqdxx
	 * </p>
	 * <p>
	 * ������������ȡ���뵥��Ϣ
	 * </p>
	 * 
	 * @param xzqh_dm
	 *            ������������
	 * @author lijl
	 * @since 2009-7-8
	 */
	public String  isUpload(String xzqh_dm,String fileName) throws Exception{
		String flag = "�����ϴ�";
		DataWindow dw1 = DataWindow.dynamicCreate("select ZIPXH,WJM from XZQH_jZBGZIP where WJM like '"+fileName+"%'");
		dw1.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		if(dw1.retrieve()>0){
			return "�ϴ����ļ����Ѵ��ڣ�������������";
		}		
		DataWindow dw = DataWindow.dynamicCreate("select SQDXH,SQDZT_DM from XZQH_BGSQD where SBXZQH_DM='"+xzqh_dm+"'");
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long cnt = dw.retrieve();
		if(cnt>0){
			String sqdzt_dm = StringEx.sNull(dw.getItemAny(0L, "SQDZT_DM"));
			if(!sqdzt_dm.equals("50")&&!sqdzt_dm.equals("60")){
				flag = "���ȵ��ϴε����ݷ��������ϴ���";
			}
		}
		return flag;
	}
	
	/**
	 * 
	 * <p>
	 * �������ƣ�getZipzt
	 * </p>
	 * <p>
	 * ������������ȡZIP�ļ�״̬
	 * </p>
	 * 
	 * @param zipxhs
	 *            zip�ļ����
	 * @author lijl
	 * @since 2009-11-09
	 */
	public String getZipzt(String zipxhs) throws Exception{
		String[] zipxh = zipxhs.split(",");
		String msg = "����ɾ��";
		for(int i=0;i<zipxh.length;i++){
			D_xzqh_jzbgzip zipDw = new D_xzqh_jzbgzip();
			zipDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
			StringBuffer sql1 = new StringBuffer();
			sql1.append("ZIPXH = '").append(zipxh[i]).append("'");
			zipDw.setFilter(sql1.toString());
			long count1 = zipDw.retrieve();
			if(count1>0){
				String jzbgzt_dm = StringEx.sNull(zipDw.getItemAny(0, "JZBGZT_DM"));
				String wjm = StringEx.sNull(zipDw.getItemAny(0, "WJM"));
				if(jzbgzt_dm.equals("40")){
					msg = "�ļ���Ϊ��"+wjm+"����״̬Ϊ�����뵥���ɳɹ���,���ܱ�ɾ����";
					break;
				}
			}
		}
		return msg;
	}
}
