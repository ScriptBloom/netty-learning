package top.dzou.grpc;

import io.grpc.stub.StreamObserver;

/**
 * @author dingxiang
 * @date 19-10-14 下午2:54
 */
public class StudentServiceImpl extends MyServiceGrpc.MyServiceImplBase{
    /**
     * 单请求单响应
     * @param request
     * @param responseObserver
     */
    @Override
    public void getRealNameByUserName(MyDataInfo.MyRequest request, StreamObserver<MyDataInfo.MyResponse> responseObserver) {
        System.out.println("接收到客户端信息："+request.getUsername());
        responseObserver.onNext(MyDataInfo.MyResponse.newBuilder().setRealname("丁祥").build());
        responseObserver.onCompleted();
    }

    /**
     * 流请求流响应
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<MyDataInfo.AgeRequest> getStreamStudentByAge(StreamObserver<MyDataInfo.StudentResponse> responseObserver) {
        return new StreamObserver<MyDataInfo.AgeRequest>() {
            @Override
            public void onNext(MyDataInfo.AgeRequest value) {
                System.out.println(value.getAge());
                responseObserver.onNext(MyDataInfo.StudentResponse.newBuilder().setId(8423424).setName("dhisuahdsa")
                        .setAge(1903472).build());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * 流请求单响应
     * @param responseObserver
     * @return
     */
    @Override
    public StreamObserver<MyDataInfo.AgeRequest> getStudentWrapperByAge(StreamObserver<MyDataInfo.StudentsListResponse> responseObserver) {
        return new StreamObserver<MyDataInfo.AgeRequest>() {
            @Override
            public void onNext(MyDataInfo.AgeRequest value) {
                System.out.println("OnNext:"+value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                MyDataInfo.StudentResponse response  = MyDataInfo.StudentResponse.newBuilder()
                        .setAge(10).setId(1).setName("ds").build();
                MyDataInfo.StudentResponse response2  = MyDataInfo.StudentResponse.newBuilder()
                        .setAge(20).setId(2).setName("fdvf").build();
                MyDataInfo.StudentsListResponse listResponse = MyDataInfo.StudentsListResponse.newBuilder()
                        .addStudentResponse(response).addStudentResponse(response2).build();
                responseObserver.onNext(listResponse);
            }
        };
    }

    /**
     * 单请求流响应
     * @param request
     * @param responseObserver
     */
    @Override
    public void getStudentsByAge(MyDataInfo.AgeRequest request, StreamObserver<MyDataInfo.StudentResponse> responseObserver) {
        System.out.println("收到客户端信息："+request.getAge());
        responseObserver.onNext(MyDataInfo.StudentResponse.newBuilder().setAge(10).setId(1).setName("dx").build());
        responseObserver.onNext(MyDataInfo.StudentResponse.newBuilder().setAge(20).setId(2).setName("zfb").build());
        responseObserver.onNext(MyDataInfo.StudentResponse.newBuilder().setAge(30).setId(3).setName("wgy").build());
        responseObserver.onNext(MyDataInfo.StudentResponse.newBuilder().setAge(40).setId(4).setName("wdt").build());
        responseObserver.onNext(MyDataInfo.StudentResponse.newBuilder().setAge(50).setId(5).setName("bm").build());
        responseObserver.onCompleted();
    }
}
