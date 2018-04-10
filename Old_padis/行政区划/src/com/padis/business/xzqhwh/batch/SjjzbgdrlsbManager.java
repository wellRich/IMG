package com.padis.business.xzqhwh.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import com.padis.business.xzqhwh.common.UnicodeReader;
import com.padis.business.common.data.xzqh.D_xzqh_jzbgdzb_temp;
import com.padis.business.common.data.xzqh.D_xzqh_jzbgsj_temp;
import com.padis.business.common.data.xzqh.D_xzqh_jzbgzip;
import com.padis.business.xzqhwh.common.Common;
import com.padis.business.xzqhwh.common.FileUtils;
import com.padis.business.xzqhwh.common.XzqhbgBean;
import com.padis.business.xzqhwh.common.XzqhbgCommon;
import com.padis.business.xzqhwh.common.ZipUtil;
import com.padis.common.xtservice.XtDate;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.business.common.sequence.IGeneralSequence;
import ctais.services.data.DataWindow;
import ctais.services.log.LogManager;
import ctais.services.or.UserTransaction;
import ctais.services.sequence.SequenceManager;
import ctais.util.StringEx;

/**
 * 
 * <p>
 * Title:
 * </P>
 * <p>
 * Description: 集中变更导入临时表
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
 * @since 2009-10-23 修改人： 修改时间： 修改内容： 修改版本号：
 */
public class SjjzbgdrlsbManager {
	public void drlsb(String zipxh) throws Exception {

		D_xzqh_jzbgzip zipdw = new D_xzqh_jzbgzip();	
		zipdw.setFilter("ZIPXH='"+zipxh+"'");
		long count = zipdw.retrieve();
		if(count<=0)
			return;
		//String zipxh = StringEx.sNull(zipdw.getItemAny(i, "ZIPXH"));
		String wjlj = StringEx.sNull(zipdw.getItemAny(0, "WJLJ"));
		String czry_dm = StringEx.sNull(zipdw.getItemAny(0, "LRR_DM"));
		String qxjg_dm = StringEx.sNull(zipdw.getItemAny(0, "LRJG_DM"));
		String wjm = StringEx.sNull(zipdw.getItemAny(0, "WJM"));
		wjm  = wjm.substring(0, wjm.lastIndexOf("."));
		String unzipPath = "";
		String path ="";
		try {
			LogManager.getLogger().info("批处理开始执行ZIP文件序号【" + zipxh + "】");
			if (!wjlj.equals("")) {
				int pos = wjlj.lastIndexOf("/");
				if (pos > -1) {
					unzipPath = wjlj.substring(0, pos);
				}
			}
			File fileUnZipDir = new File(unzipPath+"/unzip",wjm);
			if (!fileUnZipDir.exists()) {
				if (!fileUnZipDir.mkdirs()) {
					LogManager.getLogger().error("无法创建文件上传目录");
					return ;
				}
			}
			path = fileUnZipDir.getAbsolutePath();
			path = path.replaceAll("\\\\", "/");
			ZipUtil.unzip(wjlj, path);

			this.importTempTables(zipxh, path, czry_dm,qxjg_dm);

		} catch (Exception e) {

			LogManager.getLogger().error(e.getMessage());
			zipdw.setItemString(0, "BZ", e.getMessage());
			zipdw.setItemString(0, "JZBGZT_DM", Common.XZQH_JZBGZT_DRLSBSB);
			zipdw.setTransObject(new UserTransaction());
			zipdw.update(true);
		} finally {
			FileUtils.deleteDir(path);
		}

	}

