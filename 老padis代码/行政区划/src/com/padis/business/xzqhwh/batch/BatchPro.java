package com.padis.business.xzqhwh.batch;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.padis.business.common.data.xzqh.D_xzqh_bgsqd;
import com.padis.business.xzqhwh.xzqhbg.XzqhbgService;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.services.log.LogManager;
import ctais.services.or.ORManager;
import ctais.services.or.UserTransaction;
import ctais.util.StringEx;

public class BatchPro {
	public void processPrivinceMove(int runminutes, String proxzqh)
			throws Exception {
		LogManager.getLogger().error(
				"BatchPro log ----------------------------------------start"
						+ proxzqh + new Date());
		try {
			long begintime = System.currentTimeMillis();

			DataWindow mxbDw = DataWindow
					.dynamicCreate(" SELECT MXBXH FROM xzqh_bgmxb WHERE clzt_dm='31' and mbxzqh_dm like '"
							+ proxzqh + "'");

			if (mxbDw.retrieve() > 0L) {
				throw new Exception(
						"正式变更区划批处理运行的时候存在“处理失败”的变更对照表，系统不能继续处理，请人工检查干预");
			}

			StringBuffer queryBuffer = new StringBuffer(
					"SELECT A.SQDXH,B.GROUPXH,C.MXBXH,C.YSXZQH_DM,C.YSXZQH_MC,C.BGLX_DM,C.MBXZQH_DM,C.MBXZQH_MC FROM XZQH_BGSQD A, XZQH_BGGROUP B, XZQH_BGMXB C WHERE A.SQDXH = B.SQDXH AND B.GROUPXH = C.GROUPXH AND A.SQDZT_DM in('50','61') AND C.CLZT_DM in('10','20') and C.mbxzqh_dm like'"
							+ proxzqh + "'" + " ORDER BY B.PXH, C.PXH");

			ResultSet resultSet = null;
			Connection conn = ORManager.getInstance()
					.getConnectionPool("ctais").getConnection("0");

			Statement stmt = conn.createStatement();
			resultSet = stmt.executeQuery(queryBuffer.toString());
			if (resultSet != null)
				;
			try {
				while (resultSet.next()) {
					if (runminutes > 0) {
						long endtime = System.currentTimeMillis();
						if ((endtime - begintime) / 1000L / 60L > runminutes) {
							throw new Exception("系统运行时间超过设定的最大分钟数，系统终止");
						}
					}

					String sqdxh = StringEx.sNull(resultSet.getString("SQDXH"));

					D_xzqh_bgsqd d_xzqh_bgsqd = new D_xzqh_bgsqd();
					d_xzqh_bgsqd.setConnectionName(ConnConfig
							.getConnectionName(super.getClass()));

					d_xzqh_bgsqd.setFilter("SQDXH='" + sqdxh + "'");
					long rows = d_xzqh_bgsqd.retrieve();

					if (rows > 0L) {
						d_xzqh_bgsqd.setItemString(0L, "SQDZT_DM", "61");

						d_xzqh_bgsqd.setTransObject(new UserTransaction());
						d_xzqh_bgsqd.update(true);
					}

					String mxbxh = StringEx.sNull(resultSet.getString("MXBXH"));

					XzqhbgService service = new XzqhbgService();
					LogManager.getLogger().info(
							"批处理开始执行变更申请单对照表明细序号【" + mxbxh + "】");

					PrintStream ps = new PrintStream(new FileOutputStream(
							"xzqh.log"));

					System.setOut(ps);
					SimpleDateFormat format = new SimpleDateFormat();
					Date date1 = new Date();
					String start1 = format.format(date1);
					LogManager.getLogger().error(
							"[BatchPro]-processPrivinceMove() province:"
									+ proxzqh + " mxbxh:" + mxbxh
									+ " starttime:" + start1);

					service.start(mxbxh);//111111111111
					Date date2 = new Date();
					String start2 = format.format(date2);
					long numTime1 = date1.getTime();
					long numTime2 = date2.getTime();
					long between = (numTime2 - numTime1) / 1000L;

					LogManager.getLogger().error(
							"[BatchPro]-processPrivinceMove() province:"
									+ proxzqh + " mxbxh:" + mxbxh + " endtime:"
									+ start2 + " cost:" + between + "second");

					DataWindow queryDw = DataWindow
							.dynamicCreate("SELECT COUNT(*) ZS FROM  XZQH_BGGROUP B, XZQH_BGMXB C WHERE  B.GROUPXH = C.GROUPXH  AND B.SQDXH='"
									+ sqdxh
									+ "' AND C.CLZT_DM IN ('10','20','31')");

					if ((queryDw.retrieve() > 0L)
							&& (((StringEx.sNull(queryDw.getItemAny(0L, "ZS"))
									.equals("0")) || (StringEx.sNull(queryDw
									.getItemAny(0L, "ZS")).equals(""))))) {
						d_xzqh_bgsqd.setItemString(0L, "SQDZT_DM", "60");

						d_xzqh_bgsqd.setTransObject(new UserTransaction());

						d_xzqh_bgsqd.update(true);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				try {
					if (resultSet != null) {
						resultSet.close();
					}
					if (stmt != null) {
						stmt.close();
					}
					if (conn != null)
						conn.close();
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}

				DataWindow updateDw = DataWindow
						.dynamicCreate("UPDATE  xzqh_bgsqd a SET a.sqdzt_dm='60' WHERE NOT EXISTS(SELECT 1 FROM xzqh_bggroup b WHERE b.sqdxh=a.sqdxh) AND a.SQDZT_DM ='50'");

				updateDw.setTransObject(new UserTransaction());
				updateDw.update(true);
			}

		} catch (Exception e) {
			LogManager.getLogger().log(e);
			throw e;
		}
	}
}