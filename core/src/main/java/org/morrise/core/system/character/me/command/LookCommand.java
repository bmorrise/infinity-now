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
import org.morrise.api.models.item.Item;
import org.morrise.api.models.item.types.Settable;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.command.Command;
import org.morrise.core.models.ship.structure.Room;
import org.morrise.core.system.character.me.MeSystem;

import java.util.List;

/**
 * Created by bmorrise on 10/4/17.
 */
@Command(system = MeSystem.class, keywords = {"look"})
public class LookCommand extends BaseCommand<MeSystem> {

  public LookCommand( MeSystem system ) {
    super( system );
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    Room room = (Room) character.getCharacterable();
    List<Item> items = room.getItems();
    system.sendMessage( character, MessageBroker.RESPONSE, room.getDescription() );
    StringBuilder output = new StringBuilder();
    output.append( "In room:\n" );
    output.append( "=======================\n" );
    output.append( "Adjacent Rooms:\n" );
    room.getAdjacent().forEach( adjacent -> {
      output.append( adjacent );
      output.append( "\n" );
    } );
    output.append( "Characters:\n" );
    room.getCharacters().forEach( character1 -> {
      output.append( character1.getNameAndRank() );
      output.append( "\n" );
    } );
    items.forEach( item -> {
      output.append( item.getName() );
      if ( item instanceof Settable ) {
        output.append( " with a " );
        ((Settable) item).getItems().forEach( item1 -> {
          output.append( item1.getName() );
          output.append( " and " );
        } );
        output.delete( output.length() - 4, output.length() );
        output.append( "on it.\n" );
      } else {
        output.append( "\n" );
      }
    } );
    output.append( "In hand:\n" );
    character.getItems().forEach( item -> {
      output.append( item.getName() );
      output.append( "\n" );
    } );
    system.sendMessage( character, MessageBroker.SECONDARY, output );
    return true;
  }
}
