package server;

import client.NettyClientHandler;
import common.CommonDecoder;
import common.CommonEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serializer.HessianSerializer;
import serializer.JsonSerializer;
import serializer.KryoSerializer;

public class NettyServer implements RpcServer{
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Override
    public void start(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //主从线程池初始化到启动器中
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //日志Handler
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG,256)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline channelPipeline = ch.pipeline();
                            channelPipeline
                                    .addLast(new CommonDecoder())
//                                    .addLast(new CommonEncoder(new JsonSerializer()))  //Encoder和Decoder顺序无所谓  但是要在Server前面
                                    .addLast(new CommonEncoder(new KryoSerializer()))
//                                    .addLast(new CommonEncoder(new HessianSerializer()))
                                    .addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        }catch (InterruptedException e) {
            logger.error("启动服务器时有错误发生", e);
        } finally {
            //优雅关闭Netty服务端且清理掉内存，shutdownGracefully()执行逻辑参考：
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
