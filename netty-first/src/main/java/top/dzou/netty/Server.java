package top.dzou.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author dingxiang
 * @date 19-10-10 下午11:14
 */

/**
 * netty处理流程：
 * 1.启动server，定义组
 * 2.initializer 其中添加handler
 * 3.实现handler回调方法
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
