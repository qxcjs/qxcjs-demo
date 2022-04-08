package com.qxcjs.demo.stream;

import com.qxcjs.demo.base.Base;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
public class Mapping implements Base {
    public static void main(String[] args) {
        List<String> list = strs.get().collect(Collectors.mapping(s -> s.substring(0, 1), Collectors.toList()));
        log.info(list);

        /**
         * mapping 类似先 map 再 collector
         */
        List<String> list1 = strs.get().map(s -> s.substring(0, 1)).collect(Collectors.toList());
        log.info(list1);

        Map<String, List<String>> map = strs.get().collect(Collectors.groupingBy(s -> s.substring(0, 1), Collectors.mapping(Function.identity(), Collectors.toList())));
        log.info(map);
    }
}
