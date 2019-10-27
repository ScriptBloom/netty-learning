package top.dzou.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.24.0)",
    comments = "Source: Student.proto")
public final class MyServiceGrpc {

  private MyServiceGrpc() {}

  public static final String SERVICE_NAME = "top.dzou.grpc.MyService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<top.dzou.grpc.MyDataInfo.MyRequest,
      top.dzou.grpc.MyDataInfo.MyResponse> getGetRealNameByUserNameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRealNameByUserName",
      requestType = top.dzou.grpc.MyDataInfo.MyRequest.class,
      responseType = top.dzou.grpc.MyDataInfo.MyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<top.dzou.grpc.MyDataInfo.MyRequest,
      top.dzou.grpc.MyDataInfo.MyResponse> getGetRealNameByUserNameMethod() {
    io.grpc.MethodDescriptor<top.dzou.grpc.MyDataInfo.MyRequest, top.dzou.grpc.MyDataInfo.MyResponse> getGetRealNameByUserNameMethod;
    if ((getGetRealNameByUserNameMethod = MyServiceGrpc.getGetRealNameByUserNameMethod) == null) {
      synchronized (MyServiceGrpc.class) {
        if ((getGetRealNameByUserNameMethod = MyServiceGrpc.getGetRealNameByUserNameMethod) == null) {
          MyServiceGrpc.getGetRealNameByUserNameMethod = getGetRealNameByUserNameMethod =
              io.grpc.MethodDescriptor.<top.dzou.grpc.MyDataInfo.MyRequest, top.dzou.grpc.MyDataInfo.MyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRealNameByUserName"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  top.dzou.grpc.MyDataInfo.MyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  top.dzou.grpc.MyDataInfo.MyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MyServiceMethodDescriptorSupplier("GetRealNameByUserName"))
              .build();
        }
      }
    }
    return getGetRealNameByUserNameMethod;
  }

