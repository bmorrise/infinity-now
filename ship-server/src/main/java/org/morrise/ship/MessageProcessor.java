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
import org.morrise.api.system.common.exception.InvalidAccessException;
import org.morrise.core.models.ship.CentralComputer;
import org.morrise.core.models.ship.Ship;
import org.morrise.core.models.ship.structure.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by bmorrise on 10/2/17.
 */
public class MessageProcessor {

  public static final String QUIT = "quit";
  public static final String JOIN = "join";
  private MainFrame mainFrame;

  public MessageProcessor( MainFrame mainFrame ) {
    this.mainFrame = mainFrame;
  }

  private static final Logger logger = LogManager.getLogger( Server.class );

  public boolean process( Ship ship, QueueItem<CommandMessage> queueItem ) {
    CommandMessage message = queueItem.getMessage();
    if ( message.getSystemKeyword() != null && message.getKeyword() != null ) {
      if ( message.isSystemKeyword( QUIT ) ) {
        return false;
      }

      if ( message.isSystemKeyword( JOIN ) ) {
        Character character = ship.getCharacterByUsername( message.getKeyword() );
        if ( character == null ) {
          mainFrame.sendEventSingle( queueItem.getClientId(), MainFrame.STATUS, "No character " + message
                  .getKeyword() );
          return true;
        }
        if ( !character.getPassword().equals( message.getPayload() ) ) {
          mainFrame.sendEventSingle( queueItem.getClientId(), MainFrame.STATUS, "Invalid password" );
          return true;
        }
        character.setUuid( queueItem.getClientId() );
        mainFrame.sendEventAll( character.getUuid(), MainFrame.STATUS, character.getFullName() + " has joined" +
                "." );
        mainFrame.sendEventSingle( character.getUuid(), MainFrame.STATUS, "Connected." );
        Map<String, List<String>> keywords = new TreeMap<>();
        keywords.putAll( ((Room) character.getCharacterContainer()).getKeywords() );
        keywords.putAll( character.getKeywords() );
        mainFrame.sendEventSingle( character.getUuid(), MainFrame.COMMANDS, keywords );
        List<String> possible = new ArrayList<>();
        possible.addAll( ((Room) character.getCharacterContainer()).getPossible( character ) );
        possible.addAll( character.getPossible() );
        mainFrame.sendEventSingle( character.getUuid(), "possible", possible );
        return true;
      }

      Character character = ship.getCharacterByClientId( queueItem.getClientId() );
      if ( character == null ) {
        mainFrame.sendEventSingle( queueItem.getClientId(), MainFrame.STATUS, "You have not joined yet." );
        return true;
      }

      CentralComputer centralComputer = ship.getCentralComputer();
      try {
        if ( centralComputer.isSystem( message.getSystemKeyword() ) ) {
          centralComputer.executeCommand( character, message );
        } else if ( character.isSystem( message.getSystemKeyword() ) ) {
          character.executeCommand( character, message );
        } else {
          mainFrame.sendEventSingle( queueItem.getClientId(), MainFrame.STATUS, "'" + message.getCommand() +
                  "' is not a valid command." );
        }
      } catch ( InvalidAccessException iae ) {
        logger.info( "You do not have access" );
      }
    }
    return true;
  }
}