	/**
	 * 
	 * <p>
	 * 方法名称：addXzqhjzbgdzb
	 * </p>
	 * <p>
	 * 方法描述：将一个业务记录转化成指令，例如A县变更B县，最后需要将A下所有的乡和村都要列举出来返回
	 * </p>
	 * 
	 * @param xb
	 * @return
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-7-8
	 */
	private void importTempTables(String zipxh, String unzipPath,
			String czry_dm, String qxjg_dm) throws Exception {
		File file = new File(unzipPath);
		UserTransaction ut = new UserTransaction();
		BufferedReader reader = null;
		D_xzqh_jzbgzip zipDw = new D_xzqh_jzbgzip();
		zipDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		StringBuffer zipsql = new StringBuffer();
		zipsql.append("ZIPXH = '").append(zipxh).append("'");
		zipDw.setFilter(zipsql.toString());
		long count = zipDw.retrieve();
		String xzqh_dm = "";
		if(count>0){
			xzqh_dm = StringEx.sNull(zipDw.getItemAny(0L, "XZQH_DM"));
		}
		this.checkFileName(file, zipxh, xzqh_dm);
		try {
			ut.begin();
			String cwxx = "";// 数据错误信息
			if (file.isDirectory()) {
				String[] tempList = file.list();
				for (int i = 0; i < tempList.length; i++) {
					reader = new BufferedReader(new UnicodeReader(
							new FileInputStream(unzipPath + "/" + tempList[i]),"UTF-8"));
					String tempString = null;
					String counts = "0";
					int line = 1;
					int pxh = 1;
					while ((tempString = reader.readLine()) != null) {
						if (tempString.trim().length() <= 0)
							continue;
						cwxx = "";
						String[] args = tempString.split(",");
						if (line == 1 && tempString.length() > 0) {
							counts = tempString.trim();
						}
						LogManager.getLogger().info("tempString=" + tempString);
						// 如果是第二行，查看是否符合标题规范
						if (line == 2 && args.length > 0) {

							if (tempList[i].toUpperCase()
									.indexOf("QHDM_BGDZB_") > -1) {
								if (args.length != 7) {
									throw new Exception("“" + tempString
											+ "”逗号分隔错误");
								}
								LogManager.getLogger().info(
										"agrs0=" + args[0] + "agrs1=" + args[1]
												+ "agrs2=" + args[2] + "agrs3="
												+ args[3] + "agrs4=" + args[4]
												+ "agrs5=" + args[5] + "agrs6="
												+ args[6]);
								// 导入XZQH_JZBGDZB_TEMP表
								if (!StringEx.sNull(args[0].trim()).equals(
										"调整编号")
										|| !StringEx.sNull(args[1].trim())
												.equals("调整说明")
										|| !StringEx.sNull(args[2].trim())
												.equals("原区划名称")
										|| !StringEx.sNull(args[3].trim())
												.equals("原区划代码")
										|| !StringEx.sNull(args[4].trim())
												.equals("调整类型")
										|| !StringEx.sNull(args[5].trim())
												.equals("现区划名称")
										|| !StringEx.sNull(args[6].trim())
												.equals("现区划代码")) {
									throw new Exception(
											tempList[i]+"文件标题错误，上传文件标题是["+tempString.trim()+"],规范中要求标题是：[调整编号,调整说明,原区划名称,原区划代码,调整类型,现区划名称,现区划代码]");
								}
							} else if (tempList[i].toUpperCase().indexOf("QHDM_") > -1) {
								if (args.length != 2) {
									throw new Exception("“" + tempString
											+ "”逗号分隔错误");
								}
								LogManager.getLogger()
										.info("agrs0=" + args[0] + "agrs1="+ args[1]);
								// 导入XZQH_JZBGDZB_TEMP表
								if (!StringEx.sNull(args[0].trim()).equals("区划名称")
										|| !StringEx.sNull(args[1].trim()).equals("区划代码")) {
									throw new Exception(
											tempList[i]+"文件标题错误，上传文件标题是["+tempString.trim()+"],规范中要求标题是：[区划名称,区划代码]");

								}
							}

						}
						// 第三行以后是正式数据
						if (line >= 3 && args.length > 0) {
							if (tempList[i].toUpperCase().indexOf("BGDZB") > -1) {
								if (args.length != 7) {
									throw new Exception("“" + tempString
											+ "”逗号分隔错误");
								}
								LogManager.getLogger().info(
										"agrs0=" + args[0] + "agrs1=" + args[1]
												+ "agrs2=" + args[2] + "agrs3="
												+ args[3] + "agrs4=" + args[4]
												+ "agrs5=" + args[5] + "agrs6="
												+ args[6]);
								String yxzqh_dm = StringEx.sNull(args[3].trim());
								String mbxzqh_dm = StringEx.sNull(args[6].trim());
								String bglx_dm = StringEx.sNull(args[4].trim());
								String bh = StringEx.sNull(args[0].trim());

								if (bh.equals("")) {
									cwxx = "第"+line+"行出错：调整编号为空，请检查！";
								} else if (yxzqh_dm.equals("")) {
									if (!bglx_dm.equals(Common.ADD)) {
										cwxx = "第"+line+"行出错：原区划代码为空，请检查！";
									}
								} else if (yxzqh_dm.length() != 15
										&& yxzqh_dm.length() != 12) {
									cwxx = "第"+line+"行出错：原区划代码“" + yxzqh_dm + "”不符合规范，请检查！";
								} else if (mbxzqh_dm.equals("")) {
									cwxx = "第"+line+"行出错：现区划代码为空，请检查！";
								} else if (mbxzqh_dm.length() != 15
										&& mbxzqh_dm.length() != 12) {
									cwxx = "第"+line+"行出错：现区划代码“" + yxzqh_dm + "”不符合规范，请检查！";
								}
								if(yxzqh_dm.equals("")){
									if(!xzqh_dm.substring(0, 2).equals(mbxzqh_dm.substring(0, 2))){
										cwxx = tempList[i]+"文件的第"+line+"行出错：现区划代码“" + mbxzqh_dm + "”和ZIP文件区划代码不一致，请检查！";
									}
								}else if(!xzqh_dm.substring(0, 2).equals(yxzqh_dm.substring(0, 2))){
									cwxx = tempList[i]+"文件的第"+line+"行出错：原区划代码“" + yxzqh_dm + "”和ZIP文件区划代码不一致，请检查！";
								}
								if(!xzqh_dm.substring(0, 2).equals(mbxzqh_dm.substring(0, 2))){
									cwxx = tempList[i]+"文件的第"+line+"行出错：现区划代码“" + mbxzqh_dm + "”和ZIP文件区划代码不一致，请检查！";
								}
								if (yxzqh_dm.length() == 12) {
									yxzqh_dm = yxzqh_dm + "000";
								}
								if (mbxzqh_dm.length() == 12) {
									mbxzqh_dm = mbxzqh_dm + "000";
								}
								if(!Common.getSjxzqhdm(Common.getSjxzqhdm(yxzqh_dm))
								.equals(Common.getSjxzqhdm(Common.getSjxzqhdm(mbxzqh_dm)))
								&&(bglx_dm.equals(Common.MERGE)||bglx_dm.equals(Common.MOVE))){
									this.insertKljbg(args, zipxh, czry_dm,qxjg_dm, cwxx, pxh++, ut);
								}else{
									this.addXzqhjzbgdzb(args, zipxh, czry_dm,qxjg_dm, cwxx, pxh++, ut);
								}							
							} else {
								if (args.length != 2) {
									throw new Exception("“" + tempString
											+ "”逗号分隔错误");
								}
								LogManager.getLogger()
										.info("agrs0=" + args[0] + "agrs1="+ args[1]);
								if(!xzqh_dm.substring(0, 2).equals(args[1].substring(0, 2))){
									cwxx = tempList[i]+"文件的第"+line+"行出错：区划代码“" + args[1] + "”和ZIP文件区划代码不一致，请检查！";
									throw new Exception(cwxx);
								}
								//导入XZQH_JZBGSJ_TEMP表中
								this.addXzqhjzsj(args, zipxh, ut);
							}

						}

						line++;
					}
					// 比较行数是否与提供的数字一样
					if ((line - 3) != Integer.parseInt(counts)) {
						throw new Exception("给出的数据量和实际条数不符合，请检查！");
					}
					if (reader != null){
						reader.close();
						reader =null;
					}
				}
			}

			// 更新XT_XZQHJZBGZIP表的字段
			updateXzqhjzbgzip(zipxh, cwxx, ut);
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
			ut.rollback();
			throw e;
		}finally {
			if (reader != null){
				reader.close();
				reader = null;
			}
		}
	}

