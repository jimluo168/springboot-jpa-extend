package com.bms.monitor.sync;

import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * HexUtils测试类.
 *
 * @author luojimeng
 * @date 2020/4/8
 */
class HexUtilsTest {

    @Test
    public void test() {
        String accessKey = "$$test1##";

        char[] data4 = Hex.encodeHex(accessKey.getBytes(StandardCharsets.UTF_8), false);
        String data4HexString = Hex.encodeHexString(accessKey.getBytes(StandardCharsets.UTF_8));
        System.out.println("data" + data4 + data4HexString);
    }

}