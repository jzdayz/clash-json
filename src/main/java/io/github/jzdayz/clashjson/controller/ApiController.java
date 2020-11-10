package io.github.jzdayz.clashjson.controller;

import io.github.jzdayz.clashjson.service.DataService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ApiController {
    
    private DataService dataService;

    @GetMapping("get")
    public Object get(String alias){
        String body = dataService.cleanYaml(alias);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE,"text/plain;charset=utf-8");
        return new ResponseEntity<>(body,headers, HttpStatus.OK);
    }
    
}
