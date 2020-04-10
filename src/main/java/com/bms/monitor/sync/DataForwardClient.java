package com.bms.monitor.sync;

import com.bms.industry.sync.SyncProperties;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 数据转发客户端.
 *
 * @author luojimeng
 * @date 2020/4/8
 */
@Component
@RequiredArgsConstructor
public class DataForwardClient {
    private static final Logger logger = LoggerFactory.getLogger(DataForwardClient.class);
    /**
     * 数据包头部.
     */
    public static final String packet_head = "$$";
    /**
     * 数据包尾部.
     */
    public static final String packet_end = "##";

    private final SyncProperties syncProperties;

    private static final EventLoopGroup group = new NioEventLoopGroup();

//    @PostConstruct
    public void start() {
        String host = syncProperties.getDataForward().getHost();
        int port = syncProperties.getDataForward().getPort();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30 * 1000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            logger.info("正在连接服务器{}:{} ...", host, port);
                            ChannelPipeline p = ch.pipeline();

                            p.addLast(
                                    new DataForwardEncoder(),
                                    new DataForwardDecoder(),
                                    new DataForwardClientHandler(syncProperties));
                        }
                    });


            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        logger.info("连接服务器{}:{}成功.", host, port);
                    } else {
                        logger.info("连接服务器{}:{}失败.", host, port);
                        channelFuture.cause().printStackTrace();
                    }
                }
            });
        } catch (InterruptedException e) {
            logger.error("连接服务器 " + host + ":" + port + " 异常.", e);
        }
    }

    @PreDestroy
    public void destory() {
        logger.info("销毁 Dataforward Client Socket");
        group.shutdownGracefully();
    }
}
