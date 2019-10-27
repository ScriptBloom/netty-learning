package top.dzou.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.NettyServerBuilder;

/**
 * @author dingxiang
 * @date 19-10-14 下午2:57
 */
public class GrpcServer {
    private Server server;
    private void start() throws Exception{
        int port = 8899;
        server = ServerBuilder.forPort(port)
                .addService(new StudentServiceImpl())
                .build().start();
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("关闭连接！");
            stop();
        }));
        System.out.println("异步调用，再关闭之前");
    }
    private void stop(){
        if(server!=null){
            server.shutdown();
        }
    }
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
    public static void main(String[] args) throws Exception, InterruptedException {
        GrpcServer s = new GrpcServer();
        s.start();
        s.blockUntilShutdown();
    }
}
