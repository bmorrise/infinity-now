package org.morrise.space;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TimeClient {
  public static void main(String[] args) throws Exception {
    String host = "localhost";
    int port = 8080;
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      Bootstrap b = new Bootstrap(); // (1)
      b.group(workerGroup); // (2)
      b.channel(NioSocketChannel.class); // (3)
      b.option( ChannelOption.SO_KEEPALIVE, true); // (4)
      b.handler(new ChannelInitializer() {
        @Override
        protected void initChannel( Channel ch ) throws Exception {
          ch.pipeline().addLast(new StringDecoder(),
                  new StringEncoder(),
                  new TimeClientHandler());
        }
      });

      // Start the client.
      ChannelFuture f = b.connect(host, port).sync(); // (5)
      Channel channel = f.channel();
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      while (true){
        channel.writeAndFlush( in.readLine() );
      }
      // Wait until the connection is closed.
      //f.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
    }
  }
}
