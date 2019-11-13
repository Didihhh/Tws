package com.it6.tws.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it6.tws.dao.IOrderDao;
import com.it6.tws.entity.OrderItem;
import com.it6.tws.entity.PageBean;
import com.it6.tws.service.IOrderService;

import net.sf.json.JSONArray;
@Service
@Transactional
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private IOrderDao orderDao;

	@Override
	public List<OrderItem> findAllOrderByUid(String uid) {
		// TODO Auto-generated method stub
		return orderDao.findAllOrderByUid(uid);
	}
	@Override
	public List<OrderItem> FindOredrsByPname(String uid, String pname) {
		// TODO Auto-generated method stub
		return orderDao.FindOredrsByPname(uid,pname);
	}
	@Override
	public String updateOrderItemState(String uid, String itemid) {
		// TODO Auto-generated method stub
		return orderDao.updateOrderItemState(uid,itemid);
	}

	@Override
	public PageBean getPageBean(Integer currentPage, Integer pageSize, String uid) {
		// TODO Auto-generated method stub
		Integer totalCount=orderDao.getTotalCount(uid);
		PageBean pb=new PageBean(currentPage, totalCount, pageSize);
		List<OrderItem> list=orderDao.getPageBean(uid,pb.getStart(),pb.getPageSize());
		pb.setList(list);
		return pb;
	}
	@Override
	public PageBean getPageBean(Integer currentPage, Integer pageSize, String uid, String pname) {
		// TODO Auto-generated method stub
		Integer totalCount=orderDao.getTotalCount(uid,pname);
		PageBean pb=new PageBean(currentPage, totalCount, pageSize);
		List<OrderItem> list=orderDao.getPageBean(uid,pname,pb.getStart(),pb.getPageSize());
		pb.setList(list);
		return pb;
	}
	@Override
	public String addToOrders(OrderItem orderItem, String address, String pconsignee, String telephone) {
		// TODO Auto-generated method stub
		return orderDao.addToOrders(orderItem,address,pconsignee,telephone);
	}
	@Override
	public String addToOredrsInCast(JSONArray jsonArray, String total, String address, String pconsignee,
			String telephone) {
		return orderDao.addToOredrsInCast(jsonArray,total,address,pconsignee,telephone);
	}
	@Override
	public void deleteOrderItemById(String itemid) {
		orderDao.deleteOrderItemById(itemid);
		// TODO Auto-generated method stub
		
	}

}
