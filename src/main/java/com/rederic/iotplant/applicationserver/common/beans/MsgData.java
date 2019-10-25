package com.rederic.iotplant.applicationserver.common.beans;

import java.util.List;

public class MsgData {
    private List<MsgDetail> datalist;

    public List<MsgDetail> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<MsgDetail> datalist) {
        this.datalist = datalist;
    }

    public MsgData(List<MsgDetail> datalist) {
        this.datalist = datalist;
    }
}
