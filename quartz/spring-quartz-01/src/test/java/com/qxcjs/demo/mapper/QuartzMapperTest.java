package com.qxcjs.demo.mapper;

import com.qxcjs.demo.entity.QuartzTask;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class QuartzMapperTest {

    @Autowired
    private QuartzTaskMapper quartzTaskMapper;

    @Test
    public void testSelect() {
        List<QuartzTask> tasks = quartzTaskMapper.selectList(null);
        tasks.forEach(System.out::println);
    }
}