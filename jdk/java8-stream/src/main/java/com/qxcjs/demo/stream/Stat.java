package com.qxcjs.demo.stream;

import com.qxcjs.demo.base.Base;
import lombok.extern.log4j.Log4j2;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 统计函数
 */
@Log4j2
public class Stat implements Base {

    public static void main(String[] args) {
        max();
        min();
        sum();
        avg();
        count();
    }

    private static void max() {
        nums.get().max(Comparator.comparing(v -> v.intValue()));

        nums.get().max(Comparator.comparing(v -> v.intValue(), Integer::compareTo));

        nums.get().max(Comparator.comparing(v -> v.intValue(), (v1, v2) -> v1 > v2 ? -1 : 1));

        nums.get().max(Comparator.comparing(v -> v.intValue(), (v1, v2) -> v2.compareTo(v1)));

        nums.get().max(Comparator.comparing(v -> v.intValue(), Comparator.reverseOrder()));
    }

    private static void min() {
        strs.get().min(Comparator.comparing(v -> v.length()));
    }

    private static void sum() {
        nums.get().collect(Collectors.summingInt(Integer::intValue));

        nums.get().collect(Collectors.summingInt(v -> v.intValue()));

        nums.get().reduce(0, (a, b) -> a + b);

        /**
         * Integer 下面的 sum 方法
         */
        nums.get().reduce(0, Integer::sum);

        IntStream intStream = nums.get().mapToInt(Integer::intValue);
        intStream.sum();
    }

    private static void avg() {
        nums.get().collect(Collectors.averagingInt(Integer::intValue));

        nums.get().collect(Collectors.averagingInt(v -> v.intValue()));
    }

    private static void count() {
        nums.get().count();

        nums.get().collect(Collectors.counting());
    }
}
