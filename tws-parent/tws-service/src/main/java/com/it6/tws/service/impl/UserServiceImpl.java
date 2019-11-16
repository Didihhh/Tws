package com.it6.tws.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.it6.tws.dao.IUserDao;
import com.it6.tws.entity.User;
import com.it6.tws.service.IUserService;
import com.it6.tws.utils.MD5Utils;

@Service
@Transactional
public class UserServiceImpl implements IUserService{
	@Autowired
	private IUserDao userDao;
	/***
	 * 用户登录
	 */
	public User login(User user) {
		//使用MD5加密密码
		//String password = MD5Utils.md5(user.getPassword());
		return userDao.findUserByUsernameAndPassword(user.getUid(),user.getPassword());
	}
	@Override
	public String register(User user) {
		// TODO Auto-generated method stub
		//String password = MD5Utils.md5(user.getPassword());
		return userDao.saveUser(user);
	}
	@Override
	public User fingUserByUid(String uid) {
		// TODO Auto-generated method stub
		return userDao.fingUserByUid(uid);
	}

	@Override
	public void updateAddress(User user) {
		// TODO Auto-generated method stub
		userDao.updateAddress(user);
	}
	@Override
	public boolean findUserByUsernameAndPassword(User u) {
		// TODO Auto-generated method stub
		return userDao.findUserByUsernameAndPassword(u);
	}
}
