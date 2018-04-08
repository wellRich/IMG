package com.padis.business.xzqhwh.batch;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.padis.common.xtservice.connection.ConnConfig;

import ctais.business.common.xtservice.batch.BatchFactory;
import ctais.business.common.xtservice.batch.BatchTask;
import ctais.business.common.xtservice.batch.DbHelper;
import ctais.business.common.xtservice.batch.IBatchable;
import ctais.business.common.xtservice.batch.IManagerCommand;
import ctais.business.common.xtservice.batch.IScheduleable;
import ctais.services.data.DataWindow;
import ctais.services.log.LogManager;
import ctais.services.xml.Response;
import ctais.services.xml.XMLDataObject;
import ctais.services.xml.XMLDataObjectHelper;
import ctais.services.xml.XMLParser;
import ctais.util.StringEx;

/**
 * 
 * <p>
 * Title:��������������ɨ�����
 * </P>
 * <p>
 * Description:ɨ�������״̬������������ɨ���ִ��������������״̬�д����еģ������б��
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: DigitalChina Co.Ltd
 * </p>
 * 
 * @auther dongzga
 * @version 1.0
 * @since 2009-3-2 �޸��ˣ� �޸�ʱ�䣺 �޸����ݣ� �޸İ汾�ţ�
 */
public class BgsqbBatch implements IManagerCommand, IBatchable {

	private int progressPercent = 0;

	private StringBuffer sbProcessMsg = new StringBuffer();

	/**
	 * ������ĵ��ȵ�Ԫ
	 */
	private IScheduleable schedUnit;

