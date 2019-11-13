## Netty和NIO的学习典型案例
- netty-first netty入门案例，创建一个服务器
- netty-2-socket netty创建一个服务器和客户端交互,netty单客户端单服务器发一条消息
- netty-3-mult-client netty广播消息，类似群发
- netty-4-heartBeat netty心跳检测机制—IdleStateHandler读写空闲检查
- netty-5-websocket netty使用websocket长连接实现的多人聊天室
- netty-6-google-protobuf google序列化实现protobuf 单个ProtoBuf类型的传输.使用enum实现的多个ProtoBuf类型的传输：一个总类型，枚举类中包含多个不同的类型，总类型就类似与多个类的统一借口
- netty-7-grpc-demo grpc实现rpc调用 客户端服务器单个数据传递
- netty-8-grpc-stream-demo grpc实现rpc调用 客户端服务器stream流数据传递
  - 客户端单个，服务端单个
  - 客户端流，服务端单个
  - 客户端单个，服务端流
  - 客户端流，服务端流
  
  客户端单个：同步
  客户端流：异步
- netty-9-nio Non-blockingIO基本使用
- netty-10-nio-multi-port-client 基于NIO实现的多端口服务器网页端手机端数据传输
- netty-11-wxq-websocket-socket 基于Netty实现的多端口服务器数据传输(网页端和是服务器长连接，手机端和服务器socket连接)

## netty-11-wxq-websocket-socket

最近正在学习netty，跟着教程写了一个基于WebSocket的网页聊天室，对netty有了一定的了解，现在正好项目使用到长连接，选用了netty。

**项目目标：客户端A(网页)和服务端通过WebSocket进行通信，客户端B和服务端通过Socket通信，把客户端B的数据传输到客户端A，桥梁为服务端**

> Socket服务端监听8090端口，长连接服务端监听8089端口，客户端A连接到8089端口，客户端B连接到8090端口

