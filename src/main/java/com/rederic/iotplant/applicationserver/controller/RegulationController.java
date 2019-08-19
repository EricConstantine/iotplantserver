package com.rederic.iotplant.applicationserver.controller;

import com.rederic.iotplant.applicationserver.common.beans.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;

@RestController
@Api(value="规则服务",tags={"规则服务"})
public class RegulationController {

    @ApiOperation(value = "" ,notes = "" )
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "keywords" ,value = "搜索关键字" , required = false, dataType = "String")
    })
    @RequestMapping(value = "/login", method = { RequestMethod.GET,RequestMethod.POST  })
    public String login(){
        return "";
    }

    @ApiOperation(value = "" ,notes = "" )
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "keywords" ,value = "搜索关键字" , required = false, dataType = "String")
    })
    @RequestMapping(value = "/get_info", method = { RequestMethod.GET,RequestMethod.POST  })
    public UserInfo get_info(){
        return new UserInfo("head.jpg","name","userid","access");
    }

    @ApiOperation(value = "" ,notes = "" )
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "keywords" ,value = "搜索关键字" , required = false, dataType = "String")
    })
    @RequestMapping(value = "/logout", method = { RequestMethod.GET,RequestMethod.POST  })
    public String logout(){
        return "";
    }

    @RequestMapping(value = "/test", method = { RequestMethod.GET,RequestMethod.POST  })
    public String test(String a){
        return a;
    }



}