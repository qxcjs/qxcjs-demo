package com.qxcjs.demo.base;

import com.qxcjs.demo.entity.Person;

import java.util.function.Supplier;
import java.util.stream.Stream;

public interface Base {


    Supplier<Stream<Person>> persons = () -> Stream.of(
            new Person(1, 30, "wt", null),
            new Person(2, 30, "liss", null),
            new Person(3, 50, "ellisli", null),
            new Person(4, 75, "lb", null)
    );

    Supplier<Stream<String>> strs = () -> Stream.of("wt", "liss", "ellisli", "lb");
    Supplier<Stream<Integer>> nums = () -> Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
}
