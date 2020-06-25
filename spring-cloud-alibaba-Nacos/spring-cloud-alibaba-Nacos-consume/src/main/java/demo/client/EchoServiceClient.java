package demo.client;

import static demo.constant.constant.PROVIDER_SERVICE_NAME;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = PROVIDER_SERVICE_NAME)
public interface EchoServiceClient {
    @GetMapping(value = "/echo/{str}")
    String echo(@PathVariable("str") String str);
}