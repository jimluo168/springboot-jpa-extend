package com.bms.monitor.sync;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;

/**
 * 数据转发编码器.
 *
 * @author luojimeng
 * @date 2020/4/8
 */
public class DataForwardEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object message, ByteBuf out) throws Exception {
        if (message instanceof String) {
            String wrapPacket = DataForwardClient.packet_head + message + DataForwardClient.packet_end;
            byte[] data = wrapPacket.getBytes(StandardCharsets.UTF_8);
            out.writeBytes(data);
        } else {
            throw new RuntimeException("不支持的类型");
        }
    }
}
