package com.iwill.consul.controller;

import com.iwill.consul.service.ConsulService;
import com.orbitz.consul.model.health.ServiceHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consul")
public class ConsulController {

    @Autowired
    private ConsulService consulService;

    /*******************************服务注册与发现*******************************/
    @RequestMapping(value = "/registerService/{servicename}/{serviceid}", method = RequestMethod.POST)
    public void registerService(@PathVariable("servicename") String serviceName,
                                @PathVariable("serviceid") String serviceId) {
        consulService.registerService(serviceName, serviceId);
    }

    @RequestMapping(value = "/discoverService/{servicename}", method = RequestMethod.GET)
    public List<ServiceHealth> discoverService(@PathVariable("servicename") String serviceName) {
        return consulService.findHealthyService(serviceName);
    }

    /*******************************KV*******************************/
    @RequestMapping(value = "/kv/{key}/{value}", method = RequestMethod.POST)
    public void storeKV(@PathVariable("key") String key,
                        @PathVariable("value") String value) {
        consulService.storeKV(key, value);
    }

    @RequestMapping(value = "/kv/{key}", method = RequestMethod.GET)
    public String getKV(@PathVariable("key") String key) {
        return consulService.getKV(key);
    }

    /*******************************server*******************************/
    @RequestMapping(value = "/raftpeers", method = RequestMethod.GET)
    public List<String> findRaftPeers() {
        return consulService.findRaftPeers();
    }

    @RequestMapping(value = "/leader", method = RequestMethod.GET)
    public String leader() {
        return consulService.findRaftLeader();
    }
}
