package com.padis.business.xzqhwh.delxzqh;

import java.util.List;

public interface IDelXzqh {
	/**
	 * 
	 * <p>�������ƣ��Ƿ�����ɾ�������������ɾ������Ҫ����false��ͬʱ�Ѿ�����ϢsetReturnMessage��</p>
	 * <p>����������</p>
	 * @param xzqhlist ������洢���Ǳ�ɾ���������������� List<String>
	 * @return true��false
	 * @author dongzga
	 * @since 2009-7-31
	 * �ر��������ٴβ�ѯ��ʱ�����ĳ��������ɾ������Ҫ������ѯ����Ҫ�׳��쳣��
	 * �����ر����������ɾ������Ҫ���������ж����꽱���Ƿ�����ɾ����
	 * �����ͻ���ʾ��returnMessage��ʱ����һ��ȫ������Ϣbean
	 */
	public boolean isAllowDelXzqh(List xzqhlist);

}
