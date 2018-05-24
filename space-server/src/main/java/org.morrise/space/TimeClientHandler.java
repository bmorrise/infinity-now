package org.morrise.space;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class TimeClientHandler extends SimpleChannelInboundHandler<String> {

  @Override
  protected void channelRead0( ChannelHandlerContext channelHandlerContext, String s ) throws Exception {
    System.out.println( s );
  }

  @Override
  public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) {
    cause.printStackTrace();
    ctx.close();
  }
}
