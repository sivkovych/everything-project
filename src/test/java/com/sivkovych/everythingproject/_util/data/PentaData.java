package com.sivkovych.everythingproject._util.data;

import com.sivkovych.everythingproject._util.HexaFunction;
import com.sivkovych.everythingproject._util.PentaFunction;
import org.paukov.combinatorics3.Generator;

import java.util.stream.Stream;

public record PentaData<A, B, C, D, E, EXP>(EXP expected, String text, A a, B b, C c, D d, E e) implements Data {
    public static <A, B, C, D, E, EXP> Stream<PentaData<A, B, C, D, E, EXP>> stream(PentaFunction<A, B, C, D, E, EXP> e,
                                                                                    HexaFunction<A, B, C, D, E, EXP,
                                                                                            String> text,
                                                                                    A first,
                                                                                    B second,
                                                                                    C third,
                                                                                    D forth,
                                                                                    E fifth) {
        return Generator.permutation(true, false)
                .withRepetitions(5)
                .stream()
                .map(list -> {
                    var aArgument = Data.get(list.get(0), first);
                    var bArgument = Data.get(list.get(1), second);
                    var cArgument = Data.get(list.get(2), third);
                    var dArgument = Data.get(list.get(3), forth);
                    var eArgument = Data.get(list.get(4), fifth);
                    var expectedValue = e.apply(aArgument, bArgument, cArgument, dArgument, eArgument);
                    return new PentaData<>(expectedValue,
                                           text.apply(aArgument, bArgument, cArgument, dArgument, eArgument,
                                                      expectedValue), aArgument, bArgument, cArgument, dArgument,
                                           eArgument);
                });
    }
}
