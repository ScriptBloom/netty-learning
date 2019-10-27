package top.dzou.netty.websocket_socket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author dingxiang
 * @date 19-10-25 下午7:00
 */
public class Server {
    public static void main(String[] args) throws InterruptedException {
        //两个事件循环组 boss获取连接发送 worker接收处理
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            //server启动器
            ServerBootstrap serverWxQBootstrap = new ServerBootstrap();
            ServerBootstrap serverGzhBootstrap = new ServerBootstrap();
            // 定义组
            // channel(反射)
            // 定义处理器(自定义)：连接上channel后执行init
            System.out.println("启动server");
            serverGzhBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer());
            serverWxQBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketChannelInitializer());
            //绑定端口，同步
            ChannelFuture wxq = serverGzhBootstrap.bind(8090).sync();
            ChannelFuture gzh = serverWxQBootstrap.bind(8089).sync();
            gzh.channel().closeFuture().sync();
            wxq.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
