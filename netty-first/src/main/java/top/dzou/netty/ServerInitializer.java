package top.dzou.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author dingxiang
 * @date 19-10-10 下午11:15
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    //channel连接创建调用，回调
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //HttpServerCodec用于http编码解码
        //自定义handler,连接上发送消息
        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        pipeline.addLast("myHttpServerHandler",new HttpServerHandler());
    }
}
