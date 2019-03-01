package com.purefun.fstp.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.purefun.fstp.web.login.model.UserInfo;

public interface UserInfoMapper {
	List<UserInfo> selectUser(@Param("username") String username);
}
