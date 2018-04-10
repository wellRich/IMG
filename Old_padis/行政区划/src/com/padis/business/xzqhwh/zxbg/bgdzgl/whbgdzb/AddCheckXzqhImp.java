package com.padis.business.xzqhwh.zxbg.bgdzgl.whbgdzb;

import com.padis.business.xzqhwh.common.Common;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.util.StringEx;

public class AddCheckXzqhImp extends CheckXzqhImp{

	/**
	 * <p>方法名称：checkXzqh</p>
	 * <p>方法描述：检查明细表是否存在和此行政区划变更有关的区划</p>
	 * @param xzqh_dm 行政区划代码
	 * @param groupxh 组序号
	 * @param sqdxh   申请单序号
	 * @return boolean
	 * @author lijl
	 * @throws Exception 
	 * @since 2009-10-10
	 */
	/**
	 *
	 * @param srcXzqh_dm 原区划代码
	 * @param xzqh_dm 目标区划代码
	 * @param groupxh 变更组序号
	 * @param lrsj 录入时间
	 * @param sbxzqh_dm 上报行政区划代码
	 * @return
	 * @throws Exception
	 */
	public boolean checkXzqh(String srcXzqh_dm, String xzqh_dm, String groupxh, String lrsj ,String sbxzqh_dm) throws Exception{
		boolean flag=true;

		//级别代码
		String xzqh_jbdm = Common.getJbdm(xzqh_dm);
		StringBuffer message= new StringBuffer("");
		StringBuffer sql = new StringBuffer("SELECT M.MBXZQH_DM,M.MBXZQH_MC,M.YSXZQH_DM,M.YSXZQH_MC,M.BGLX_DM,G.GROUPMC FROM XZQH_BGMXB M,XZQH_BGGROUP G" +
				",XZQH_BGSQD S WHERE S.SQDXH=G.SQDXH AND M.GROUPXH=G.GROUPXH AND M.GROUPXH<>'");
		sql.append(groupxh);
		sql.append("'");
		sql.append(" AND S.SBXZQH_DM like'");
		sql.append(sbxzqh_dm);
		sql.append("%'");
		sql.append(" AND M.LRSJ>=to_date('");
		sql.append(lrsj);
		sql.append("','YYYY-MM-DD hh24:mi:ss')");
		sql.append("order by M.LRSJ DESC,M.PXH");
		DataWindow mxbDw = DataWindow.dynamicCreate(sql.toString());
		mxbDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long cnt = mxbDw.retrieve();
		if(cnt>0){
			for(int i=0;i<cnt ;i++){
				String groupmc = StringEx.sNull(mxbDw.getItemAny(i, "GROUPMC"));
				String mbxzqh_dm = StringEx.sNull(mxbDw.getItemAny(i, "MBXZQH_DM"));
				String ysxzqh_dm = StringEx.sNull(mxbDw.getItemAny(i, "YSXZQH_DM"));
				String bglx_dm = StringEx.sNull(mxbDw.getItemAny(i, "BGLX_DM"));
				String msg = "";
				if(!groupmc.equals("")){
					msg = "在调整说明“"+groupmc+"”";
				}
				if(bglx_dm.equals(Common.ADD)){
					if(mbxzqh_dm.indexOf(xzqh_jbdm)>-1){
						message.append("现区划代码“").append(xzqh_dm).append("”").append(msg).append("下有新增区划").append("，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());
					}else{
						continue;
					}
				}else if(bglx_dm.equals(Common.CHANGE)){
					if(xzqh_dm.equals(ysxzqh_dm)){
						message.append("原区划代码“").append(xzqh_dm).append("”").append(msg)
						.append("中已被变更为“").append(mbxzqh_dm).append("”，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(ysxzqh_dm.indexOf(xzqh_jbdm)>-1){
							message.append("原区划代码“").append(xzqh_dm).append("”的下级区划“").append(ysxzqh_dm).append("”")
							.append(msg).append("中已被变更为：").append(mbxzqh_dm).append("，请先删除此调整说明！");
							flag=false;
							throw new Exception(message.toString());
						}else if(checkSjXzqh(xzqh_dm,ysxzqh_dm)){
							message.append("原区划代码“").append(xzqh_dm).append("”的上级区划“").append(ysxzqh_dm).append("”")
							.append(msg).append("中已被变更为：").append(mbxzqh_dm).append("，请先删除此调整说明！");
							flag=false;
							throw new Exception(message.toString());					
						}else{
							continue;
						}
					}
				}else if(bglx_dm.equals(Common.MERGE)){
					if(xzqh_dm.equals(mbxzqh_dm)){
						message.append("原区划代码“").append(ysxzqh_dm).append("”").append(msg)
						.append("中已被并入到“").append(mbxzqh_dm).append("下”，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());
					}else if(xzqh_dm.equals(ysxzqh_dm)){
						message.append("原区划代码“").append(ysxzqh_dm).append("”").append(msg)
						.append("中已被并入到“").append(mbxzqh_dm).append("下”，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());
					}
				}else if(bglx_dm.equals(Common.MOVE)){
					//查看是否有区划迁移到此区划或者此区划下级区划的下面
					if(xzqh_dm.equals(mbxzqh_dm)){
						message.append("现区划代码“").append(xzqh_dm).append("”").append(msg)
						.append("中新增迁移区划“").append(mbxzqh_dm).append("”，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(mbxzqh_dm.indexOf(xzqh_jbdm)>-1){
							message.append("现区划代码“").append(xzqh_dm).append("”");
							if(!xzqh_dm.equals(Common.getSjxzqhdm(mbxzqh_dm))){
								message.append("的下级区划“").append(Common.getSjxzqhdm(mbxzqh_dm)).append("”");
							}
							message.append(msg).append("中有新迁移区划：").append(mbxzqh_dm).append("，请先删除此调整说明！");
							flag=false;
							throw new Exception(message.toString());
						}
					}
					//查看此区划或者此区划上下级区划是否被迁移走
					if(xzqh_dm.equals(ysxzqh_dm)){
						message.append("现区划代码“").append(xzqh_dm).append("”").append(msg)
						.append("中已被迁移到“").append(Common.getSjxzqhdm(mbxzqh_dm)).append("下”，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(ysxzqh_dm.indexOf(xzqh_jbdm)>-1){
							message.append("现区划代码“").append(xzqh_dm).append("”");
							if(!xzqh_dm.equals(ysxzqh_dm)){
								message.append("的下级区划“").append(ysxzqh_dm).append("”");
							}
							message.append(msg).append("中已被迁移到：").append(Common.getSjxzqhdm(mbxzqh_dm)).append("下，请先删除此调整说明！");
							flag=false;
							throw new Exception(message.toString());
						}else if(checkSjXzqh(xzqh_dm,ysxzqh_dm)){
							message.append("现区划代码“").append(xzqh_dm).append("”的上级区划“").append(ysxzqh_dm).append("”")
							.append(msg).append("中已被迁移到：").append(Common.getSjxzqhdm(mbxzqh_dm)).append("下，请先删除此调整说明！");
							flag=false;
							throw new Exception(message.toString());					
						}else{
							continue;
						}
					}
				}
			}
		}
		return flag;
	}

}
