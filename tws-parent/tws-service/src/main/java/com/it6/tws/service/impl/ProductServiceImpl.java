package com.it6.tws.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it6.tws.dao.IProductDao;
import com.it6.tws.dao.impl.ProductDaoImpl;
import com.it6.tws.entity.Product;
import com.it6.tws.service.IProductService;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

	@Autowired
	private IProductDao productDao;
	
	@Override
	public List<Product> findLimmitProduct() {
		return productDao.findLimitProduct();
	}

	
	@Override
	public List<Product> findProductByCidAndDiscount(String categoryId, int isDiscount) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Product.class);
		if(isDiscount!=-1)
			criteria.add(Restrictions.eq("pstatus", isDiscount));
		return productDao.findProductByCidAndDiscount(categoryId,criteria);
	}
	
	
	@Override
	public List<Product> screenProductByPriceAndAddress(String pname,String searchPrice, String serachAddress) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria=DetachedCriteria.forClass(Product.class);
		criteria.add(Restrictions.like("pname",pname,MatchMode.ANYWHERE));
		if(searchPrice.equals("100"))
			criteria.add(Restrictions.ge("shop_price",(double)100));
		else
			criteria.add(Restrictions.lt("shop_price",(double)100));
		if(serachAddress!=null&&!serachAddress.trim().equals(""))
			criteria.add(Restrictions.eq("paddress", serachAddress));
		return productDao.screenProductByPriceAndAddress(criteria);
	}
	
	
	@Override
	public List<Product> screenProductByCategoryAndPriceAndAddress(String categoryId, String searchPrice, String serachAddress) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria=DetachedCriteria.forClass(Product.class);
		criteria.add(Restrictions.eq("cid",categoryId));
		if(searchPrice.equals("100"))
			criteria.add(Restrictions.ge("shop_price",(double)100));
		else
			criteria.add(Restrictions.lt("shop_price",(double)100));
		if(serachAddress!=null&&!serachAddress.trim().equals(""))
			criteria.add(Restrictions.eq("paddress", serachAddress));
		return productDao.screenProductByCategoryAndPriceAndAddress(criteria);
	}


	@Override
	public List<Product> findProductByProName(String pname) {
		// TODO Auto-generated method stub
		return productDao.findProductByProName(pname);
	}




	@Override
	public Product findProductByPid(String pid) {
		// TODO Auto-generated method stub
		return productDao.findProductByPid(pid);
	}


	@Override
	public void saveProduct(Product model) {
		// TODO Auto-generated method stub
		productDao.saveProduct(model);
	}





}
