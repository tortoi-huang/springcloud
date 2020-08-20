package org.huang.cloud2.svr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/discovery")
public class DiscoveryController {

    /**
     * consul agent会调用这个地址检查健康状态
     * @return 只检查 response code， 不检查返回值
     */
    @GetMapping("/health")
    public String hello() {
        System.out.println("called hello");
        return "ok";
    }
}
