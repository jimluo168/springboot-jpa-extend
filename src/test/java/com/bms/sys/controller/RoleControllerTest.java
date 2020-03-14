package com.bms.sys.controller;

import com.bms.common.util.JSON;
import com.bms.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by zouyongcan on 2020/3/13
 */

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RoleControllerTest {
    @Autowired
    private MockMvc mvc;

    Long id = 434194181452337152L;

    @Test
    void create() throws Exception {
        Role role = new Role();
        role.setName("角色测试");
        role.setRemark("描述");
        mvc.perform(post("/sys/roles")
                .content(JSON.toJSONString(role))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void list() throws Exception {
        mvc.perform(get("/sys/roles/list")
                .param("page", "1")
                .param("size", "10")
                .param("name", "角色"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.count").exists())
                .andDo(print())
                .andReturn();
    }

    @Test
    void details() throws Exception {
        mvc.perform(get("/sys/roles/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id.toString()))
                .andDo(print())
                .andReturn();
    }

    @Test
    void edit() throws Exception {
        Role role = new Role();
        role.setName("角色测试1");
        role.setRemark("描述1");
        mvc.perform(put("/sys/roles/" + id)
                .content(JSON.toJSONString(role))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void deleteById() throws Exception {
        mvc.perform(delete("/sys/roles/" + id)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
}
