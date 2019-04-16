package com.myproject.tinyalarmclock;

import com.myproject.tinyalarmclock.config.RecordProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TinyAlarmClockApplicationTests {

    @Autowired
    RecordProducer recordProducer;

    @Test
    public void contextLoads() {
        recordProducer.recordThoughts("下单成功");
    }

}
