package com.it6.tws.web.action.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.it6.tws.dao.IOrderDao;
import com.it6.tws.entity.MyOrderItem;
import com.it6.tws.entity.OrderItem;
import com.it6.tws.entity.Orders;
import com.it6.tws.entity.PageBean;
import com.it6.tws.entity.Product;
import com.it6.tws.entity.RestResponse;
import com.it6.tws.entity.User;
import com.it6.tws.service.IAdminOrderService;
import com.it6.tws.service.IOrderService;
import com.it6.tws.service.IProductService;
import com.it6.tws.service.IUserService;
import com.it6.tws.service.impl.ProductServiceImpl;
import com.it6.tws.service.impl.UserServiceImpl;
import com.it6.tws.utils.BuildMyOrder;
import com.it6.tws.web.action.base.BaseAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class AdminOrderAction extends BaseAction<OrderItem>{
	@Autowired
	private RestResponse<Product> rest;

	@Autowired
	private IAdminOrderService adminOrderService;
	@Autowired
	private IOrderDao orderDao;
	@Autowired
	private IUserService userService;
	@Autowired
	private IProductService productService;
	private String pname;
	private String itemJSON;
	private Integer currentPage; //当前页
	private Integer pageSize=4;  //一页有多少条 数据
	/**
	 * 分页显示所有订单
	 */
	public String findAllOrderItem(){
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		//获取分页订单项
		PageBean pageBean=adminOrderService.getPageBean(currentPage,pageSize);
		Product pro=null;
		Orders order=null;
		User user=null;
		MyOrderItem myOrderItem;
		List<OrderItem> orderList=null;
		List<MyOrderItem> myOrderList=new ArrayList<MyOrderItem>();
		orderList=pageBean.getList();
		if(orderList!=null)
		{
			//将orderList构造为myOrderList
			for(OrderItem orderItem:orderList) {
				myOrderItem = new MyOrderItem();
				order=orderDao.findOrderByOid(orderItem.getOid());
				pro=productService.findProductByPid(orderItem.getPid());
				user=userService.fingUserByUid(order.getUid());
				BuildMyOrder build=new BuildMyOrder(orderItem,user.getUsername(),pro,myOrderItem);
				myOrderItem=build.getMyOrderItem();
				myOrderList.add(myOrderItem);
			}	
			rest.setCode(1);
			rest.setMsg("订单分页完成");
		}
		else
		{
			rest.setCode(0);
			rest.setMsg("无订单");
		}
		JSONObject json= JSONObject.fromObject(rest);
		json.put("data", myOrderList);
		json.put("total", pageBean.getTotalCount());
		json.put("count", pageSize);
		String jsonStr=json.toString();
		
		try {		
			ServletActionContext.getResponse().getWriter().write(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 搜索
	 */
	public String findOrderItemByName(){
		System.out.println(pname);
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		//获取分页订单项
		PageBean pageBean=adminOrderService.getPageBean(pname,currentPage,pageSize);
		
		Product pro=null;
		Orders order=null;
		User user=null;
		MyOrderItem myOrderItem;
		List<OrderItem> orderList=null;
		List<MyOrderItem> myOrderList=new ArrayList<MyOrderItem>();
		orderList=pageBean.getList();
		
		//将orderList构造为myOrderList
		if(orderList!=null)
		{
			for(OrderItem orderItem:orderList) {
				myOrderItem = new MyOrderItem();
				order=orderDao.findOrderByOid(orderItem.getOid());
				pro=productService.findProductByPid(orderItem.getPid());
				user=userService.fingUserByUid(order.getUid());
				BuildMyOrder build=new BuildMyOrder(orderItem,user.getUsername(),pro,myOrderItem);
				myOrderItem=build.getMyOrderItem();
				myOrderList.add(myOrderItem);
			}
			System.out.println(myOrderList.get(0).getPname());
			rest.setCode(1);
			rest.setMsg("搜索订单分页完成");
		}
		else
		{
			rest.setCode(0);
			rest.setMsg("无订单");
		}
		JSONObject json= JSONObject.fromObject(rest);
		json.put("data", myOrderList);
		json.put("total", pageBean.getTotalCount());
		json.put("count", pageSize);
		
		String jsonStr=json.toString();
		try {		
			ServletActionContext.getResponse().getWriter().write(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 删除订单
	 */
	public String deleteOrderItemById(){
		System.out.println(itemJSON);
		String itemID=null;
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		JSONObject jsonObject=null;
		JSONArray jsonArray=null;
		if(itemJSON!=null)
		{
			jsonObject = JSONObject.fromObject(itemJSON);
			jsonArray=jsonObject.getJSONArray("itemJSON");
		}
		if(jsonArray !=null && jsonArray.size()>0){ 
			for( int i=0; i< jsonArray.size(); i++){ 
				itemID=jsonArray.get(i).toString();
				adminOrderService.deleteOrderItemById(itemID);
			} 
		}
		rest.setCode(1);
		rest.setMsg("成功删除订单");
		JSONObject json= JSONObject.fromObject(rest);
		String jsonStr=json.toString();
		try {		
			ServletActionContext.getResponse().getWriter().write(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 修改订单状态
	 */
	public String updateOrderItemState(){
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		OrderItem oitem=adminOrderService.findOrderItemById(model.getItemid());
		oitem.setState(0);
		adminOrderService.updateOrderItemState(oitem);
		rest.setCode(1);
		rest.setMsg("成功修改订单");
		JSONObject json= JSONObject.fromObject(rest);
		String jsonStr=json.toString();
		try {		
			ServletActionContext.getResponse().getWriter().write(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getItemJSON() {
		return itemJSON;
	}

	public void setItemJSON(String itemJSON) {
		this.itemJSON = itemJSON;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}
	


	
}
