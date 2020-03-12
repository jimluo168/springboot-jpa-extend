package com.bms.common.config.redis;

import redis.clients.jedis.commands.*;

/**
 * Redis Client.
 *
 * @author luojimeng
 * @date 2020/3/10
 */
public interface RedisClient extends JedisCommands, BinaryJedisCommands, MultiKeyBinaryCommands, MultiKeyCommands, ScriptingCommands, ExtendCommands {
}
