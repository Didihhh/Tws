package com.it6.tws.service;

import com.it6.tws.entity.User;

public interface IUserService {

	public User login(User model);

	public String register(User user);

	public User fingUserByUid(String uid);

	public void updateAddress(User user);

	public boolean findUserByUsernameAndPassword(User u);

}
