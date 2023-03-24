package com.sivkovych.everythingproject._util;

@FunctionalInterface
public interface TetraFunction<A, B, C, D, RETURN> {
    RETURN apply(A a, B b, C c, D d);
}
