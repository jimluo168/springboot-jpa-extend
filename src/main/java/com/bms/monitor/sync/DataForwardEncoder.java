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
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf out) throws Exception {
        if (o instanceof String) {
            String wrapPacket = DataForwardClient.packet_head + o + DataForwardClient.packet_end;
            String hex = Hex.encodeHexString(wrapPacket.getBytes(StandardCharsets.UTF_8), false);
            byte[] data = hex.getBytes(StandardCharsets.UTF_8);
            out.writeInt(data.length); // 先将消息长度写入，也就是消息头
            out.writeBytes(data); // 消息体中包含我们要发送的数据
        } else {
            throw new RuntimeException("不支持的类型");
        }
    }
}
