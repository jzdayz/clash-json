package io.github.jzdayz.clashjson.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class OkHttpConfig {
    
    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient();
    }

}
