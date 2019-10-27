package top.dzou.netty.websocket_socket.gzh_client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author dingxiang
 * @date 19-10-25 下午7:03
 */
public class GzhClientHandler extends SimpleChannelInboundHandler<String> {
    private String json;
    public GzhClientHandler(String json){
        this.json = json;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("client receive:"+msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(json);
    }
}
