package com.rederic.iotplant.applicationserver.controller;

import com.rederic.iotplant.applicationserver.common.CommonController;
import com.rederic.iotplant.applicationserver.common.beans.CommonResult;
import com.rederic.iotplant.applicationserver.entity.ModelSensor;
import com.rederic.iotplant.applicationserver.service.SensorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sensor")
@Api(value="传感器接口",tags={"sensor(传感器)-增删改查;导入导出"})
public class SensorController extends CommonController {
	
    @Autowired
	SensorService sensorService;

	@ApiOperation(value = "获取分页数据" ,notes = "获取分页数据" )
	@ApiImplicitParams({
		@ApiImplicitParam(name = "keywords" ,value = "搜索关键字" , required = false, dataType = "String")
	})
    @RequestMapping(value = "/pagedata", method = { RequestMethod.GET  })
    public Page<ModelSensor> pagedata(Pageable pageable, String keywords){
        return sensorService.findAll(pageable,new Object[]{keywords});
    }

	@ApiOperation(value = "获取单条数据对象" ,notes = "获取单条数据对象")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query",name = "sensorid" ,value = "传感器ID" , required = true, dataType = "String")
	})
	@RequestMapping(value = "/singledata" ,method = { RequestMethod.GET })
	public ModelSensor singledata(String sensorid){
		return sensorService.findById(sensorid);
	}
	
	
	@ApiOperation(value = "删除传感器", notes = "删除传感器" )
	@ApiImplicitParams({ @ApiImplicitParam(name = "sensorids", value = "传感器ID", required = true, dataType = "String")
	})
	@RequestMapping(value = "/delete" ,method = { RequestMethod.DELETE})
	public CommonResult delete(String sensorids) {
		try {
			String[] id_array = sensorids.split(",");
			for(String sensorid:id_array){
				sensorService.deleteById(sensorid);
			}
			cr = new CommonResult(true,0,null,"删除成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cr;
	}

	@ApiOperation(value = "保存传感器", notes = "保存传感器,id列为空则为新增,不为空则为修改")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ModelSensor",value = "传感器",required = false,dataType = "ModelSensor")
	})
	@RequestMapping(value = "/save" ,method = { RequestMethod.POST })
	public CommonResult save(ModelSensor modelsensor) {
		try {
			sensorService.save(modelsensor);
			cr = new CommonResult(true,0,null,"保存成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return   cr;
	}

	@ApiOperation(value = "导出数据", notes = "导出数据")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "keywords" ,value = "搜索关键字" , required = false, dataType = "String")
	})
	@RequestMapping(value = "/expexcel", method = { RequestMethod.GET  })
	public ResponseEntity<byte[]> expexcel( Pageable pageable,String keywords) {
		ResponseEntity<byte[]> entity = null;
	    String[] titleNameArray = {"设备id","名称","kEY","类型","数值","单位","排序","图片"};
		String[] fieldNameArray = {"pid","name","skey","stype","svalue","sunit","sort","img"};
		try {
			//根据条件获取数据
            List<ModelSensor> data = sensorService.findAll(pageable,new Object[]{keywords}).getContent();
			//数据转换成流并导出
			InputStream is = super.exportExcelContent(data,titleNameArray,fieldNameArray);
			byte[] body = new byte[is.available()];
			is.read(body);
			HttpHeaders headers = new HttpHeaders();
			String exportFilename = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()) + ".xls";
			headers.add("Content-Disposition", "attchement;filename=" + exportFilename);
			HttpStatus statusCode = HttpStatus.OK;
			entity = new ResponseEntity<byte[]>(body, headers, statusCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	@ApiOperation(value = "导入传感器", notes = "导入传感器")
    @RequestMapping(value = "/impexcel", method = { RequestMethod.POST })
	public CommonResult impexcel(@RequestParam("file") MultipartFile file) {
		int imp_num = 0;
		//如果文件不为空，写入上传路径
		try {
			if(!file.isEmpty()) {
				String[] fieldNameArray = {"pid","name","skey","stype","svalue","sunit","sort","img"};
				List<Map<String,String>> list = super.getExcelContent(file, fieldNameArray);
                imp_num =  sensorService.saveFromList(list);
				cr = new CommonResult(true,0,null,"导入成功，导入数据："+imp_num+"条！");
			} else {
				cr = new CommonResult(false,0,null,"文件上传失败！");
			}
		} catch (Exception e) {
			cr = new CommonResult(false,0,null,"导入失败,请确认Excel内容是否正确。</br>错误信息："+super.getPointOfException(e.getMessage()));
		}
		return  cr;
	}
	@ApiOperation(value = "根据设备id获取所有传感器" ,notes = "获取传感器" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pid" ,value = "设备id" , required = false, dataType = "String")
	})
	@RequestMapping(value = "/getsensors", method = { RequestMethod.POST  })
	public CommonResult getSensorByPid(String pid){
		System.out.println(222);
		System.out.println(pid);
		List<ModelSensor> sensors = sensorService.getSensorsByPid(pid);
		CommonResult cr = new CommonResult();
		cr.setOk(true);
		cr.setData(sensors);
		return cr;
	}
}