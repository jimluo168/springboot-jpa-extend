package com.bms.common.config.session;

import com.bms.common.util.IdGenerator;

/**
 * 用户会话ID生成器.
 *
 * @author luojimeng
 * @date 2020/3/10
 */
public class SessionIdGenerator {

    private static final IdGenerator idGenerator = new IdGenerator();

    /**
     * 会话ID的长度.
     */
    public static int length = 20;

    public static String generate() {
        return idGenerator.generateId(length);
    }
}
