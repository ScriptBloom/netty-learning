package top.dzou.netty.websocket_socket.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import top.dzou.netty.websocket_socket.GlobalChannelGroup;

/**
 * @author dingxiang
 * @date 19-10-11 下午1:34
 */
public class TextWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        int size = GlobalChannelGroup.channelGroup.size();
        System.out.println("当前微信墙连接数:"+(size==0?0:size-1));
        System.out.println("收到消息："+msg.text());
        Channel channel = ctx.channel();
        GlobalChannelGroup.channelGroup.forEach(o->{
            if (o.localAddress().toString().endsWith("8090")){
                o.writeAndFlush(msg.text());
            }else {
                TextWebSocketFrame text = new TextWebSocketFrame(o.remoteAddress() + "发送消息：" + msg.text() + "\n");
                o.writeAndFlush(text);
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel ch = ctx.channel();
        GlobalChannelGroup.channelGroup.add(ch);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+":离开聊天室");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel ch = ctx.channel();
        System.out.println(ch.remoteAddress()+"：连接到聊天室");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常");
        ctx.close();
    }
}
