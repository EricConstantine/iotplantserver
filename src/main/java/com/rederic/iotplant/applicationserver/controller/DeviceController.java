package com.rederic.iotplant.applicationserver.controller;

import com.rederic.iotplant.applicationserver.common.CommonController;
import com.rederic.iotplant.applicationserver.common.beans.CommonResult;
import com.rederic.iotplant.applicationserver.entity.ModelDevice;
import com.rederic.iotplant.applicationserver.service.DeviceService;
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
@RequestMapping("/device")
@Api(value="设备接口",tags={"device(设备)-增删改查;导入导出"})
public class DeviceController extends CommonController {
	
    @Autowired
	DeviceService deviceService;

	@ApiOperation(value = "获取分页数据" ,notes = "获取分页数据" )
	@ApiImplicitParams({
		@ApiImplicitParam(name = "keywords" ,value = "搜索关键字" , required = false, dataType = "String")
	})
    @RequestMapping(value = "/pagedata", method = { RequestMethod.GET  })
    public Page<ModelDevice> pagedata(Pageable pageable, String keywords){
        return deviceService.findAll(pageable,new Object[]{keywords});
    }

	@ApiOperation(value = "获取单条数据对象" ,notes = "获取单条数据对象")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query",name = "deviceid" ,value = "设备ID" , required = true, dataType = "String")
	})
	@RequestMapping(value = "/singledata" ,method = { RequestMethod.GET })
	public ModelDevice singledata(String deviceid){
		return deviceService.findById(deviceid);
	}
	
	
	@ApiOperation(value = "删除设备", notes = "删除设备" )
	@ApiImplicitParams({ @ApiImplicitParam(name = "deviceids", value = "设备ID", required = true, dataType = "String")
	})
	@RequestMapping(value = "/delete" ,method = { RequestMethod.DELETE})
	public CommonResult delete(String deviceids) {
		try {
			String[] id_array = deviceids.split(",");
			for(String deviceid:id_array){
				deviceService.deleteById(deviceid);
			}
			cr = new CommonResult(true,0,null,"删除成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cr;
	}

	@ApiOperation(value = "保存设备", notes = "保存设备,id列为空则为新增,不为空则为修改")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ModelDevice",value = "设备",required = false,dataType = "ModelDevice")
	})
	@RequestMapping(value = "/save" ,method = { RequestMethod.POST })
	public CommonResult save(ModelDevice modeldevice) {
		try {
			deviceService.save(modeldevice);
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
	    String[] titleNameArray = {"产品ID","设备名称","设备描述","设备位置","创建时间","更改时间"};
		String[] fieldNameArray = {"pid","name","describes","location","creatime","updatetime"};
		try {
			//根据条件获取数据
            List<ModelDevice> data = deviceService.findAll(pageable,new Object[]{keywords}).getContent();
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

	@ApiOperation(value = "导入设备", notes = "导入设备")
    @RequestMapping(value = "/impexcel", method = { RequestMethod.POST })
	public CommonResult impexcel(@RequestParam("file") MultipartFile file) {
		int imp_num = 0;
		//如果文件不为空，写入上传路径
		try {
			if(!file.isEmpty()) {
				String[] fieldNameArray = {"pid","name","describes","location","creatime","updatetime"};
				List<Map<String,String>> list = super.getExcelContent(file, fieldNameArray);
                imp_num =  deviceService.saveFromList(list);
				cr = new CommonResult(true,0,null,"导入成功，导入数据："+imp_num+"条！");
			} else {
				cr = new CommonResult(false,0,null,"文件上传失败！");
			}
		} catch (Exception e) {
			cr = new CommonResult(false,0,null,"导入失败,请确认Excel内容是否正确。</br>错误信息："+super.getPointOfException(e.getMessage()));
		}
		return  cr;
	}
	@ApiOperation(value = "根据产品id获取所有设备数据" ,notes = "获取设备" )
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pid" ,value = "产品id" , required = true, dataType = "String")
	})
	@RequestMapping(value = "/getDeviceByPid")
	public CommonResult getSensorByPid(String pid){
		System.out.println(pid);
		List<Map<String,Object>> devices = deviceService.getDeviceByPid(pid);
		CommonResult cr = new CommonResult();
		cr.setOk(true);
		cr.setData(devices);
		return cr;
	}
}