package top.dzou.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import top.dzou.netty.DataInfo;
import top.dzou.netty.MyDataInfo;

import java.util.Random;

/**
 * @author dingxiang
 * @date 19-10-11 上午9:04
 */
public class ClientHandler extends SimpleChannelInboundHandler<DataInfo.Student> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Student msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int random = new Random().nextInt(3);
        MyDataInfo.DataInfo dataInfo;
        if(random==0){
            dataInfo = MyDataInfo.DataInfo.newBuilder()
                    .setDataType(MyDataInfo.DataInfo.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder()
                    .setEmail("17@qq.com").setId(123).setName("dx").build())
                    .build();
        }else if (random==1){
            dataInfo = MyDataInfo.DataInfo.newBuilder()
                    .setDataType(MyDataInfo.DataInfo.DataType.ParentType)
                    .setParent(MyDataInfo.Parent.newBuilder()
                    .setId(121).setName("zfb").setPhone("137462387432").build())
                    .build();
        }else{
            dataInfo = MyDataInfo.DataInfo.newBuilder()
                    .setDataType(MyDataInfo.DataInfo.DataType.FriendType)
                    .setFriend(MyDataInfo.Friend.newBuilder()
                    .setAddress("云南昆明市").setId(10000).setName("wgy").build())
                    .build();
        }
        ctx.channel().writeAndFlush(dataInfo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
