package top.dzou.grpc.client_stream_server_stream;

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
 * 双向流客户端
 */
public class GrpcClient {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost",8899 )
                .usePlaintext().build();
        MyServiceGrpc.MyServiceStub stub = MyServiceGrpc.newStub(managedChannel);
        StreamObserver<MyDataInfo.StudentResponse> observer = new StreamObserver<MyDataInfo.StudentResponse>() {
            @Override
            public void onNext(MyDataInfo.StudentResponse value) {
                System.out.println(value.getId());
                System.out.println();
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
        StreamObserver<MyDataInfo.AgeRequest> requestStreamObserver = stub.getStreamStudentByAge(observer);
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(10).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(20).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(30).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(40).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(50).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(50).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(50).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(50).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(50).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(50).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(50).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(50).build());
        requestStreamObserver.onNext(MyDataInfo.AgeRequest.newBuilder().setAge(50).build());
        requestStreamObserver.onCompleted();
        System.out.println("异步来到这");
        Thread.sleep(10000);
    }
}
