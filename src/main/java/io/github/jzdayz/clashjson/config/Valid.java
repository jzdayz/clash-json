package io.github.jzdayz.clashjson.config;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DESede;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Configuration
@AllArgsConstructor
@Slf4j
public class Valid {

    private Environment environment;

    private ConfigurableApplicationContext context;

    private UrlData urlData;

    @Bean
    public DESede check() {
        String key = environment.getProperty("key");
        if (StringUtils.isEmpty(key)) {
            log.error("需要一个27位的密码[重复3次]");
            System.exit(1);
        }
        DESede desede = SecureUtil.desede(key.getBytes(StandardCharsets.UTF_8));
        Map<String, String> rr = new HashMap<>();
        urlData.getData().forEach((k, v) -> {
            String kk = desede.decryptStr(k);
            log.info("{}=>{}",k,kk);
            String vv = desede.decryptStr(v);
            log.info("{}=>{}",v,vv);
            rr.put(kk, vv);
        });
        urlData.setData(rr);
        return desede;
    }

}
