package com.padis.business.xzqhwh.zxbg.bgdzgl.whbgdzb;

import com.padis.business.xzqhwh.common.Common;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.util.StringEx;

public class MoveCheckXzqhImp extends CheckXzqhImp{

	/**
	 * <p>方法名称：checkXzqh</p>
	 * <p>方法描述：检查明细表是否存在和此行政区划变更有关的区划</p>
	 * @param xzqh_dm 行政区划代码
	 * @param groupSeq 组序号
	 * @param sqdxh   申请单序号
	 * @return boolean
	 * @author lijl
	 * @throws Exception 
	 * @since 2009-10-10
	 */
	public boolean checkXzqh(String originalZoningCode, String targetZoningCode, String groupSeq, String createDate ,String ownZoningCode) throws Exception{
		boolean flag=true;
		String xzqh_jbdm = Common.getJbdm(targetZoningCode);
		StringBuffer message= new StringBuffer("");
		StringBuffer sql = new StringBuffer("SELECT M.MBXZQH_DM,M.MBXZQH_MC,M.YSXZQH_DM,M.YSXZQH_MC,M.BGLX_DM,G.GROUPMC FROM XZQH_BGMXB M,XZQH_BGGROUP G" +
				",XZQH_BGSQD S WHERE M.GROUPXH=G.GROUPXH AND M.GROUPXH=G.GROUPXH AND M.GROUPXH<>'");
		sql.append(groupSeq);
		sql.append("'");
		sql.append(" AND S.SBXZQH_DM LIKE'");
		sql.append(ownZoningCode);
		sql.append("%'");
		sql.append(" AND M.LRSJ>=to_date('");
		sql.append(createDate);
		sql.append("','YYYY-MM-DD hh24:mi:ss')");
		sql.append("order by M.LRSJ DESC,M.PXH");
		DataWindow mxbDw = DataWindow.dynamicCreate(sql.toString());
		mxbDw.setConnectionName(ConnConfig.getConnectionName(this.getClass()));
		long cnt = mxbDw.retrieve();
		if(cnt>0){
			for(int i=0;i<cnt ;i++){
				String groupName = StringEx.sNull(mxbDw.getItemAny(i, "GROUPMC"));
				String targetCode = StringEx.sNull(mxbDw.getItemAny(i, "MBXZQH_DM"));
				String originalCode = StringEx.sNull(mxbDw.getItemAny(i, "YSXZQH_DM"));
				String changeType = StringEx.sNull(mxbDw.getItemAny(i, "BGLX_DM"));
				String msg = "";
				if(!groupName.equals("")){
					msg = "在调整说明“"+groupName+"”";
				}
				if(changeType.equals(Common.ADD)){
					if(originalZoningCode.equals(targetCode)){
						message.append("原区划代码“").append(originalZoningCode).append("”").append(msg)
						.append("中被再次使用，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());
					}else if(targetCode.indexOf(xzqh_jbdm)>-1){
						message.append("现区划代码“").append(targetZoningCode).append("”").append(msg).append("下有新增区划").append("，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());
					}else{
						continue;
					}
				}else if(changeType.equals(Common.CHANGE)){
					if(originalZoningCode.equals(targetCode)){
						message.append("原区划代码“").append(originalZoningCode).append("”").append(msg)
						.append("中被再次使用，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());
					}else if(checkSjXzqh(originalZoningCode,originalCode)){
						message.append("原区划代码“").append(originalZoningCode).append("”的上级区划“").append(originalCode).append("”")
						.append(msg).append("中已被变更为：").append(targetCode).append("，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());					
					}
					if(targetZoningCode.equals(originalCode)){
						message.append("现区划代码“").append(targetZoningCode).append("”").append(msg)
						.append("中已被变更为“").append(targetCode).append("”，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(originalCode.indexOf(xzqh_jbdm)>-1){
							message.append("现区划代码“").append(targetZoningCode).append("”的下级区划“").append(originalCode).append("”")
							.append(msg).append("中已被变更为：").append(targetCode).append("，请先删除此调整说明！");
							flag=false;
							throw new Exception(message.toString());
						}else if(checkSjXzqh(targetZoningCode,originalCode)){
							message.append("现区划代码“").append(targetZoningCode).append("”的上级区划“").append(Common.getSjxzqhdm(originalCode)).append("”")
							.append(msg).append("中已被变更为：").append(targetCode).append("，请先删除此调整说明！");
							flag=false;
							throw new Exception(message.toString());					
						}else{
							continue;
						}
					}
				}else if(changeType.equals(Common.MERGE)){
					//查看是否有区划并入到此区划或者此区划下级区划的下面
					if(targetZoningCode.equals(targetCode)){
						message.append("现区划代码“").append(originalCode).append("”").append(msg)
						.append("中被并入到“").append(targetZoningCode).append("”下，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(targetCode.indexOf(xzqh_jbdm)>-1){
							message.append("现区划代码“").append(targetZoningCode).append("”");
							if(!targetZoningCode.equals(originalCode)){
								message.append("的下级区划“").append(originalCode).append("”");
							}
							message.append(msg).append("中已被并入到“").append(targetCode).append("”下，请先删除此调整说明！");
							flag=false;
							throw new Exception(message.toString());
						}
					}				
					if(checkSjXzqh(originalZoningCode,originalCode)){
						message.append("原区划代码“").append(originalZoningCode).append("”的上级区划“").append(originalCode).append("”")
						.append(msg).append("中已被并入到“").append(targetCode).append("”下，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());					
					}
					if(targetZoningCode.equals(targetCode)){
						message.append("现区划代码“").append(originalCode).append("”").append(msg)
						.append("中已被并入到“").append(targetCode).append("下”，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(originalCode.indexOf(xzqh_jbdm)>-1){
							message.append("现区划代码“").append(targetZoningCode).append("”");
							if(!targetZoningCode.equals(originalCode)){
								message.append("的下级区划“").append(originalCode).append("”");
							}
							message.append(msg).append("中已被并入到：").append(targetCode).append("下，请先删除此调整说明！");
							flag=false;
							throw new Exception(message.toString());
						}else if(checkSjXzqh(targetZoningCode,originalCode)){
							message.append("现区划代码“").append(targetZoningCode).append("”的上级区划“").append(originalCode).append("”")
							.append(msg).append("中已被并入到：").append(targetCode).append("下，请先删除此调整说明！");
							flag=false;
							throw new Exception(message.toString());					
						}else{
							continue;
						}
					}
				}else if(changeType.equals(Common.MOVE)){
					//查看是否有区划迁移到此区划或者此区划下级区划的下面
					if(originalZoningCode.equals(targetCode)){
						message.append("原区划代码“").append(originalZoningCode).append("”").append(msg)
						.append("中被再次使用，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());
					}else if(targetZoningCode.equals(Common.getSjxzqhdm(targetCode))){
						message.append("现区划代码“").append(targetZoningCode).append("”").append(msg)
						.append("中新增迁移区划“").append(targetCode).append("”，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(targetCode.indexOf(xzqh_jbdm)>-1){
							message.append("现区划代码“").append(targetZoningCode).append("”的下级区划“").append(Common.getSjxzqhdm(targetCode)).append("”")
							.append(msg).append("中有新迁移区划：").append(targetCode).append("，请先删除此调整说明！");
							flag=false;
							throw new Exception(message.toString());
						}
					}
					
					if(checkSjXzqh(originalZoningCode,originalCode)){
						message.append("原区划代码“").append(originalZoningCode).append("”的上级区划“").append(originalCode).append("”")
						.append(msg).append("中已被迁移到：").append(Common.getSjxzqhdm(targetCode)).append("，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());					
					}
					if(targetZoningCode.equals(originalCode)){
						message.append("现区划代码“").append(targetZoningCode).append("”").append(msg)
						.append("中已被迁移到“").append(Common.getSjxzqhdm(targetCode)).append("下”，请先删除此调整说明！");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(originalCode.indexOf(xzqh_jbdm)>-1){
							message.append("现区划代码“").append(targetZoningCode).append("”的下级区划“").append(originalCode).append("”")
							.append(msg).append("中已被迁移到：").append(Common.getSjxzqhdm(targetCode)).append("下，请先删除此调整说明！");
							flag=false;
							throw new Exception(message.toString());
						}else if(checkSjXzqh(targetZoningCode,originalCode)){
							message.append("现区划代码“").append(targetZoningCode).append("”的上级区划“").append(originalCode).append("”")
							.append(msg).append("中已被迁移到：").append(Common.getSjxzqhdm(targetCode)).append("下，请先删除此调整说明！");
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
