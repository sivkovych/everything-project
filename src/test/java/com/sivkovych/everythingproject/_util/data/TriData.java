package com.sivkovych.everythingproject._util.data;

import com.sivkovych.everythingproject._util.TetraFunction;
import com.sivkovych.everythingproject._util.TriFunction;
import org.paukov.combinatorics3.Generator;

import java.util.stream.Stream;

public record TriData<A, B, C, EXP>(EXP expected, String text, A a, B b, C c) implements Data {
    public static <A, B, C, EXP> Stream<TriData<A, B, C, EXP>> stream(TriFunction<A, B, C, EXP> expected,
                                                                      TetraFunction<A, B, C, EXP, String> text,
                                                                      A first,
                                                                      B second,
                                                                      C third) {
        return Generator.permutation(true, false)
                .withRepetitions(3)
                .stream()
                .map(list -> {
                    var aArgument = Data.get(list.get(0), first);
                    var bArgument = Data.get(list.get(1), second);
                    var cArgument = Data.get(list.get(2), third);
                    var expectedValue = expected.apply(aArgument, bArgument, cArgument);
                    return new TriData<>(expectedValue, text.apply(aArgument, bArgument, cArgument, expectedValue),
                                         aArgument, bArgument, cArgument);
                });
    }
}
