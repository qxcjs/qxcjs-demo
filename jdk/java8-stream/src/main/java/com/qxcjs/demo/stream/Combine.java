package com.qxcjs.demo.stream;

import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 合并多个 stream
 */
@Log4j2
public class Combine {
    private static Collection<String> collectionA = Arrays.asList("S", "T");
    private static Collection<String> collectionB = Arrays.asList("U", "V");
    private static Collection<String> collectionC = Arrays.asList("Z", "X");

    public static void main(String[] args) {
        Stream<String> combinedStream = Stream.concat(
                collectionA.stream(),
                collectionB.stream());
        log.info(Arrays.toString(combinedStream.toArray()));

        Stream<String> combinedStream1 = Stream.concat(
                Stream.concat(collectionA.stream(), collectionB.stream()),
                collectionC.stream());
        log.info(Arrays.toString(combinedStream1.toArray()));

        Stream<String> combinedStream2 = Stream.of(collectionA, collectionB)
                .flatMap(Collection::stream);
        Collection<String> collectionCombined =
                combinedStream2.collect(Collectors.toList());
        log.info(Arrays.toString(collectionCombined.toArray()));
    }
}
