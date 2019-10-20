package com.rederic.iotplant.applicationserver.controller;

import com.rederic.iotplant.applicationserver.common.CommonController;
import com.rederic.iotplant.applicationserver.common.beans.CommonResult;
import com.rederic.iotplant.applicationserver.common.util.ConvertUtil;
import com.rederic.iotplant.applicationserver.entity.ModelNode;
import com.rederic.iotplant.applicationserver.entity.ModelProduct;
import com.rederic.iotplant.applicationserver.service.NodeService;
import com.rederic.iotplant.applicationserver.service.ProductService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@Api(value="产品接口",tags={"product(产品)-增删改查;导入导出"})
public class ProductController extends CommonController {
	
    @Autowired
	ProductService productService;
	@Autowired
	NodeService nodeService;

	@ApiOperation(value = "获取分页数据" ,notes = "获取分页数据" )
	@ApiImplicitParams({
		@ApiImplicitParam(name = "keywords" ,value = "搜索关键字" , required = false, dataType = "String")
	})
    @RequestMapping(value = "/pagedata", method = { RequestMethod.GET  })
    public Page<ModelProduct> pagedata(Pageable pageable, String keywords){
        return productService.findAll(pageable,new Object[]{keywords});
    }

	@ApiOperation(value = "获取单条数据对象" ,notes = "获取单条数据对象")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query",name = "productid" ,value = "产品ID" , required = true, dataType = "String")
	})
	@RequestMapping(value = "/singledata" ,method = { RequestMethod.GET })
	public ModelProduct singledata(String productid){
		return productService.findById(productid);
	}
	
	
	@ApiOperation(value = "删除产品", notes = "删除产品" )
	@ApiImplicitParams({ @ApiImplicitParam(name = "productids", value = "产品ID", required = true, dataType = "String")
	})
	@RequestMapping(value = "/delete" ,method = { RequestMethod.DELETE})
	public CommonResult delete(String productids) {
		try {
			String[] id_array = productids.split(",");
			for(String productid:id_array){
				productService.deleteById(productid);
			}
			cr = new CommonResult(true,0,null,"删除成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cr;
	}

	@ApiOperation(value = "保存产品", notes = "保存产品,id列为空则为新增,不为空则为修改")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "ModelProduct",value = "产品",required = false,dataType = "ModelProduct")
	})
	@RequestMapping(value = "/save" ,method = { RequestMethod.POST })
	public CommonResult save(ModelProduct modelproduct) {
		try {
			productService.save(modelproduct);
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
	    String[] titleNameArray = {"产品描述","产品描述","传输协议","创建者","更新时间","创建时间"};
		String[] fieldNameArray = {"name","describes","treaty","creator","updatetime","createtime"};
		try {
			//根据条件获取数据
            List<ModelProduct> data = productService.findAll(pageable,new Object[]{keywords}).getContent();
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

	@ApiOperation(value = "导入产品", notes = "导入产品")
    @RequestMapping(value = "/impexcel", method = { RequestMethod.POST })
	public CommonResult impexcel(@RequestParam("file") MultipartFile file) {
		int imp_num = 0;
		//如果文件不为空，写入上传路径
		try {
			if(!file.isEmpty()) {
				String[] fieldNameArray = {"name","describes","treaty","creator","updatetime","createtime"};
				List<Map<String,String>> list = super.getExcelContent(file, fieldNameArray);
                imp_num =  productService.saveFromList(list);
				cr = new CommonResult(true,0,null,"导入成功，导入数据："+imp_num+"条！");
			} else {
				cr = new CommonResult(false,0,null,"文件上传失败！");
			}
		} catch (Exception e) {
			cr = new CommonResult(false,0,null,"导入失败,请确认Excel内容是否正确。</br>错误信息："+super.getPointOfException(e.getMessage()));
		}
		return  cr;
	}
	@ApiOperation(value = "保存产品", notes = "保存产品,id列为空则为新增,不为空则为修改")
	@ApiImplicitParams({
//            @ApiImplicitParam(name = "device",value = "传感器",required = false,dataType = "Map")
	})
	@RequestMapping(value = "/saveProduct" ,method = { RequestMethod.POST })
	public CommonResult saveDevice(@RequestBody Map<String,Object> data) {
		try {
			ModelProduct device = new ModelProduct();
			device.setId((String) data.get("id"));
			device.setName((String) data.get("name"));
			device.setDescribes((String) data.get("describes"));
			device.setTreaty((String) data.get("treaty"));
			device.setCreator("1111");
			Timestamp d = new Timestamp(System.currentTimeMillis());
			if("".equals((String) data.get("id"))|| data.get("id") == null ){
				device.setCreatetime(d);
			}else {
				device.setUpdatetime(d);
			}
			ModelProduct savedev = productService.save(device);
			List<Map<String,Object>> list = (List<Map<String,Object>>)data.get("nodes");
			for(int i=0 ;i<list.size();i++){
				ModelNode sensor =(ModelNode) ConvertUtil.map2Obj(list.get(i),ModelNode.class);
				sensor.setPid(savedev.getId());
				nodeService.save(sensor);
			}
			cr = new CommonResult(true,0,savedev.getId(),"保存成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return   cr;
	}
}