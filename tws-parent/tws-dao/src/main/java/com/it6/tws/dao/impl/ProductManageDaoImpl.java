package com.it6.tws.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.it6.tws.dao.IProductManageDao;
import com.it6.tws.dao.base.impl.BaseDaoImpl;
import com.it6.tws.entity.Product;
import com.it6.tws.utils.PageHibernateCallback;
@Repository
public class ProductManageDaoImpl extends BaseDaoImpl<Product> implements IProductManageDao{
	
    //获取商品数据总数
	@Override
	public Integer getTotalCount() {
		// TODO Auto-generated method stub
		DetachedCriteria detached=DetachedCriteria.forClass(Product.class);
		detached.setProjection(Projections.rowCount());
		List<Long> list =  (List<Long>) this.getHibernateTemplate().findByCriteria(detached);
		if(list!=null&&list.size()>0) {
			Long len=list.get(0);
			return len.intValue();
		}
		else return null;

	}

	//显示所有商品
	@Override
	public List<Product> displayAllProduct(int start, Integer pageSize) {
		// TODO Auto-generated method stub
		DetachedCriteria detached=DetachedCriteria.forClass(Product.class);
		List<Product> list= (List<Product>) this.getHibernateTemplate().findByCriteria(detached,start,pageSize);
		if(list!=null&&list.size()>0)
			return list;
		else 
			return null;
	}
	
	//搜索商品
	@Override
	public List<Product> findProductByProName(String name,int start, Integer pageSize) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria=DetachedCriteria.forClass(Product.class);
		criteria.add(Restrictions.like("pname","%"+name+"%"));
		List proLists=this.getHibernateTemplate().findByCriteria(criteria,start,pageSize);
		if(proLists!=null&&proLists.size()>0)
			return proLists;
		else 
			return null;

	}

	//点击编辑商品
	@Override
	public List<Product> editProduct(DetachedCriteria criteria){
		
		List<Product> productLists =  (List<Product>) this.getHibernateTemplate().findByCriteria(criteria);
		
		return productLists;

	}
	
	//更新编辑后商品
	@Override
	public void editProductInfo(Product model) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(model);
	}
	 
	//点击增加商品
	@Override
	public List<Product> addProduct(Product product) {
		// TODO Auto-generated method stub
		
		return null;
	}

	//删除某个商品
	@Override
	public void deleteProductByPid(String pid) {
		// TODO Auto-generated method stub
		Product product = this.findById(pid);
		this.getHibernateTemplate().delete(product);
		
	}
    
	//获得搜索的商品总数量
	@Override
	public Integer getTotalCount(String pname) {
		// TODO Auto-generated method stub
		DetachedCriteria detached=DetachedCriteria.forClass(Product.class);
		detached.setProjection(Projections.rowCount());
		detached.add(Restrictions.like("pname","%"+pname+"%"));
		List<Long> list =  (List<Long>) this.getHibernateTemplate().findByCriteria(detached);
		if(list!=null&&list.size()>0) {
			Long len=list.get(0);
			return len.intValue();
		}
		else return 0;
	}



}