	/**
	 * 
	 * <p>
	 * 方法名称：checkFileName
	 * </p>
	 * <p>
	 * 方法描述：校验压缩文件的区划代码是否和压缩文件里面的区划代码是否一致
	 * </p>
	 * 
	 * @param file 压缩文件
	 * @param zipxh ZIP文件序列
	 * @param xzqh_dm 行政区划代码
	 * @return
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-7-8
	 */
	private void checkFileName(File file, String zipxh, String xzqh_dm) throws Exception {
		UserTransaction ut = new UserTransaction();
		String cwxx = "";
		try {
			if (!file.exists()) {
				cwxx = file.getName() + "文件不存在";
				throw new Exception(file.getName() + "文件不存在");
			}
			if (file.isDirectory()) {
				String[] tempList = file.list();
				for (int i = 0; i < tempList.length; i++) {
					int index = tempList[i].lastIndexOf(".");
					if (tempList[i].toUpperCase().indexOf("QHDM_BGDZB_") < 0) {
						if (tempList[i].toUpperCase().indexOf("QHDM_") < 0){
							cwxx = tempList[i] + "文件名称不规范！请检查";
							throw new Exception(tempList[i] + "文件名称不规范！请检查");
						}
					}
					if (index < 0|| !tempList[i].substring(index + 1).toLowerCase().equals("txt")){
						cwxx = tempList[i]+ "文件名称不规范！请检查,扩展名不是txt格式";
						throw new Exception(tempList[i]+ "文件名称不规范！请检查,扩展名不是txt格式");
					}
					if(tempList[i].indexOf(xzqh_dm)<0){
						cwxx = tempList[i] + "文件和ZIP文件的区划代码不一致！请检查";
						throw new Exception(tempList[i] + "文件和ZIP文件的区划代码不一致！请检查");
					}
				}
			}
		}catch (Exception e) {
			throw e;
		}finally {		
			if(!cwxx.equals("")){
				ut.begin();
				updateXzqhjzbgzip(zipxh, cwxx, ut);
				ut.commit();
			}		
		}
	}
	
