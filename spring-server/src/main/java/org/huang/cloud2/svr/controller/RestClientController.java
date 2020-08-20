package org.huang.cloud2.svr.controller;

import org.huang.cloud2.common.ShareBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/rest")
public class RestClientController {

    @GetMapping("/string")
    public String restStr() {
        return "a string value";
    }
    @GetMapping("/json")
    public ShareBean restJson() {
        return new ShareBean("json name",21,new Date());
    }
}
