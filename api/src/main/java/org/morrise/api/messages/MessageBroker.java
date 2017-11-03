package org.morrise.api.messages;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import io.socket.client.Socket;

import java.util.UUID;

/**
 * Created by bmorrise on 9/25/17.
 */
public class MessageBroker {

  public static final String RESPONSE = "response";
  public static final String STATUS = "status";
  public static final String COMMANDS = "commands";
  public static final String PERSIST = "persist";
  public static final String SECONDARY = "secondary";

  private static MessageBroker instance;
  private SocketIOServer server;
  private Socket client;

  public static MessageBroker getInstance() {
    if ( instance == null ) {
      instance = new MessageBroker();
    }
    return instance;
  }

  public void sendEventSingle( UUID clientId, String event, Object data ) {
    if ( clientId != null ) {
      SocketIOClient socketIOClient = server.getClient( clientId );
      if ( socketIOClient != null ) {
        socketIOClient.sendEvent( event, data );
      }
    }
  }

  public void sendEventSingle( UUID clientId, String event ) {
    sendEventSingle( clientId, event, null );
  }

  public void sendEventSingle( UUID fromClientId, UUID toClientId, String event, Object data ) {
    if ( toClientId != null ) {
      SocketIOClient socketIOClient = server.getClient( toClientId );
      if ( socketIOClient != null ) {
        socketIOClient.sendEvent( event, data );
      }
    }
  }

  public void persist( UUID clientId ) {
    sendEventSingle( clientId, PERSIST );
  }

  public void sendEventAll( UUID clientId, String event, Object data ) {
    server.getAllClients().forEach( client -> {
      if ( client != null && !client.getSessionId().equals( clientId ) ) {
        client.sendEvent( event, data );
      }
    } );
  }

  public void sendEventBroadcast( String event, Object data ) {
    server.getBroadcastOperations().sendEvent( event, data );
  }

  public void setServer( SocketIOServer server ) {
    this.server = server;
  }

  public void setClient( Socket client ) {
    this.client = client;
  }
}
