package com.padis.business.xzqhwh.zxbg.bgdzgl.whbgdzb;

import com.padis.business.xzqhwh.common.Common;
import com.padis.common.xtservice.connection.ConnConfig;

import ctais.services.data.DataWindow;
import ctais.util.StringEx;

public class AddCheckXzqhImp extends CheckXzqhImp{

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
	/**
	 *
	 * @param srcXzqh_dm ԭ��������
	 * @param xzqh_dm Ŀ����������
	 * @param groupxh ��������
	 * @param lrsj ¼��ʱ��
	 * @param sbxzqh_dm �ϱ�������������
	 * @return
	 * @throws Exception
	 */
	public boolean checkXzqh(String srcXzqh_dm, String xzqh_dm, String groupxh, String lrsj ,String sbxzqh_dm) throws Exception{
		boolean flag=true;

		//�������
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
					msg = "�ڵ���˵����"+groupmc+"��";
				}
				if(bglx_dm.equals(Common.ADD)){
					if(mbxzqh_dm.indexOf(xzqh_jbdm)>-1){
						message.append("���������롰").append(xzqh_dm).append("��").append(msg).append("������������").append("������ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());
					}else{
						continue;
					}
				}else if(bglx_dm.equals(Common.CHANGE)){
					if(xzqh_dm.equals(ysxzqh_dm)){
						message.append("ԭ�������롰").append(xzqh_dm).append("��").append(msg)
						.append("���ѱ����Ϊ��").append(mbxzqh_dm).append("��������ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(ysxzqh_dm.indexOf(xzqh_jbdm)>-1){
							message.append("ԭ�������롰").append(xzqh_dm).append("�����¼�������").append(ysxzqh_dm).append("��")
							.append(msg).append("���ѱ����Ϊ��").append(mbxzqh_dm).append("������ɾ���˵���˵����");
							flag=false;
							throw new Exception(message.toString());
						}else if(checkSjXzqh(xzqh_dm,ysxzqh_dm)){
							message.append("ԭ�������롰").append(xzqh_dm).append("�����ϼ�������").append(ysxzqh_dm).append("��")
							.append(msg).append("���ѱ����Ϊ��").append(mbxzqh_dm).append("������ɾ���˵���˵����");
							flag=false;
							throw new Exception(message.toString());					
						}else{
							continue;
						}
					}
				}else if(bglx_dm.equals(Common.MERGE)){
					if(xzqh_dm.equals(mbxzqh_dm)){
						message.append("ԭ�������롰").append(ysxzqh_dm).append("��").append(msg)
						.append("���ѱ����뵽��").append(mbxzqh_dm).append("�¡�������ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());
					}else if(xzqh_dm.equals(ysxzqh_dm)){
						message.append("ԭ�������롰").append(ysxzqh_dm).append("��").append(msg)
						.append("���ѱ����뵽��").append(mbxzqh_dm).append("�¡�������ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());
					}
				}else if(bglx_dm.equals(Common.MOVE)){
					//�鿴�Ƿ�������Ǩ�Ƶ����������ߴ������¼�����������
					if(xzqh_dm.equals(mbxzqh_dm)){
						message.append("���������롰").append(xzqh_dm).append("��").append(msg)
						.append("������Ǩ��������").append(mbxzqh_dm).append("��������ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(mbxzqh_dm.indexOf(xzqh_jbdm)>-1){
							message.append("���������롰").append(xzqh_dm).append("��");
							if(!xzqh_dm.equals(Common.getSjxzqhdm(mbxzqh_dm))){
								message.append("���¼�������").append(Common.getSjxzqhdm(mbxzqh_dm)).append("��");
							}
							message.append(msg).append("������Ǩ��������").append(mbxzqh_dm).append("������ɾ���˵���˵����");
							flag=false;
							throw new Exception(message.toString());
						}
					}
					//�鿴���������ߴ��������¼������Ƿ�Ǩ����
					if(xzqh_dm.equals(ysxzqh_dm)){
						message.append("���������롰").append(xzqh_dm).append("��").append(msg)
						.append("���ѱ�Ǩ�Ƶ���").append(Common.getSjxzqhdm(mbxzqh_dm)).append("�¡�������ɾ���˵���˵����");
						flag=false;
						throw new Exception(message.toString());
					}else{
						if(ysxzqh_dm.indexOf(xzqh_jbdm)>-1){
							message.append("���������롰").append(xzqh_dm).append("��");
							if(!xzqh_dm.equals(ysxzqh_dm)){
								message.append("���¼�������").append(ysxzqh_dm).append("��");
							}
							message.append(msg).append("���ѱ�Ǩ�Ƶ���").append(Common.getSjxzqhdm(mbxzqh_dm)).append("�£�����ɾ���˵���˵����");
							flag=false;
							throw new Exception(message.toString());
						}else if(checkSjXzqh(xzqh_dm,ysxzqh_dm)){
							message.append("���������롰").append(xzqh_dm).append("�����ϼ�������").append(ysxzqh_dm).append("��")
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
