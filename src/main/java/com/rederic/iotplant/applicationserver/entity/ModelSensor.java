package com.rederic.iotplant.applicationserver.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ModelSensor {

	@Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @GeneratedValue(generator = "system-uuid")	
    private String id;
    private String pid;
    private String name;
    private String skey;
    private String stype;
    private String svalue;
    private String sunit;
    private Integer sort;
    private String img;

	public ModelSensor() {
		super();
	}

	public ModelSensor(String id,String pid,String name,String skey,String stype,String svalue,String sunit,Integer sort,String img) {
		super();
		   this.id = id;
		   this.pid = pid;
		   this.name = name;
		   this.skey = skey;
		   this.stype = stype;
		   this.svalue = svalue;
		   this.sunit = sunit;
		   this.sort = sort;
		   this.img = img;
	}

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkey() {
        return this.skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public String getStype() {
        return this.stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getSvalue() {
        return this.svalue;
    }

    public void setSvalue(String svalue) {
        this.svalue = svalue;
    }

    public String getSunit() {
        return this.sunit;
    }

    public void setSunit(String sunit) {
        this.sunit = sunit;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}