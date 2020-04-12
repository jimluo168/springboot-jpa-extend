package com.bms.common.util;

import com.bms.monitor.view.MoRescueMaterialJson;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO(类的简要说明)
 *
 * @author luojimeng
 * @date 2020/4/12
 */
class JSONTest {

    @Test
    void toJSONString() {
        List<MoRescueMaterialJson> list = new ArrayList<>();
        MoRescueMaterialJson json = new MoRescueMaterialJson();
        json.setId(444318159630110720L);
        json.setUsageQuantity(new BigDecimal(10));
        list.add(json);

        String str = JSON.toJSONString(list);

        System.out.println(str);
    }
}