package com.chanct.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chanct.common.model.User;

@Mapper
public interface UserMapper {

	public List<User> selectUsers();

	public int insert(User account);

	public int delete(String value);

	public int update(User updateUser);

	public List<User> selectUsersByQuery(User user);
	
}
