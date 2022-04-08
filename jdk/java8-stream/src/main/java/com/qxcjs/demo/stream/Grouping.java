package com.qxcjs.demo.stream;

import com.qxcjs.demo.base.Base;
import com.qxcjs.demo.entity.Person;
import lombok.extern.log4j.Log4j2;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
public class Grouping implements Base {
    public static void main(String[] args) {
        groupingByType();
        groupingMultipleColumn();
        modifyResultType();
        processGroupingResult();
    }

    /**
     * 按单属性，复杂类型分组
     */
    private static void groupingByType() {
        /**
         * 按年龄分组
         */
        Map<Integer, List<Person>> map = persons.get().collect(Collectors.groupingBy(u -> u.getAge()));
        log.info(map);

        /**
         * 按复杂对象分组，对象需要实现 equals 和 hashcode 方法
         */
        Map<AbstractMap.SimpleEntry<Integer, String>, List<Person>> map1 = persons.get().collect(Collectors.groupingBy(new Function<Person, AbstractMap.SimpleEntry<Integer, String>>() {
            @Override
            public AbstractMap.SimpleEntry<Integer, String> apply(Person person) {
                AbstractMap.SimpleEntry entry = new AbstractMap.SimpleEntry(person.getId(), person.getName());
                return entry;
            }
        }));
        log.info(map1);
    }

    /**
     * 按多个字段分组
     */
    private static void groupingMultipleColumn() {
        /**
         * 先按年龄分组，再按名称分组
         */
        Map<Integer, Map<String, List<Person>>> map = persons.get().collect(Collectors.groupingBy(u -> u.getAge(), Collectors.groupingBy(u -> u.getName())));
        log.info(map);
    }

    /**
     * 处理分组后的数据
     */
    private static void processGroupingResult() {
        /**
         * 对分组后的年龄求平均
         */
        Map<Integer, Double> map = persons.get().collect(Collectors.groupingBy(u -> u.getAge(), Collectors.averagingInt(Person::getAge)));
        log.info(map);
    }

    /**
     * 修改分组结果值类型
     */
    private static void modifyResultType() {
        Map<Integer, List<Person>> map = persons.get().collect(Collectors.groupingBy(u -> u.getAge()));
        log.info(map);

        /**
         * 通过 collectingAndThen 改变结果集类型
         */
        Map<Integer, List<Map<String, Object>>> map1 = persons.get().collect(
                Collectors.groupingBy(
                        u -> u.getAge(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                l -> l.stream().map(p -> {
                                            Map<String, Object> m = new HashMap<>();
                                            m.put("id", p.getId());
                                            m.put("name", p.getName());
                                            return m;
                                        }
                                ).collect(Collectors.toList())
                        )
                ));
        log.info(map1);

        /**
         * 通过 mapping 改变结果集类型
         */
        Map<Integer, List<Map>> map2 = persons.get().collect(
                Collectors.groupingBy(
                        u -> u.getAge(),
                        Collectors.mapping(
                                p -> {
                                    Map<String, Object> m = new HashMap<>();
                                    m.put("id", p.getId());
                                    m.put("name", p.getName());
                                    return m;
                                },
                                Collectors.toList()
                        )
                ));
        log.info(map2);
    }
}
