package com.bms.sys.controller;

import com.alibaba.fastjson.JSON;
import com.bms.common.exception.ExceptionFactory;
import com.bms.entity.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 机构组织的测试类.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@SpringBootTest
@AutoConfigureMockMvc
//@Transactional
class OrganizationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void create() throws Exception {
        Organization organization = new Organization();
        organization.setName("贵阳公交集团2");
        organization.setLevel(1);
        organization.setProvince("贵州省");
        organization.setCity("贵阳市");
        organization.setCounty("南明区");
        organization.setAddress("贵阳市观山湖区发展路1号 腾祥·迈德国际A3栋");
        organization.setBusinessLicense("/");
        organization.setBusinessScope("10,11,809");
        organization.setContact("0851-85986000");
        mvc.perform(post("/sys/organizations")
                .content(JSON.toJSONString(organization))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void edit() throws Exception {
        Long id = 433870687619387392L;
        Organization organization = new Organization();
        organization.setName("贵阳公交集团-更新3");
        organization.setLevel(2);
        organization.setProvince("贵州省-更新");
        organization.setCity("贵阳市");
        organization.setCounty("南明区");
        organization.setAddress("贵阳市观山湖区发展路1号 腾祥·迈德国际A3栋");
        organization.setBusinessLicense("/");
        organization.setBusinessScope("10,11,809");
        organization.setContact("0851-85986000");
        mvc.perform(put("/sys/organizations/" + id)
                .content(JSON.toJSONString(organization))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void list() throws Exception {
        mvc.perform(get("/sys/organizations/list")
                .param("page", "0")
                .param("size", "20")
                .param("name", "贵阳公交集团")
                .param("level", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.count").exists())
                .andDo(print())
                .andReturn();
    }

    @Test
    void details() throws Exception {
        Long id = 433870687619387392L;
        mvc.perform(get("/sys/organizations/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(id.toString()))
                .andDo(print())
                .andReturn();
    }

    @Test
    void deleteById() throws Exception {
        Long id = 433870687619387392L;
        mvc.perform(delete("/sys/organizations/" + id)
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void deleteById4DataNotExist() throws Exception {
        Long id = 123L;
        mvc.perform(delete("/sys/organizations/" + id)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.code").value(ExceptionFactory.DATA_NOT_EXIST_ERR))
                .andDo(print())
                .andReturn();
    }

    @Test
    void audit() throws Exception {
        Long id = 433870687619387392L;
        Organization organization = new Organization();
        organization.setReason("运营执照合法 通过");
        mvc.perform(post("/sys/organizations/" + id + "/status/" + Organization.STATUS_PASS_AUDIT)
                .content(JSON.toJSONString(organization))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void audit2() throws Exception {
        Long id = 433870687619387392L;
        Organization organization = new Organization();
        organization.setReason("运营执照不合法 不能通过");
        mvc.perform(post("/sys/organizations/" + id + "/status/" + Organization.STATUS_UN_AUDIT)
                .content(JSON.toJSONString(organization))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
}