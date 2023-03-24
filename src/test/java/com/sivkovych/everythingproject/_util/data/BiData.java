package com.sivkovych.everythingproject._util.data;

import com.sivkovych.everythingproject._util.TriFunction;
import org.paukov.combinatorics3.Generator;

import java.util.function.BiFunction;
import java.util.stream.Stream;

public record BiData<A, B, EXP>(EXP expected, String text, A a, B b) implements Data {
    public static <A, B, E> Stream<BiData<A, B, E>> stream(BiFunction<A, B, E> expected,
                                                           TriFunction<A, B, E, String> text,
                                                           A first,
                                                           B second) {
        return Generator.permutation(true, false)
                .withRepetitions(2)
                .stream()
                .map(list -> {
                    var aArgument = Data.get(list.get(0), first);
                    var bArgument = Data.get(list.get(1), second);
                    var expectedValue = expected.apply(aArgument, bArgument);
                    return new BiData<>(expectedValue, text.apply(aArgument, bArgument, expectedValue), aArgument,
                                        bArgument);
                });
    }
}
