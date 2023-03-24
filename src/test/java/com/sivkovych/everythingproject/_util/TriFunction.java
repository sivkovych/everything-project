package com.sivkovych.everythingproject._util;

@FunctionalInterface
public interface TriFunction<A, B, C, RETURN> {
    RETURN apply(A a, B b, C c);
}
