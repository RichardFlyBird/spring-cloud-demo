package demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接入文档：https://nacos.io/en-us/docs/quick-start-spring-cloud.html
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @Value("${test_param:helloworld}")
    private String testParam;

    @RequestMapping("/get")
    public String get() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format("useLocalCache---->>>>%s", useLocalCache))
                    .append("\n")
                    .append(String.format("testParam---->>>>%s", testParam));
        return stringBuffer.toString();
    }
}
