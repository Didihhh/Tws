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
	public void editProductInfo(String cid,String pid,String psrc1,String psrc2,String psrc3,String psrc4,String psrc5,int pstatus,String pname,double market_price,double shop_price,String paddress,String classify1,String classify2) {
		// TODO Auto-generated method stub
		Product product=new Product();
		product.setCid(cid);
		product.setPid(pid);
		product.setPsrc1(psrc1);
		product.setPsrc2(psrc2);
		product.setPsrc3(psrc3);
		product.setPsrc4(psrc4);
		product.setPsrc5(psrc5);
		product.setPstatus(pstatus);
		product.setPname(pname);
		product.setMarket_price(market_price);
		product.setShop_price(shop_price);
		product.setClassify1(classify1);
		product.setClassify2(classify2);
		product.setPaddress(paddress);
		this.getHibernateTemplate().update(product);
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
		Product product = new Product();
		product.setPid(pid);
		this.getHibernateTemplate().delete(product);
		
	}

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
		else return null;
	}




//	@Override
//	public void deleteAllProduct() {
//		// TODO Auto-generated method stub
//		DetachedCriteria detached=DetachedCriteria.forClass(Product.class);
//		List productLists =  this.getHibernateTemplate().findByCriteria(detached,0,10);
//		this.getHibernateTemplate().deleteAll(productLists);
//	}
//


}
