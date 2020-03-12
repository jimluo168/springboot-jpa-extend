package com.bms.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
public class StringsUtils {

    /**
     * sha1加密.
     *
     * @param text   加密的明文
     * @param salt   盐值
     * @param secret 安全码
     * @return
     */
    public static String sha256Hex(String text, String salt, String secret) {
        return DigestUtils.sha256Hex(StringUtils.join(new String[]{text, salt, secret}, '/'));
    }
}
