package org.morrise.api.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.morrise.api.messages.RequestMessage;

import java.net.URISyntaxException;

/**
 * Created by bmorrise on 5/23/18.
 */
public class Conduit {

  public static final String HANDSHAKE = "handshake";
  public static final String COMMAND = "command";
  public static final String RESPONSE = "response";

  private ObjectMapper objectMapper = new ObjectMapper();
  private Socket socket;

  public Conduit( Socket socket ) {
    this.socket = socket;
  }

  public static Conduit get() throws URISyntaxException {
    String clientHost = "localhost";
    int clientPort = 9092;
    String url = "http://" + clientHost + ":" + clientPort;
    Conduit conduit = new Conduit( IO.socket( url ) );
    conduit.connect();
    return conduit;
  }

  public <T> Emitter on( String event, Class<T> clazz, Response<T> response ) {
    return socket.on( event, objects -> {
      T object;
      try {
        object = objectMapper.readValue( (String) objects[0], clazz );
      } catch ( Exception e ) {
        object = null;
      }
      response.call( object );
    } );
  }

  public void emit(final String event, final Object object ) {
    try {
      socket.emit( event, objectMapper.writeValueAsString( object ) );
    } catch ( JsonProcessingException jpe ) {
      jpe.printStackTrace();
    }
  }

  public Emitter on(String event, Emitter.Listener fn) {
    if ( socket != null ) {
      return socket.on( event, fn );
    }
    return null;
  }

  public void connect() {
    socket.connect();
  }

  public interface Response<T> {
    void call( T object );
  }

  public void handshake( Object object ) {
    emit( HANDSHAKE, object );
  }

  public void connect( Response<Object> response ) {
    socket.on( Socket.EVENT_CONNECT, response::call );
  }

  public void command( Response<RequestMessage> response ) {
    on( COMMAND, RequestMessage.class, response );
  }

  public void respond( Object object ) {
    emit( RESPONSE, object );
  }
}
