package com.qxcjs.demo.stream;

import com.qxcjs.demo.entity.Employee;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream Join
 */
public class Join {
    private static Map<String, Employee> map1 = new HashMap<>();
    private static Map<String, Employee> map2 = new HashMap<>();

    static {
        Employee employee1 = new Employee(1L, "Henry");
        map1.put(employee1.getName(), employee1);
        Employee employee2 = new Employee(22L, "Annie");
        map1.put(employee2.getName(), employee2);
        Employee employee3 = new Employee(8L, "John");
        map1.put(employee3.getName(), employee3);

        Employee employee4 = new Employee(2L, "George");
        map2.put(employee4.getName(), employee4);
        Employee employee5 = new Employee(3L, "Henry");
        map2.put(employee5.getName(), employee5);
    }

    public static void main(String[] args) {
        mapMerge();
    }

    public static void mapMerge() {
        Map<String, Employee> map3 = new HashMap<>(map1);
        map2.forEach(
                (key, value) -> map3.merge(key, value, (v1, v2) ->
                        new Employee(v1.getId(), v2.getName()))
        );
        map3.entrySet().forEach(System.out::println);
    }

    private static void streamOf() {
        Map<String, Employee> map3 = Stream.of(map1, map2)
                .flatMap(map -> map.entrySet().stream())
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (v1, v2) -> new Employee(v1.getId(), v2.getName())
                        )
                );

        map3.entrySet().forEach(System.out::println);
    }

    public static void leftJoin1() {
        List<Integer> s1 = Arrays.asList(1, 2);
        List<Integer> s2 = Arrays.asList(1, 3);

        s1.stream()
                .flatMap(v1 -> s2.stream()
                        .filter(v2 -> Objects.equals(v1, v2))
                        .map(v2 -> new Object[]{v1, v2}))
                .forEach(System.out::println);
    }
}
