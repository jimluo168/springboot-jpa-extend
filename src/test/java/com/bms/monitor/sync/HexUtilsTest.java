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
        String hexStr = HexUtils.string2HexString(accessKey);
        byte[] data = HexUtils.hexString2Bytes(hexStr);

        byte[] data2 = accessKey.getBytes(StandardCharsets.UTF_8);

        System.out.println("data" + data + data2);

        char[] data3 = HexUtils.bytes2HexBytes(accessKey.getBytes(StandardCharsets.UTF_8));
        String data3HexString = HexUtils.bytes2HexString(accessKey.getBytes(StandardCharsets.UTF_8));
        System.out.println("data" + data3 + data3HexString);

        char[] data4 = Hex.encodeHex(accessKey.getBytes(StandardCharsets.UTF_8), false);
        String data4HexString = Hex.encodeHexString(accessKey.getBytes(StandardCharsets.UTF_8));
        System.out.println("data" + data4 + data4HexString);
    }

}