package com.rederic.iotplant.applicationserver.common.beans;

/**
 * Created by Libre on 2017/10/8.
 */
public class GoodModel {

    private String title;       // 商品标题

    private String pictUrl;     // 图片地址

    private String qrcode;      // 二维码图片地址

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictUrl() {
        return pictUrl;
    }

    public void setPictUrl(String pictUrl) {
        this.pictUrl = pictUrl;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

}
