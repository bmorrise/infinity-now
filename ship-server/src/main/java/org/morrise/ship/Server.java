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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.morrise.api.messages.CommandMessage;
import org.morrise.api.messages.MainFrame;
import org.morrise.api.messages.QueueItem;
import org.morrise.api.models.character.Character;
import org.morrise.core.models.ship.Ship;

import java.io.IOException;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {

  private static final Logger logger = LogManager.getLogger( Server.class );
  private Queue<QueueItem<CommandMessage>> commandQueue;
  private Ship ship;
  private Properties properties;

  public Server( String shipPath, String configPath ) throws IOException {
    commandQueue = new ConcurrentLinkedQueue<>();

    ShipLoader shipLoader = new ShipLoader();
    ship = shipLoader.load( shipPath );
  }

  public static void main( String[] args ) throws Exception {
    ShipBuilder.build();

    Server server = new Server( args[0], args[1] );
    server.start();

//    Client client = new Client();
//    client.load();
  }

  public void start() throws Exception {

    MainFrame mainFrame = MainFrame.get();

    try {
      mainFrame.disconnect( (clientId, o ) -> {
        Character character = ship.getCharacterByClientId( clientId );
        character.setUuid( null );
        mainFrame.sendEventBroadcast( MainFrame.STATUS, character.getFullName() + " has disconnected." );
      } );

      mainFrame.command( (clientId, commandMessage) -> {
        logger.info( "Received command from: " + clientId );
        QueueItem<CommandMessage> queueItem = new QueueItem<>( clientId, commandMessage );
        commandQueue.add( queueItem );
      } );

      MessageProcessor messageProcessor = new MessageProcessor( mainFrame );

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
      mainFrame.stop();
      logger.info( "done." );
    }
  }
}
