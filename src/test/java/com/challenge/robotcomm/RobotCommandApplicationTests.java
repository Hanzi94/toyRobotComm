package com.challenge.robotcomm;

import static org.assertj.core.api.Assertions.assertThat;
import com.challenge.robotcomm.controller.RobotController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RobotCommandApplicationTests {
    @Autowired
    private RobotController robotController;

    @Test
    void contextLoads() {
        assertThat(robotController).isNotNull();
    }
}
