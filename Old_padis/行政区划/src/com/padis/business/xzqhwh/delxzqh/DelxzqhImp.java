package com.padis.business.xzqhwh.delxzqh;

import java.util.ArrayList;
import java.util.List;

public abstract class DelxzqhImp implements IDelXzqh {

	private List returnMessageList = new ArrayList();
	/**
	 * 
	 * <p>方法名称：</p>
	 * <p>方法描述：设置返回的信息</p>
	 * @param returnMessage
	 * @author dongzga
	 * @since 2009-7-31
	 */
	public void addReturnMessage(DelxzqhBean returnMessage) {
		returnMessageList.add(returnMessage);
	}
	/**
	 * 
	 * <p>方法名称：获取返回的信息</p>
	 * <p>方法描述：</p>
	 * @return
	 * @author dongzga
	 * @since 2009-7-31
	 */
	public List getReturnMessage() {
		return returnMessageList;
	}
	/**
	 * 是否允许删除
	 */
	public abstract boolean isAllowDelXzqh(List xzqhlist);

}
