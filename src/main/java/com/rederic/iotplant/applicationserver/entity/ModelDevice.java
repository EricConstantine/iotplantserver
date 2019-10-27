package com.rederic.iotplant.applicationserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class ModelDevice {

	@Id
    //@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    //@GeneratedValue(generator = "system-uuid")
    private String id;
    private String pid;
    private String name;
    private String describes;
    private String location;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createtime;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatetime;

	public ModelDevice() {
		super();
	}

	public ModelDevice(String id,String pid,String name,String describes,String location,Timestamp createtime,Timestamp updatetime) {
		super();
		   this.id = id;
		   this.pid = pid;
		   this.name = name;
		   this.describes = describes;
		   this.location = location;
		   this.createtime = createtime;
		   this.updatetime = updatetime;
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

    public String getDescribes() {
        return this.describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
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

    public Timestamp getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }


}