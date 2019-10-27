package top.dzou.netty.websocket_socket.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import top.dzou.netty.websocket_socket.GlobalChannelGroup;

/**
 * @author dingxiang
 * @date 19-10-25 下午7:02
 */
public class ServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+","+msg);
        ctx.channel().writeAndFlush("消息已经进入数据库，正在赶往微信墙!");
        GlobalChannelGroup.channelGroup.forEach(o->{
            if (o.localAddress().toString().endsWith("8089")){
                TextWebSocketFrame text = new TextWebSocketFrame(o.remoteAddress() + "发送消息：" + msg + "\n");
                o.writeAndFlush(text);
            }
        });
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+","+msg);
        ctx.channel().writeAndFlush("消息已经进入数据库，正在赶往微信墙!");
        GlobalChannelGroup.channelGroup.forEach(o->{
            if (o.localAddress().toString().endsWith("8089")){
                TextWebSocketFrame text = new TextWebSocketFrame(o.remoteAddress() + "发送消息：" + msg + "\n");
                o.writeAndFlush(text);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+":连接到微信墙模式成功!");
        int size = GlobalChannelGroup.channelGroup.size();
        System.out.println("当前微信墙连接数:"+(size==0?0:size-1));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        GlobalChannelGroup.channelGroup.add(channel);
    }
}
