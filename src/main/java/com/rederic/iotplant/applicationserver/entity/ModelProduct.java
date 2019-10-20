package com.rederic.iotplant.applicationserver.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class ModelProduct {

	@Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @GeneratedValue(generator = "system-uuid")	
    private String id;
    private String name;
    private String describes;
    private String treaty;
    private String creator;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatetime;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createtime;

	public ModelProduct() {
		super();
	}

	public ModelProduct(String id,String name,String describes,String treaty,String creator,Timestamp updatetime,Timestamp createtime) {
		super();
		   this.id = id;
		   this.name = name;
		   this.describes = describes;
		   this.treaty = treaty;
		   this.creator = creator;
		   this.updatetime = updatetime;
		   this.createtime = createtime;
	}

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    public Timestamp getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }


}