	/**
	 * 
	 * <p>
	 * 方法名称：addXzqhjzbgdzb
	 * </p>
	 * <p>
	 * 方法描述：保存对照表数据
	 * </p>
	 * 
	 * @param xb
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2009-09-10
	 */
	private void addXzqhjzbgdzb(String[] args, String zipxh, String czry_dm,
			String qxjg_dm, String cwxx, int pxh, UserTransaction ut)
			throws Exception {
		if (zipxh == null || zipxh.equals("")) {
			throw new Exception("没有获取ZIPXH，请检查！");
		}
		if (args == null || args.length != 7) {
			throw new Exception("数据格式不正确，请检查！");
		}
		String ysxzqh_dm = StringEx.sNull(args[3].trim());
		if (ysxzqh_dm.length() == 12) {
			ysxzqh_dm = ysxzqh_dm + "000";
		}
		String mbxzqh_dm = StringEx.sNull(args[6].trim());
		if (mbxzqh_dm.length() == 12) {
			mbxzqh_dm = mbxzqh_dm + "000";
		}
		D_xzqh_jzbgdzb_temp dzbDw = new D_xzqh_jzbgdzb_temp();
		dzbDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		dzbDw.insert(-1);
		IGeneralSequence iseq = (IGeneralSequence) SequenceManager
				.getSequenceFactory();
		String rzbxh = iseq.get("SEQ_XZQH_JZBGDZB_TEMP_XL");
		dzbDw.setItemString(0L, "DZBXH", rzbxh);//对照表序号
		dzbDw.setItemString(0L, "ZIPXH", zipxh);//文件序号
		dzbDw.setItemString(0L, "BH", StringEx.sNull(args[0].trim()));//调整编号
		dzbDw.setItemString(0L, "GROUPMC", StringEx.sNull(args[1].trim()));//调整说明
		dzbDw.setItemString(0L, "YSXZQH_MC", StringEx.sNull(args[2].trim()));//原区划名称
		dzbDw.setItemString(0L, "YSXZQH_DM", ysxzqh_dm);//原区划代码
		dzbDw.setItemString(0L, "BGLX_DM", StringEx.sNull(args[4].trim()));//调整类型
		dzbDw.setItemString(0L, "MBXZQH_MC", StringEx.sNull(args[5].trim()));//现区划名称
		dzbDw.setItemString(0L, "MBXZQH_DM", mbxzqh_dm);//现区划代码
		dzbDw.setItemString(0L, "CWXX", cwxx);//错误信息
		if (!cwxx.equals("")) {
			dzbDw.setItemString(0L, "CWSJBZ", "Y");
		}
		dzbDw.setItemString(0, "LRR_DM", czry_dm);
		dzbDw.setItemString(0, "LRSJ", XtDate.getDBTime());
		dzbDw.setItemString(0, "LRJG_DM", qxjg_dm);
		dzbDw.setItemString(0, "PXH", String.valueOf(pxh));
		dzbDw.setTransObject(ut);
		dzbDw.update(false);
	}

	/**
	 * 
	 * <p>
	 * 方法名称：addBgrzb
	 * </p>
	 * <p>
	 * 方法描述：保存ZIP文件
	 * </p>
	 * 
	 * @param xb
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2009-09-10
	 */
	private void updateXzqhjzbgzip(String zipxh, String cwxx, UserTransaction ut)
			throws Exception {
		D_xzqh_jzbgzip zipDw = new D_xzqh_jzbgzip();
		zipDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		StringBuffer sql = new StringBuffer();
		sql.append("ZIPXH = '").append(zipxh).append("'");
		zipDw.setFilter(sql.toString());
		long count = zipDw.retrieve();
		if (count > 0) {
			if (cwxx != null && cwxx.length() > 0) {
				zipDw.setItemString(0L, "JZBGZT_DM", Common.XZQH_JZBGZT_DRLSBSB);
				zipDw.setItemString(0L, "BZ",
						"导入临时表数据有错误，请在“变更对照查询”模块查询具体错误信息：" + cwxx);
			} else {
				//导入临时表后直接就行逻辑校验
				zipDw.setItemString(0L, "JZBGZT_DM", Common.XZQH_JZBGZT_LJJYCLZ);
				zipDw.setItemString(0L, "BZ", "导入临时表成功，正在进行逻辑校验处理");
			}
			zipDw.setTransObject(ut);
			zipDw.update(false);
		}
	}

