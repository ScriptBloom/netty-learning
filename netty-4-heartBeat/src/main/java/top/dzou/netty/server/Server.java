package top.dzou.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author dingxiang
 * @date 19-10-11 上午9:21
 */
public class Server {
    public static void main(String[] args) throws InterruptedException {
        //两个事件循环组 boss获取连接发送 worker接收处理
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            //server启动器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 定义组
            // channel(反射)
            // 定义处理器(自定义)：连接上channel后执行init
            System.out.println("启动server");
            serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ServerInitializer());
            //绑定端口，同步
            ChannelFuture future = serverBootstrap.bind(8899).sync();
            future.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
