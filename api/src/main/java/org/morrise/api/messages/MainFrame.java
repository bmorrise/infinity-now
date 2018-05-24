package org.morrise.api.messages;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.socket.client.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

/**
 * Created by bmorrise on 5/23/18.
 */
public class MainFrame {

  private static final Logger logger = LogManager.getLogger( MainFrame.class );

  public static final String RESPONSE = "response";
  public static final String STATUS = "status";
  public static final String COMMANDS = "commands";
  public static final String PERSIST = "persist";
  public static final String SECONDARY = "secondary";

  private ObjectMapper objectMapper = new ObjectMapper();
  private SocketIOServer server;
  private static MainFrame instance;
  private Socket client;

  private MainFrame( SocketIOServer server ) {
    this.server = server;
  }

  public String format( Object object ) {
    try {
      return objectMapper.writeValueAsString( object );
    } catch ( Exception e ) {
      return null;
    }
  }

  public static MainFrame get() {
    if ( instance == null ) {
//    Properties properties = new Properties();
//    properties.load( new FileReader( configPath ) );

      String serverHost = "localhost";
      int serverPort = 9092;

      Configuration config = new Configuration();
      config.setHostname( serverHost );
      config.setPort( serverPort );

      SocketConfig socketConfig = new SocketConfig();
      socketConfig.setReuseAddress( true );
      config.setSocketConfig( socketConfig );
      config.setAuthorizationListener( handshakeData -> true );

      SocketIOServer server = new SocketIOServer( config );
      server.start();

      server.addConnectListener( socketIOClient -> {
        logger.info( "Connecting as: " + socketIOClient.getSessionId() );
      } );

      instance = new MainFrame( server );
    }
    return instance;
  }

  public void stop() {
    server.stop();
  }

  public void disconnect( Request<Object> request ) {
    server.addDisconnectListener( socketIOClient -> request.call( socketIOClient.getSessionId().toString(), null ) );
  }

  public <T> void addListener( String eventName, Class<T> clazz, DataListener<T> listener  ) {
    server.addEventListener( eventName, String.class, ( client, data, ackRequest ) -> {
      T value = objectMapper.readValue( data, clazz );
      listener.onData( client, value, ackRequest );
    } );
  }

  public void command( Request<CommandMessage> request ) {
    server.addEventListener( "command", CommandMessage.class, ( client, data, ackRequest ) -> {
      request.call( client.getSessionId().toString(), data );
    } );
  }

  public interface Request<T> {
    void call( String clientId, T value );
  }

  public void sendEventSingle( String clientId, String event, Object data ) {
    if ( clientId != null ) {
      SocketIOClient socketIOClient = server.getClient( UUID.fromString( clientId ) );
      if ( socketIOClient != null ) {
        socketIOClient.sendEvent( event, data );
      }
    }
  }

  public void sendEventSingle( String clientId, String event ) {
    sendEventSingle( clientId, event, null );
  }

  public void sendEventSingle( String fromClientId, String toClientId, String event, Object data ) {
    if ( toClientId != null ) {
      SocketIOClient socketIOClient = server.getClient( UUID.fromString( toClientId ) );
      if ( socketIOClient != null ) {
        socketIOClient.sendEvent( event, data );
      }
    }
  }

  public void persist( String clientId ) {
    sendEventSingle( clientId, PERSIST );
  }

  public void sendEventAll( String clientId, String event, Object data ) {
    server.getAllClients().forEach( client -> {
      if ( client != null && !client.getSessionId().toString().equals( clientId ) ) {
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
