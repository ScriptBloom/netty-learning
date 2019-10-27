package top.dzou.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import top.dzou.netty.DataInfo;
import top.dzou.netty.MyDataInfo;

/**
 * @author dingxiang
 * @date 19-10-12 下午9:19
 */
public class ProtoBufHandler extends SimpleChannelInboundHandler<MyDataInfo.DataInfo> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.DataInfo msg) throws Exception {
        MyDataInfo.DataInfo.DataType dataType = msg.getDataType();
        switch (dataType){
            case FriendType:
                System.out.println(msg.getFriend().toString());
                break;
            case ParentType:
                System.out.println(msg.getParent().toString());
                break;
            case StudentType:
                System.out.println(msg.getStudent().toString());
                break;
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
