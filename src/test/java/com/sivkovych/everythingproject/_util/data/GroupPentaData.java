package com.sivkovych.everythingproject._util.data;

import com.sivkovych.everythingproject._util.HexaFunction;
import com.sivkovych.everythingproject._util.PentaFunction;

import java.util.stream.Stream;

public record GroupPentaData<A, B, C, D, E, G, EXP>(EXP expected, String text, G group, A a, B b, C c, D d, E e)
        implements Data {
    public static <A, B, C, D, E, G, EXP> Stream<MonoData<G, EXP>> stream(PentaFunction<A, B, C, D, E, EXP> expected,
                                                                          HexaFunction<A, B, C, D, E, EXP, String> text,
                                                                          PentaFunction<A, B, C, D, E, G> group,
                                                                          A first,
                                                                          B second,
                                                                          C third,
                                                                          D forth,
                                                                          E fifth) {
        return PentaData.stream(expected, text, first, second, third, forth, fifth)
                .map(data -> {
                    var value = expected.apply(data.a(), data.b(), data.c(), data.d(), data.e());
                    return new MonoData<>(value, text.apply(data.a(), data.b(), data.c(), data.d(), data.e(), value),
                                          group.apply(data.a(), data.b(), data.c(), data.d(), data.e()));
                });
    }
}
