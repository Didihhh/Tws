package com.it6.tws.web.action.admin;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.apache.commons.fileupload.FileItem;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.it6.tws.entity.Product;
import com.it6.tws.entity.RestResponse;
import com.it6.tws.service.IProductService;
import com.it6.tws.web.action.base.BaseAction;

import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class AdminProductAction extends BaseAction<Product>{
	@Autowired
	private RestResponse<Product> rest;
	@Autowired
	private IProductService productService;
	
	/**
	 * 下载图片
	 */
	public String downImg(){
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		
		int p=0;
		
		//收集数据的容器
		Map<String,Object> map = new HashMap<String,Object>();
		String imgs[] =new String[5];
		for(int i=0;i<imgs.length;i++)
			imgs[i]=null;
		try {
			//创建磁盘文件项工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//创建文件上传核心对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解析request获得文件项对象集合
			
			List<FileItem> parseRequest = upload.parseRequest(ServletActionContext.getRequest());
			for(FileItem item : parseRequest){
				//判断是否是普通表单项
				boolean formField = item.isFormField();
				if(!formField){
					//文件上传项 获得文件名称 获得文件的内容
					String fir=UUID.randomUUID().toString().substring(0,5);
					String fileName = item.getName()+fir;
					imgs[p]=fileName;
					p++;
					String path = ServletActionContext.getServletContext().getRealPath("/img/upload");
					InputStream in = item.getInputStream();
					OutputStream out = new FileOutputStream(path+"/"+fileName);//I:/xxx/xx/xxx/xxx.jpg
					IOUtils.copy(in, out);
					in.close();
					out.close();
					item.delete();					
				}		
			}
			rest.setCode(1);
			rest.setMsg("成功下载图片");
			JSONObject json= JSONObject.fromObject(rest);
			json.put("psrc1", imgs[0]);
			json.put("psrc2", imgs[1]);
			json.put("psrc3", imgs[2]);
			json.put("psrc4", imgs[3]);
			json.put("psrc5", imgs[4]);
			String jsonStr=json.toString();
			ServletActionContext.getResponse().getWriter().write(jsonStr);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 上传商品
	 */
	public String uploadProduct(){
		ServletActionContext.getResponse().setContentType("application/json; charset=UTF-8");
		productService.saveProduct(model);
		
		try {
			rest.setCode(1);
			rest.setMsg("成功上传图片");
			JSONObject json= JSONObject.fromObject(rest);
			String jsonStr=json.toString();
			ServletActionContext.getResponse().getWriter().write(jsonStr);
		}			
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
