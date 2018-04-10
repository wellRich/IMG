package com.padis.business.xzqhwh.common;

import java.util.List;

import ctais.services.or.UserTransaction;

public interface IXzqhYwsjbg {

	/**
	 * 
	 * <p>
	 * 方法名称：
	 * </p>
	 * <p>
	 * 方法描述：查看业务数据是否可以批量进行变更，如果整体变更会遇到问题，返回false,否则返回true,
	 * 行政区划系统在接收到true后，认为该批量数据在业务变更不会出现业务错误，系统会继续执行updateYwsj接口真正变更
	 * </p>
	 * 
	 * @param xzqhbgxx
	 * @return
	 * @throws Exception
	 * @author dongzga
	 * @since 2009-7-8
	 */
	public boolean isAllowBatchUpdate(List xzqhbgxx) throws Exception;

	/**
	 * 
	 * <p>
	 * 方法名称：
	 * </p>
	 * <p>
	 * 方法描述：业务数据更新,其中List存储的数据结构类型是（XzqhbgBean），
	 * 业务只需要保证List数据顺序变更，并且在一个事务即可，不要提交，有外部系统统一提交 如果更新业务失败，则返回false,或者抛出异常
	 * throws e ；如果更新成功，返回true 如果各个业务需要记录日志，请自己在业务中记录，框架统一记录是否更新成功的日志
	 * </p>
	 * 
	 * @param xzqhbgxx
	 * @return 如果更新业务失败，则返回false,或者抛出异常 throws e ；如果更新成功，返回true
	 * @author dongzga
	 * @since 2009-7-8
	 */
	public boolean updateYwsj(List xzqhbgxx, UserTransaction ut)
			throws Exception;
}
