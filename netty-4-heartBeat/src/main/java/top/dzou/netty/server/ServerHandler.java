package top.dzou.netty.server;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author dingxiang
 * @date 19-10-11 上午9:22
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            String evenType = null;
            switch (event.state()){
                case ALL_IDLE:
                    evenType = "全部空闲";
                    break;
                case READER_IDLE:
                    evenType = "读空闲";
                    break;
                case WRITER_IDLE:
                    evenType = "写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+"超时事件："+evenType);
            ctx.channel().close();
        }
    }
}
