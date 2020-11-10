package io.github.jzdayz.clashjson.service;

import cn.hutool.crypto.symmetric.DESede;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Service
public class JsonHandlerConfigService {

    @Bean
    public RuleHandler rule1() {
        return (rules) -> {
            rules.removeIf(e -> e.contains("apache.org"));
            rules.add(0,"IP-CIDR,10.0.0.0/8,DIRECT");
            rules.add(0,"IP-CIDR,10.0.0.0/16,DIRECT");
            rules.add(0,"IP-CIDR,10.0.0.0/24,DIRECT");
            rules.add(0,"IP-CIDR,10.0.0.0/32,DIRECT");
        };
    }

    @SuppressWarnings({"unchecked", "SpellCheckingInspection"})
    @Bean
    public ProxyHandler addCustomProxy(DESede deSede){
        return (proxy,proxyGroup)->{
            LinkedHashMap<String, Object> proxyTrojan = new LinkedHashMap<>();
            final String name = "v2ray";
            proxyTrojan.put("name", name);
            proxyTrojan.put("type", "vmess");
            proxyTrojan.put("server", deSede.decryptStr("dpdRvlZi5d0xM9u1H+zOng=="));
            proxyTrojan.put("port", 80);
            proxyTrojan.put("cipher", "auto");
            proxyTrojan.put("uuid", deSede.decryptStr("nMFk92L1lCHgaiUjKUTbwaHaZWQgjXn57bAmsvRBELonbPucQU7Y0w=="));
            proxyTrojan.put("alterId", "64");
            proxyTrojan.put("network", "ws");
            proxy.add(proxyTrojan);

            for (LinkedHashMap<String, Object> entry : proxyGroup) {
                if (((String) entry.get("name")).contains("手动选择")) {
                    ArrayList<String> proxies = (ArrayList<String>) entry.get("proxies");
                    proxies.add(0, name);
                }
            }
        };
    }

}
