package io.github.jzdayz.clashjson.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("clash")
@Data
public class UrlData {
    private Map<String,String> data;
}