	public BgsqbBatch() {
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
							(new XMLParser())
									.parseXMLStr("<?xml version=\"1.0\" encoding=\"GBK\"?>"
											+ argXml));
				} else {
					arg = new XMLDataObject((new XMLParser())
							.parseXMLStr(argXml));
				}

				// ����XMLDataObject�ĵ�ǰλ��
				arg.rootScrollTo("Service");
				arg.retrieve();
				System.out.println("��ʼִ������[" + (String) enviroment[0]
						+ "]......");
				String endtime = XMLDataObjectHelper.getXDOValue(arg,
						"COMMAND", "");
				LogManager.getLogger().info("����ִ�е����ʱ�䡾" + endtime + "������");
				int runminutes = -1;
				try {
					runminutes = endtime.length() > 0 ? Integer
							.parseInt(endtime) : -1;
				} catch (Exception e) {
					LogManager.getLogger().error("���������õ��������ʱ�䲻�����ָ�ʽ");
				}
				this.process(runminutes);//111

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
	 * 
	 * <p>
	 * �������ƣ�process
	 * </p>
	 * <p>
	 * ����������
	 * </p>
	 * 
	 * @author
	 * @since
	 */
	public void process(int runminutes) throws Exception {
		
		LogManager.getLogger().error("[bgsqbbatch] log start................"+new Date());		
		////System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
//		Province  
		// 11 12 13 14 15   �ܹ�22
		// 21 22 23 
		// 31 32 33 34 35 36 37(ľ��)
		// 41 42 43 44 45 46 
		// 50 51 52 53 54
		// 61 62 63��ľ�У� 64 65 66

		List list = new ArrayList();
		//----------
		
		
		StringBuffer addywsjqy = new StringBuffer("select xzqh_dm from xzqh_ywsjqy p where p.qhzt_dm!='30' order by xzqh_dm");
		
		DataWindow dw = DataWindow.dynamicCreate(addywsjqy.toString());
		
		dw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		
		long rows = dw.retrieve();

		if (rows != 0L) {
		
			long size=dw.getRowCount();
			
			for(int i=0; i<size;i++){
				
				String xzqh_dm = StringEx.sNull(dw.getItemAny(i, "xzqh_dm"));
				xzqh_dm = xzqh_dm.trim()+"%";
				
				
				
				new ProcessProvinceMoveThread(runminutes,xzqh_dm.trim()).start();
			}
		} 
		else{
			LogManager.getLogger().error("No data!");
			
		}
		//----------
		/*
		LogManager.getLogger().error("[bgsqbbatch] log 52 start()%");
		new ProcessProvinceMoveThread(runminutes,"52%").start();	
	
		LogManager.getLogger().error("[bgsqbbatch] log 15 start()%");
		new ProcessProvinceMoveThread(runminutes,"15%").start();
		
		LogManager.getLogger().error("[bgsqbbatch] log 21 start()%");
		new ProcessProvinceMoveThread(runminutes,"21%").start();*/
		/*
		LogManager.getLogger().error("[bgsqbbatch] log 43 start()%");
		new ProcessProvinceMoveThread(runminutes,"43%").start();
			
		LogManager.getLogger().error("[bgsqbbatch] log 3308   start()%");
		new ProcessProvinceMoveThread(runminutes,"3308%").start();
		
		LogManager.getLogger().error("[bgsqbbatch] log 3706   start()%");
		new ProcessProvinceMoveThread(runminutes,"3706%").start();
		
		LogManager.getLogger().error("[bgsqbbatch] log 4210 start()%");
		new ProcessProvinceMoveThread(runminutes,"4210%").start(); 
		
		LogManager.getLogger().error("[bgsqbbatch] log 3311 start()%");
		new ProcessProvinceMoveThread(runminutes,"3311%").start();		
		
    	LogManager.getLogger().error("[bgsqbbatch] log 3608 start()%");
		new ProcessProvinceMoveThread(runminutes,"3608%").start();	

		LogManager.getLogger().error("[bgsqbbatch] log 3715% start()");
		new ProcessProvinceMoveThread(runminutes,"3715%").start();		

		LogManager.getLogger().error("[bgsqbbatch] log 5206% start()");
		new ProcessProvinceMoveThread(runminutes,"5206%").start();		

		LogManager.getLogger().error("[bgsqbbatch] log 6501% start()");
		new ProcessProvinceMoveThread(runminutes,"6501%").start();
		
		LogManager.getLogger().error("[bgsqbbatch] log 4228% start()");
		new ProcessProvinceMoveThread(runminutes,"4228%").start();		

		LogManager.getLogger().error("[bgsqbbatch] log 4311% start()");
		new ProcessProvinceMoveThread(runminutes,"4311%").start();		

		LogManager.getLogger().error("[bgsqbbatch] log 4205% start()");
		new ProcessProvinceMoveThread(runminutes,"4205%").start();		

		LogManager.getLogger().error("[bgsqbbatch] log 5308% start()");
		new ProcessProvinceMoveThread(runminutes,"5308%").start();		

		LogManager.getLogger().error("[bgsqbbatch] log 4304% start()");
		new ProcessProvinceMoveThread(runminutes,"4304%").start();		

		LogManager.getLogger().error("[bgsqbbatch] log 1201% start()");
		new ProcessProvinceMoveThread(runminutes,"1201%").start();		

		LogManager.getLogger().error("[bgsqbbatch] log 4110% start()");
		new ProcessProvinceMoveThread(runminutes,"4110%").start();		
		
		LogManager.getLogger().error("[bgsqbbatch] log 2308 start()%");
		new ProcessProvinceMoveThread(runminutes,"2308%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 4109% start()");
		new ProcessProvinceMoveThread(runminutes,"4109%").start();		

		LogManager.getLogger().error("[bgsqbbatch] log 1301% start()");
		new ProcessProvinceMoveThread(runminutes,"1301%").start();		

		LogManager.getLogger().error("[bgsqbbatch] log 3703% start()");
		new ProcessProvinceMoveThread(runminutes,"3703%").start();		

		LogManager.getLogger().error("[bgsqbbatch] log 6604% start()");
		new ProcessProvinceMoveThread(runminutes,"6604%").start();
		


		LogManager.getLogger().error("[bgsqbbatch] log 5001% start()");
		new ProcessProvinceMoveThread(runminutes,"5001%").start();
		
		LogManager.getLogger().error("[bgsqbbatch] log 6601% start()");
		new ProcessProvinceMoveThread(runminutes,"6601%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 2309% start()");
		new ProcessProvinceMoveThread(runminutes,"2309%").start();


 		LogManager.getLogger().error("[bgsqbbatch] log 3601% start()");
 		new ProcessProvinceMoveThread(runminutes,"3601%").start();

 		LogManager.getLogger().error("[bgsqbbatch] log 1101% start()");
 		new ProcessProvinceMoveThread(runminutes,"1101%").start();
	
		LogManager.getLogger().error("[bgsqbbatch] log 1301% start()");
 		new ProcessProvinceMoveThread(runminutes,"1301%").start();
		
		LogManager.getLogger().error("[bgsqbbatch] log 6611% start()");
 		new ProcessProvinceMoveThread(runminutes,"6611%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 1304% start()");
 		new ProcessProvinceMoveThread(runminutes,"1304%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3101% start()");
 		new ProcessProvinceMoveThread(runminutes,"3101%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 4602% start()");
 		new ProcessProvinceMoveThread(runminutes,"4602%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3205% start()");
 		new ProcessProvinceMoveThread(runminutes,"3205%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 5002% start()");
 		new ProcessProvinceMoveThread(runminutes,"5002%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 4211% start()");
 		new ProcessProvinceMoveThread(runminutes,"4211%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 5202% start()");
 		new ProcessProvinceMoveThread(runminutes,"5202%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 6110% start()");
 		new ProcessProvinceMoveThread(runminutes,"6110%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3716% start()");
 		new ProcessProvinceMoveThread(runminutes,"3716%").start();


		LogManager.getLogger().error("[bgsqbbatch] log 5205% start()");
		new ProcessProvinceMoveThread(runminutes,"5205%").start();
		
		LogManager.getLogger().error("[bgsqbbatch] log 6612% start()");
 		new ProcessProvinceMoveThread(runminutes,"6612%").start();
		
		LogManager.getLogger().error("[bgsqbbatch] log 5103% start()");
 		new ProcessProvinceMoveThread(runminutes,"5103%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 5101% start()");
 		new ProcessProvinceMoveThread(runminutes,"5101%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 2306% start()");
 		new ProcessProvinceMoveThread(runminutes,"2306%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3707% start()");
 		new ProcessProvinceMoveThread(runminutes,"3707%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3717% start()");
 		new ProcessProvinceMoveThread(runminutes,"3717%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3204% start()");
 		new ProcessProvinceMoveThread(runminutes,"3204%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 5119% start()");
 		new ProcessProvinceMoveThread(runminutes,"5119%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 2202% start()");
 		new ProcessProvinceMoveThread(runminutes,"2202%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 4307% start()");
 		new ProcessProvinceMoveThread(runminutes,"4307%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3702% start()");
 		new ProcessProvinceMoveThread(runminutes,"3702%").start();

 		LogManager.getLogger().error("[bgsqbbatch] log 3701% start()");
		new ProcessProvinceMoveThread(runminutes,"3701%").start();


		LogManager.getLogger().error("[bgsqbbatch] log 1305% start()");
 		new ProcessProvinceMoveThread(runminutes,"1305%").start();
		
		LogManager.getLogger().error("[bgsqbbatch] log 6603% start()");
 		new ProcessProvinceMoveThread(runminutes,"6603%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3410% start()");
 		new ProcessProvinceMoveThread(runminutes,"3410%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 5309% start()");
 		new ProcessProvinceMoveThread(runminutes,"5309%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3203% start()");
 		new ProcessProvinceMoveThread(runminutes,"3203%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3309% start()");
 		new ProcessProvinceMoveThread(runminutes,"3309%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3501% start()");
 		new ProcessProvinceMoveThread(runminutes,"3501%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 6602% start()");
 		new ProcessProvinceMoveThread(runminutes,"6602%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3604% start()");
 		new ProcessProvinceMoveThread(runminutes,"3604%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 2371% start()");
 		new ProcessProvinceMoveThread(runminutes,"2371%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 5226% start()");
 		new ProcessProvinceMoveThread(runminutes,"5226%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3415% start()");
 		new ProcessProvinceMoveThread(runminutes,"3415%").start();
		
		LogManager.getLogger().error("[bgsqbbatch] log 2203% start()");
		new ProcessProvinceMoveThread(runminutes,"2203%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 4513% start()");
 		new ProcessProvinceMoveThread(runminutes,"4513%").start();
		
		LogManager.getLogger().error("[bgsqbbatch] log 3306% start()");
 		new ProcessProvinceMoveThread(runminutes,"3306%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 4301% start()");
 		new ProcessProvinceMoveThread(runminutes,"4301%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 4107% start()");
 		new ProcessProvinceMoveThread(runminutes,"4107%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 4514% start()");
 		new ProcessProvinceMoveThread(runminutes,"4514%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 6613% start()");
 		new ProcessProvinceMoveThread(runminutes,"6613%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 4101% start()");
 		new ProcessProvinceMoveThread(runminutes,"4101%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 4412% start()");
 		new ProcessProvinceMoveThread(runminutes,"4412%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3609% start()");
 		new ProcessProvinceMoveThread(runminutes,"3609%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 6610% start()");
 		new ProcessProvinceMoveThread(runminutes,"6610%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3708% start()");
 		new ProcessProvinceMoveThread(runminutes,"3708%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 6104% start()");
 		new ProcessProvinceMoveThread(runminutes,"6104%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3418% start()");
 		new ProcessProvinceMoveThread(runminutes,"3418%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3713% start()");
 		new ProcessProvinceMoveThread(runminutes,"3713%").start();
		
		LogManager.getLogger().error("[bgsqbbatch] log 2207% start()");
		new ProcessProvinceMoveThread(runminutes,"2207%").start();
				
		LogManager.getLogger().error("[bgsqbbatch] log 4208% start()");
		new ProcessProvinceMoveThread(runminutes,"4208%").start();
		
		LogManager.getLogger().error("[bgsqbbatch] log 4310% start()");
 		new ProcessProvinceMoveThread(runminutes,"4310%").start();
		
		LogManager.getLogger().error("[bgsqbbatch] log 6532% start()");
 		new ProcessProvinceMoveThread(runminutes,"6532%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3302% start()");
 		new ProcessProvinceMoveThread(runminutes,"3302%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 6609% start()");
 		new ProcessProvinceMoveThread(runminutes,"6609%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 4103% start()");
 		new ProcessProvinceMoveThread(runminutes,"4103%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3705% start()");
 		new ProcessProvinceMoveThread(runminutes,"3705%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 5305% start()");
 		new ProcessProvinceMoveThread(runminutes,"5305%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 3210% start()");
 		new ProcessProvinceMoveThread(runminutes,"3210%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 4206% start()");
 		new ProcessProvinceMoveThread(runminutes,"4206%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 1408% start()");
 		new ProcessProvinceMoveThread(runminutes,"1408%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 5105% start()");
 		new ProcessProvinceMoveThread(runminutes,"5105%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 5113% start()");
 		new ProcessProvinceMoveThread(runminutes,"5113%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 5109% start()");
 		new ProcessProvinceMoveThread(runminutes,"5109%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 1506% start()");
 		new ProcessProvinceMoveThread(runminutes,"1506").start();

		LogManager.getLogger().error("[bgsqbbatch] log 4105% start()");
 		new ProcessProvinceMoveThread(runminutes,"4105%").start();
		
		
		LogManager.getLogger().error("[bgsqbbatch] log 6106% start()");
		new ProcessProvinceMoveThread(runminutes,"6106%").start();
		
 		LogManager.getLogger().error("[bgsqbbatch] log 3301% start()");
 		new ProcessProvinceMoveThread(runminutes,"3301%").start();

		LogManager.getLogger().error("[bgsqbbatch] log 2103% start()");
 		new ProcessProvinceMoveThread(runminutes,"2103%").start();
		
		LogManager.getLogger().error("[bgsqbbatch] log 4201% start()");
 		new ProcessProvinceMoveThread(runminutes,"4201%").start();
		*/
//		try {
//			
//			long begintime = System.currentTimeMillis();
//			// 1 ���ұ�����ձ��Ƿ��д���ʧ�ܵĶ��ձ��������,�׳��쳣
//			DataWindow mxbDw = DataWindow
//					.dynamicCreate(" SELECT MXBXH FROM xzqh_bgmxb WHERE clzt_dm='31'");
//			if (mxbDw.retrieve() > 0) {
//				throw new Exception(
//						"��ʽ����������������е�ʱ����ڡ�����ʧ�ܡ��ı�����ձ�ϵͳ���ܼ����������˹�����Ԥ");
//			}
//			
////			DataWindow sqdDw = DataWindow
////					.dynamicCreate("SELECT A.SQDXH,B.GROUPXH,C.MXBXH,C.YSXZQH_DM,C.YSXZQH_MC,C.BGLX_DM,C.MBXZQH_DM,C.MBXZQH_MC FROM XZQH_BGSQD A, XZQH_BGGROUP B, XZQH_BGMXB C WHERE A.SQDXH = B.SQDXH AND B.GROUPXH = C.GROUPXH AND A.SQDZT_DM ='50' AND C.CLZT_DM ='10' ORDER BY B.LRSJ, C.PXH");
////			long count = sqdDw.retrieve();
//			// �������ϱ������뵥��������ϵͳ��ѯ������¼��
////			 2 ɨ�����뵥״̬Ϊ ���ѷ���������ҵ�����ݴ����С� �����뵥��Ӧ�� ������ձ�״̬Ϊ �����������������С�
//			// �ı�����ձ�����˳������
//			StringBuffer queryBuffer = new StringBuffer("SELECT A.SQDXH,B.GROUPXH,C.MXBXH,C.YSXZQH_DM,C.YSXZQH_MC,C.BGLX_DM,C.MBXZQH_DM,C.MBXZQH_MC FROM XZQH_BGSQD A, XZQH_BGGROUP B, XZQH_BGMXB C WHERE A.SQDXH = B.SQDXH AND B.GROUPXH = C.GROUPXH AND A.SQDZT_DM in('50','61') AND C.CLZT_DM in('10','20') ORDER BY B.PXH, C.PXH");
//			
//			ResultSet resultSet= null;
//			Connection conn = ORManager.getInstance().getConnectionPool("ctais").getConnection("0");
//			Statement stmt = conn.createStatement();
//			resultSet = stmt.executeQuery(queryBuffer.toString());
//			if(resultSet!=null){
//				try {
//					while (resultSet.next()) {
//						// �������ʱ�䳬���趨��������з�������������ֹͣ����
//						if (runminutes > 0) {
//							long endtime = System.currentTimeMillis();
//							if ((endtime - begintime) / 1000 / 60 > runminutes)
//								throw new Exception("ϵͳ����ʱ�䳬���趨������������ϵͳ��ֹ");
//						}
//						// 3,����ÿ�����ձ��Ӧ�����뵥���״̬Ϊ �����У�������Ҫ�Ż�һ�£�����Ѿ��� ������ ״̬�ˣ��Ͳ���Ҫ���޸���
//						String sqdxh = StringEx.sNull(resultSet.getString( "SQDXH"));
//						
//						D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
//						d_xzqh_bgsqd.setConnectionName(ConnConfig
//								.getConnectionName(this.getClass()));
//						d_xzqh_bgsqd.setFilter("SQDXH='" + sqdxh + "'");
//						long rows = d_xzqh_bgsqd.retrieve();
//						// ������뵥״̬Ϊ �ѷ��� �������뵥״̬����Ϊ�����У�������뵥״̬Ϊ ������ �����ڽ������ã�Ϊ���Ż����ݿ�
//						if (rows > 0) {
//							d_xzqh_bgsqd.setItemString(0, "SQDZT_DM",Common.XZQH_SQDZT_SJCLZ);
//							d_xzqh_bgsqd.setTransObject(new UserTransaction());
//							d_xzqh_bgsqd.update(true);
//						}
//						// 4, ���ó������α�����ձ�, ���ڱ��ʧ�ܵģ�Ҫ��¼ ����ʧ�� ״̬��ͬʱ��������ֹ
//						String mxbxh = StringEx.sNull(resultSet.getString("MXBXH"));
//						XzqhbgService service = new XzqhbgService();
//						LogManager.getLogger().info("������ʼִ�б�����뵥���ձ���ϸ��š�" + mxbxh + "��");
//						//PrintStream out = System.out;
//						PrintStream ps =  new PrintStream(new FileOutputStream("xzqh.log"));   
//						System.setOut(ps);
//						SimpleDateFormat format=new SimpleDateFormat();
//						Date date1 = new Date();
//						String start1 = format.format(date1);
//						LogManager.getLogger().error(mxbxh+"...................BgsqbBatch.java-process() start start: "  + start1);
//						service.start(mxbxh);
//						Date date2 = new Date();
//						String start2 = format.format(date2);
//						long numTime1 = date1.getTime();
//						long numTime2 = date2.getTime();
//						long between = (numTime2 - numTime1)/1000;
//						LogManager.getLogger().error(mxbxh+"...................BgsqbBatch.java-process() start end:   " + start2 + "method---start cost:"+between+"second");
//						//LogManager.getLogger().error("method---start cost:"+between+"second"); 
//	
//						// 5ϵͳ���ö��ձ��Ӧ�����뵥��������ϸ���ձ��Ƿ�ȫ�����ǡ�����ɹ�����������ǣ��������뵥״̬Ϊ��ҵ��������Ǩ�ơ����������ѭ����һ�����
//	
//						DataWindow queryDw = DataWindow
//								.dynamicCreate("SELECT COUNT(*) ZS FROM  XZQH_BGGROUP B, XZQH_BGMXB C WHERE  B.GROUPXH = C.GROUPXH  AND B.SQDXH='"
//										+ sqdxh + "' AND C.CLZT_DM IN ('10','20','31')");
//						if (queryDw.retrieve() > 0) {
//							if(StringEx.sNull(queryDw.getItemAny(0L, "ZS")).equals("0")
//									||StringEx.sNull(queryDw.getItemAny(0L, "ZS")).equals("")){
//								d_xzqh_bgsqd.setItemString(0, "SQDZT_DM",Common.XZQH_SQDZT_SJYQY);
//								d_xzqh_bgsqd.setTransObject(new UserTransaction());
//								d_xzqh_bgsqd.update(true);
//							}
//							
//						}
//					}
//				}catch (Exception e) {
//					e.printStackTrace();
//		            throw e;
//		        }
//				finally {
//		            try {
//		            	if(resultSet!=null){
//		            		resultSet.close();
//		            	}
//		            	if(stmt!=null){
//		            		stmt.close();
//		            	}
//		            	if(conn!=null){
//		            		conn.close();
//		            	}
//		            } catch (Exception e) {
//		                e.printStackTrace();
//		                throw e;
//		            }
//		        }
//			}else{
////				 �����ϱ������뵥״̬����Ϊ��ҵ��������Ǩ�ơ�
//				DataWindow updateDw = DataWindow
//						.dynamicCreate("UPDATE  xzqh_bgsqd a SET a.sqdzt_dm='60' WHERE NOT EXISTS(SELECT 1 FROM xzqh_bggroup b WHERE b.sqdxh=a.sqdxh) AND a.SQDZT_DM ='50'");
//				updateDw.setTransObject(new UserTransaction());
//				updateDw.update(true);
//				return;
//			}
//			
//			
//			
//			
//			
////			DataWindow sqdDw = DataWindow.dynamicCreate(queryBuffer.toString(), true);
////			sqdDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));// �������ӳ�
////			sqdDw.getRowCount(queryBuffer.toString());
////			long ps = 1000L; // һҳ�а������������ݣ����з�ҳ׼��
////			long pageCount = sqdDw.getPageCount(ps); // һ������ҳ
////			long cnt = 0L;
////			
////			for(long j=1; j<=pageCount ;j++){
////				cnt = sqdDw.retrieve(ps, j, pageCount,queryBuffer.toString());
////				if (cnt <= 0) {
////					// �����ϱ������뵥״̬����Ϊ��ҵ��������Ǩ�ơ�
////					DataWindow updateDw = DataWindow
////							.dynamicCreate("UPDATE  xzqh_bgsqd a SET a.sqdzt_dm='60' WHERE NOT EXISTS(SELECT 1 FROM xzqh_bggroup b WHERE b.sqdxh=a.sqdxh) AND a.SQDZT_DM ='50'");
////					updateDw.setTransObject(new UserTransaction());
////					updateDw.update(true);
////					return;
////				}
////				for (int i = 0; i < cnt; i++) {
////
////					// �������ʱ�䳬���趨��������з�������������ֹͣ����
////					if (runminutes > 0) {
////						long endtime = System.currentTimeMillis();
////						if ((endtime - begintime) / 1000 / 60 > runminutes)
////							throw new Exception("ϵͳ����ʱ�䳬���趨������������ϵͳ��ֹ");
////					}
////					// 3,����ÿ�����ձ��Ӧ�����뵥���״̬Ϊ �����У�������Ҫ�Ż�һ�£�����Ѿ��� ������ ״̬�ˣ��Ͳ���Ҫ���޸���
////					String sqdxh = StringEx.sNull(sqdDw.getItemAny(i, "SQDXH"));
////					
////					D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
////					d_xzqh_bgsqd.setConnectionName(ConnConfig
////							.getConnectionName(this.getClass()));
////					d_xzqh_bgsqd.setFilter("SQDXH='" + sqdxh + "'");
////					long rows = d_xzqh_bgsqd.retrieve();
////					// ������뵥״̬Ϊ �ѷ��� �������뵥״̬����Ϊ�����У�������뵥״̬Ϊ ������ �����ڽ������ã�Ϊ���Ż����ݿ�
////					if (rows > 0) {
////						d_xzqh_bgsqd.setItemString(0, "SQDZT_DM",Common.XZQH_SQDZT_SJCLZ);
////						d_xzqh_bgsqd.setTransObject(new UserTransaction());
////						d_xzqh_bgsqd.update(true);
////						
//////						DataWindow updateSqdDw = DataWindow
//////						.dynamicCreate("UPDATE XZQH_BGSQD SET SQDZT_DM='61' WHERE SQDXH='" + sqdxh+ "' AND SQDZT_DM='50'");
//////						updateSqdDw.setTransObject(new UserTransaction());
//////						updateSqdDw.update(true);
//////						int updateCount = updateSqdDw.getUpdatedRowNum();
//////						if(updateCount<=0){
//////							continue;
//////						}
////					}
////					// 4, ���ó������α�����ձ�, ���ڱ��ʧ�ܵģ�Ҫ��¼ ����ʧ�� ״̬��ͬʱ��������ֹ
////					String mxbxh = StringEx.sNull(sqdDw.getItemAny(i, "MXBXH"));
////					XzqhbgService service = new XzqhbgService();
////					LogManager.getLogger().info("������ʼִ�б�����뵥���ձ���ϸ��š�" + mxbxh + "��");
////					service.start(mxbxh);
////
////					// 5ϵͳ���ö��ձ��Ӧ�����뵥��������ϸ���ձ��Ƿ�ȫ�����ǡ�����ɹ�����������ǣ��������뵥״̬Ϊ��ҵ��������Ǩ�ơ����������ѭ����һ�����
////
////					DataWindow queryDw = DataWindow
////							.dynamicCreate("SELECT C.MXBXH  FROM  XZQH_BGGROUP B, XZQH_BGMXB C WHERE  B.GROUPXH = C.GROUPXH  AND B.SQDXH='"
////									+ sqdxh + "' AND C.CLZT_DM IN ('10','20','31')");
////					if (queryDw.retrieve() <= 0) {
//////						DataWindow updateSqdDw = DataWindow
//////						.dynamicCreate("UPDATE XZQH_BGSQD SET SQDZT_DM='60' WHERE SQDXH='" + sqdxh+ "' AND SQDZT_DM='61'");
//////						updateSqdDw.setTransObject(new UserTransaction());
//////						updateSqdDw.update(true);
////						
////						d_xzqh_bgsqd.setItemString(0, "SQDZT_DM",Common.XZQH_SQDZT_SJYQY);
////						d_xzqh_bgsqd.setTransObject(new UserTransaction());
////						d_xzqh_bgsqd.update(true);
////					}
////
////				}
////			}
////			
//
//		} catch (Exception e) {
//			LogManager.getLogger().log(e);
//			throw e;
//
//		}

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
			bt.setGroupTitle("����������������");
			bt.setGroupId(this.getClass().getName() + "_GROUP");
			bt.setGroupYxjb(IScheduleable.DEFAULT_GROUP_PRIORITY);
			bt.setPriority(1);
			bt.setBz(this.getClass().getName());
			bt.setRedo("N");
			bt.setBPersistent(true);
			bt.setPh(DbHelper.newSequence());
			bt.setDdh(DbHelper.newSequence());
			bt.setTaskDescription("����������������[" + this.getClass().getName()
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
