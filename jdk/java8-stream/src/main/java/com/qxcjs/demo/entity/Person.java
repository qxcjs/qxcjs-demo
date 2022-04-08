package com.qxcjs.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Person {
    private int id;
    private int age;
    private String name;
    private List<String> address;
}
