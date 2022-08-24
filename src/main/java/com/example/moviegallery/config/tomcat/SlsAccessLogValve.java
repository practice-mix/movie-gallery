package com.example.moviegallery.config.tomcat;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.valves.AbstractAccessLogValve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.CharArrayWriter;

@Slf4j
public class SlsAccessLogValve extends AbstractAccessLogValve {
    private static final Logger accessLogger = LoggerFactory.getLogger("tomcat_accesslog");

    @Override
    protected void log(CharArrayWriter message) {
        accessLogger.info(message.toString());
    }
}
