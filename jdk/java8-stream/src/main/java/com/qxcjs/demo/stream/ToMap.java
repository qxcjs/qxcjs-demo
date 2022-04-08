package com.qxcjs.demo.stream;

import com.qxcjs.demo.base.Base;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * 简单的 Collector
 */
@Log4j2
public class ToMap implements Base {

    public static void main(String[] args) {
        toMap1();
    }

    private static void toMap1() {
        Map<String, Integer> map = strs.get().collect(toMap(k -> k, v -> v.length()));
        log.info(map);

        strs.get().collect(toMap(Function.identity(), String::length));

        /**
         * Duplicate key
         */
        Map<Integer, String> map1 = strs.get().collect(toMap(String::length, Function.identity()));

        strs.get().collect(toMap(
                String::length,
                Function.identity(),
                (s1, s2) -> s2) // key 相同时取后面的值
        );

        strs.get().collect(toMap(
                String::length, // key
                v -> Arrays.asList(v), // value
                (s1, s2) -> { // key 相同时，如何处理 value
                    s2.addAll(s1);
                    return s2;
                }));
    }
}
