package com.rederic.iotplant.applicationserver.controller;

import com.rederic.iotplant.applicationserver.entity.IotDevice;
import com.rederic.iotplant.applicationserver.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deivce")
@Api(value="设备接口",tags={"device(设备)-增删改查;导入导出"})
public class DeviceController {
    @Autowired
    DeviceService deviceService;
    @ApiOperation(value = "获取设备分页数据" ,notes = "获取分页数据" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keywords" ,value = "搜索关键字" , required = false, dataType = "String")
    })
    @RequestMapping(value = "/pagedata", method = { RequestMethod.GET  })
    public Page<IotDevice> pagedata(Pageable pageable, String keywords){
        System.out.println(111);
        return deviceService.findAll(pageable,new Object[]{keywords});
    }
}
