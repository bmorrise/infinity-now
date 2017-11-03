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

package org.morrise.core.system.character.me.command;

import org.morrise.api.messages.CommandMessage;
import org.morrise.api.messages.MessageBroker;
import org.morrise.api.models.character.Character;
import org.morrise.api.models.character.CharacterState;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.command.Command;
import org.morrise.api.system.common.exception.InvalidAccessException;
import org.morrise.core.models.ship.structure.Deck;
import org.morrise.core.models.ship.structure.Room;
import org.morrise.core.system.character.me.MeSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by bmorrise on 10/4/17.
 */
@Command(system = MeSystem.class, keywords = {"enter"})
public class EnterCommand extends BaseCommand<MeSystem> {

  public static final int WALK_DURATION = 3000;

  public EnterCommand( MeSystem system ) {
    super( system );
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    new Thread( () -> {
      doProcess( character, arguments );
    } ).start();
    return true;
  }

  private void doProcess( Character character, String... arguments ) {
    logger.info( "Processing enter command..." );
    if ( character.getState().equals( CharacterState.SITTING ) ) {
      try {
        character.executeCommand( character, new CommandMessage( "me stand" ) );
      } catch ( InvalidAccessException e ) {
        e.printStackTrace();
      }
    }
    if ( arguments.length == 1 ) {
      Room room = (Room) character.getCharacterable();
      if ( room.isRoom( arguments[0] ) ) {
        system.sendMessage( character, MessageBroker.STATUS, "You are already in " + room.getName() );
      }
      if ( !room.getAdjacent().contains( arguments[0] ) ) {
        system.sendMessage( character, MessageBroker.STATUS, "There is no room " + arguments[0] );
      }
      Room enter = ((Deck) room.getParent()).getRoomByName( arguments[0] );
      if ( enter != null ) {
        system.sendMessage( character, MessageBroker.STATUS, "You are leaving " + room.getName() );
        try {
          Thread.sleep( WALK_DURATION );
        } catch ( Exception e ) {
          e.printStackTrace();
        }
        room.removeCharacter( character );
        enter.addCharacter( character );
        Map<String, List<String>> keywords = new TreeMap<>();
        keywords.putAll( enter.getKeywords() );
        keywords.putAll( character.getKeywords() );
        system.sendMessage( character, MessageBroker.COMMANDS, keywords );
        system.sendMessage( character, MessageBroker.STATUS, character.getNameAndRank() + " entered the room" );
        List<String> possible = new ArrayList<>();
        possible.addAll( ((Room) character.getCharacterable()).getPossible( character ) );
//        possible.addAll( character.getPossible( character ) );
        system.sendMessage( character, "possible", possible );
      } else {
        system.sendMessage( character, MessageBroker.STATUS, "No room " + arguments[0] );
      }
    }
  }
}
