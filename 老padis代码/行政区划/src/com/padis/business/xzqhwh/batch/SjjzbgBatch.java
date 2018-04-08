package com.padis.business.xzqhwh.batch;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.padis.business.xzqhwh.common.Common;

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

public class SjjzbgBatch implements IManagerCommand, IBatchable {

	private int progressPercent = 0;

	private StringBuffer sbProcessMsg = new StringBuffer();

	/**
	 * ������ĵ��ȵ�Ԫ
	 */
	private IScheduleable schedUnit;

	public SjjzbgBatch() {
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
				LogManager.getLogger().info("����ִ�еĽ���ʱ�䡾" + endtime + "��");

				this.process();

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
	public void process() throws Exception {
		// ������ʱ�����С��߼�У�鴦���С�������ʽ������
		DataWindow zipdw = DataWindow
				.dynamicCreate("select ZIPXH,JZBGZT_DM from XZQH_JZBGZIP where JZBGZT_DM in ('"
						+ Common.XZQH_JZBGZT_DRLSBCLZ
						+ "','"
						+ Common.XZQH_JZBGZT_LJJYCLZ
						+ "','"
						+ Common.XZQH_JZBGZT_SQDSQCLZ + "') order by LRSJ");
		long count = zipdw.retrieve();

		SjjzbgdrlsbManager drlsbManager = new SjjzbgdrlsbManager();
		SjjzbgljclManager ljclManager = new SjjzbgljclManager();
		SjjzbgdrzsbManager drzsbManager = new SjjzbgdrzsbManager();

		for (int i = 0; count > 0 && i < count; i++) {

			String jzbgzt_dm = StringEx.sNull(zipdw.getItemAny(i, "JZBGZT_DM"));
			String zipxh = StringEx.sNull(zipdw.getItemAny(i, "ZIPXH"));
			if (jzbgzt_dm.equals(Common.XZQH_JZBGZT_DRLSBCLZ))
				drlsbManager.drlsb(zipxh);
			else if (jzbgzt_dm.equals(Common.XZQH_JZBGZT_LJJYCLZ))
				ljclManager.checkLogic(zipxh);
			else if (jzbgzt_dm.equals(Common.XZQH_JZBGZT_SQDSQCLZ))
				drzsbManager.drzsb(zipxh);
		}

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
			bt.setGroupTitle("ʡ�����ݼ��б��");
			bt.setGroupId(this.getClass().getName() + "_GROUP");
			bt.setGroupYxjb(IScheduleable.DEFAULT_GROUP_PRIORITY);
			bt.setPriority(1);
			bt.setBz(this.getClass().getName());
			bt.setRedo("N");
			bt.setBPersistent(true);
			bt.setPh(DbHelper.newSequence());
			bt.setDdh(DbHelper.newSequence());
			bt
					.setTaskDescription("ʡ�����ݼ��б��[" + this.getClass().getName()
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
