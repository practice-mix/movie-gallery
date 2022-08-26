package com.example.moviegallery.webrtc.signaling.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;


@Configuration
public class SocketIOConfig implements ApplicationRunner {


    @Value("${socketio.port}")
    private Integer port;


    /**
     * The following configuration has been noted in the application.properties above
     */
    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setPort(port);
        config.setPingInterval(10000);
        config.setPingTimeout(3000);
        SocketIOServer socketIOServer = new SocketIOServer(config);

        return socketIOServer;
    }


    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        socketIOServer().start();
    }

    @PreDestroy
    public void stop() {
        socketIOServer().stop();
    }


}
