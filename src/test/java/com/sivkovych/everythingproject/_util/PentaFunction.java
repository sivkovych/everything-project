package com.sivkovych.everythingproject._util;

@FunctionalInterface
public interface PentaFunction<A, B, C, D, E, RETURN> {
    RETURN apply(A a, B b, C c, D d, E e);
}
