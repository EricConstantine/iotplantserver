package com.rederic.iotplant.applicationserver.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttClientUtil {
    private static MqttClient instance=null;
    private static MqttConnectOptions options;

    private MqttClientUtil(){}

    /**
     * 返回MqttClient的单例
     * @return
     */
    public static MqttClient getInstance(){
        if (instance==null){
            synchronized (MqttClientUtil.class){
                try {
                    if (instance==null){
                        instance=new MqttClient("tcp://121.199.23.184:61613",MqttClient.generateClientId(),new MemoryPersistence());
                    }
                    options=new MqttConnectOptions();
                    //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
                    options.setCleanSession(true);
                    options.setUserName("admin");
                    options.setPassword("password".toCharArray());
                    // 设置超时时间 单位为秒
                    options.setConnectionTimeout(10);
                    // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
                    options.setKeepAliveInterval(20);
                }catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }
        return instance;
    }

    /**
     * 发布消息
     * @param content 消息的文本
     * @param topic   发送的主题
     */
    public static void sendMessege(String content,String topic){
        MqttTopic mqttTopic=getInstance().getTopic(topic);
        MqttMessage message=new MqttMessage();
        message.setQos(1);
        message.setRetained(false);
        message.setPayload(content.getBytes());
        try {
            //发布MqttMessege消息
            mqttTopic.publish(message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static MqttConnectOptions getOptions() {
        return options;
    }
}