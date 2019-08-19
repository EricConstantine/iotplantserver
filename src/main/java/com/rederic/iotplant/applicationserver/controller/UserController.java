package com.rederic.iotplant.applicationserver.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
@Api(value="用户接口",tags={"user(用户)-增删改查;导入导出"})
public class UserController {
	@ApiOperation(value = "测试接口" ,notes = "测试接口" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "keywords" ,value = "搜索关键字" , required = false, dataType = "String"),
	})
	@RequestMapping(value = "/test", method = { RequestMethod.GET  })
	public String test(String keywords){
		return keywords;
	}

}