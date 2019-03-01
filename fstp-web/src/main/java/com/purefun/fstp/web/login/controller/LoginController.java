package com.purefun.fstp.web.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.purefun.fstp.web.PWebService;
import com.purefun.fstp.web.login.model.UserInfo;
import com.purefun.fstp.web.login.model.UserLogin;
import com.purefun.fstp.web.mapper.UserInfoMapper;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	PWebService service;
	
	private SqlSession sqlSession;
	
	private UserInfoMapper userMapper;
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String,UserLogin> getUser(@RequestBody String request) {
		Map maps = (Map)JSON.parse(request);
		String username = (String)maps.get("username");
		String password = (String)maps.get("password");
		
		service.log.info("username={}, password={}", username, password);
		Map<String,UserLogin> ret = new HashMap<String, UserLogin>();

		sqlSession = service.getBeanFactory().getBean(SqlSession.class);
		userMapper = sqlSession.getMapper(UserInfoMapper.class);		
		List<UserInfo> user = userMapper.selectUser(username);
		UserLogin admin = new UserLogin();
		
		if(user!=null && user.get(0).getPassword().equals(password)) {
			admin.setCode(200);
			admin.setMsg("请求成功");
			admin.setUser(user.get(0));
		}else {
			admin.setCode(500);
			admin.setMsg("账号或密码错误");
			admin.setUser(null);
		}
		ret.put("data", admin);	
		
		return ret;
	}
}
