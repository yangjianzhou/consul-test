package com.iwill.consul.service;

import com.orbitz.consul.*;
import com.orbitz.consul.model.health.ServiceHealth;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

@Service
public class ConsulService {

    public void registerService(String serviceName, String serviceId) {
        Consul consul = Consul.newClient("127.0.0.1",8500);          //建立consul实例
        AgentClient agentClient = consul.agentClient();        //建立AgentClient

        try {
            agentClient.register(8080, URI.create("http://localhost:8080/health").toURL(), 3, serviceName, serviceId, "dev");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public List<ServiceHealth> findHealthyService(String serviceName) {
        Consul consul = Consul.newClient("127.0.0.1",8500);
        HealthClient healthClient = consul.healthClient();
        return healthClient.getHealthyServiceInstances(serviceName).getResponse();//寻找passing状态的节点
    }

    public void storeKV(String key, String value) {
        Consul consul = Consul.newClient("127.0.0.1",8500);
        KeyValueClient kvClient = consul.keyValueClient();
        kvClient.putValue(key, value);//存储KV
    }

    public String getKV(String key) {
        Consul consul = Consul.newClient("127.0.0.1",8500);
        KeyValueClient kvClient = consul.keyValueClient();
        return kvClient.getValueAsString(key).get();
    }

    public List<String> findRaftPeers() {
        Consul consul = Consul.newClient("127.0.0.1",8500);
        StatusClient statusClient = consul.statusClient();
        return statusClient.getPeers();
    }

    public String findRaftLeader() {
        Consul consul = Consul.newClient("127.0.0.1",8500);
        StatusClient statusClient = consul.statusClient();
        return statusClient.getLeader();
    }
}
