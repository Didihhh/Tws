package com.it6.tws.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.it6.tws.dao.base.IBaseDao;
import com.it6.tws.entity.Product;

public interface IProductManageDao extends IBaseDao<Product>{
	
	public Integer getTotalCount();//获得返回数据行数
	
	public List<Product> displayAllProduct(int start, Integer pageSize);//显示所有商品
	
	public List<Product> findProductByProName(String name,int start, Integer pageSize);//搜索商品
	
	public List<Product> editProduct(DetachedCriteria criteria);//点击编辑商品
	
	public void editProductInfo(DetachedCriteria criteria);//编辑商品
	
	public List<Product> addProduct(Product product);//增加商品
	
	public void deleteProductByPid(String pid);//删除商品

	public Integer getTotalCount(String pname);


}
