package com.padis.business.xzqhwh.zxbg.bgdzgl.bgdzblr;

import com.padis.business.xzqhwh.common.Common;
import com.padis.business.xzqhwh.common.Tree;
import com.padis.business.xzqhwh.common.XtwhBaseService;
import com.padis.business.xzqhwh.common.XzqhbgCommon;
import com.padis.common.constants.CommonConstants;
import com.padis.common.jgservice.IJgService;
import com.padis.common.jgservice.JgInterfaceFactory;
import com.padis.common.xzqhservice.IXzqhService;
import com.padis.common.xzqhservice.XzqhInterfaceFactory;

import ctais.services.log.LogManager;
import ctais.services.xml.XMLDataObject;
import ctais.util.StringEx;
import ctais.util.XmlStringBuffer;

/**
 * <p>
 * Description: �������������Service��
 * </p>
 * <p>
 * Copyright: Copyright (c) digitalchina 2007
 * </p>
 * <p>
 * Company: digitalchina
 * </p>
 * 
 * @Date 2009-06-11
 * @author ���
 * @version 1.0
 */
public class BgdzblrService extends XtwhBaseService{

	/**
	 * XzqhbgsqbService���Manager
	 */

	BgdzblrManager mgr;
	CheckSqd checkSqd;

	/**
	 * ���캯��
	 */
	public BgdzblrService() {
		mgr = new BgdzblrManager();
		checkSqd = new CheckSqd();
	}
	
	/** �������������ϸ
	 * <p>�������ƣ�addXzqhgbdzb()��������������</p>
	 * <p>����˵�����������������ķ�����������������DM_XZQH_WH,XT_YWXTWH_XZQHBGDZB���и�����һ����¼��</p>
	 * @param ��
	 * @Date 2009-06-16
	 * @author ���
	 * @return ��
	 */
	public void addXzqhbgsqb() {
		try {
			XMLDataObject xmldo = this.uwa.getArgXml();
			String czry_dm = uwa.getCzry_dm();
			String jg_dm = uwa.getSwjg_dm();
			String qxjg_dm = uwa.getQx_swjg_dm();
			
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			String xzqh_dm = jgService.getXzqhDmByJg(jg_dm);
			mgr.addXzqhbgsqb(xmldo,czry_dm,qxjg_dm,xzqh_dm);
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"�������������ɹ���","");
			
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.getLogger().error("������������ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);			
			this.setResponse(CommonConstants.RTN_SUCCESS, 2006, e.getMessage(), e.getMessage());
		}
	}
	
