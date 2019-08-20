package com.rederic.iotplant.applicationserver.controller;

import com.rederic.iotplant.applicationserver.entity.IotSensor;
import com.rederic.iotplant.applicationserver.service.SensorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sensor")
@Api(value="传感器接口",tags={"sensor(传感器)-增删改查;导入导出"})
public class DeviceController {
    @Autowired
    SensorService sensorService;
    @ApiOperation(value = "根据设备id获取所有传感器" ,notes = "获取传感器" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid" ,value = "设备id" , required = false, dataType = "String")
    })
    @RequestMapping(value = "/getsensorpid", method = { RequestMethod.GET  })
    public List<IotSensor> getSensorByPid(String pid){
        System.out.println(222);
        return sensorService.getSensorsByPid(pid);
    }
}
