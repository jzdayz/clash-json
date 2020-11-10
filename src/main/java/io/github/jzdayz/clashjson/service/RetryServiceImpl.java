package io.github.jzdayz.clashjson.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.ConnectException;

@Slf4j
@Service
public class RetryServiceImpl implements RetryService{
    @Override
    public <T> T retry(Action<T> action) {
        for (int j = 1; j <= 3; j++) {
            try {
                return action.action();
            } catch (ConnectException e) {
                /*ignored*/
                log.info("retry {}", j);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException();
    }
}
