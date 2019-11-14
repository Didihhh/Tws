package com.it6.tws.service;

import java.util.List;

import com.it6.tws.entity.OrderItem;
import com.it6.tws.entity.PageBean;
import com.it6.tws.entity.Product;

import net.sf.json.JSONArray;

public interface IOrderService {
	



	public List<OrderItem> findAllOrderByUid(String uid);

	public List<OrderItem> FindOredrsByPname(String uid, String pname);

	public String updateOrderItemState(String uid, String itemid);


	public PageBean getPageBean(Integer currentPage, Integer pageSize, String uid);

	public PageBean getPageBean(Integer currentPage, Integer pageSize, String uid, String pname);

	public String addToOrders(OrderItem orderItem, String address, String pconsignee, String telephone);

	public String addToOredrsInCast(JSONArray jsonArray, String total, String address, String pconsignee,
			String telephone);

	public void deleteOrderItemById(String itemid);





}
