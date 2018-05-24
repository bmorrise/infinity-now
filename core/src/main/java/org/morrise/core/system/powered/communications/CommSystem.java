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

package org.morrise.core.system.powered.communications;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.morrise.api.messages.MainFrame;
import org.morrise.api.models.character.Character;
import org.morrise.api.models.character.CharacterContainer;
import org.morrise.api.system.annotation.System;
import org.morrise.api.system.powered.BasePoweredSystemable;
import org.morrise.api.system.powered.PoweredSystem;
import org.morrise.core.models.ship.CentralComputer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bmorrise on 9/18/17.
 */
@System
public class CommSystem extends PoweredSystem<CentralComputer> {

  public static final String TYPE = "communications";
  public static final String KEYWORD = "communications";

  private static final Logger logger = LogManager.getLogger( CommSystem.class );
  private Map<String, Channel> channels = new HashMap<>();
  private Thread thread;

  public CommSystem( CentralComputer entity ) {
    super( entity );
//    super( 50, 100 );
//    capabilities.add( new RangeCapability( 100 ) );
//    this.status = new OperationalStatus();
  }

  @Override
  public String getType() {
    return TYPE;
  }

  @Override
  public String getKeyword() {
    return KEYWORD;
  }

  public Map<String, Channel> getChannels() {
    return channels;
  }

  public void setChannels( Map<String, Channel> channels ) {
    this.channels = channels;
  }

  public void connectCloseProximity() {

  }

  public void connect( String entity ) {

  }

  private void waitForResponse( Channel channel ) {
    if ( thread != null && thread.isAlive() ) {
      thread.interrupt();
    }
    thread = new Thread( () -> {
      try {
        Thread.sleep( 10000 );
        if ( channel.isStatus( Channel.Status.PENDING ) ) {
          channel.setStatus( Channel.Status.CLOSED );
          status( "No response" );
        }
      } catch ( InterruptedException ie ) {
        // Do nothing
      }
    } );
    thread.start();
  }

  private void status( String message ) {
    ((CharacterContainer) getSystemable()).getCharacters().forEach( character -> sendMessage( character, message ) );
  }

  public void sendHail( CommSystem destinationComm ) {

  }

  public void sendSingle( Character from, Character to, Object data ) {
    getMainFrame().sendEventSingle( from.getUuid(), to.getUuid(), MainFrame.RESPONSE, data );
    getMainFrame().persist( from.getUuid() );
  }

  public void sendMessage( Character character, Object message ) {
    sendMessage( character, MainFrame.RESPONSE, message );
  }

  public void sendMessage( Message message ) {

  }

  public void receivedMessage( Message<String> message ) {

  }

  public void sendAll( Character character, Object data ) {
    getMainFrame().sendEventAll( character.getUuid(), MainFrame.RESPONSE, data );
  }

  public void addChannel( String entity, Channel channel ) {
    channels.put( entity.toLowerCase(), channel );
  }

  public Collection<Channel> getAllChannels() {
    return channels.values();
  }

  public Channel getChannelByEntity( String entity ) {
    return channels.get( entity );
  }

  @Override
  public String toString() {
    return "Comm system of " + entity.getName();
  }

  @Override
  public String getDescription() {
    return "Communications";
  }

  public void broadcastMessage( String message ) {
    ((CharacterContainer) entity).getCharacters().forEach( character -> sendMessage( character, message ) );
  }
}
