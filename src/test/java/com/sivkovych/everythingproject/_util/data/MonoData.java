package com.sivkovych.everythingproject._util.data;

import org.paukov.combinatorics3.Generator;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public record MonoData<A, EXP>(EXP expected, String text, A a) implements Data {
    public static <A, E> Stream<MonoData<A, E>> stream(Function<A, E> expected,
                                                       BiFunction<A, E, String> text,
                                                       A first) {
        return Generator.permutation(true, false)
                .withRepetitions(2)
                .stream()
                .map(list -> {
                    var aArgument = Data.get(list.get(0), first);
                    var expectedValue = expected.apply(aArgument);
                    return new MonoData<>(expectedValue, text.apply(aArgument, expectedValue), aArgument);
                });
    }
}
