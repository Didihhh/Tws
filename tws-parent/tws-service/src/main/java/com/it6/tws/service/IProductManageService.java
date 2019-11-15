package com.it6.tws.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.it6.tws.entity.Product;
import com.it6.tws.entity.PageBean;

public interface IProductManageService {
	
	public PageBean displayAllProduct(Integer currentPage, Integer pageSize);//显示所有商品
	
	public PageBean findProductByProName(String pname, Integer currentPage, Integer pageSize);//搜索商品
	
	public List<Product> editProduct(String pid);//点击编辑商品
	
	public void editProductInfo(Product model);//编辑商品
	
	
	public List<Product> addProduct(Product product);//增加商品
	
    public void deleteProductByPid(String pid);//删除商品


}
