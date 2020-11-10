package io.github.jzdayz.clashjson.service;

import java.util.LinkedHashMap;
import java.util.List;

public interface ProxyHandler {

    void handler(List<LinkedHashMap<String, Object>> proxy, List<LinkedHashMap<String, Object>> proxyGroup);

}
