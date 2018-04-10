package com.padis.business.xzqhwh.common;

import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.util.XmlStringBuffer;

/**
 * <p>Title: Tree</P>
 * <p>Description: ��������������</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: DigitalChina Co.Ltd</p>
 * @auther lijl
 * @version 1.0
 * @since 2009-11-10
 * �޸��ˣ�
 * �޸�ʱ�䣺
 * �޸����ݣ�
 * �޸İ汾�ţ�
 */
public class Tree {
	
	/**
	 * <p>�������ƣ�createMoreTree</p>
	 * <p>������������ѯ�缶Ǩ�Ƶ�����</p>
	 * @param xzhq_dm ��ǰ�ڵ�
	 * @param jc_dm ���δ���
	 * @return �ӽڵ�����
	 * @throws Exception
	 * @author lijl
	 * @since 2009-11-10
	 */
	public String createXzqhTree(String xzqh_dm, String jc_dm) throws Exception{
		
		XmlStringBuffer xsb = new XmlStringBuffer();
		DataWindow dw = getSubtree(xzqh_dm);
		long cnt = dw.getRowCount();
		if(cnt > 0L){
			for(int i = 0; i < (int)cnt; i++){
				boolean flag = false;
				String xzqh_mc = (String) dw.getItemAny(i, "XZQH_MC");				//�ӽڵ�����
				String xzqhDm = (String)(dw.toXDO()).getItemAny(i, "XZQH_DM");
				DataWindow tmpDw = getSubtree(xzqhDm);
				if(tmpDw.getRowCount() > 0L){				//�ж��Ƿ����ӽڵ�
					flag = true;
				}
				xsb.append(createNode1(xzqh_mc, xzqhDm,flag));
			}
		}else{
			xsb.append("");
		}
		XmlStringBuffer xsbrtn = new XmlStringBuffer();
		xsbrtn.append("<tree>");
		xsbrtn.append(xsb.toString());
		xsbrtn.append("</tree>");
		return xsbrtn.toString();
	}
	
	/**
	 * <p>�������ƣ�createMoreTree</p>
	 * <p>������������ѯ�缶Ǩ�Ƶ�����</p>
	 * @param xzhq_dm ��ǰ�ڵ�
	 * @param jc_dm ���δ���
	 * @return �ӽڵ�����
	 * @throws Exception
	 * @author lijl
	 * @since 2009-11-10
	 */
	public String createMoreTree(String xzqh_dm, String ysXzqh_dm, String jc_dm) throws Exception{
		
		XmlStringBuffer xsb = new XmlStringBuffer();
		DataWindow dw = getSubtree(xzqh_dm);
		long cnt = dw.getRowCount();
		if(cnt > 0L){
			for(int i = 0; i < (int)cnt; i++){
				boolean disabled = false;
				boolean flag = false;
				String xzqh_mc = (String) dw.getItemAny(i, "XZQH_MC");				//�ӽڵ�����
				String xzqhDm = (String)(dw.toXDO()).getItemAny(i, "XZQH_DM");		
				if(ysXzqh_dm.equals(xzqhDm)||Common.getSjxzqhdm(ysXzqh_dm).equals(xzqhDm)){
					continue;
				}
				DataWindow tmpDw = getSubtree(xzqhDm);
				String jcDm =  Common.getJcdm(xzqhDm);
				if(Integer.parseInt(jc_dm)-Integer.parseInt(jcDm)>1){
					disabled = true;
				}else if(Integer.parseInt(jc_dm)-Integer.parseInt(jcDm)<=0){
					break;
				}
				if(tmpDw.getRowCount() > 0L&&Integer.parseInt(jc_dm)-Integer.parseInt(jcDm)>1){				//�ж��Ƿ����ӽڵ�
					flag = true;
				}
				xsb.append(createNode(xzqh_mc, xzqhDm,jc_dm, ysXzqh_dm, flag, disabled));
			}
		}else{
			xsb.append("");
		}
		XmlStringBuffer xsbrtn = new XmlStringBuffer();
		xsbrtn.append("<tree>");
		xsbrtn.append(xsb.toString());
		xsbrtn.append("</tree>");
		return xsbrtn.toString();
	}
	
	/**
	 * <p>�������ƣ�creatMergeTree</p>
	 * <p>������������ѯ�缶���������</p>
	 * @param xzhq_dm ��ǰ�ڵ�
	 * @param jc_dm ���δ���
	 * @return �ӽڵ�����
	 * @throws Exception
	 * @author lijl
	 * @since 2009-11-10
	 */
	public String creatMergeTree(String xzqh_dm, String ysXzqh_dm, String jc_dm) throws Exception{
		
		XmlStringBuffer xsb = new XmlStringBuffer();
		DataWindow dw = getSubtree(xzqh_dm);
		long cnt = dw.getRowCount();
		if(cnt > 0L){
			for(int i = 0; i < (int)cnt; i++){
				boolean disabled = false;
				boolean flag = false;
				String xzqh_mc = (String) dw.getItemAny(i, "XZQH_MC");				//�ӽڵ�����
				String xzqhDm = (String)(dw.toXDO()).getItemAny(i, "XZQH_DM");
				if(ysXzqh_dm.equals(xzqhDm)){
					continue;
				}
				DataWindow tmpDw = getSubtree(xzqhDm);
				String jcDm =  Common.getJcdm(xzqhDm);
				if(Integer.parseInt(jc_dm)-Integer.parseInt(jcDm)>=1){
					disabled = true;
				}else if(Integer.parseInt(jc_dm)-Integer.parseInt(jcDm)<0){
					break;
				}
				if(tmpDw.getRowCount() > 0L&&Integer.parseInt(jc_dm)-Integer.parseInt(jcDm)>=1){				//�ж��Ƿ����ӽڵ�
					flag = true;
				}
				xsb.append(createNode(xzqh_mc, xzqhDm,jc_dm,ysXzqh_dm, flag, disabled));
			}
		}else{
			xsb.append("");
		}
		XmlStringBuffer xsbrtn = new XmlStringBuffer();
		xsbrtn.append("<tree>");
		xsbrtn.append(xsb.toString());
		xsbrtn.append("</tree>");
		return xsbrtn.toString();
	}
	
