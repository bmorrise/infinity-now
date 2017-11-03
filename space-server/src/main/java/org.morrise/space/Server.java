/*
 * Copyright 2017 Benjamin Morrise
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.morrise.space;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.morrise.api.messages.JoinMessage;

import java.util.Properties;

/**
 * Created by bmorrise on 9/29/17.
 */
public class Server {
  private static final Logger logger = LogManager.getLogger( Server.class );

  public static void main( String[] args ) throws Exception {
    Server server = new Server();
    server.start();
  }

  public void start() throws Exception {
    Properties properties = new Properties();
    properties.load( getClass().getClassLoader().getResourceAsStream( "config.properties" ) );
    String host = properties.getProperty( "host" );
    int port = Integer.valueOf( properties.getProperty( "port" ) );

    Configuration config = new Configuration();
    config.setHostname( host );
    config.setPort( port );

    SocketConfig socketConfig = new SocketConfig();
    socketConfig.setReuseAddress( true );
    config.setSocketConfig( socketConfig );

    config.setAuthorizationListener( handshakeData -> true );

    final SocketIOServer server = new SocketIOServer( config );

    server.addConnectListener( socketIOClient -> logger.info( "Connecting as: " + socketIOClient.getSessionId() ) );
    server.addEventListener( "join", JoinMessage.class, ( client, data, ackRequest ) -> {
      logger.info( "Received command from: " + client.getSessionId() );
      logger.info( data.getUsername() );
    } );
    server.start();

    Thread.sleep( Integer.MAX_VALUE );

  }

}
