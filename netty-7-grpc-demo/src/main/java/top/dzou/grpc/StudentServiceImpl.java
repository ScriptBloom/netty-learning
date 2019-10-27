package top.dzou.grpc;

import io.grpc.stub.StreamObserver;

/**
 * @author dingxiang
 * @date 19-10-14 下午2:54
 */
public class StudentServiceImpl extends MyServiceGrpc.MyServiceImplBase{
    @Override
    public void getRealNameByUserName(MyDataInfo.MyRequest request, StreamObserver<MyDataInfo.MyResponse> responseObserver) {
        System.out.println("接收到客户端信息："+request.getUsername());
        responseObserver.onNext(MyDataInfo.MyResponse.newBuilder().setRealname("丁祥").build());
        responseObserver.onCompleted();
    }
}
