package com.padis.business.xzqhwh.common.linkxzqh;

import java.util.ArrayList;
import java.util.List;

import com.padis.business.xzqhwh.common.XzqhbgBean;


public class LinkedXzqh {

	public List data = new ArrayList();
	public void init() {
		XzqhbgBean xb = new XzqhbgBean();
		xb.setSrcXzqhdm("3");
		xb.setDestXzqhdm("1");
		XzqhNode node1 = new XzqhNode(xb);

		XzqhbgBean xb2 = new XzqhbgBean();
		xb2.setSrcXzqhdm("2");
		xb2.setDestXzqhdm("3");
		XzqhNode node2 = new XzqhNode(xb2);

		XzqhbgBean xb3 = new XzqhbgBean();
		xb3.setSrcXzqhdm("1");
		xb3.setDestXzqhdm("2");
		XzqhNode node3 = new XzqhNode(xb3);		

		XzqhbgBean xb4 = new XzqhbgBean();
		xb4.setSrcXzqhdm("4");
		xb4.setDestXzqhdm("9");
		XzqhNode node4 = new XzqhNode(xb4);		

		XzqhbgBean xb5 = new XzqhbgBean();
		xb5.setSrcXzqhdm("7");
		xb5.setDestXzqhdm("A");
		XzqhNode node5 = new XzqhNode(xb5);		

		XzqhbgBean xb6 = new XzqhbgBean();
		xb6.setSrcXzqhdm("8");
		xb6.setDestXzqhdm("6");
		XzqhNode node6 = new XzqhNode(xb6);		

		XzqhbgBean xb7 = new XzqhbgBean();
		xb7.setSrcXzqhdm("6");
		xb7.setDestXzqhdm("7");
		XzqhNode node7 = new XzqhNode(xb7);		

		XzqhbgBean xb8 = new XzqhbgBean();
		xb8.setSrcXzqhdm("5");
		xb8.setDestXzqhdm("8");
		XzqhNode node8 = new XzqhNode(xb8);	
		
		XzqhbgBean xb9 = new XzqhbgBean();
		xb9.setSrcXzqhdm("9");
		xb9.setDestXzqhdm("10");
		XzqhNode node9 = new XzqhNode(xb9);		

		XzqhbgBean xb10 = new XzqhbgBean();
		xb10.setSrcXzqhdm("10");
		xb10.setDestXzqhdm("11");
		XzqhNode node10 = new XzqhNode(xb10);		

		XzqhbgBean xb11 = new XzqhbgBean();
		xb11.setSrcXzqhdm("11");
		xb11.setDestXzqhdm("12");
		XzqhNode node11 = new XzqhNode(xb11);


		data.add(node1);
		data.add(node7);
		data.add(node2);
		data.add(node10);
		data.add(node5);
		data.add(node3);
		data.add(node6);	
		data.add(node8);	
		
		data.add(node9);		
		data.add(node4);
		data.add(node11);

		
	}
	/**
	 * 递归分组
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param xzqhNode
	 * @param result 使用LinkedList更合适
	 * @author dongzga
	 * @since 2009-6-30
	 */
	public void group(XzqhNode xzqhNode,List result) {

		// 查找后继结点		
		XzqhNode nextXzqhNode = this.getNextNode(xzqhNode);
		if (nextXzqhNode != null) {//如果有后继结点，添加到列表的后面，并删除原节点
			result.add(result.indexOf(xzqhNode)+1, nextXzqhNode);//往后加入
			data.remove(xzqhNode);
			group(nextXzqhNode,result);
		} 
		//else {
			//如果没有后继节点，那么找前驱结点,如果找到前驱结点，把找到的结点放到该节点的前面
			XzqhNode priorXzqhNode = this.getPriorNode(xzqhNode);// 得到前驱
			if (priorXzqhNode != null) {
				result.add(result.indexOf(xzqhNode), priorXzqhNode);//往前加入
				data.remove(xzqhNode);
				group(priorXzqhNode,result);
			}else{
				
				//前驱后继都没有的话，直接加入不重复的节点
				if(!result.contains(xzqhNode))
					result.add(xzqhNode);
				data.remove(xzqhNode);
			}
	//	}

	}
	/**
	 * 获取后继结点
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param xzqhNode
	 * @return
	 * @author dongzga
	 * @since 2009-6-30
	 */
	public XzqhNode getNextNode(XzqhNode xzqhNode) {
		for (int i = 0; i < data.size(); i++) {
			XzqhNode xn = (XzqhNode) data.get(i);
			if (xzqhNode.getXzqhbgBean().getDestXzqhdm().equals(xn.getXzqhbgBean().getSrcXzqhdm()))
				return xn;
		}
		return null;
	}
	/**
	 * 获取前驱结点
	 * <p>方法名称：</p>
	 * <p>方法描述：</p>
	 * @param xzqhNode
	 * @return
	 * @author dongzga
	 * @since 2009-6-30
	 */
	public XzqhNode getPriorNode(XzqhNode xzqhNode) {
		for (int i = 0; i < data.size(); i++) {
			XzqhNode xn = (XzqhNode) data.get(i);
			if (xzqhNode.getXzqhbgBean().getSrcXzqhdm().equals(xn.getXzqhbgBean().getDestXzqhdm()))
				return xn;
		}
		return null;
	}


	/**
	 * <p>
	 * 方法名称：
	 * </p>
	 * <p>
	 * 方法描述：
	 * </p>
	 * 
	 * @param args
	 * @author dongzga
	 * @since 2009-6-29
	 */
	public static void main(String[] args) {
		LinkedXzqh c = new LinkedXzqh();
		c.init();

		int i=0;
		while(c.data.size()>0) {
			i++;
			XzqhNode xn = (XzqhNode) c.data.get(0);
			List result = new ArrayList();
			result.add(xn);

			c.group(xn,result);
			System.out.println("第"+i+"个数据类型");
			for(int j=0;j<result.size();j++){
				XzqhNode tmp = (XzqhNode) result.get(j);
				System.out.println(tmp.getXzqhbgBean().getSrcXzqhdm()+"-->"+tmp.getXzqhbgBean().getDestXzqhdm());
			}
				
		}

	}

}
