package demo;

import static demo.constant.constant.PROVIDER_SERVICE_NAME;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import demo.client.EchoServiceClient;

/**
 * 接入文档: https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-discovery-example/readme-zh.md
 *
 * 服务发现+客户端负载均衡Demo
 *   其中演示两种负载均衡的方式：
 *      1. 添加 @LoadBlanced 注解，使得 RestTemplate 接入 Ribbon
 *      2. 使用FeignClient, 已经默认集成了 Ribbon，此处演示如何配置一个 FeignClient。
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class NacosConsumerApplication {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerApplication.class, args);
    }

    @RestController
    class TestController {

        // 1.使用添加 @LoadBlanced 注解的RestTemplate，使得 RestTemplate 接入 Ribbon
        private final RestTemplate restTemplate;

        // 2.使用FeignClient, 已经默认集成了 Ribbon，此处演示如何配置一个 FeignClient。
        @Autowired
        private EchoServiceClient echoServiceClient;

        @Autowired
        public TestController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

        @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
        public String echo(@PathVariable String str) {
            String url = String.format("%s%s%s%s", "http://", PROVIDER_SERVICE_NAME, "/echo/", str);
            return restTemplate.getForObject(url, String.class);
        }

        @GetMapping(value = "/echo-feign/{str}")
        public String feign(@PathVariable String str) {
            return echoServiceClient.echo(str);
        }
    }
}