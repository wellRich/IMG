package com.padis.business.xzqhwh.delxzqh;

import java.util.ArrayList;
import java.util.List;

public abstract class DelxzqhImp implements IDelXzqh {

	private List returnMessageList = new ArrayList();
	/**
	 * 
	 * <p>�������ƣ�</p>
	 * <p>�������������÷��ص���Ϣ</p>
	 * @param returnMessage
	 * @author dongzga
	 * @since 2009-7-31
	 */
	public void addReturnMessage(DelxzqhBean returnMessage) {
		returnMessageList.add(returnMessage);
	}
	/**
	 * 
	 * <p>�������ƣ���ȡ���ص���Ϣ</p>
	 * <p>����������</p>
	 * @return
	 * @author dongzga
	 * @since 2009-7-31
	 */
	public List getReturnMessage() {
		return returnMessageList;
	}
	/**
	 * �Ƿ�����ɾ��
	 */
	public abstract boolean isAllowDelXzqh(List xzqhlist);

}
