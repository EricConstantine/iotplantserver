package com.rederic.iotplant.applicationserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class ModelDevice {

	@Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @GeneratedValue(generator = "system-uuid")	
    private String id;
    private String sn;
    private String name;
    private String describes;
    private String treaty;
    private String location;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createtime;
    private String userid;

	public ModelDevice() {
		super();
	}

	public ModelDevice(String id,String sn,String name,String describes,String treaty,String location,Timestamp createtime,String userid) {
		super();
		   this.id = id;
		   this.sn = sn;
		   this.name = name;
		   this.describes = describes;
		   this.treaty = treaty;
		   this.location = location;
		   this.createtime = createtime;
		   this.userid = userid;
	}

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribes() {
        return this.describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getTreaty() {
        return this.treaty;
    }

    public void setTreaty(String treaty) {
        this.treaty = treaty;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


}