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

import org.morrise.api.messages.MessageBroker;
import org.morrise.api.models.character.Character;
import org.morrise.api.models.character.CharacterState;
import org.morrise.api.models.item.Item;
import org.morrise.api.models.item.Itemable;
import org.morrise.api.models.item.types.Sitable;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.command.Command;
import org.morrise.core.models.item.furniture.Chair;
import org.morrise.core.system.character.me.MeSystem;

import java.util.Collections;
import java.util.List;

/**
 * Created by bmorrise on 10/4/17.
 */
@Command(system = MeSystem.class,
        keywords = {"sit"},
        validCommands = {"on (.*)", "(.*)"})
public class SitCommand extends BaseCommand<MeSystem> {

  public SitCommand( MeSystem system ) {
    super( system );
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    if ( character.getState().equals( CharacterState.SITTING ) ) {
      system.sendMessage( character, MessageBroker.STATUS, "You are already sitting" );
      return true;
    }
    List<Item> items;
    if ( arguments.length == 1 ) {
      Item item = ((Itemable) character.getCharacterable()).getItemByName( arguments[0] );
      items = Collections.singletonList( item );
    } else {
      items = ((Itemable) character.getCharacterable()).getItemsByType( Sitable.class );
    }
    if ( items.size() == 1 ) {
      Item item = items.get( 0 );
      if ( !(item instanceof Sitable) ) {
        system.sendMessage( character, MessageBroker.STATUS, "You can not sit on " + item.getName() );
        return true;
      }
      if ( item.getState().equals( Chair.ChairState.OCCUPIED ) ) {
        system.sendMessage( character, MessageBroker.STATUS, "The chair is occupied" );
        return true;
      }
      item.use( character );
      system.sendMessage( character, MessageBroker.RESPONSE, String.format( "%s sits down on %s", character
              .getNameAndRank(), item.getName() ) );
    } else {
      if ( items.size() > 0 ) {
        for ( Item item : items ) {
          if ( item instanceof Sitable && item.getState().equals( Chair.ChairState.UNOCCUPIED ) ) {
            item.use( character );
            system.sendMessage( character, MessageBroker.RESPONSE, String.format( "%s sits down in" + " %s",
                    character.getNameAndRank(), item.getName() ) );
            return true;
          }
        }
        system.sendMessage( character, MessageBroker.STATUS, "All the chairs are occupied" );
      }
    }
    return true;
  }
}
