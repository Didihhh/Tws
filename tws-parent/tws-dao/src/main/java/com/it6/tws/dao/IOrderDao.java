package com.it6.tws.dao;

import java.util.List;

import com.it6.tws.dao.base.IBaseDao;
import com.it6.tws.entity.OrderItem;
import com.it6.tws.entity.Orders;

import net.sf.json.JSONArray;


public interface IOrderDao extends IBaseDao<OrderItem> {


	List<OrderItem> findAllOrderByUid(String uid);

	List<OrderItem> FindOredrsByPname(String uid, String pname);

	String updateOrderItemState(String uid, String itemid);
	
	Orders findOrderByOid(String oid);

	Integer getTotalCount(String uid);

	List<OrderItem> getPageBean(String uid, int start, Integer pageSize);

	Integer getTotalCount(String uid, String pname);

	List<OrderItem> getPageBean(String uid, String pname, int start, Integer pageSize);

	String addToOrders(OrderItem orderItem, String address, String pconsignee, String telephone);

	String addToOredrsInCast(JSONArray jsonArray, String total, String address, String pconsignee, String telephone);

	void deleteOrderItemById(String itemid);
}
