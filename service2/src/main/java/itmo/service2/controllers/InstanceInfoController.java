package itmo.service2.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstanceInfoController {
    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    @GetMapping("instance/id")
    public String test(){
        return instanceId;
    }
}
