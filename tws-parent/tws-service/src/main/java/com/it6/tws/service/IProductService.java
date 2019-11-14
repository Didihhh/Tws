package com.it6.tws.service;

import java.util.List;

import com.it6.tws.entity.Product;

public interface IProductService {
	


	public List<Product> findProductByProName(String pname);


	public List<Product> screenProductByCategoryAndPriceAndAddress(String categoryId, String searchPrice, String serachAddress);

	public List<Product> screenProductByPriceAndAddress(String pname, String searchPrice, String searchAddress);


	public List<Product> findLimmitProduct();



	public List<Product> findProductByCidAndDiscount(String categoryId, int isDiscount);




	public Product findProductByPid(String pid);


	public void saveProduct(Product model);

}
