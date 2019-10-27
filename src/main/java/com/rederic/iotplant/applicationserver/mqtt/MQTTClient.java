package com.rederic.iotplant.applicationserver.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description:
 * author: yangzihe
 * date: 2018-12-17 17:33
 **/
public class MQTTClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(MQTTClient.class);

    private static MqttClient client;

    public static MqttClient getClient() {
        return client;
    }

    public static void setClient(MqttClient client) {
        MQTTClient.client = client;
    }

    private String host;
    private String username;
    private String password;
    private String clientId;
    private int timeout;
    private int keepalive;

    public MQTTClient(String host, String username, String password, String clientId, int timeOut, int keepAlive) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.clientId = clientId;
        this.timeout = timeOut;
        this.keepalive = keepAlive;
    }

    /**
     * 设置mqtt连接参数
     *
     * @param username
     * @param password
     * @param timeout
     * @param keepalive
     * @return
     */
    public MqttConnectOptions setMqttConnectOptions(String username, String password, int timeout, int keepalive) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(timeout);
        options.setKeepAliveInterval(keepalive);
        options.setCleanSession(false);
        return options;
    }

    /**
     * 连接mqtt服务端，得到MqttClient连接对象
     */
    public void connect() throws MqttException {
        if (client == null) {
            client = new MqttClient(host, clientId, new MemoryPersistence());
            client.setCallback(new MQTTCallback(MQTTClient.this));
        }
        MqttConnectOptions mqttConnectOptions = setMqttConnectOptions(username, password, timeout, keepalive);
        if (!client.isConnected()) {
            client.connect(mqttConnectOptions);
        } else {
            client.disconnect();
            client.connect(mqttConnectOptions);
        }
        LOGGER.info("MQTT connect success");//未发生异常，则连接成功
    }

    /**
     * 发布，默认qos为0，非持久化
     *
     * @param pushMessage
     * @param topic
     */
    public void publish(String pushMessage, String topic) {
        publish(pushMessage, topic, 0, false);
    }

    /**
     * 发布消息
     *
     * @param pushMessage
     * @param topic
     * @param qos
     * @param retained:留存
     */
    public void publish(String pushMessage, String topic, int qos, boolean retained) {
        MqttMessage message = new MqttMessage();
        message.setPayload(pushMessage.getBytes());
        message.setQos(qos);
        message.setRetained(retained);
        MqttTopic mqttTopic = MQTTClient.getClient().getTopic(topic);
        if (null == mqttTopic) {
            LOGGER.error("topic is not exist");
        }
        MqttDeliveryToken token;//Delivery:配送
        synchronized (this) {//注意：这里一定要同步，否则，在多线程publish的情况下，线程会发生死锁，分析见文章最后补充
        try {
            token = mqttTopic.publish(message);//也是发送到执行队列中，等待执行线程执行，将消息发送到消息中间件
            token.waitForCompletion(2000L);
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        }
    }

    /**
     * 订阅某个主题，qos默认为0
     *
     * @param topic
     */
    public void subscribe(String topic) {
        subscribe(topic, 0);
    }

    /**
     * 订阅某个主题
     *
     * @param topic
     * @param qos
     */
    public void subscribe(String topic, int qos) {
        try {
            MQTTClient.getClient().subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
