package com.rederic.iotplant.applicationserver.common.config;


import com.rederic.iotplant.applicationserver.mqtt.MQTTClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * description:
 * author: yangzihe
 * date: 2018-12-17 17:36
 **/
@Configuration
@PropertySource(value="classpath:mqtt.properties")
public class MqttConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(MqttConfiguration.class);

    @Value("${mqtt.host}")
    String host;
    @Value("${mqtt.username}")
    String username;
    @Value("${mqtt.password}")
    String password;
    @Value("${mqtt.clientId}")
    String clientId;
    @Value("${mqtt.timeout}")
    int timeOut;
    @Value("${mqtt.keepalive}")
    int keepAlive;
    @Value("${mqtt.defaultTopic}")
    String defaultTopic;

    @Bean//注入spring
    public MQTTClient myMQTTClient() {
        MQTTClient myMQTTClient = new MQTTClient(host, clientId, username, password, timeOut, keepAlive);
        for (int i = 0; i < 10; i++) {
            try {
                myMQTTClient.connect();
                myMQTTClient.subscribe(defaultTopic);
                System.out.println("MQTT订阅完成BBB");
                return myMQTTClient;
            } catch (MqttException e) {
            	LOGGER.error("MQTT connect exception,connect time = " + i);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                //e.printStackTrace();
            }
        }
        return myMQTTClient;
    }

}
