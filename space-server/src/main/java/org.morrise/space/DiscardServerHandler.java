package org.morrise.space;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends SimpleChannelInboundHandler<String> {

  private ChannelGroup channelGroup;

  public DiscardServerHandler( ChannelGroup channelGroup ) {
    this.channelGroup = channelGroup;
  }

  @Override
  public void channelActive( ChannelHandlerContext ctx ) {
    System.out.println( ctx.channel().id() );
    channelGroup.add( ctx.channel() );
  }

  @Override
  public void exceptionCaught( ChannelHandlerContext ctx, Throwable cause ) {
    // Close the connection when an exception is raised.
    cause.printStackTrace();
    ctx.close();
  }

  @Override
  protected void channelRead0( ChannelHandlerContext ctx, String s ) throws Exception {
    System.out.println( s );
    channelGroup.forEach( c -> {
      System.out.println( c.id() );
      c.writeAndFlush( s );
    } );
  }
}
