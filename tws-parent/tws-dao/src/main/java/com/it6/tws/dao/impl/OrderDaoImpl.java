package com.it6.tws.dao.impl;

import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.it6.tws.dao.IOrderDao;
import com.it6.tws.dao.base.impl.BaseDaoImpl;
import com.it6.tws.entity.OrderItem;
import com.it6.tws.entity.Orders;
import com.it6.tws.entity.PageBean;
import com.it6.tws.entity.User;
import com.it6.tws.utils.PageHibernateCallback;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Repository
public class OrderDaoImpl extends BaseDaoImpl<OrderItem> implements IOrderDao{

	@Override
	public String addToOrders(OrderItem orderItem, String address, String pconsignee, String telephone) {
		// TODO Auto-generated method stub
		
		String str=null;
		String Itemid=UUID.randomUUID().toString();
		String oid=UUID.randomUUID().toString();
		orderItem.setItemid(Itemid);
		orderItem.setOid(oid);
		
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		Orders order=new Orders();
		order.setOid(oid);
		order.setAddress(address);
		order.setPconsignee(pconsignee);
		order.setTelephone(telephone);
		order.setUid(user.getUid());
		order.setTotal(orderItem.getSubtotal());
		this.getHibernateTemplate().save(order);
		str=(String) this.getHibernateTemplate().save(orderItem);
		
		return str;
	}
	

	@Override
	public List<OrderItem> findAllOrderByUid(String uid) {
		// TODO Auto-generated method stub
		String hql="select new com.it6.tws.entity.OrderItem(oi.itemid,oi.pid,oi.pnum,oi.classify1,oi.classify2,oi.state,oi.subtotal,oi.oid) FROM OrderItem oi,Orders o where oi.oid = o.oid and o.uid = ?";
		List<OrderItem> orderList=(List<OrderItem>) this.getHibernateTemplate().find(hql, uid);
		return orderList;
	}
	
	@Override
	public Orders findOrderByOid(String oid) {
		// TODO Auto-generated method stub
		String hql="FROM Orders where oid = ?";
		List<Orders> orderList=(List<Orders>) this.getHibernateTemplate().find(hql, oid);
		if(orderList!=null&&orderList.size()>0)
			return orderList.get(0);
		else
			return null;
	}


	@Override
	public List<OrderItem> FindOredrsByPname(String uid, String pname) {
		// TODO Auto-generated method stub
		String hql="select new com.it6.tws.entity.OrderItem(oi.itemid,oi.pid,oi.pnum,oi.classify1,oi.classify2,oi.state,oi.subtotal,oi.oid) FROM OrderItem oi,Orders o,Product p where oi.oid = o.oid and oi.pid=p.pid and o.uid = ? and p.pname like ?";          
		List<OrderItem> orderList=(List<OrderItem>) this.getHibernateTemplate().find(hql, uid,"%"+pname+"%");
		return orderList;
	}


	@Override
	public String updateOrderItemState(String uid, String itemid) {
		// TODO Auto-generated method stub
		String str=null;
		String hql="select new com.it6.tws.entity.OrderItem(oi.itemid,oi.pid,oi.pnum,oi.classify1,oi.classify2,oi.state,oi.subtotal,oi.oid) FROM OrderItem oi,Orders o where oi.oid = o.oid and o.uid = ? and oi.itemid= ?";
		List<OrderItem> orderList=(List<OrderItem>) this.getHibernateTemplate().find(hql, uid,itemid);
		if(orderList.get(0)!=null) {
			orderList.get(0).setState(1);
			this.getHibernateTemplate().update(orderList.get(0));
			str="1";
		}
		return str;
	}


	@Override
	public String addToOredrsInCast(JSONArray jsonArray,String total,String address, String pconsignee, String telephone) {
		// TODO Auto-generated method stub
		String str=null;
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		String Itemid=null;
		String oid=UUID.randomUUID().toString();
		
		//保存到订单表
		Orders order=new Orders();
		order.setOid(oid);
		order.setUid(user.getUid());
		order.setAddress(address);
		order.setPconsignee(pconsignee);
		order.setTelephone(telephone);
		order.setTotal(Double.parseDouble(total));
		this.getHibernateTemplate().save(order);
		
		//保存到订单项目表
		for(int i=0;i<jsonArray.size();i++){
			Itemid=UUID.randomUUID().toString();
			JSONObject job = jsonArray.getJSONObject(i);  
			OrderItem orderItem=(OrderItem) JSONObject.toBean(job,OrderItem.class);
			orderItem.setItemid(Itemid);
			orderItem.setOid(oid);
			str=(String)this.getHibernateTemplate().save(orderItem);
		}
		
		
		
		
		return str;
	}
	@Override
	public Integer getTotalCount(String uid) {
		// TODO Auto-generated method stub
		String hql = "select count(*) from OrderItem oi,Orders o where oi.oid=o.oid and o.uid=?";
		Long count = (Long)getHibernateTemplate().find(hql,uid).listIterator().next();
		return count.intValue();
	}


	@Override
	public List<OrderItem> getPageBean(String uid, int start, Integer pageSize) {
		String a[]=new String[1];
		a[0]=uid;
		// TODO Auto-generated method stub
		String hql="select new com.it6.tws.entity.OrderItem(oi.itemid,oi.pid,oi.pnum,oi.classify1,oi.classify2,oi.state,oi.subtotal,oi.oid) FROM OrderItem oi,Orders o where oi.oid=o.oid and o.uid = ?";      
		List<OrderItem> orderList=(List<OrderItem>) this.getHibernateTemplate().execute(
				(HibernateCallback<OrderItem>) new PageHibernateCallback(hql,a,start, pageSize));
        if(orderList!=null&&orderList.size()>0)
            return orderList;
        return null;
	}


	@Override
	public Integer getTotalCount(String uid, String pname) {
		// TODO Auto-generated method stub
		String p="%"+pname+"%";
		String hql = "select count(*) from OrderItem oi,Orders o,Product p where oi.oid=o.oid and oi.pid=p.pid and o.uid=? and pname like ?";
		Long count = (Long)getHibernateTemplate().find(hql,uid,p).listIterator().next();
		return count.intValue();
	}


	@Override
	public List<OrderItem> getPageBean(String uid, String pname, int start, Integer pageSize) {
		// TODO Auto-generated method stub
		String p="%"+pname+"%";
		String a[]=new String[2];
		a[0]=uid;
		a[1]=p;
		String hql="select new com.it6.tws.entity.OrderItem(oi.itemid,oi.pid,oi.pnum,oi.classify1,oi.classify2,oi.state,oi.subtotal,oi.oid) FROM OrderItem oi,Orders o,Product p where oi.pid=p.pid and oi.oid=o.oid and o.uid = ? and p.pname like ?";      
		List<OrderItem> orderList=(List<OrderItem>) this.getHibernateTemplate().execute(
				(HibernateCallback<OrderItem>) new PageHibernateCallback(hql,a,start, pageSize));
        if(orderList!=null&&orderList.size()>0)
            return orderList;
        return null;
	}


	@Override
	public void deleteOrderItemById(String itemid) {
		// TODO Auto-generated method stub
		OrderItem oitem=new OrderItem();
		oitem.setItemid(itemid);
		this.getHibernateTemplate().delete(oitem);
		
	}



}
