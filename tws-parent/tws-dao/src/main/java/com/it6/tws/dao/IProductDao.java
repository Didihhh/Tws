package com.it6.tws.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.it6.tws.dao.base.IBaseDao;
import com.it6.tws.entity.Product;


public interface IProductDao extends IBaseDao<Product>{

	public List<Product> findProductByProName(String name);



	public List<Product> screenProductByCategoryAndPriceAndAddress(DetachedCriteria criteria);

	public List<Product> screenProductByPriceAndAddress(DetachedCriteria criteria);


	public List<Product> findLimitProduct();

	public List<Product> findProductByCidAndDiscount(String e,DetachedCriteria criteria);






	public Product findProductByPid(String pid);



	public void saveProduct(Product model);
}
