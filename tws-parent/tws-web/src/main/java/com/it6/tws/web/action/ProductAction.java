package com.it6.tws.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.it6.tws.entity.CartItem;
import com.it6.tws.entity.Product;
import com.it6.tws.entity.RestResponse;
import com.it6.tws.entity.User;
import com.it6.tws.service.IProductService;
import com.it6.tws.service.IUserService;
import com.it6.tws.web.action.base.BaseAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



@Controller
@Scope("prototype")
public class ProductAction extends BaseAction<Product> {
	
	
	@Autowired
	private RestResponse<Product> rest;
	@Autowired
	private IUserService userService;
	@Autowired
	private IProductService productService;  
	private String categoryId;  //分类id
	
	private String searchPrice;  //筛选的价格
	private String searchAddress; //筛选的地址
	
	private int isDiscount;  //是否打折
	private int buyNum;    //购买的数量
	private String classify11;    //购买的尺寸 
	private double buyMoney;   //购买的价格
	private String classify22;   //购买的类型

	/**
	 * 获得商品分类
	 */
	public String classificationProduct(){
		List<Product> products=productService.findProductByCidAndDiscount(categoryId,isDiscount);
		System.out.println(isDiscount);
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		rest.setCode(1);
		rest.setMsg("成功返回分类商品");
		JSONObject json= JSONObject.fromObject(rest);
		
		json.put("data", products);
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
	 * 首页为你推荐的商品
	 */
	public String recommendProduct(){
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		List<Product> products=null;
		products=productService.findLimmitProduct();
		
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		JSONObject json;
		if(user !=null){
			rest.setCode(1);
			rest.setMsg("成功拿到为你推荐商品");
			json= JSONObject.fromObject(rest);
			json.put("data", products);
			json.put("username",user.getUsername());
			
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
	 * 首页搜索商品
	 */
	public String searchProductInHome(){
		List<Product> products=productService.findProductByProName(model.getPname());
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		rest.setCode(1);
		rest.setMsg("成功返回搜索的商品");
		JSONObject json= JSONObject.fromObject(rest);
		json.put("data", products);
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
	 * 首页筛选商品
	 */
	public String screenProductInHome(){
		System.out.println(searchPrice);
		List<Product> products=productService.screenProductByPriceAndAddress(model.getPname(),searchPrice,searchAddress);
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		rest.setCode(1);
		rest.setMsg("首页成功筛选到商品");
		System.out.println(searchPrice);
		System.out.println(searchAddress);
		JSONObject json= JSONObject.fromObject(rest);
		json.put("data", products);
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
	 * 分类页筛选商品
	 */
	public String screenProductInCategory(){
		List<Product> products=productService.screenProductByCategoryAndPriceAndAddress(categoryId,searchPrice,searchAddress);
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		rest.setCode(1);
		rest.setMsg("分类页成功筛选到商品");
		JSONObject json= JSONObject.fromObject(rest);
		json.put("data", products);
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
	 * 点击商品
	 */
	public String clickProduct(){
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		// 获得客户端携带cookie---获得名字是pids的cookie
		String pids = model.getPid();
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (user.getUid().equals(cookie.getName())) {
					pids = cookie.getValue();
					// 1-3-2 本次访问商品pid是8----->8-1-3-2
					// 1-3-2 本次访问商品pid是3----->3-1-2
					// 1-3-2 本次访问商品pid是2----->2-1-3
					// 将pids拆成一个数组
					String[] split = pids.split("-");// {3,1,2}
					List<String> asList = Arrays.asList(split);// [3,1,2]
					LinkedList<String> list = new LinkedList<String>(asList);// [3,1,2]
					// 判断集合中是否存在当前pid
					if (list.contains(model.getPid())) {
						// 包含当前查看商品的pid
						list.remove(model.getPid());
						list.addFirst(model.getPid());
					} else {
						// 不包含当前查看商品的pid 直接将该pid放到头上
						list.addFirst(model.getPid());
					}
					// 将[3,1,2]转成3-1-2字符串
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < list.size() && i < 7; i++) {
						sb.append(list.get(i));
						sb.append("-");// 3-1-2-
					}
					// 去掉3-1-2-后的-
					pids = sb.substring(0, sb.length() - 1);
				}
			}
		}
		
		Cookie cookie_pids = new Cookie(user.getUid(),pids);
		cookie_pids.setMaxAge(60*60*24*30);
		ServletActionContext.getResponse().addCookie(cookie_pids);
		
		ServletActionContext.getRequest().getSession().setAttribute("pid", model.getPid());
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		rest.setCode(1);
		rest.setMsg("成功传输商品编号");
		JSONObject json= JSONObject.fromObject(rest);   //将对象转换为json格式
		String jsonStr=json.toString();      //再将json转换为string类型
		
		try {
			ServletActionContext.getResponse().getWriter().write(jsonStr);  //通过响应头发送到前台
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 返回商品详情
	 */
	public String detailProduct(){
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		//设置获取游览记录的商品数量
		int size=3;
		//定义一个记录历史商品信息的集合
		List<Product> historyProductList = new ArrayList<Product>();
		// 获得客户端携带名字叫pids的cookie
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (user.getUid().equals(cookie.getName())) {
					String pids = cookie.getValue();// 3-2-1
					String[] split = pids.split("-");
					for (String pid : split) {
						if(size>0)
						{
							Product pro = productService.findProductByPid(pid);
							historyProductList.add(pro);
							size--;
						}
					}
				}
			}
		}
		String pid=(String) ServletActionContext.getRequest().getSession().getAttribute("pid");
		Product products=productService.findProductByPid(pid);
		String classify1=products.getClassify1();
		String classify2=products.getClassify2();
		String[] split1=classify1.split(",");
		String[] split2=classify2.split(",");
		
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		rest.setCode(1);
		rest.setMsg("成功返回商品详情");
		JSONObject json= JSONObject.fromObject(rest);
		json.put("data", products);
		json.put("username", user.getUsername());
		json.put("classify1", split1);
		json.put("classify2", split2);
		json.put("historyData", historyProductList);
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
	 * 侧边游览记录
	 */
	public String historyProduct(){
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		int size=20;
		//定义一个记录历史商品信息的集合
		List<Product> historyProductList = new ArrayList<Product>();
		// 获得客户端携带名字叫pids的cookie
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (user.getUid().equals(cookie.getName())) {
					String pids = cookie.getValue();// 3-2-1
					String[] split = pids.split("-");
					for (String pid : split) {
						if(size>0)
						{
							Product pro = productService.findProductByPid(pid);
							historyProductList.add(pro);
							size--;
						}
						
					}
				}
			}
		}
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		rest.setCode(1);
		rest.setMsg("成功返回游览记录");
		JSONObject json= JSONObject.fromObject(rest);
		json.put("historyData", historyProductList);
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
	 * 获取购物车信息
	 */
	public String getCart(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user = (User) session.getAttribute("loginUser");
		Map<String,CartItem> cart=null;
		cart=(Map<String, CartItem>) session.getAttribute(user.getUid());
		List<CartItem> products=new ArrayList<CartItem>();
		if(cart!=null) {
			for(String s:cart.keySet()){
				 CartItem cartItem=cart.get(s);
				 products.add(cartItem);
			}
		}
		 
		 ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
			rest.setCode(1);
			rest.setMsg("成功获取购物车信息");
			JSONObject json= JSONObject.fromObject(rest);
			json.put("data", products);
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
	 * 添加商品到购物车
	 */
	public String addToCart(){
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user=(User) session.getAttribute("loginUser");

		//获得product对象
		Product product = productService.findProductByPid(model.getPid());
		System.out.println(model.getPid());
		//封装CartItem
		CartItem item = new CartItem();
		item.setPid(product.getPid());
		item.setBuyNum(buyNum);
		item.setShop_price(buyMoney);
		item.setClassify1(classify11);
		item.setClassify2(classify22);
		item.setPsrc1(product.getPsrc1());
		//获得购物车---判断是否在session中已经存在购物车
		Map<String,CartItem> cart = (Map<String, CartItem>) session.getAttribute(user.getUid());
		if(cart==null){
			cart = new HashMap<String,CartItem>();
		}

		//将购物项放到车中---key是pid
		//先判断购物车中是否已将包含此购物项了 ----- 判断key是否已经存在
		//如果购物车中已经存在该商品----将现在买的数量与原有的数量进行相加操作

		if(cart.containsKey(model.getPid())){
			//取出原有商品的数量
			CartItem cartItem = cart.get(model.getPid());
			int oldBuyNum = cartItem.getBuyNum();
			oldBuyNum+=buyNum;
			cartItem.setBuyNum(oldBuyNum);
		}else{
			//如果车中没有该商品
			cart.put(product.getPid(), item);
		}
		
		//将车再次存入session
		session.setAttribute(user.getUid(), cart);
		
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		rest.setCode(1);
		rest.setMsg("成功添加到购物车");
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
	 * 删除购物车的商品
	 */
	public String delProFromCart(){
		//删除session中的购物车中的购物项集合中的item
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user=(User) session.getAttribute("loginUser");
		Map<String,CartItem> cart = (Map<String, CartItem>) session.getAttribute(user.getUid());
		if(cart!=null){
			//删除
			cart.remove(model.getPid());
		}
		//将车再次存入session
		session.setAttribute(user.getUid(), cart);
		return null;

	}
	/**
	 * 清空购物车
	 */
	public String clearCart(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		User user=(User) session.getAttribute("loginUser");
		session.removeAttribute(user.getUid());
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		rest.setCode(1);
		rest.setMsg("成功清空购物车");
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
	 * get(),set()方法
	 */
	public String getSearchPrice() {
		return searchPrice;
	}

	
	public void setSearchPrice(String searchPrice) {
		this.searchPrice = searchPrice;
	}


	public int getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(int isDiscount) {
		this.isDiscount = isDiscount;
	}

	public String getSearchAddress() {
		return searchAddress;
	}


	public void setSearchAddress(String searchAddress) {
		this.searchAddress = searchAddress;
	}


	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}








	public String getClassify11() {
		return classify11;
	}

	public void setClassify11(String classify11) {
		this.classify11 = classify11;
	}

	public String getClassify22() {
		return classify22;
	}

	public void setClassify22(String classify22) {
		this.classify22 = classify22;
	}

	public double getBuyMoney() {
		return buyMoney;
	}

	public void setBuyMoney(double buyMoney) {
		this.buyMoney = buyMoney;
	}



	
	

}
