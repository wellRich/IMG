package com.padis.business.xzqhwh.zxbg.bgdzgl.whbgdzb;

import com.padis.business.xzqhwh.common.Common;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.util.StringEx;

public class MoveCheckXzqhImp extends CheckXzqhImp{

	/**
	 * <p>�������ƣ�checkXzqh</p>
	 * <p>���������������ϸ���Ƿ���ںʹ�������������йص�����</p>
	 * @param xzqh_dm ������������
	 * @param groupxh �����
	 * @param sqdxh   ���뵥���
	 * @return boolean
	 * @author lijl
	 * @throws Exception 
	 * @since 2009-10-10
	 */
	public boolean checkXzqh(String srcXzqh_dm, String destXxzqh_dm, String groupxh, String lrsj ,String sbxzqh_dm) throws Exception{
		boolean flag=true;
		String xzqh_jbdm = Common.getJbdm(destXxzqh_dm);
		StringBuffer message= new StringBuffer("");
		StringBuffer sql = new StringBuffer("SELECT M.MBXZQH_DM,M.MBXZQH_MC,M.YSXZQH_DM,M.YSXZQH_MC,M.BGLX_DM,G.GROUPMC FROM XZQH_BGMXB M,XZQH_BGGROUP G" +
				",XZQH_BGSQD S WHERE M.GROUPXH=G.GROUPXH AND M.GROUPXH=G.GROUPXH AND M.GROUPXH<>'");
		sql.append(groupxh);
		sql.append("'");
		sql.append(" AND S.SBXZQH_DM LIKE'");
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
					msg = "�ڵ���˵����"+groupmc+"��";
				}
				if(bglx_dm.equals(Common.ADD)){
					if(srcXzqh_dm.equals(mbxzqh_dm)){
						message.append("ԭ�������롰").append(srcXzqh_dm).append("��").append(msg)
						.append("�б��ٴ�ʹ�ã�����ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());
					}else if(mbxzqh_dm.indexOf(xzqh_jbdm)>-1){
						message.append("���������롰").append(destXxzqh_dm).append("��").append(msg).append("������������").append("������ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());
					}else{
						continue;
					}
				}else if(bglx_dm.equals(Common.CHANGE)){
					if(srcXzqh_dm.equals(mbxzqh_dm)){
						message.append("ԭ�������롰").append(srcXzqh_dm).append("��").append(msg)
						.append("�б��ٴ�ʹ�ã�����ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());
					}else if(checkSjXzqh(srcXzqh_dm,ysxzqh_dm)){
						message.append("ԭ�������롰").append(srcXzqh_dm).append("�����ϼ�������").append(ysxzqh_dm).append("��")
						.append(msg).append("���ѱ����Ϊ��").append(mbxzqh_dm).append("������ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());					
					}
					if(destXxzqh_dm.equals(ysxzqh_dm)){
						message.append("���������롰").append(destXxzqh_dm).append("��").append(msg)
						.append("���ѱ����Ϊ��").append(mbxzqh_dm).append("��������ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(ysxzqh_dm.indexOf(xzqh_jbdm)>-1){
							message.append("���������롰").append(destXxzqh_dm).append("�����¼�������").append(ysxzqh_dm).append("��")
							.append(msg).append("���ѱ����Ϊ��").append(mbxzqh_dm).append("������ɾ���˵���˵����");
							flag=false;
							throw new Exception(message.toString());
						}else if(checkSjXzqh(destXxzqh_dm,ysxzqh_dm)){
							message.append("���������롰").append(destXxzqh_dm).append("�����ϼ�������").append(Common.getSjxzqhdm(ysxzqh_dm)).append("��")
							.append(msg).append("���ѱ����Ϊ��").append(mbxzqh_dm).append("������ɾ���˵���˵����");
							flag=false;
							throw new Exception(message.toString());					
						}else{
							continue;
						}
					}
				}else if(bglx_dm.equals(Common.MERGE)){
					//�鿴�Ƿ����������뵽���������ߴ������¼�����������
					if(destXxzqh_dm.equals(mbxzqh_dm)){
						message.append("���������롰").append(ysxzqh_dm).append("��").append(msg)
						.append("�б����뵽��").append(destXxzqh_dm).append("���£�����ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(mbxzqh_dm.indexOf(xzqh_jbdm)>-1){
							message.append("���������롰").append(destXxzqh_dm).append("��");
							if(!destXxzqh_dm.equals(ysxzqh_dm)){
								message.append("���¼�������").append(ysxzqh_dm).append("��");
							}
							message.append(msg).append("���ѱ����뵽��").append(mbxzqh_dm).append("���£�����ɾ���˵���˵����");
							flag=false;
							throw new Exception(message.toString());
						}
					}				
					if(checkSjXzqh(srcXzqh_dm,ysxzqh_dm)){
						message.append("ԭ�������롰").append(srcXzqh_dm).append("�����ϼ�������").append(ysxzqh_dm).append("��")
						.append(msg).append("���ѱ����뵽��").append(mbxzqh_dm).append("���£�����ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());					
					}
					if(destXxzqh_dm.equals(mbxzqh_dm)){
						message.append("���������롰").append(ysxzqh_dm).append("��").append(msg)
						.append("���ѱ����뵽��").append(mbxzqh_dm).append("�¡�������ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(ysxzqh_dm.indexOf(xzqh_jbdm)>-1){
							message.append("���������롰").append(destXxzqh_dm).append("��");
							if(!destXxzqh_dm.equals(ysxzqh_dm)){
								message.append("���¼�������").append(ysxzqh_dm).append("��");
							}
							message.append(msg).append("���ѱ����뵽��").append(mbxzqh_dm).append("�£�����ɾ���˵���˵����");
							flag=false;
							throw new Exception(message.toString());
						}else if(checkSjXzqh(destXxzqh_dm,ysxzqh_dm)){
							message.append("���������롰").append(destXxzqh_dm).append("�����ϼ�������").append(ysxzqh_dm).append("��")
							.append(msg).append("���ѱ����뵽��").append(mbxzqh_dm).append("�£�����ɾ���˵���˵����");
							flag=false;
							throw new Exception(message.toString());					
						}else{
							continue;
						}
					}
				}else if(bglx_dm.equals(Common.MOVE)){
					//�鿴�Ƿ�������Ǩ�Ƶ����������ߴ������¼�����������
					if(srcXzqh_dm.equals(mbxzqh_dm)){
						message.append("ԭ�������롰").append(srcXzqh_dm).append("��").append(msg)
						.append("�б��ٴ�ʹ�ã�����ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());
					}else if(destXxzqh_dm.equals(Common.getSjxzqhdm(mbxzqh_dm))){
						message.append("���������롰").append(destXxzqh_dm).append("��").append(msg)
						.append("������Ǩ��������").append(mbxzqh_dm).append("��������ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(mbxzqh_dm.indexOf(xzqh_jbdm)>-1){
							message.append("���������롰").append(destXxzqh_dm).append("�����¼�������").append(Common.getSjxzqhdm(mbxzqh_dm)).append("��")
							.append(msg).append("������Ǩ��������").append(mbxzqh_dm).append("������ɾ���˵���˵����");
							flag=false;
							throw new Exception(message.toString());
						}
					}
					
					if(checkSjXzqh(srcXzqh_dm,ysxzqh_dm)){
						message.append("ԭ�������롰").append(srcXzqh_dm).append("�����ϼ�������").append(ysxzqh_dm).append("��")
						.append(msg).append("���ѱ�Ǩ�Ƶ���").append(Common.getSjxzqhdm(mbxzqh_dm)).append("������ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());					
					}
					if(destXxzqh_dm.equals(ysxzqh_dm)){
						message.append("���������롰").append(destXxzqh_dm).append("��").append(msg)
						.append("���ѱ�Ǩ�Ƶ���").append(Common.getSjxzqhdm(mbxzqh_dm)).append("�¡�������ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(ysxzqh_dm.indexOf(xzqh_jbdm)>-1){
							message.append("���������롰").append(destXxzqh_dm).append("�����¼�������").append(ysxzqh_dm).append("��")
							.append(msg).append("���ѱ�Ǩ�Ƶ���").append(Common.getSjxzqhdm(mbxzqh_dm)).append("�£�����ɾ���˵���˵����");
							flag=false;
							throw new Exception(message.toString());
						}else if(checkSjXzqh(destXxzqh_dm,ysxzqh_dm)){
							message.append("���������롰").append(destXxzqh_dm).append("�����ϼ�������").append(ysxzqh_dm).append("��")
							.append(msg).append("���ѱ�Ǩ�Ƶ���").append(Common.getSjxzqhdm(mbxzqh_dm)).append("�£�����ɾ���˵���˵����");
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
