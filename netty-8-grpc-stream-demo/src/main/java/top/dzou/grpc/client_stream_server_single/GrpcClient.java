package top.dzou.grpc.client_stream_server_single;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import top.dzou.grpc.MyDataInfo;
import top.dzou.grpc.MyServiceGrpc;

/**
 * @author dingxiang
 * @date 19-10-14 下午2:57
 */
/**
 * 服务端请求流 客户端响应单向
 */
public class GrpcClient {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost",8899 )
                .usePlaintext().build();
        MyServiceGrpc.MyServiceStub stub = MyServiceGrpc.newStub(managedChannel);
        StreamObserver<MyDataInfo.StudentsListResponse> observer = new StreamObserver<MyDataInfo.StudentsListResponse>() {
            @Override
            public void onNext(MyDataInfo.StudentsListResponse value) {
                value.getStudentResponseList().forEach(o->{
                    System.out.println(o.getName());
                    System.out.println(o.getAge());
                    System.out.println(o.getId());
                });
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Completed!");
            }
        };
        StreamObserver<MyDataInfo.AgeRequest> requestStreamObserver = stub.getStudentWrapperByAge(observer);
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(10).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(20).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(30).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(40).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(50).build());
        requestStreamObserver.onCompleted();
        System.out.println("异步来到这");
        Thread.sleep(10000);
    }
}
