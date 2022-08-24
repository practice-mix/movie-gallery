package com.example.moviegallery.config.tomcat;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class TomcatWebServerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    private final ServerProperties serverProperties;

    public TomcatWebServerCustomizer(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        customizeAccessLog(factory);
    }

    private void customizeAccessLog(ConfigurableTomcatWebServerFactory factory) {
        ServerProperties.Tomcat tomcatProperties = this.serverProperties.getTomcat();
        SlsAccessLogValve valve = new SlsAccessLogValve();
        PropertyMapper map = PropertyMapper.get();
        ServerProperties.Tomcat.Accesslog accessLogConfig = tomcatProperties.getAccesslog();
        map.from(accessLogConfig.getConditionIf()).to(valve::setConditionIf);
        map.from(accessLogConfig.getConditionUnless()).to(valve::setConditionUnless);
        map.from(accessLogConfig.getPattern()).to(valve::setPattern);
        map.from(accessLogConfig.getLocale()).whenHasText().to(valve::setLocale);
        map.from(accessLogConfig.isIpv6Canonical()).to(valve::setIpv6Canonical);
        map.from(accessLogConfig.isRequestAttributesEnabled()).to(valve::setRequestAttributesEnabled);
        factory.addEngineValves(valve);
    }
}
