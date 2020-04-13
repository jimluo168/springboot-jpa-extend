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
    /**
     * 剩余部分.
     */
    private String remainder = "";

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            String msg = in.toString(StandardCharsets.UTF_8);
            if (msg == null || msg.length() == 0) {
                return;
            }
            if (remainder != null && remainder.length() > 0 && !msg.startsWith(DataForwardClient.PACKET_HEAD)) {
                msg = new StringBuilder().append(remainder).append(msg).toString();
            }
            if (!msg.endsWith(DataForwardClient.PACKET_END)) {
                int lastIndex = msg.lastIndexOf(DataForwardClient.PACKET_HEAD);
                if (lastIndex == 0) {
                    remainder = msg;
                    return;
                }
                String actualMsg = msg.substring(0, lastIndex);
                remainder = msg.substring(lastIndex);
                out.add(actualMsg);
            } else {
                out.add(msg);
            }
        } finally {
            in.skipBytes(in.readableBytes());
        }
    }
}
