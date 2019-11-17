package com.it6.tws.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it6.tws.entity.PageBean;
import com.it6.tws.entity.Product;
import com.it6.tws.entity.RestResponse;
import com.it6.tws.entity.User;
import com.it6.tws.service.IProductManageService;

import com.it6.tws.web.action.base.BaseAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("productManageAction")
@Scope("prototype")

public class ProductManageAction extends BaseAction<Product> {
	
	@Autowired
	private RestResponse rest;
	@Autowired
	private IProductManageService productManageService;  
	private String productJSON;
	private Integer currentPage; //当前页
	private Integer pageSize=3; 
	
	
	/**
	 * 管理员登录后显示商品列表
	 */
	public String AllProductList(){
		
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		PageBean pageBean=productManageService.displayAllProduct(currentPage,pageSize);
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		JSONObject json;
		System.out.println(pageSize);
		if(user!=null){
			rest.setCode(1);
			rest.setMsg("成功显示所有商品");
			json= JSONObject.fromObject(rest);
			json.put("data", pageBean.getList());	
			json.put("total", pageBean.getTotalCount());
			json.put("count",pageSize);
		}else{
			rest.setCode(0);
			rest.setMsg("未登录");
			json= JSONObject.fromObject(rest);
		}
		
		try {		
			String jsonStr=json.toString();
			ServletActionContext.getResponse().getWriter().write(jsonStr);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 商品管理页面搜索商品
	 */

	public String searchProduct(){
		//System.out.println(model.getPname());
		PageBean pageBean=productManageService.findProductByProName(model.getPname(),currentPage,pageSize);
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		List<Product> proList=null;
		System.out.println(proList);
		proList=pageBean.getList();
		rest.setCode(1);
		rest.setMsg("成功返回搜索的商品");
		JSONObject json= JSONObject.fromObject(rest);
		json.put("data", proList==null?"":proList);
		json.put("total", pageBean.getTotalCount());
		json.put("count",pageSize);
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
	 * 商品管理页面点击编辑商品
	 */
	public String editProduct() {
		
		List<Product> proList=null;
		proList=productManageService.editProduct(model.getPid());
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		System.out.println(proList.size());
		rest.setCode(1);
		rest.setMsg("成功返回商品");
		JSONObject json= JSONObject.fromObject(rest);
		json.put("data",proList.get(0));
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
	 *  更新编辑过的商品信息
	 */
	public String saveProductInfo(){
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		productManageService.editProductInfo(model);
		rest.setCode(1);
		rest.setMsg("成功更新商品");
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
	 * 商品管理页面删除商品
	 */
	public String deleteProductByPid(){
		String pid=null;
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		JSONObject jsonObject=JSONObject.fromObject(productJSON);
		JSONArray jsonArray=jsonObject.getJSONArray("itemJSON");
		if(jsonArray !=null && jsonArray.size()>0){ 
			for( int i=0; i< jsonArray.size(); i++){ 
				pid=jsonArray.get(i).toString();
				productManageService.deleteProductByPid(pid);
			} 
		}
		rest.setCode(1);
		rest.setMsg("成功删除商品");
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
	public RestResponse getRest() {
		return rest;
	}

	public void setRest(RestResponse rest) {
		this.rest = rest;
	}

	public IProductManageService getProductManageService() {
		return productManageService;
	}

	public void setProductManageService(IProductManageService productManageService) {
		this.productManageService = productManageService;
	}



	public String getProductJSON() {
		return productJSON;
	}

	public void setProductJSON(String productJSON) {
		this.productJSON = productJSON;
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


}