	/**
	 * <p>�������ƣ� initXzqh()����ʼ����������ҳ��</p>
	 * <p>����˵������ʼ����������ҳ��ķ�����</p>
	 * @param ��
	 * @Date 2009-06-17
	 * @author ���
	 * @return ��
	 */
	public void initXzqh() {
		try {
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			IXzqhService xzqhService =XzqhInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm= uwa.getSwjg_dm();
			String czry_dm = uwa.getCzry_dm();
			String qxjgz_dm = uwa.getQx_swjg_dm();
			String xzqh_dm = jgService.getXzqhDmByJg(swjg_dm);
			XMLDataObject xdo = xzqhService.getXzqhXxxx(xzqh_dm);		
			String dwlsgx_dm = xdo.getItemValue("DWLSGX_DM");	
			
			String resultXml = mgr.getSqdxx(xzqh_dm);
			System.out.println("sqdxx������---------------------"+resultXml);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			String jb_dm = xzqhService.getJbdm(qxjgz_dm);
			String jc_dm = xzqhService.getJcdm(xzqh_dm);
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("CZRY_DM",czry_dm);//¼���˴���
			xsBuf.append("JC_DM",jc_dm);//���δ���
			xsBuf.append("XZQH_DM",xzqh_dm);//������������
			xsBuf.append("DWLSGX_DM",dwlsgx_dm);//��λ������ϵ����
			xsBuf.append("XZQHJB_DM",jb_dm);//�������
			xsBuf.append(resultXml);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"��ת����ʼ��ҳ��ɹ���",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ʼ��ҳ��ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS, 2006, e.toString(), "");
		}
	}
	
	/**
	 * <p>�������ƣ�checkSqd()��У�������</p>
	 * <p>����˵����У��������ķ�����</p>
	 * @param ��
	 * @Date 2009-06-26
	 * @author ���
	 * @return ��
	 */
	public void checkSqd(){
		try {	
			XMLDataObject xmldo = uwa.getArgXml();
			checkSqd.checkBgdmSqd(xmldo);
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"У��������ɹ���","");
		} catch (Exception e) {
			LogManager.getLogger().error("У�������ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.getMessage(), "");
		}
	}
	
	/**
	 * <p>�������ƣ�checkTj()����ѯ�Ƿ���ͬ����������</p>
	 * <p>����˵������ѯ�Ƿ����ͬ�����������ķ�����</p>
	 * @param ��
	 * @Date 2009-06-16
	 * @author ���
	 * @return ��
	 */
	public void checkTj(){
		try {	
			XMLDataObject xdo = uwa.getArgXml();
			String ysxzqh_dm = StringEx.sNull(xdo.getItemValue("YSXZQH_DM"));
			String mbxzqh_dm = StringEx.sNull(xdo.getItemValue("MBXZQH_DM"));
			String bglx_dm = StringEx.sNull(xdo.getItemValue("XZQHBGLX_DM"));
			
			String resultXml = checkSqd.checkTj(ysxzqh_dm,mbxzqh_dm);
			if(bglx_dm.equals("31")||bglx_dm.equals("32")){
				resultXml = checkSqd.checkSxj(ysxzqh_dm,mbxzqh_dm);
			}
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("FLAG",resultXml);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"��ѯ��������������ʹ���ɹ���",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ��������������ʹ���ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>�������ƣ�isExistsXzqh()����ѯ���������Ƿ����</p>
	 * <p>����˵������ѯ���������Ƿ���ڵķ���������������ӱ�V_DM_XZQH��ѯ�������Ϣ��¼��</p>
	 * @param ��
	 * @Date 2009-06-16
	 * @author ���
	 * @return ��
	 */
	public void isExistsXzqh(){
		try {	
			XMLDataObject xdo = uwa.getArgXml();
			String xzqh_dm = StringEx.sNull(xdo.getItemValue("XZQH_DM"));
			String resultXml = checkSqd.isExistsXzqhdm(xzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("FLAG",resultXml);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"��ѯ��������������ʹ���ɹ���",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ��������������ʹ���ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>�������ƣ�isExistsYjyXzqh()���Ƿ�����ѽ��õ��������룬Ҳ���Ǹ���������������ʹ�ù�</p>
	 * <p>����˵�������Ƿ�����ѽ��õ��������룬Ҳ���Ǹ���������������ʹ�ù��ķ���������������ӱ�V_DM_XZQH��ѯ�������Ϣ��¼��</p>
	 * @param ��
	 * @Date 2009-11-13
	 * @author ���
	 * @return ��
	 */
	public void isExistsYjyXzqh(){
		try {	
			XMLDataObject xdo = uwa.getArgXml();
			String xzqh_dm = StringEx.sNull(xdo.getItemValue("XZQH_DM"));
			XzqhbgCommon bgCommon = new XzqhbgCommon();
			boolean flag = bgCommon.isExistsYjyXzqh(xzqh_dm, "DM_XZQH_YLSJ");
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("FLAG",String.valueOf(flag));
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"��ѯ��������������ʹ���ɹ���",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ��������������ʹ���ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>�������ƣ�mergerTjXzqh()����ѯ���ϼ�ͬ���������������롢����</p>
	 * <p>����˵������ѯ���ϼ�ͬ���������������롢���Ƶķ���������������ӱ�V_DM_XZQH��ѯ�������Ϣ��¼��</p>
	 * @param ��
	 * @Date 2009-06-16
	 * @author ���
	 * @return ��
	 */
	public void mergerXzqh(){
		try {	
			XMLDataObject xdo = uwa.getArgXml();
			String dbrxzqh ="";
			dbrxzqh = checkSqd.mergerXzqh(xdo);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("ITEMS",dbrxzqh);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"��ѯ��������������ʹ���ɹ���",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ��������������ʹ���ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	
	/**
	 * <p>�������ƣ�mergerTjXzqh()����ѯ���ϼ�ͬ���������������롢����</p>
	 * <p>����˵������ѯ���ϼ�ͬ���������������롢���Ƶķ���������������ӱ�V_DM_XZQH��ѯ�������Ϣ��¼��</p>
	 * @param ��
	 * @Date 2009-06-16
	 * @author ���
	 * @return ��
	 */
	public void getTjXzqh(){
		try {	
			XMLDataObject xdo = uwa.getArgXml();
			String tjxzqh ="";
			String ysxzqh_dm = StringEx.sNull(xdo.getItemValue("YSXZQH_DM"));
			tjxzqh = checkSqd.getTjXzqh(ysxzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.append("ITEMS",tjxzqh);
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"��ѯ��������������ʹ���ɹ���",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ��������������ʹ���ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * <p>�������ƣ�hasChildXzqh()����ѯ�Ƿ������¼���������</p>
	 * <p>����˵������ѯ�Ƿ�������¼����������ķ�����</p>
	 * @param ��
	 * @Date 2009-07-28
	 * @author ���
	 * @return ��
	 */
	public void hasChildXzqh(){
		try {	
			XMLDataObject xdo = uwa.getArgXml();
			String xzqh_dm = StringEx.sNull(xdo.getItemValue("XZQH_DM"));
			String resultXml = checkSqd.hasChildXzqh(xzqh_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.append("FLAG",resultXml);
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendHead("tempDataMap");
			xsBuf.appendTail("tempDataMap");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS,"��ѯ��������������ʹ���ɹ���",xsBuf.toString());
		} catch (Exception e) {
			LogManager.getLogger().error("��ѯ��������������ʹ���ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,2009, e.toString(), "");
		}
	}
	
	/**
	 * ��ʼ������������ѯ��Ϊ��WDCJ_MLFL����
	 * 
	 * @param ��
	 * @Date 2008-09-25
	 * @author ���
	 * @return ��
	 * @exception ��
	 */
	public void createSjXzqhTree() {
		try {
			XMLDataObject xdo = uwa.getArgXml();
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm= uwa.getSwjg_dm();
			String xzqhDm = jgService.getXzqhDmByJg(swjg_dm);
			Tree tree = new Tree();
			String xzqh_dm = xdo.getItemValue("XZQH_DM");
			String jc_dm = xdo.getItemValue("JC_DM");
			String ysXzqh_dm = "";
			if(xzqh_dm==null){
				ysXzqh_dm = xdo.getItemValue("YSXZQH_DM");
				xzqh_dm = ysXzqh_dm;
				jc_dm = Common.getJcdm(xzqh_dm);
				String sjXzhq_dm ="";
				if(Integer.parseInt(jc_dm)>3){
					if(Integer.parseInt(jc_dm)-Integer.parseInt(Common.getJcdm(xzqhDm))>2){
						sjXzhq_dm  = Common.getSjxzqhdm(Common.getSjxzqhdm(Common.getSjxzqhdm(xzqh_dm)));
					}else{
						sjXzhq_dm  = Common.getSjxzqhdm(Common.getSjxzqhdm(xzqh_dm));
					}
				}else{
					sjXzhq_dm = xzqhDm.substring(0, 2)+"0000000000000";
				}
				xzqh_dm = sjXzhq_dm;
			}else{
				ysXzqh_dm = xdo.getItemValue("SRCXZQH_DM");
			}
			String subTree = tree.createMoreTree(xzqh_dm,ysXzqh_dm,jc_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendHead("TREE");
			StringBuffer sbuf = new StringBuffer("<![CDATA[");
			sbuf.append(subTree);
			sbuf.append("]]>");
			xsBuf.append(sbuf.toString());
			xsBuf.appendTail("TREE");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "�����ĵ�Ŀ¼������سɹ���", xsBuf.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.getLogger().error("�����ĵ�Ŀ¼�������ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTN_FAIL, "�����ĵ�Ŀ¼�������ʧ�ܣ�", e.toString());
		}
	}
	
	
	/**
	 * ��ʼ������������ѯ��Ϊ��WDCJ_MLFL����
	 * 
	 * @param ��
	 * @Date 2008-09-25
	 * @author ���
	 * @return ��
	 * @exception ��
	 */
	public void createTjXzqhTree() {
		try {
			XMLDataObject xdo = uwa.getArgXml();
			IJgService jgService =JgInterfaceFactory.getInstance().getInterfaceImp();
			String swjg_dm= uwa.getSwjg_dm();
			String xzqhDm = jgService.getXzqhDmByJg(swjg_dm);
			Tree tree = new Tree();
			String xzqh_dm = xdo.getItemValue("XZQH_DM");
			String jc_dm = xdo.getItemValue("JC_DM");
			String ysXzqh_dm = "";
			if(xzqh_dm==null){
				ysXzqh_dm = xdo.getItemValue("YSXZQH_DM");
				xzqh_dm = ysXzqh_dm;
				jc_dm = Common.getJcdm(xzqh_dm);
				String sjXzhq_dm ="";
				if(Integer.parseInt(jc_dm)>2){
					if(Integer.parseInt(jc_dm)-Integer.parseInt(Common.getJcdm(xzqhDm))>1){
						sjXzhq_dm  = Common.getSjxzqhdm(Common.getSjxzqhdm(xzqh_dm));
					}else{
						sjXzhq_dm  = Common.getSjxzqhdm(xzqh_dm);
					}
				}else{
					sjXzhq_dm = xzqhDm.substring(0, 2)+"0000000000000";
				}
				xzqh_dm = sjXzhq_dm;
			}else{
				ysXzqh_dm = xdo.getItemValue("SRCXZQH_DM");
			}
			String subTree = tree.creatMergeTree(xzqh_dm,ysXzqh_dm,jc_dm);
			XmlStringBuffer xsBuf = new XmlStringBuffer();
			xsBuf.appendHead("ROOT");
			xsBuf.appendHead("Result");
			xsBuf.appendHead("MAP");
			xsBuf.appendHead("TREE");
			StringBuffer sbuf = new StringBuffer("<![CDATA[");
			sbuf.append(subTree);
			sbuf.append("]]>");
			xsBuf.append(sbuf.toString());
			xsBuf.appendTail("TREE");
			xsBuf.appendTail("MAP");
			xsBuf.appendHead("BEANMAP");
			xsBuf.appendTail("BEANMAP");
			xsBuf.appendTail("Result");
			xsBuf.appendTail("ROOT");
			this.setResponse(CommonConstants.RTN_SUCCESS,CommonConstants.RTNMSG_SUCCESS, "�����ĵ�Ŀ¼������سɹ���", xsBuf.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.getLogger().error("�����ĵ�Ŀ¼�������ʧ��:" + e.getMessage());
			LogManager.getLogger().log(e);
			this.setResponse(CommonConstants.RTN_SUCCESS,
					CommonConstants.RTN_FAIL, "�����ĵ�Ŀ¼�������ʧ�ܣ�", e.toString());
		}
	}
}
