package com.bms.monitor.sync;

import com.bms.common.config.redis.RedisClient;
import com.bms.industry.service.BusRouteService;
import com.bms.industry.service.BusSiteService;
import com.bms.industry.sync.SyncProperties;
import com.bms.monitor.service.MoBusVehicleGpsDataService;
import com.bms.monitor.service.MoOffSiteDataService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
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
@ConditionalOnExpression("${sync.data-forward.enabled:true}")
public class DataForwardClient {
    private static final Logger logger = LoggerFactory.getLogger(DataForwardClient.class);
    /**
     * 数据包头部.
     */
    public static final String PACKET_HEAD = "$$";
    /**
     * 数据包尾部.
     */
    public static final String PACKET_END = "##";

    private final SyncProperties syncProperties;
    private final MoBusVehicleGpsDataService moBusVehicleGpsDataService;
    private final MoOffSiteDataService moOffSiteDataService;
    private final DataForwardService dataForwardService;

    private EventLoopGroup group;
    private ChannelFuture channelFuture;

    @PostConstruct
    public void start() {
        String host = syncProperties.getDataForward().getHost();
        int port = syncProperties.getDataForward().getPort();
        try {
            group = new NioEventLoopGroup();
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
                                    new DataForwardClientHandler(syncProperties, moBusVehicleGpsDataService,
                                            moOffSiteDataService, dataForwardService));
                        }
                    });


            // Start the client.
            channelFuture = b.connect(host, port).sync();

            channelFuture.addListener(new ChannelFutureListener() {
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
        } catch (Exception e) {
            logger.error("连接服务器 " + host + ":" + port + " 异常.", e);
            destory();
        }
    }

    @PreDestroy
    public void destory() {
        logger.info("销毁 Dataforward Client Socket");
        if (channelFuture != null) {
            channelFuture.channel().close();
        }
        if (group != null) {
            group.shutdownGracefully();
        }
        DataForwardClientHandler.THREAD_POOL_EXECUTOR.shutdown();
    }
}
