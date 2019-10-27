package top.dzou.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

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
