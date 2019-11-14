package com.it6.tws.dao.impl;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.it6.tws.dao.IProductDao;
import com.it6.tws.dao.base.impl.BaseDaoImpl;
import com.it6.tws.entity.Product;
import com.it6.tws.entity.User;

@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product> implements IProductDao{

	
	@Override
	public List<Product> findLimitProduct() {
		DetachedCriteria detached=DetachedCriteria.forClass(Product.class);
		// TODO Auto-generated method stub
		List proLists =  this.getHibernateTemplate().findByCriteria(detached,0,20);
		return proLists;
	}

	@Override
	public List<Product> findProductByCidAndDiscount(String categoryId,DetachedCriteria criteria) {
		criteria.add(Restrictions.eq("cid", categoryId));
		List proLists =  this.getHibernateTemplate().findByCriteria(criteria);
		return proLists;
	}

	@Override
	public List<Product> findProductByProName(String name) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria=DetachedCriteria.forClass(Product.class);
		criteria.add(Restrictions.like("pname",name,MatchMode.ANYWHERE));
		List proLists=this.getHibernateTemplate().findByCriteria(criteria);
		return proLists;
	}

	@Override
	public List<Product> screenProductByPriceAndAddress(DetachedCriteria criteria) {
		// TODO Auto-generated method stub
		List proLists =  this.getHibernateTemplate().findByCriteria(criteria);
		return proLists;
	}

	@Override
	public List<Product> screenProductByCategoryAndPriceAndAddress(DetachedCriteria criteria) {
		// TODO Auto-generated method stub
		List proLists =  this.getHibernateTemplate().findByCriteria(criteria);
		return proLists;
	}


	@Override
	public Product findProductByPid(String pid) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Product.class);
		criteria.add(Restrictions.eq("pid",pid));
		List proLists=this.getHibernateTemplate().findByCriteria(criteria);
		if(proLists.size()!=0)
			return (Product) proLists.get(0);
		return null;
	}

	@Override
	public void saveProduct(Product model) {
		// TODO Auto-generated method stub
		model.setPid(UUID.randomUUID().toString().substring(0,5));
		model.setPstatus(-1);
		this.getHibernateTemplate().save(model);
	}

}
