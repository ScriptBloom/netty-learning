package top.dzou.netty.websocket_socket.gzh_client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * @author dingxiang
 * @date 19-10-25 下午7:03
 */
public class GzhClient {
    public static void main(String[] args) {
        EventLoopGroup eventExecutors = null;
        ChannelFuture channelFuture = null;
        try{
//            while (true) {
                eventExecutors = new NioEventLoopGroup();
                Scanner scanner = new Scanner(System.in);
                System.out.println("启动客户端");
                String json = scanner.nextLine();
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(eventExecutors).channel(NioSocketChannel.class)
                        .handler(new GzhClientInitializer(json));
                channelFuture = bootstrap.connect("localhost", 8090).sync();
//            }
//            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
