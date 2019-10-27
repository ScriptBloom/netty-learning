package top.dzou.grpc.client_single_server_single;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import top.dzou.grpc.MyDataInfo;
import top.dzou.grpc.MyServiceGrpc;

/**
 * @author dingxiang
 * @date 19-10-14 下午2:57
 */
public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost",8899 )
                .usePlaintext().build();
        MyServiceGrpc.MyServiceBlockingStub blockingStub = MyServiceGrpc.newBlockingStub(managedChannel);
        MyDataInfo.MyResponse myResponse = blockingStub.getRealNameByUserName(MyDataInfo.MyRequest.newBuilder()
                .setUsername("dx").build());
        System.out.println(myResponse.getRealname());
    }
}
