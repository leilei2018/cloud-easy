package com.fd.userservice.config;

import com.alibaba.cloud.sentinel.custom.SentinelAutoConfiguration;
import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/*@Configuration
@AutoConfigureAfter(SentinelAutoConfiguration.class)*/
public class SentinelClientConfiguration {
    @PostConstruct
    public void init(){
        initSentinelClusterClient();
        this.start();
    }
    public void initSentinelClusterClient(){
        ClusterClientAssignConfig assignConfig = new ClusterClientAssignConfig();
        assignConfig.setServerHost("localhost");
        assignConfig.setServerPort(18730);
        ClusterClientConfigManager.applyNewAssignConfig(assignConfig);

        ClusterClientConfig ccc = new ClusterClientConfig();
        ccc.setRequestTimeout(200000); //200s
        ClusterClientConfigManager.applyNewConfig(ccc);
    }

    public void start(){
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);
    }

}
