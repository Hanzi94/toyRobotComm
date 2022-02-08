package com.challenge.robotcomm.controller;
import com.challenge.robotcomm.service.RobotService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
 class RobotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
     void test() throws Exception{

        // place wrong -1,1,EAST
        mockMvc.perform(put("/place?x=-1&y=1&face=EaST")).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().string(containsString("Place at (-1,1) will cause falling off")));
        // report error
        mockMvc.perform(get("/report")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("Output: ROBOT MISSING")));
        // turn left error
        mockMvc.perform(put("/left")).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().string(containsString("Robot Missing")));
        // turn right error
        mockMvc.perform(put("/right")).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().string(containsString("Robot Missing")));
        // move error
        mockMvc.perform(put("/move")).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().string(containsString("Robot Missing")));
        // place ok 1,1,EAST
        mockMvc.perform(put("/place?x=1&y=1&face=EaST")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("")));
        // report location ok
        mockMvc.perform(get("/report")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("Output: 1,1,EAST")));
        // move ok to 2,1,EAST
        mockMvc.perform(put("/move")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("")));
        // report OK
        mockMvc.perform(get("/report")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("Output: 2,1,EAST")));
        // turn left
        mockMvc.perform(put("/left")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("")));
        // report OK
        mockMvc.perform(get("/report")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("Output: 2,1,NORTH")));
        // turn right 2 times
        mockMvc.perform(put("/right")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("")));
        mockMvc.perform(put("/right")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("")));
        // report OK
        mockMvc.perform(get("/report")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("Output: 2,1,SOUTH")));
        // move ok to 2,0,SOUTH
        mockMvc.perform(put("/move")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("")));
        // move error
        mockMvc.perform(put("/move")).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().string(containsString("Move action will cause falling off, ignored")));
        // position not change
        mockMvc.perform(get("/report")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("Output: 2,0,SOUTH")));
        // place wrong -1,1,EAST
        mockMvc.perform(put("/place?x=-1&y=1&face=EaST1")).andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content().string(containsString("Place at (-1,1) will cause falling off\n" +
                        "Illegal face: EaST1")));
        // position not change
        mockMvc.perform(get("/report")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("Output: 2,0,SOUTH")));
    }
}
