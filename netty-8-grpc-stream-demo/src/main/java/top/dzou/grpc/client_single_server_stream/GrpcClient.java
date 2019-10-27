package top.dzou.grpc.client_single_server_stream;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import top.dzou.grpc.MyDataInfo;
import top.dzou.grpc.MyServiceGrpc;

import java.util.Iterator;

/**
 * @author dingxiang
 * @date 19-10-14 下午2:57
 */
/**
 * 服务端请求单向 客户端响应流
 */
public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost",8899 )
                .usePlaintext().build();
        MyServiceGrpc.MyServiceBlockingStub blockingStub = MyServiceGrpc.newBlockingStub(managedChannel);
        Iterator<MyDataInfo.StudentResponse> studentsByAge = blockingStub.getStudentsByAge(MyDataInfo.AgeRequest
                .newBuilder().setAge(10).build());
        while(studentsByAge.hasNext()){
            MyDataInfo.StudentResponse response = studentsByAge.next();
            System.out.println(response.getName()+":"+response.getAge()+":"+response.getId());
        }
    }
}
