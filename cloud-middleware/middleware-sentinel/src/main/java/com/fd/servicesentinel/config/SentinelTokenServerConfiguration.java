package com.fd.servicesentinel.config;

import com.alibaba.cloud.sentinel.SentinelProperties;
import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Configuration
public class SentinelTokenServerConfiguration {
    @Autowired(required = false)
    SentinelProperties sentinelProperties;

    @Value("${project.name:${spring.application.name:}}")
    private String projectName;

    private String nacosAddress = "localhost:8848";

    @Value("${project.name:${spring.application.name:}}")
    private String groupId;

    private String dataId = "flow";

    private boolean appendApp = false;

    @PostConstruct
    public void init(){
       /* ServerTransportConfig serverTransportConfig = new ServerTransportConfig();
        ClusterServerConfigManager.loadGlobalTransportConfig(new ServerTransportConfig()
                .setIdleSeconds(600)
                .setPort(11111));
        if (projectName!=null){
            ClusterServerConfigManager.loadServerNamespaceSet(Collections.singleton(projectName));
        }*/


        ClusterFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<FlowRule>> ds = new NacosDataSource<List<FlowRule>>(nacosAddress, groupId,
                    appendApp? namespace + "-"+dataId :  dataId,
                    source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                    }));
            return ds.getProperty();
        });
        ClusterServerConfigManager.loadServerNamespaceSet(Collections.singleton(projectName));
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_SERVER);
    }
}
