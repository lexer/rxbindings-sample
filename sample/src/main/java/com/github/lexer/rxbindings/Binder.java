package com.github.lexer.rxbindings;

/**
* Created by zakharov on 2/16/14.
*/
public interface Binder<T> {
    void onNext(T value);
}
