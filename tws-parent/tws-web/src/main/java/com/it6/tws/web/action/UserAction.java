package com.it6.tws.web.action;

import java.io.IOException;
import java.util.List;


import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

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
public class UserAction extends BaseAction<User> {
	@Autowired
	private IUserService userService;
	
	@Autowired
	private RestResponse rest;
	
	private String oldPassword;
	
	private String newPassword;
	/**
	 * 用户登录
	 */
	public String login(){
		User user = userService.login(model);	   //将数据传输给service层	
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		JSONObject json;
		if(user !=null&&!user.getUid().equals("admin")){
			//登录成功,将user对象放入session，跳转到首页
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			rest.setCode(1);
			rest.setMsg("登录成功");
		}
		else if(user !=null&&user.getUid().equals("admin"))
		{
			//管理员登录成功,将user对象放入session，跳转到首页
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			rest.setCode(2);
			rest.setMsg("管理员登录成功");
		}
		else{
			rest.setCode(0);
			rest.setMsg("账号或者密码错误");
			
		}
		json= JSONObject.fromObject(rest);      //将对象转换为json格式
		try {		
			String jsonStr=json.toString();     //将json转换为string类型
			ServletActionContext.getResponse().getWriter().write(jsonStr);    //通过响应头发送到前台
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 用户注册
	 */
	public String register() {
		String flag=null;
		flag=userService.register(model);
		//注册成功
		if(flag!=null)
		{
			rest.setCode(1);
			rest.setMsg("注册成功");
		}
		else
		{
			rest.setCode(0);
			rest.setMsg("已有该账号");
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
	 * 修改用户的默认地址，电话
	 */
	public String updateAddress() {
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		user.setAutoaddress(model.getAutoaddress());
		user.setAutopconsignee(model.getAutopconsignee());
		user.setAutotelephone(model.getAutotelephone());
		userService.updateAddress(user);
		ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);

		rest.setCode(1);
		rest.setMsg("修改成功");
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
	 * 修改用户的用户名
	 */
	public String updateUserName() {
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		user.setUsername(model.getUsername());
		userService.updateAddress(user);
		ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);

		rest.setCode(1);
		rest.setMsg("修改成功");
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
	 * 修改用户的密码
	 */
	public String updatePassword() {
		User u = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		u.setPassword(oldPassword);
		User user = userService.login(u);
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		JSONObject json;
		System.out.println(newPassword);
		System.out.println(oldPassword);
		System.out.println(u !=null);
		if(user !=null){
			user.setPassword(newPassword);
			userService.updateAddress(user);
			rest.setCode(1);
			rest.setMsg("修改密码成功");
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
		}else{
			rest.setCode(0);
			rest.setMsg("密码错误");
			
		}
		json= JSONObject.fromObject(rest);      //将对象转换为json格式
		try {		
			String jsonStr=json.toString();     //将json转换为string类型
			ServletActionContext.getResponse().getWriter().write(jsonStr);    //通过响应头发送到前台
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得用户信息
	 */
	public String getUserInform() {
		User u = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		
		User user = userService.fingUserByUid(u.getUid());
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		JSONObject json;
		rest.setCode(1);
		rest.setMsg("成功获得用户信息");
		json= JSONObject.fromObject(rest);      //将对象转换为json格式
		json.put("data", user);
		try {		
			String jsonStr=json.toString();     //将json转换为string类型
			ServletActionContext.getResponse().getWriter().write(jsonStr);    //通过响应头发送到前台
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public String getOldPassword() {
		return oldPassword;
	}


	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


	public String getNewPassword() {
		return newPassword;
	}


	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	
}
