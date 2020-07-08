1：利用nacos作为配置中心，有时候local环境，有些配置需要已本地配置，作为优先。
例如一些上传下载路径，需要配置成window路径，解决办法，在nacos配置属性中添加如下即可：
<br>spring:
  cloud:
    config:
      override-none: true


2:java-api官网地址<br>
https://www.apiref.com/java11-zh/java.base/java/security/KeyStore.html


3:springboot文档<br>
https://www.apiref.com/java11-zh/java.base/java/security/KeyStore.html


4:springcloud的feign.sentinel.enable=true
默认资源名称是<br>
${HttpMethod}:http://${feignName}${requestMapping}<br>
POST:http://user-service/api/iousUntion/passwordCheck

5:redis分析key内存大小<br>
https://blog.csdn.net/qmhball/article/details/86063466?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase

6.intellij添加ignore
https://www.cnblogs.com/suizhikuo/p/9804864.html