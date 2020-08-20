# springcloud
spring cloud 服务注册和发现练习，本项目需要依赖consul服务

## 基于consul的服务注册和发现
配置：
```yaml
spring:
  cloud:
    consul:
      enabled: true
      host: ${CONSUL_SERVER_ADDR:127.0.0.1}
      port: 8500
      discovery:
        enabled: true
        health-check-interval: 30s
        instance-id: service-producer-1
        service-name: service-producer
        health-check-path: /discovery/health
```
注意项： host配置的服务器要能访问服务部署所在的地址，因为consul agent 会定期调用health-check-path地址确定服务是否正常

## 基于consul的配置管理
配置项应该放在 consul的配置目录 /${prefix}/${defaultContext}/${dataKey}/ 目录下。
配置会在启动的时候读取一次，如果需要实时更新，则需要在注入属性的类加上注解 @RefreshScope
配置：
```yaml
spring:
  cloud:
    consul:
      config:
        data-key: configuration
        default-context: ${spring.application.name}
        format: yaml
        prefix: cloud2.config
```
注意项： 截止spring 2.3.3版本以上配置必须和spring.cloud.consul.discovery放在一个配置文件，如都放在bootstrap.yml或者都放在application.yml，否则不生效。
* 默认配置启动后不在变化，参见类 org.huang.cloud2.svr.service.impl.ConfigServiceImpl
* 实时更新配置参见: org.huang.cloud2.svr.service.impl.RefreshConfigServiceImpl
