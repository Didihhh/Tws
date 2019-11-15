package com.it6.tws.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it6.tws.dao.IProductManageDao;
import com.it6.tws.entity.PageBean;
import com.it6.tws.entity.Product;
import com.it6.tws.service.IProductManageService;
@Service
@Transactional
public class ProductManageServiceImpl implements IProductManageService{

	@Autowired
	private IProductManageDao productManageDao;
	
	//显示所有商品
	@Override
	public PageBean displayAllProduct(Integer currentPage, Integer pageSize) {
		// TODO Auto-generated method stub
		Integer totalCount=productManageDao.getTotalCount();
		PageBean pb=new PageBean(currentPage, totalCount, pageSize);
		List<Product> list=productManageDao.displayAllProduct(pb.getStart(),pb.getPageSize());
		pb.setList(list);
		return pb;
	}
	
	//搜索商品
	@Override
	public PageBean findProductByProName(String pname, Integer currentPage, Integer pageSize) {
		// TODO Auto-generated method stub
		Integer totalCount=productManageDao.getTotalCount(pname);
		PageBean pb=new PageBean(currentPage, totalCount, pageSize);
		List<Product> productlist=productManageDao.findProductByProName(pname,pb.getStart(),pageSize);
		pb.setList(productlist);
		return pb;
	}
	
    //点击编辑商品
	@Override
	public List<Product> editProduct(String pid) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria=DetachedCriteria.forClass(Product.class);
		criteria.add(Restrictions.eq("pid",pid));
		return productManageDao.editProduct(criteria);
	}
	
	//更新编辑后商品
	@Override
	public void editProductInfo(Product model) {
		// TODO Auto-generated method stub
		productManageDao.editProductInfo(model);
		
	}

		
    //增加商品
	@Override
	public List<Product> addProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	//删除某个商品
	@Override
	public void deleteProductByPid(String pid) {
		// TODO Auto-generated method stub
		productManageDao.deleteProductByPid(pid);
	}




}