	/**
	 * <p>�������ƣ�getSubtree</p>
	 * <p>�����������������л�ȡ�¼�Ŀ¼�ڵ���Ϣ</p>
	 * @param forCurJddm Ŀ¼�ڵ������
	 * @return Ŀ¼�ڵ���Ϣ
	 * @throws Exception
	 * @author lijl
	 * @since 2009-11-10
	 */
	private DataWindow getSubtree(String xzqh_dm) throws Exception{
		/**
		 * ���ݵ�ǰ�ڵ�
		 */
		StringBuffer sbXzqh = new StringBuffer("select XZQH_DM,XZQH_MC from V_DM_XZQH_YLSJ " +    //�����ѯ���
				"where SJ_XZQH_DM ='");
		sbXzqh.append(xzqh_dm).append("' order by XZQH_DM asc");
		
		DataWindow dw = DataWindow.dynamicCreate(sbXzqh.toString());
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		dw.retrieve();
		return dw;
	}
	/**
	 * <p>�������ƣ�createNode</p>
	 * <p>�����������������л�ȡĿ¼�ڵ�����</p>
	 * @param jdmc Ŀ¼�ڵ�����
	 * @param jddm Ŀ¼�ڵ�����
	 * @param flag �Ƿ����¼��ı�־
	 * @param disabled Ŀ¼�ڵ��Ƿ���Ч�ı�־
	 * @return Ŀ¼�ڵ�����
	 * @throws Exception
	 * @author lijl
	 * @since 2009-11-10
	 */
	private String createNode(String jdmc, String jddm,String jc_dm,String ysXzqh_dm, boolean flag,boolean disabled) {
		StringBuffer sb = new StringBuffer();
		StringBuffer str1 = new StringBuffer("action_prefix_");
		String action = str1.append(jddm).append("','").append(disabled).append("_action_suffix").toString();
		sb.append(" <tree ");
		sb.append(" text= ");
		sb.append(String.valueOf(String.valueOf((new StringBuffer(
				"\"")).append(jdmc))));		
		sb.append("\"");
		if (flag) {
			StringBuffer str2 = new StringBuffer("src_prefix_");
			StringBuffer str3 = new StringBuffer("&amp;JC_DM=");
			str3.append(jc_dm);
			str3.append("&amp;SRCXZQH_DM=").append(ysXzqh_dm);
			String src = str2.append(jddm).append(str3.toString()).append("_src_suffix").toString();
			sb.append(" src= ");
			sb.append(String.valueOf(String.valueOf((new StringBuffer("\"")).append(src).append("\""))));
		}
		sb.append(" disabled= ");
		sb.append(String.valueOf(String.valueOf((new StringBuffer(
				"\"")).append(disabled).append("\""))));
		sb.append(" action= ");
		sb.append(String.valueOf(String.valueOf((new StringBuffer(
				"\"")).append(action).append("\""))));
		sb.append(" key= ");
		sb.append(String.valueOf(String.valueOf((new StringBuffer(
				"\"")).append(jddm).append("\""))));
		sb.append(" ></tree>");
		return sb.toString();
		
	}
	
	/**
	 * <p>�������ƣ�createNode</p>
	 * <p>�����������������л�ȡĿ¼�ڵ�����</p>
	 * @param jdmc Ŀ¼�ڵ�����
	 * @param jddm Ŀ¼�ڵ�����
	 * @param flag �Ƿ����¼��ı�־
	 * @param disabled Ŀ¼�ڵ��Ƿ���Ч�ı�־
	 * @return Ŀ¼�ڵ�����
	 * @throws Exception
	 * @author lijl
	 * @since 2009-11-10
	 */
	private String createNode1(String jdmc, String jddm,boolean flag) {
		StringBuffer sb = new StringBuffer();
		StringBuffer str1 = new StringBuffer("action_prefix_");
		String action = str1.append(jddm).append("_action_suffix").toString();
		sb.append(" <tree ");
		sb.append(" text= ");
		sb.append(String.valueOf(String.valueOf((new StringBuffer(
				"\"")).append(jdmc))));		
		sb.append("\"");
		if (flag) {
			StringBuffer str2 = new StringBuffer("src_prefix_");
			String src = str2.append(jddm).append("_src_suffix").toString();
			sb.append(" src= ");
			sb.append(String.valueOf(String.valueOf((new StringBuffer("\"")).append(src).append("\""))));
		}
		sb.append(" action= ");
		sb.append(String.valueOf(String.valueOf((new StringBuffer(
				"\"")).append(action).append("\""))));
		sb.append(" key= ");
		sb.append(String.valueOf(String.valueOf((new StringBuffer(
				"\"")).append(jddm).append("\""))));
		sb.append(" ></tree>");
		return sb.toString();
		
	}
}
