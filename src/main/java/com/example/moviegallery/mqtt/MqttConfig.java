package com.example.moviegallery.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {
    public static final String MQTT_BROKER_URL_BASE = "tcp://socialme.hopto.org:1883";

    @Bean(destroyMethod = "disconnect")
    public MqttClient mqttClient() throws MqttException {
        MqttClient client = new MqttClient(
                MQTT_BROKER_URL_BASE, //URI
                MqttClient.generateClientId(), //ClientId
                new MemoryPersistence()); //Persistence

        client.setCallback(new MyMqttCallback());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
//                options.setUserName("username");
//                options.setPassword("password".toCharArray());
        options.setAutomaticReconnect(true);
        options.setMaxReconnectDelay(128000);

        client.connect(options);
        return client;
    }


}
