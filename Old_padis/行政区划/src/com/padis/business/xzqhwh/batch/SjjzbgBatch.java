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
 * Title:导出行政区划扫描程序
 * </P>
 * <p>
 * Description:扫描全国的行政区划以及最近变更的行政区划，扫描后导出行政区划
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
 * @since 2009-08-14 修改人： 修改时间： 修改内容： 修改版本号：
 */

public class SjjzbgBatch implements IManagerCommand, IBatchable {

	private int progressPercent = 0;

	private StringBuffer sbProcessMsg = new StringBuffer();

	/**
	 * 批任务的调度单元
	 */
	private IScheduleable schedUnit;

	public SjjzbgBatch() {
	}

	/**
	 * 取得当前处理状态
	 * 
	 * @return 处理状态信息
	 */
	public String getProcessMsg() {
		return this.sbProcessMsg.toString();
	}

	/**
	 * 返回处理进度百分比
	 * 
	 * @return 百分比(0-100)
	 */
	public int getProgressPercent() {
		return this.progressPercent;
	}

	public void setSchedUnit(IScheduleable forSchedUnit) {
		this.schedUnit = forSchedUnit;
	}

	public void execute() throws Exception {
		String argXml = null; // 任务参数
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

				// 重置XMLDataObject的当前位置
				arg.rootScrollTo("Service");
				arg.retrieve();
				System.out.println("开始执行任务[" + (String) enviroment[0]
						+ "]......");
				String endtime = XMLDataObjectHelper.getXDOValue(arg,
						"COMMAND", "");
				LogManager.getLogger().info("任务执行的结束时间【" + endtime + "】");

				this.process();

				System.out.println("执行完成任务[" + (String) enviroment[0] + "]。");

				this.progressPercent = 100;
				this.sbProcessMsg.append("执行任务[" + StringEx.sNull(argXml)
						+ "]成功。");
			} else {
				throw new IllegalArgumentException("任务参数不应为空");
			}
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			String strMsg = "执行任务[" + StringEx.sNull(argXml) + "]失败："
					+ sw.toString();
			this.sbProcessMsg.append(strMsg);
			LogManager.getLogger().error(strMsg);
			throw ex;
		}
	}

	/**
	 * 
	 * <p>
	 * 方法名称：process
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @author
	 * @since
	 */
	public void process() throws Exception {
		// 导入临时表处理中、逻辑校验处理中、导入正式表处理中
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
	 * 事件:任务加入队列
	 * 
	 * @throws Exception
	 *             错误
	 */
	public void queueEntered() throws Exception {
		LogManager.getLogger().debug(
				this.schedUnit.getTaskDescription() + "： 任务加入队列。");
	}

	/**
	 * 任务取消
	 * 
	 * @throws Exception
	 *             取消失败
	 */
	public void canceled() throws Exception {
		LogManager.getLogger().debug(
				this.schedUnit.getTaskDescription() + "： 任务取消。");
	}

	/**
	 * 任务执行关闭
	 */
	public void closed() {
		LogManager.getLogger().debug(
				this.schedUnit.getTaskDescription() + "： 任务执行关闭。");
	}

	/**
	 * 任务出队列
	 * 
	 * @throws Exception
	 *             取消失败
	 */
	public void queueOut() throws Exception {
		LogManager.getLogger().debug(
				this.schedUnit.getTaskDescription() + "： 任务出队列。");
	}

	XMLDataObject xdoArg = null; // 命令参数

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
			// 重置XMLDataObject的当前位置
			this.xdoArg.rootScrollTo("Service");
			this.xdoArg.retrieve();

			String cmd = XMLDataObjectHelper.getXDOValue(this.xdoArg,
					"COMMAND", "");

			BatchTask bt = new BatchTask();
			bt.setId(this.getClass().getName());
			bt.setGroupTitle("省级数据集中变更");
			bt.setGroupId(this.getClass().getName() + "_GROUP");
			bt.setGroupYxjb(IScheduleable.DEFAULT_GROUP_PRIORITY);
			bt.setPriority(1);
			bt.setBz(this.getClass().getName());
			bt.setRedo("N");
			bt.setBPersistent(true);
			bt.setPh(DbHelper.newSequence());
			bt.setDdh(DbHelper.newSequence());
			bt
					.setTaskDescription("省级数据集中变更[" + this.getClass().getName()
							+ "]");
			bt.setTaskExecutor(this.getClass().getName());

			// 设置批处理参数
			StringBuffer sbXml = new StringBuffer("<CtaisSession><Service>");
			sbXml.append("<COMMAND>" + cmd + "</COMMAND>");
			sbXml.append("</Service></CtaisSession>");
			bt.setEnvironment(new Object[] { this.getClass().getName(),
					sbXml.toString() });

			BatchFactory.getTaskScheduler().addTask(bt);

			return new Response(0, 2000, "添加到任务队列成功。", "");
		} catch (Exception ex) {
			return new Response(1, 2005, "添加到任务队列中失败！", "");
		}
	}

}
