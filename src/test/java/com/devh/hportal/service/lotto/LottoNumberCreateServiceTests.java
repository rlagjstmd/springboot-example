package com.devh.hportal.service.lotto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LottoNumberCreateServiceTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void numberCreateTest() throws Exception {
        System.out.println("#################### START TEST ####################");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("createType", "RANDOM");
        params.add("startNumber", "35");
        params.add("endNumber", "45");
        params.add("numberCount", "2");
        params.add("gameCount", "3");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/lotto/number-create").params(params))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        System.out.println("#################### END TEST ####################");
    }
}
