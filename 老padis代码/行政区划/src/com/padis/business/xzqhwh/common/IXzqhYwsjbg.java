package com.padis.business.xzqhwh.common;

import java.util.List;

import ctais.services.or.UserTransaction;

public interface IXzqhYwsjbg {

	/**
	 * 
	 * <p>
	 * �������ƣ�
	 * </p>
	 * <p>
	 * �����������鿴ҵ�������Ƿ�����������б����������������������⣬����false,���򷵻�true,
	 * ��������ϵͳ�ڽ��յ�true����Ϊ������������ҵ�����������ҵ�����ϵͳ�����ִ��updateYwsj�ӿ��������
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
	 * �������ƣ�
	 * </p>
	 * <p>
	 * ����������ҵ�����ݸ���,����List�洢�����ݽṹ�����ǣ�XzqhbgBean����
	 * ҵ��ֻ��Ҫ��֤List����˳������������һ�����񼴿ɣ���Ҫ�ύ�����ⲿϵͳͳһ�ύ �������ҵ��ʧ�ܣ��򷵻�false,�����׳��쳣
	 * throws e ��������³ɹ�������true �������ҵ����Ҫ��¼��־�����Լ���ҵ���м�¼�����ͳһ��¼�Ƿ���³ɹ�����־
	 * </p>
	 * 
	 * @param xzqhbgxx
	 * @return �������ҵ��ʧ�ܣ��򷵻�false,�����׳��쳣 throws e ��������³ɹ�������true
	 * @author dongzga
	 * @since 2009-7-8
	 */
	public boolean updateYwsj(List xzqhbgxx, UserTransaction ut)
			throws Exception;
}
