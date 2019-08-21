package com.rederic.iotplant.applicationserver.controller;

import com.rederic.iotplant.applicationserver.common.beans.CommonResult;
import com.rederic.iotplant.applicationserver.service.SensorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensor")
@Api(value="传感器接口",tags={"sensor(传感器)-增删改查;导入导出"})
public class SensorController extends CommonResult {
    @Autowired
    SensorService sensorService;

    @ApiOperation(value = "根据设备ID获取该设备的传感器" ,notes = "获取设备传感器" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid" ,value = "pid" , required = true, dataType = "String")
    })
    @RequestMapping(value = "/findbypid", method = { RequestMethod.POST  })
    public CommonResult findByPid(String pid){
        System.out.println(111);
        CommonResult cr = new CommonResult();
        cr.setOk(true);
        cr.setData(sensorService.getSensorsByPid(pid));
        return cr;
    }


}
