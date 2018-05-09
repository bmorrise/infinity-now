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

package org.morrise.ship;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.morrise.api.messages.CommandMessage;
import org.morrise.api.messages.MessageBroker;
import org.morrise.api.messages.QueueItem;
import org.morrise.api.models.character.Character;
import org.morrise.core.models.ship.Ship;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {

  private static final Logger logger = LogManager.getLogger( Server.class );
  private Queue<QueueItem<CommandMessage>> commandQueue;
  private Ship ship;
  private MessageBroker messageBroker = MessageBroker.getInstance();
  private Properties properties;

  public Server( String shipPath, String configPath ) throws IOException {
    commandQueue = new ConcurrentLinkedQueue<>();

    ShipLoader shipLoader = new ShipLoader();
    ship = shipLoader.load( shipPath );

    properties = new Properties();
    properties.load( new FileReader( configPath ) );
  }

  public static void main( String[] args ) throws Exception {
    Server server = new Server( args[0], args[1] );
    server.start();
//
//    Client client = new Client();
//    client.load();
  }

  public void start() throws Exception {

    String serverHost = properties.getProperty( "server.host" );
    int serverPort = Integer.valueOf( properties.getProperty( "server.port" ) );

    Configuration config = new Configuration();
    config.setHostname( serverHost );
    config.setPort( serverPort );

    SocketConfig socketConfig = new SocketConfig();
    socketConfig.setReuseAddress( true );
    config.setSocketConfig( socketConfig );

    config.setAuthorizationListener( handshakeData -> true );

    final SocketIOServer server = new SocketIOServer( config );
    messageBroker.setServer( server );

    try {
      server.addConnectListener( socketIOClient -> {
        logger.info( "Connecting as: " + socketIOClient.getSessionId() );
      } );

      server.addDisconnectListener( client -> {
        Character character = ship.getCharacterByClientId( client.getSessionId() );
        character.setUuid( null );
        messageBroker.sendEventBroadcast( MessageBroker.STATUS, character.getFullName() + " has disconnected." );
      } );

      server.addEventListener( "command", CommandMessage.class, ( client, data, ackRequest ) -> {
        logger.info( "Received command from: " + client.getSessionId() );
        QueueItem<CommandMessage> queueItem = new QueueItem<>( client.getSessionId(), data );
        commandQueue.add( queueItem );
      } );

      server.start();

      MessageProcessor messageProcessor = new MessageProcessor();

      int loop = 0;
      while ( true ) {
        if ( commandQueue.size() > 0 ) {
          QueueItem<CommandMessage> queueItem = commandQueue.poll();
          try {
            if ( !messageProcessor.process( ship, queueItem ) ) {
              break;
            }
          } catch ( Exception e ) {
            e.printStackTrace();
          }
        }
        if ( loop++ == 10000000 ) {
          ship.operate();
          loop = 0;
        }
      }
    } finally {
      logger.info( "Quitting..." );
      server.stop();
      logger.info( "done." );
    }
  }
}
