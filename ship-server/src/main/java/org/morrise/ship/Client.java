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

import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;
import org.morrise.api.messages.JoinMessage;
import org.morrise.api.messages.MessageBroker;

import java.util.Properties;

/**
 * Created by bmorrise on 10/2/17.
 */
public class Client {

  private MessageBroker messageBroker = MessageBroker.getInstance();

  public void start() throws Exception {

    Properties properties = new Properties();
    properties.load( getClass().getClassLoader().getResourceAsStream( "config.properties" ) );

    String clientHost = properties.getProperty( "client.host" );
    int clientPort = Integer.valueOf( properties.getProperty( "client.port" ) );

    String url = "http://" + clientHost + ":" + clientPort;

    Socket client = IO.socket( url );
    client.on( Socket.EVENT_CONNECT, args -> {
      messageBroker.sendEventBroadcast( MessageBroker.RESPONSE, "Connected to space" );
      JoinMessage joinMessage = new JoinMessage( "Aries" );
      try {
        client.emit( "join", new JSONObject( joinMessage ) );
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    } ).on( "event", args -> {
    } ).on( Socket.EVENT_DISCONNECT, args -> {
    } );

    messageBroker.setClient( client );

    client.connect();
  }
}
