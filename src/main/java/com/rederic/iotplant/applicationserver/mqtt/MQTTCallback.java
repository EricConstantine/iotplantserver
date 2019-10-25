package com.rederic.iotplant.applicationserver.mqtt;


import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rederic.iotplant.applicationserver.common.beans.MsgData;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;


/**
 * description:
 * author: yangzihe
 * date: 2018-12-17 17:37
 **/
@Component
public class MQTTCallback implements MqttCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(MQTTCallback.class);

    Set<String> netset = Sets.newConcurrentHashSet();
    private MQTTClient myMQTTClient;

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
    		Map<String,String> map = gson.fromJson(new String(mqttMessage.getPayload()),Map.class);
    		String code = map.get("key");
    		String value = map.get("value");
            MsgData msgdata = gson.fromJson(value,MsgData.class);
            System.out.println(msgdata.getDatalist().get(0).getKey());
            System.out.println(code);
            System.out.println("收到一条消息"+s);
//    		GtCommonResult cr = gson.fromJson(new String(mqttMessage.getPayload()), GtCommonResult.class);
    		//String code = cr.getCode();
    		//上线
//    		if("online".equals(code)) {
//    			netset.add(sn);
//    		}else if("offline".equals(code)) {
//    			netset.remove(sn);
//    		}
    		//只针对在线的集合进行处理
//    		if(netset.contains(sn)) {
//    			if("rts".equals(code)){
//    				int type = cr.getType();
//    				String data = gson.toJson(cr.getData());
//    				Map map = new HashMap<>();
//    				map.put("rts", cr.getData());
//    				System.out.println("新建一个推送线程，将要推送的数据大小为"+gson.toJson(map).length());
////    				PostToServerThread t =  new PostToServerThread(gson.toJson(map));
////    				t.start();
//    			}
//    		}

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
