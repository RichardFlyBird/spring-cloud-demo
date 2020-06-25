package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import demo.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class EchoController {
    private final static String PORT = "local.server.port";

    @Autowired
    private Environment environment;

    @Autowired
    private ServerConfig serverConfig;

    @GetMapping(value = "/echo/{string}")
    public String echo(@PathVariable String string) {
        log.info(String.format("%s:%s", "第一种方式获取服务端口", environment.getProperty(PORT)));
        log.info(String.format("%s:%s", "第二种方式获取服务端口", serverConfig.getPort()));
        return string;
    }
}