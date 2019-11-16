package com.it6.tws.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.it6.tws.entity.CartItem;
import com.it6.tws.entity.MyOrderItem;
import com.it6.tws.entity.OrderItem;
import com.it6.tws.entity.PageBean;
import com.it6.tws.entity.Product;
import com.it6.tws.entity.RestResponse;
import com.it6.tws.entity.User;
import com.it6.tws.service.IOrderService;
import com.it6.tws.service.IProductService;
import com.it6.tws.utils.BuildMyOrder;
import com.it6.tws.web.action.base.BaseAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<OrderItem>{
	@Autowired
	private RestResponse<Product> rest;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IProductService productService;
	private Integer currentPage;
	private Integer pageSize=5;
	private String pname;
	private String orderJson;
	private String total;
	private String address;
	private String pconsignee;
	private String telephone;
	/**
	 * 显示我的订单
	 */
	public String findAllOrderItem(){
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		PageBean pageBean=orderService.getPageBean(currentPage,pageSize,user.getUid());
		Product pro;
		MyOrderItem myOrderItem=null;
		List<OrderItem> orderList=null;
		List<MyOrderItem> myOrderList=new ArrayList<MyOrderItem>();
		orderList=pageBean.getList();
		
		if(orderList!=null&&orderList.size()>0)
		{
			for(OrderItem orderItem:orderList) {
				myOrderItem = new MyOrderItem();
				pro=productService.findProductByPid(orderItem.getPid());
				BuildMyOrder build=new BuildMyOrder(orderItem,null,pro,myOrderItem);
				myOrderItem=build.getMyOrderItem();
				myOrderList.add(myOrderItem);
			}
		}
		rest.setCode(1);
		rest.setMsg("我的订单显示完成");
		JSONObject json= JSONObject.fromObject(rest);
		json.put("data", myOrderList);
		json.put("username", user.getUsername());
		json.put("count", pageSize);
		json.put("total", pageBean.getTotalCount());
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
	 * 搜索订单
	 */
	public String findOredrsByPname(){
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		PageBean pageBean=orderService.getPageBean(currentPage,pageSize,user.getUid(),pname);
		Product pro;
		MyOrderItem myOrderItem=null;
		List<OrderItem> orderList=null;
		List<MyOrderItem> myOrderList=new ArrayList<MyOrderItem>();
		orderList=pageBean.getList();
		if(orderList!=null&&orderList.size()>0)
		{
			for(OrderItem orderItem:orderList) {
				myOrderItem = new MyOrderItem();
				pro=productService.findProductByPid(orderItem.getPid());
				BuildMyOrder build=new BuildMyOrder(orderItem,null,pro,myOrderItem);
				myOrderItem=build.getMyOrderItem();
				myOrderList.add(myOrderItem);
			}
		}
		rest.setCode(1);
		rest.setMsg("查找订单成功");
		JSONObject json= JSONObject.fromObject(rest);
		json.put("data", myOrderList);
		json.put("count", pageSize);
		json.put("total", pageBean.getTotalCount());
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
	 * 确认收货
	 */
	public String updateOrderItemState(){
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		String str=null;
		str=orderService.updateOrderItemState(user.getUid(),model.getItemid());
		if(str!=null) {
			rest.setCode(1);
			rest.setMsg("成功确认收货");
		}
		else {
			rest.setCode(0);
			rest.setMsg("确认收货失败");
		}
			
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
	 * 购买前获得用户信息（地址，电话，收货人）
	 */
	public String getUserInform(){
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		
		rest.setCode(1);
		rest.setMsg("用户信息传输成功");
		JSONObject json= JSONObject.fromObject(rest);
		json.put("data", user);
		
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
	 * 添加到订单
	 */
	public String addToOredrs(){
		String flag=null;
		flag=orderService.addToOrders(model,address,pconsignee,telephone);
		if(flag!=null)
		{
			rest.setCode(1);
			rest.setMsg("购买成功");
		}
		else
		{
			rest.setCode(0);
			rest.setMsg("购买失败");
		}
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		String jsonStr= JSONObject.fromObject(rest).toString();
		try {		
			ServletActionContext.getResponse().getWriter().write(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	
	/**
	 * 购物车结算
	 */
	            
	public String addToOredrsInCast(){
		String flag=null;
		JSONObject jsonObject=JSONObject.fromObject(orderJson);
		JSONArray jsonArray=jsonObject.getJSONArray("orderArray");
		
		
		if(jsonArray.size()>0){
			flag=orderService.addToOredrsInCast(jsonArray,total,address,pconsignee,telephone);
		}
		
		
		if(flag!=null)
		{
			rest.setCode(1);
			rest.setMsg("结算成功");
		}
		else
		{
			rest.setCode(0);
			rest.setMsg("结算失败");
		}
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		String jsonStr= JSONObject.fromObject(rest).toString();
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
		
		orderService.deleteOrderItemById(model.getItemid());
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		rest.setCode(1);
		rest.setMsg("成功删除我的订单");
		String jsonStr= JSONObject.fromObject(rest).toString();
		try {		
			ServletActionContext.getResponse().getWriter().write(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public String getOrderJson() {
		return orderJson;
	}

	public void setOrderJson(String orderJson) {
		this.orderJson = orderJson;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPconsignee() {
		return pconsignee;
	}

	public void setPconsignee(String pconsignee) {
		this.pconsignee = pconsignee;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
}
