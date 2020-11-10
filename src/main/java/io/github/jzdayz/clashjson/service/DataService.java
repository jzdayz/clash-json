package io.github.jzdayz.clashjson.service;

import io.github.jzdayz.clashjson.config.UrlData;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

@SuppressWarnings({"unchecked", "rawtypes"})
@Service
@Slf4j
@EnableConfigurationProperties(UrlData.class)
public class DataService {

    private UrlData data;

    private OkHttpClient okHttpClient;

    private RetryService retryService;

    private List<ProxyHandler> proxyHandlers;

    private List<RuleHandler> ruleHandlers;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public DataService(UrlData data, OkHttpClient okHttpClient, RetryService retryService, ObjectProvider<List<ProxyHandler>> proxyHandlers, ObjectProvider<List<RuleHandler>> ruleHandlers) {
        this.data = data;
        this.okHttpClient = okHttpClient;
        this.retryService = retryService;
        this.proxyHandlers = proxyHandlers.getIfAvailable();
        this.ruleHandlers = ruleHandlers.getIfAvailable();
    }

    public String cleanYaml(String alias) {
        String url = data.getData().get(alias);
        Objects.requireNonNull(url);
        Request request = new Request.Builder().get().url(url).build();
        return retryService.retry(() -> doGetSource(request));
    }

    private String doGetSource(Request request) throws Exception {
        try (Response response = okHttpClient.newCall(request).execute()) {
            return doCleanYaml(Objects.requireNonNull(response.body()).string());
        }
    }

    private String doCleanYaml(String yaml) {
        try {
            Yaml ya = new Yaml();
            Map map = ya.loadAs(yaml, Map.class);
            handlerYaml(map);
            return ya.dump(map);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return yaml;
        }
    }

    private void handlerYaml(Map map) {

        if (!CollectionUtils.isEmpty(ruleHandlers)) {
            ruleHandlers.forEach(e -> e.handler((ArrayList<String>) map.getOrDefault("Rule", map.get("rules"))));
        }
        if (!CollectionUtils.isEmpty(proxyHandlers)) {
            proxyHandlers.forEach(e -> e.handler((ArrayList<LinkedHashMap<String, Object>>) map.getOrDefault("Proxy", map.get("proxies")),
                    (ArrayList<LinkedHashMap<String, Object>>) map.getOrDefault("Proxy Group", map.get("proxy-groups"))));
        }

    }


}
