package com.it6.tws.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.it6.tws.dao.IUserDao;
import com.it6.tws.dao.base.impl.BaseDaoImpl;
import com.it6.tws.entity.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {
	/**
	 * 根据用户名和密码查询用户
	 */
	public User findUserByUsernameAndPassword(String uid, String password) {
		String hql = "FROM User u WHERE u.uid = ? AND u.password = ?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, uid,password);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public String saveUser(User user) {
		// TODO Auto-generated method stub
		String str=null;
		User u=this.findById(user.getUid());
		if(u==null) {
			this.save(user);
			str="1";
		}
		return str;
	}

	@Override
	public User fingUserByUid(String uid) {
		String hql = "FROM User u WHERE u.uid = ?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, uid);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}


	@Override
	public void updateAddress(User user) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().update(user);
	}

	@Override
	public boolean findUserByUsernameAndPassword(User u) {
		String hql = "FROM User u WHERE u.uid = ? AND u.password = ?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, u.getUid(),u.getPassword());
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
}
