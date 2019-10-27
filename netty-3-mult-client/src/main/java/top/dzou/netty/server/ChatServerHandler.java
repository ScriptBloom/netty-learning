package top.dzou.netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author dingxiang
 * @date 19-10-11 上午9:22
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    //保存多个channel对象
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(o->{
            if(o!=channel){
                o.writeAndFlush(channel.remoteAddress()+"发送消息："+msg+"\n");
            }else {
                o.writeAndFlush("[自己]："+msg+"\n");
            }
        });
        System.out.println(channel.remoteAddress()+"发送消息："+msg);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.flushAndWrite("[服务器]："+channel.remoteAddress()+" 加入～\n");
        System.out.println("[服务器]："+channel.remoteAddress()+" 加入～");
        channelGroup.add(channel);
//        System.out.println("add channel:"+channel.remoteAddress());
        System.out.println("handler add:当前客户端数量："+channelGroup.size());

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.flushAndWrite("[服务器]："+channel.remoteAddress()+" 离开～\n");
        System.out.println("[服务器]："+channel.remoteAddress()+" 离开～");
        System.out.println("handler remove:当前客户端数量："+channelGroup.size());
//        channelGroup.remove(channel);//会被自动调用
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.flushAndWrite("[服务器]："+channel.remoteAddress()+" 上线～\n");
        System.out.println("[服务器]："+channel.remoteAddress()+" 上线～\n");
//        System.out.println("channel active:当前客户端数量："+channelGroup.size());

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.flushAndWrite("[服务器]："+channel.remoteAddress()+" 下线～\n");
        System.out.println("[服务器]："+channel.remoteAddress()+" 下线～\n");
//        System.out.println("channel inactive:当前客户端数量："+channelGroup.size());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
