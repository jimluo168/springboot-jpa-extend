package com.bms.monitor.sync;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 数据转发解码器.
 *
 * @author luojimeng
 * @date 2020/4/8
 */
public class DataForwardDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        out.add(buf.toString(StandardCharsets.UTF_8));
    }
}
