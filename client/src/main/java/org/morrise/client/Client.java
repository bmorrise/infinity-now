package org.morrise.client;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by bmorrise on 9/25/17.
 */
public class Client {

  public static void main( String[] args ) throws Exception {
    Socket socket = IO.socket( "http://localhost:9092" );
    socket.on( "chatevent", new Emitter.Listener() {
      @Override
      public void call( Object... objects ) {
        System.out.println( objects[0] );
      }
    } );
    socket.connect();
  }

}
