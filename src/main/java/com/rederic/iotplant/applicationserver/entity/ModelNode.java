package com.rederic.iotplant.applicationserver.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ModelNode {

	@Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @GeneratedValue(generator = "system-uuid")	
    private String id;
    private String pid;
    private String name;
    private String skey;
    private String rwtype;
    private String datadetail;
    private String datatype;
    private String sunit;
    private String describes;

	public ModelNode() {
		super();
	}

	public ModelNode(String id,String pid,String name,String skey,String rwtype,String datadetail,String datatype,String sunit,String describes) {
		super();
		   this.id = id;
		   this.pid = pid;
		   this.name = name;
		   this.skey = skey;
		   this.rwtype = rwtype;
		   this.datadetail = datadetail;
		   this.datatype = datatype;
		   this.sunit = sunit;
		   this.describes = describes;
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

    public String getRwtype() {
        return this.rwtype;
    }

    public void setRwtype(String rwtype) {
        this.rwtype = rwtype;
    }

    public String getDatadetail() {
        return this.datadetail;
    }

    public void setDatadetail(String datadetail) {
        this.datadetail = datadetail;
    }

    public String getDatatype() {
        return this.datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getSunit() {
        return this.sunit;
    }

    public void setSunit(String sunit) {
        this.sunit = sunit;
    }

    public String getDescribes() {
        return this.describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }


}