package com.bms.sys.controller;

import com.alibaba.fastjson.JSON;
import com.bms.entity.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by zouyongcan on 2020/3/12
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MenuControllerTest {

    @Autowired
    private MockMvc mvc;

    Long id = 433980486096916480L;

    @Test
    void create() throws Exception {
        Menu menu = new Menu();
        menu.setIcon("/com/pic/aa");
        menu.setName("test测试");
        menu.setPath("/cs");
        menu.setType(Menu.TYPE_MENU);
        mvc.perform(post("/sys/menu")
                .content(JSON.toJSONString(menu))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void edit() throws Exception {
        Menu menu = new Menu();
        menu.setIcon("/com/pic/bb");
        menu.setName("test测试update");
        menu.setPath("/csUpdate");
        mvc.perform(put("/sys/menu/" + id)
                .content(JSON.toJSONString(menu))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void deleteById() throws Exception {
        mvc.perform(delete("/sys/menu/" + id)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
}
