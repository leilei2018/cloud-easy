A服务-》实例
  /haha

B服务-》实例
C服务-》实例

pc/app访问-》

1:比如线上部署，每一个服务对应一个域名

A： www.aa.com
B： www.bb.com

正常访问： www.aa.com/haha  =》 A服务 =》负载X实例

正常访问： www.bb.com/haha  =》 B服务 =》负载X实例

#没有网关gateway时候
1：为每一个服务添加一个域名   =》 启动一个独立网关服务， 只需要一个域名即可
  - Path=/api/serviceA/
  uri: lb://serviceA
  - StripPrefix=2

  - Path=/api/serviceA/
  uri: lb://serviceB
  - StripPrefix=2
这样就可以添加一个网关域名如：www.gy.com
www.gy.com/api/serviceA/haha  => 就会等于访问  =》lb://serviceA/haha =>最后通过负载均衡到具体的实例：  192.168.21.*:port/haha

##高可用,读写能力，并发能力更强
比如三台网关服务www.gy1.com, www.gy2.com, www.gy3.com


最前面用nginx路由,最外层网关www.gy.com
proxy_stream:
    www.gy1.com;
    www.gy2.com;
    www.gy3.com;


2：每一个服务都需要鉴权（同样的代码，在所有服务都要写一遍） =》 公共鉴权提取出来，放到网关来做

3：首先dubbo通信是tcp层面，所以消费端必须要有一个http服务对外提供，然后调用消费端=》服务的过程

而springcloud是http层面，可以直接根据全局路由，进行访问

4:dubbo消费端内嵌负载均衡
springcloud的都是组件式，可以选择不同的负载均衡器（ribbon,springcloud-loadbalancer)

5:对于熔断，dubbo有一个内嵌的mock机制，可以用来请求超时熔断和强制降级等；

springcloud可以选择基于hystrix和sentinel的组件来完成
sentinel功能更好，还有限流规则，鉴权规则，系统规则等

hystrix也是基于超时时间熔断
sentinel拥有rt，异常数，异常比例来进行熔断

