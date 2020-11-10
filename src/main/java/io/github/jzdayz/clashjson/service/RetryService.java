package io.github.jzdayz.clashjson.service;

public interface RetryService {

    interface Action<T> {

        T action() throws Exception;
    }

    <T> T retry(Action<T> action);

}
