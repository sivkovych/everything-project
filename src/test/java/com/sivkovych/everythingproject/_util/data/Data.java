package com.sivkovych.everythingproject._util.data;

public interface Data {
    static <T> T get(boolean key, T value) {
        return key
                ? null
                : value;
    }
}
