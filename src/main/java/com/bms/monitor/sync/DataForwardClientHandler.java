package com.bms.monitor.sync;

import com.bms.industry.sync.SyncProperties;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端消息处理类.
 *
 * @author luojimeng
 * @date 2020/4/8
 */
public class DataForwardClientHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(DataForwardClientHandler.class);

    private final SyncProperties syncProperties;

    public DataForwardClientHandler(SyncProperties syncProperties) {
        this.syncProperties = syncProperties;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("接受到消息:{}", message);
        }



    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.writeAndFlush(syncProperties.getDataForward().getAccessKey());
    }
}
