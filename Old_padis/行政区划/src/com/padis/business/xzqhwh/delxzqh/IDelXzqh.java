package com.padis.business.xzqhwh.delxzqh;

import java.util.List;

public interface IDelXzqh {
	/**
	 * 
	 * <p>方法名称：是否允许删除，如果不允许删除，需要返回false，同时把具体信息setReturnMessage中</p>
	 * <p>方法描述：</p>
	 * @param xzqhlist ，里面存储的是被删除的行政区划代码 List<String>
	 * @return true或false
	 * @author dongzga
	 * @since 2009-7-31
	 * 特别声明：再次查询的时候，如果某个表不允许删除，还要继续查询，不要抛出异常，
	 * 例如特别扶助不允许删除，还要继续向下判断老年奖扶是否允许删除，
	 * 最后给客户提示的returnMessage的时候，是一个全部的消息bean
	 */
	public boolean isAllowDelXzqh(List xzqhlist);

}
