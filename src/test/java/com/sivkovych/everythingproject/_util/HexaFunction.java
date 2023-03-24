package com.sivkovych.everythingproject._util;

@FunctionalInterface
public interface HexaFunction<A, B, C, D, E, F, RETURN> {
    RETURN apply(A a, B b, C c, D d, E e, F f);
}