![](http://dzou.wangminwei.top/static/images/netty/1.png)


> 由于是需要对两个端口数据进行不同处理，所以我们创建两个`ServerBootstrap`，分别绑定两个端口，一个`ServerGzhBootstrap`处理客户端B和服务端的socket通信；`ServerWxQBootstrap`处理客户端A和服务端之间的WebSocket长连接通信

- **`ServerInitializer`，实现`ChannelInitializer`，负责初始化客户端B和服务端通信的处理器Handler**
- **`WebSocketChannelInitializer`，实现`ChannelInitializer`，负责初始化客户端A和服务端长连接通信的处理器Handler**
- **`ServerInitializer`添加一个自定义`SimpleChannelInboundHandler`负责处理客户端B和服务端socket通信**
- **`WebSocketChannelInitializer`添加一个自定义`SimpleChannelInboundHandler`负责处理客户端A和服务端WebSocket长连接通信**

> 网页聊天室作为客户端A，客户端B通过Socket通信并接收控制台的输入作为通信数据传递给服务端，服务端再传递给客户端A



问题：

netty中SimpleChannelInboundHandler类的泛型中指定了传入的消息的类型，只能接收这种类型的消息，客户端B发送的String类型消息与客户端A接收的TextWebSocketFrame类型不同，客户端A无法接收。

解决方法：

我们把客户端B发送的String类型消息在Socket服务端接收到，要将其发送给客户端A(需要将其封装成TextWebSocketFrame类型才能发送给客户端A)，而且我们就必须要有客户端A的channel，我们才可以调用`writeAndFlush`方法把数据写入客户端A

**使用什么可以得到客户端A的channel呢？**

那就是`ChannelGroup`，我们定义一个类保存全部Channel客户端作为全局ChannelGroup，每次有客户端Channel创建(`handlerAdded`方法)，我们就把它保存到该全局ChannelGroup中，每次channel使用完毕，ChannelGroup会为我们自动删除其中无用的channel，这样我们就可以获取所有的客户端channel

**任何获取到客户端A的channel？**

客户端A和客户端B很大一个区别就是端口号，我们可以通过端口号来判断是客户端A还是客户端B



- 全局ChannelGroup

```java
public class GlobalChannelGroup {
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
```



### 服务端

- 服务端启动器

```java
public class Server {
    public static void main(String[] args) throws InterruptedException {
        //两个事件循环组 boss获取连接发送 worker接收处理
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            //server启动器
            ServerBootstrap serverWxQBootstrap = new ServerBootstrap();
            ServerBootstrap serverGzhBootstrap = new ServerBootstrap();
            // 定义组
            // channel(反射)
            // 定义处理器(自定义)：连接上channel后执行init
            System.out.println("启动server");
            serverGzhBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer());
            serverWxQBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketChannelInitializer());
            //绑定端口，同步
            ChannelFuture wxq = serverGzhBootstrap.bind(8090).sync();
            ChannelFuture gzh = serverWxQBootstrap.bind(8089).sync();
            gzh.channel().closeFuture().sync();
            wxq.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
```

- 服务端Socket端口初始化器

```java
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));//用于解码
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));//用于编码
        pipeline.addLast(new ServerHandler());//自定义处理器
    }
}
```

- 服务端Socket端口通信处理器

```java
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+","+msg);
        ctx.channel().writeAndFlush("消息已经进入数据库，正在赶往微信墙!");
        GlobalChannelGroup.channelGroup.forEach(o->{
            //如果端口以8089结尾，说明这个channel是客户端A
            if (o.localAddress().toString().endsWith("8089")){
                TextWebSocketFrame text = new TextWebSocketFrame(o.remoteAddress() + "发送消息：" + msg + "\n");
                o.writeAndFlush(text);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+":连接到微信墙模式成功!");
        int size = GlobalChannelGroup.channelGroup.size();
        System.out.println("当前微信墙连接数:"+(size==0?0:size-1));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        GlobalChannelGroup.channelGroup.add(channel);
    }
}
```

- 服务端长连接通信端口初始化器

```java
 public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        //用于将http数据聚合到一起发送一个请求 fullHttpRequest
        pipeline.addLast(new HttpObjectAggregator(8192));
        pipeline.addLast(new WebSocketServerProtocolHandler("/"));//传入websocket path
        pipeline.addLast(new TextWebSocketHandler());//传入websocket path
    }
}
```

- 服务端长连接通信处理器

```java
public class TextWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        int size = GlobalChannelGroup.channelGroup.size();
        System.out.println("当前微信墙连接数:"+(size==0?0:size-1));
        System.out.println("收到消息："+msg.text());
        Channel channel = ctx.channel();
        GlobalChannelGroup.channelGroup.forEach(o->{
            if (o.localAddress().toString().endsWith("8090")){
                o.writeAndFlush(msg.text());
            }else {
                TextWebSocketFrame text = new TextWebSocketFrame(o.remoteAddress() + "发送消息：" + msg.text() + "\n");
                o.writeAndFlush(text);
            }
        });
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel ch = ctx.channel();
        GlobalChannelGroup.channelGroup.add(ch);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+":离开聊天室");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel ch = ctx.channel();
        System.out.println(ch.remoteAddress()+"：连接到聊天室");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常");
        ctx.close();
    }
}
```

### 客户端

> 客户端把控制台的标准输入作为参数传入，创建客户端channel时将其发送

- 客户端B启动器

```java
public class GzhClient {
    public static void main(String[] args) {
        EventLoopGroup eventExecutors = null;
        ChannelFuture channelFuture = null;
        try{
//            while (true) {
                eventExecutors = new NioEventLoopGroup();
                Scanner scanner = new Scanner(System.in);
                String json = scanner.nextLine();
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(eventExecutors).channel(NioSocketChannel.class)
                        .handler(new GzhClientInitializer(json));
                System.out.println("启动客户端");
                channelFuture = bootstrap.connect("localhost", 8090).sync();
//            }
//            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
```

- 客户端B初始化器

```java
public class GzhClientInitializer extends ChannelInitializer<SocketChannel> {
    private String json;
    public GzhClientInitializer(String json){
        this.json = json;
    }
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new GzhClientHandler(json));
    }
}
```

- 客户端B与服务端Socket通信处理器

```java
public class GzhClientHandler extends SimpleChannelInboundHandler<String> {
    private String json;
    public GzhClientHandler(String json){
        this.json = json;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("client receive:"+msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("进入微信墙模式，清发消息:");
        ctx.writeAndFlush(json);
    }
}
```



### 测试

1. 启动服务端，创建两个客户端B(也就是GzhClient)
2. 打开聊天室，建立长连接
3. 两个客户端B从控制台发送消息

![](http://dzou.wangminwei.top/static/images/netty/3.png)

服务端接收到消息，打印出来并准备转发给客户端A(也就是网页聊天室)

![](http://dzou.wangminwei.top/static/images/netty/2.png)

网页聊天室接收到服务端发送的消息

![](http://dzou.wangminwei.top/static/images/netty/4.png)