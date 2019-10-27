package com.rederic.iotplant.applicationserver.mqtt;


import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rederic.iotplant.applicationserver.common.beans.MsgDetail;
import com.rederic.iotplant.applicationserver.common.util.CommonData;
import com.rederic.iotplant.applicationserver.common.util.SpringUtil;
import com.rederic.iotplant.applicationserver.entity.ModelDevice;
import com.rederic.iotplant.applicationserver.service.DeviceService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;


/**
 * description:
 * author: yangzihe
 * date: 2018-12-17 17:37
 **/
@Component
public class MQTTCallback implements MqttCallback {

    public MQTTCallback() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MQTTCallback.class);
    //当前在线的数据集合
    private Set<String> deviceSet = Sets.newConcurrentHashSet();
    private MQTTClient myMQTTClient;
    private DeviceService deviceService = (DeviceService)SpringUtil.getBean("deviceServiceImpl");

    public Set<String> getDeviceSet() {
        return deviceSet;
    }

    public MQTTCallback(MQTTClient myMQTTClient) {
        this.myMQTTClient = myMQTTClient;
    }

    /**
     * 丢失连接，可在这里做重连
     * 只会调用一次
     *
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        LOGGER.error("连接断开，下面做重连...");
        long reconnectTimes = 1;
        while (true) {
            try {
                if (MQTTClient.getClient().isConnected()) {
                    LOGGER.warn("mqtt reconnect success end");
                    return;
                }
                LOGGER.warn("mqtt reconnect times = {} try again...", reconnectTimes++);
                MQTTClient.getClient().reconnect();
            } catch (MqttException e) {
                LOGGER.error("", e);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 消息到达后
     * subscribe后，执行的回调函数
     *
     * @param s
     * @param mqttMessage
     * @throws Exception
     */
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        // subscribe后得到的消息会执行到这里面
    	//过滤bindcode和黑名单尚未加入
    	try {
    		String sn = s.substring(s.lastIndexOf("/")+1, s.length());
    		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            Type type = new TypeToken<List<MsgDetail>>(){}.getType();
            List<MsgDetail> msgList = new Gson().fromJson(new String(mqttMessage.getPayload()),type);
            for(int i=0;i<msgList.size();i++){
                System.out.println(msgList.get(i).getKey());
                String code = msgList.get(i).getKey();
                String value = msgList.get(i).getValue();
                if((CommonData.online).equals(code)){
                    Timestamp time = new Timestamp(System.currentTimeMillis());
                    ModelDevice device = new ModelDevice();
                    deviceSet.add(sn);
                    try {//设备已经存在
                        device = deviceService.findById(sn);
                        device.setUpdatetime(time);
                        //myMQTTClient.publish("webgate/onlineOne",gson.toJson(device));
                    }catch (Exception e){//设备不存在则新建设备
                        device.setId(sn);
                        device.setPid(value);
                        device.setCreatetime(time);;
                        //myMQTTClient.publish("webgate/onlineAll",gson.toJson(deviceSet));
                    }
                    deviceService.save(device);
                }else if((CommonData.offline).equals(code)){
                    deviceSet.remove(sn);
                    //myMQTTClient.publish("webgate/offlineOne",sn);
                }
            }

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        LOGGER.error("接收消息主题 : {}，接收消息内容 : {}", s, new String(mqttMessage.getPayload()));
    }

    /**
     * publish后，配送完成后回调的方法
     *
     * @param iMqttDeliveryToken
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        LOGGER.error("==========deliveryComplete={}==========", iMqttDeliveryToken.isComplete());
    }
}