  private static volatile io.grpc.MethodDescriptor<top.dzou.grpc.MyDataInfo.AgeRequest,
      top.dzou.grpc.MyDataInfo.StudentResponse> getGetStudentsByAgeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStudentsByAge",
      requestType = top.dzou.grpc.MyDataInfo.AgeRequest.class,
      responseType = top.dzou.grpc.MyDataInfo.StudentResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<top.dzou.grpc.MyDataInfo.AgeRequest,
      top.dzou.grpc.MyDataInfo.StudentResponse> getGetStudentsByAgeMethod() {
    io.grpc.MethodDescriptor<top.dzou.grpc.MyDataInfo.AgeRequest, top.dzou.grpc.MyDataInfo.StudentResponse> getGetStudentsByAgeMethod;
    if ((getGetStudentsByAgeMethod = MyServiceGrpc.getGetStudentsByAgeMethod) == null) {
      synchronized (MyServiceGrpc.class) {
        if ((getGetStudentsByAgeMethod = MyServiceGrpc.getGetStudentsByAgeMethod) == null) {
          MyServiceGrpc.getGetStudentsByAgeMethod = getGetStudentsByAgeMethod =
              io.grpc.MethodDescriptor.<top.dzou.grpc.MyDataInfo.AgeRequest, top.dzou.grpc.MyDataInfo.StudentResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStudentsByAge"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  top.dzou.grpc.MyDataInfo.AgeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  top.dzou.grpc.MyDataInfo.StudentResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MyServiceMethodDescriptorSupplier("GetStudentsByAge"))
              .build();
        }
      }
    }
    return getGetStudentsByAgeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<top.dzou.grpc.MyDataInfo.AgeRequest,
      top.dzou.grpc.MyDataInfo.StudentsListResponse> getGetStudentWrapperByAgeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStudentWrapperByAge",
      requestType = top.dzou.grpc.MyDataInfo.AgeRequest.class,
      responseType = top.dzou.grpc.MyDataInfo.StudentsListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<top.dzou.grpc.MyDataInfo.AgeRequest,
      top.dzou.grpc.MyDataInfo.StudentsListResponse> getGetStudentWrapperByAgeMethod() {
    io.grpc.MethodDescriptor<top.dzou.grpc.MyDataInfo.AgeRequest, top.dzou.grpc.MyDataInfo.StudentsListResponse> getGetStudentWrapperByAgeMethod;
    if ((getGetStudentWrapperByAgeMethod = MyServiceGrpc.getGetStudentWrapperByAgeMethod) == null) {
      synchronized (MyServiceGrpc.class) {
        if ((getGetStudentWrapperByAgeMethod = MyServiceGrpc.getGetStudentWrapperByAgeMethod) == null) {
          MyServiceGrpc.getGetStudentWrapperByAgeMethod = getGetStudentWrapperByAgeMethod =
              io.grpc.MethodDescriptor.<top.dzou.grpc.MyDataInfo.AgeRequest, top.dzou.grpc.MyDataInfo.StudentsListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStudentWrapperByAge"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  top.dzou.grpc.MyDataInfo.AgeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  top.dzou.grpc.MyDataInfo.StudentsListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MyServiceMethodDescriptorSupplier("GetStudentWrapperByAge"))
              .build();
        }
      }
    }
    return getGetStudentWrapperByAgeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<top.dzou.grpc.MyDataInfo.AgeRequest,
      top.dzou.grpc.MyDataInfo.StudentResponse> getGetStreamStudentByAgeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStreamStudentByAge",
      requestType = top.dzou.grpc.MyDataInfo.AgeRequest.class,
      responseType = top.dzou.grpc.MyDataInfo.StudentResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<top.dzou.grpc.MyDataInfo.AgeRequest,
      top.dzou.grpc.MyDataInfo.StudentResponse> getGetStreamStudentByAgeMethod() {
    io.grpc.MethodDescriptor<top.dzou.grpc.MyDataInfo.AgeRequest, top.dzou.grpc.MyDataInfo.StudentResponse> getGetStreamStudentByAgeMethod;
    if ((getGetStreamStudentByAgeMethod = MyServiceGrpc.getGetStreamStudentByAgeMethod) == null) {
      synchronized (MyServiceGrpc.class) {
        if ((getGetStreamStudentByAgeMethod = MyServiceGrpc.getGetStreamStudentByAgeMethod) == null) {
          MyServiceGrpc.getGetStreamStudentByAgeMethod = getGetStreamStudentByAgeMethod =
              io.grpc.MethodDescriptor.<top.dzou.grpc.MyDataInfo.AgeRequest, top.dzou.grpc.MyDataInfo.StudentResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStreamStudentByAge"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  top.dzou.grpc.MyDataInfo.AgeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  top.dzou.grpc.MyDataInfo.StudentResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MyServiceMethodDescriptorSupplier("GetStreamStudentByAge"))
              .build();
        }
      }
    }
    return getGetStreamStudentByAgeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MyServiceStub newStub(io.grpc.Channel channel) {
    return new MyServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MyServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MyServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MyServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MyServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class MyServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getRealNameByUserName(top.dzou.grpc.MyDataInfo.MyRequest request,
        io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.MyResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetRealNameByUserNameMethod(), responseObserver);
    }

    /**
     */
    public void getStudentsByAge(top.dzou.grpc.MyDataInfo.AgeRequest request,
        io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.StudentResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getGetStudentsByAgeMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.AgeRequest> getStudentWrapperByAge(
        io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.StudentsListResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getGetStudentWrapperByAgeMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.AgeRequest> getStreamStudentByAge(
        io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.StudentResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getGetStreamStudentByAgeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetRealNameByUserNameMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                top.dzou.grpc.MyDataInfo.MyRequest,
                top.dzou.grpc.MyDataInfo.MyResponse>(
                  this, METHODID_GET_REAL_NAME_BY_USER_NAME)))
          .addMethod(
            getGetStudentsByAgeMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                top.dzou.grpc.MyDataInfo.AgeRequest,
                top.dzou.grpc.MyDataInfo.StudentResponse>(
                  this, METHODID_GET_STUDENTS_BY_AGE)))
          .addMethod(
            getGetStudentWrapperByAgeMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                top.dzou.grpc.MyDataInfo.AgeRequest,
                top.dzou.grpc.MyDataInfo.StudentsListResponse>(
                  this, METHODID_GET_STUDENT_WRAPPER_BY_AGE)))
          .addMethod(
            getGetStreamStudentByAgeMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                top.dzou.grpc.MyDataInfo.AgeRequest,
                top.dzou.grpc.MyDataInfo.StudentResponse>(
                  this, METHODID_GET_STREAM_STUDENT_BY_AGE)))
          .build();
    }
  }

  /**
   */
  public static final class MyServiceStub extends io.grpc.stub.AbstractStub<MyServiceStub> {
    private MyServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MyServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MyServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MyServiceStub(channel, callOptions);
    }

    /**
     */
    public void getRealNameByUserName(top.dzou.grpc.MyDataInfo.MyRequest request,
        io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.MyResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetRealNameByUserNameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getStudentsByAge(top.dzou.grpc.MyDataInfo.AgeRequest request,
        io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.StudentResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetStudentsByAgeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.AgeRequest> getStudentWrapperByAge(
        io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.StudentsListResponse> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getGetStudentWrapperByAgeMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.AgeRequest> getStreamStudentByAge(
        io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.StudentResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getGetStreamStudentByAgeMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class MyServiceBlockingStub extends io.grpc.stub.AbstractStub<MyServiceBlockingStub> {
    private MyServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MyServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MyServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MyServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public top.dzou.grpc.MyDataInfo.MyResponse getRealNameByUserName(top.dzou.grpc.MyDataInfo.MyRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetRealNameByUserNameMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<top.dzou.grpc.MyDataInfo.StudentResponse> getStudentsByAge(
        top.dzou.grpc.MyDataInfo.AgeRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getGetStudentsByAgeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MyServiceFutureStub extends io.grpc.stub.AbstractStub<MyServiceFutureStub> {
    private MyServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MyServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MyServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MyServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<top.dzou.grpc.MyDataInfo.MyResponse> getRealNameByUserName(
        top.dzou.grpc.MyDataInfo.MyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetRealNameByUserNameMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_REAL_NAME_BY_USER_NAME = 0;
  private static final int METHODID_GET_STUDENTS_BY_AGE = 1;
  private static final int METHODID_GET_STUDENT_WRAPPER_BY_AGE = 2;
  private static final int METHODID_GET_STREAM_STUDENT_BY_AGE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MyServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MyServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_REAL_NAME_BY_USER_NAME:
          serviceImpl.getRealNameByUserName((top.dzou.grpc.MyDataInfo.MyRequest) request,
              (io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.MyResponse>) responseObserver);
          break;
        case METHODID_GET_STUDENTS_BY_AGE:
          serviceImpl.getStudentsByAge((top.dzou.grpc.MyDataInfo.AgeRequest) request,
              (io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.StudentResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_STUDENT_WRAPPER_BY_AGE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.getStudentWrapperByAge(
              (io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.StudentsListResponse>) responseObserver);
        case METHODID_GET_STREAM_STUDENT_BY_AGE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.getStreamStudentByAge(
              (io.grpc.stub.StreamObserver<top.dzou.grpc.MyDataInfo.StudentResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class MyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MyServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return top.dzou.grpc.MyDataInfo.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MyService");
    }
  }

  private static final class MyServiceFileDescriptorSupplier
      extends MyServiceBaseDescriptorSupplier {
    MyServiceFileDescriptorSupplier() {}
  }

  private static final class MyServiceMethodDescriptorSupplier
      extends MyServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MyServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (MyServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MyServiceFileDescriptorSupplier())
              .addMethod(getGetRealNameByUserNameMethod())
              .addMethod(getGetStudentsByAgeMethod())
              .addMethod(getGetStudentWrapperByAgeMethod())
              .addMethod(getGetStreamStudentByAgeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