	/**
	 * 
	 * <p>
	 * 方法名称：addXzqhjzsj
	 * </p>
	 * <p>
	 * 方法描述：保存省级数据
	 * </p>
	 * 
	 * @param xb
	 * @return
	 * @throws Exception
	 * @author lijl
	 * @since 2009-09-10
	 */
	private void addXzqhjzsj(String[] args, String zipxh, UserTransaction ut)
			throws Exception {
		if (args == null || args.length != 2) {
			throw new Exception("数据格式不正确！");
		}
		D_xzqh_jzbgsj_temp jzsjDw = new D_xzqh_jzbgsj_temp();
		jzsjDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		jzsjDw.insert(-1);
		String xzqh_dm = StringEx.sNull(args[1].trim());
		if (xzqh_dm.length() == 12) {
			xzqh_dm = xzqh_dm + "000";
		}
		jzsjDw.setItemString(0L, "ZIPXH", zipxh);
		jzsjDw.setItemString(0L, "XZQH_DM", xzqh_dm);
		jzsjDw.setItemString(0L, "XZQH_MC", StringEx.sNull(args[0].trim()));
		jzsjDw.setItemString(0, "DRSJ", XtDate.getDBTime());
		jzsjDw.setTransObject(ut);
		jzsjDw.update(false);
	}
	
	/**
	 * 
	 * <p>
	 * 方法名称：insertZl
	 * </p>
	 * <p>
	 * 方法描述：记录未处理数据日志表
	 * </p>
	 * 
	 * @param xzqhbgzl
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-7-21
	 */
	private void insertKljbg(String[] args, String zipxh, String czry_dm,
			String qxjg_dm, String cwxx, int pxh, UserTransaction ut) throws Exception {
		if (zipxh == null || zipxh.equals("")) {
			throw new Exception("没有获取ZIPXH，请检查！");
		}
		if (args == null || args.length != 7) {
			throw new Exception("数据格式不正确，请检查！");
		}
		String ysxzqh_dm = StringEx.sNull(args[3].trim());
		if (ysxzqh_dm.length() == 12) {
			ysxzqh_dm = ysxzqh_dm + "000";
		}
		String mbxzqh_dm = StringEx.sNull(args[6].trim());
		if (mbxzqh_dm.length() == 12) {
			mbxzqh_dm = mbxzqh_dm + "000";
		}
		String cwbz = "";
		if(!cwxx.equals("")){
			cwbz = "Y";
		}
		IGeneralSequence iseq = (IGeneralSequence) SequenceManager
		.getSequenceFactory();
		String rzbxh = iseq.get("SEQ_XZQH_JZBGDZB_TEMP_XL");
		String insert_rzsql = "INSERT INTO XZQH_JZBGKLJDZB_TEMP (DZBXH,ZIPXH,YSXZQH_DM,YSXZQH_MC,BGLX_DM,MBXZQH_DM,MBXZQH_MC,CWXX,BH,GROUPMC,PXH,CWSJBZ,LRR_DM,LRJG_DM,LRSJ) VALUES('"
				+ rzbxh
				+ "','"
				+ zipxh
				+ "','"
				+ ysxzqh_dm
				+ "','"
				+ StringEx.sNull(args[2].trim())
				+ "','"
				+ StringEx.sNull(args[4].trim())
				+ "','"
				+ mbxzqh_dm
				+ "','"
				+ StringEx.sNull(args[5].trim())
				+ "','"
				+ cwxx
				+ "',"
				+ StringEx.sNull(args[0].trim())
				+ ",'"
				+ StringEx.sNull(args[1].trim())
				+ "',"
				+ pxh
				+ ",'"
				+ cwbz
				+ "','"
				+ czry_dm
				+ "','"
				+ qxjg_dm
				+ "',sysdate)";
		DataWindow insertdw = DataWindow.dynamicCreate(insert_rzsql);
		insertdw.setTransObject(ut);
		insertdw.update(false);
	}
}
