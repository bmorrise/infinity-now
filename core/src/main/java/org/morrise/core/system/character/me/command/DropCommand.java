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

/**
 * Created by bmorrise on 10/4/17.
 */
@Command(system = MeSystem.class,
        keywords = {"drop", "set"},
        validCommands = {"(.*) on (.*)", "(.*)"})
public class DropCommand extends BaseCommand<MeSystem> {

  public DropCommand( MeSystem system ) {
    super( system );
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    logger.info( "Processing drop command..." );
    Room room = ((Room) character.getCharacterable());
    Item item = character.getItemByName( arguments[0] );
    if ( arguments.length == 1 ) {
      if ( item != null ) {
        character.removeItem( item );
        room.addItem( item );
        system.sendMessage( character, MessageBroker.STATUS, "You dropped the " + arguments[0] );
      } else {
        system.sendMessage( character, MessageBroker.STATUS, "Nope." );
      }
    }
    if ( arguments.length == 2 ) {
      Item item2 = room.getItemByName( arguments[1] );
      if ( item2 != null && item2 instanceof Settable ) {
        character.removeItem( item );
        ((Settable) item2).addItem( item );
      }
    }
    return true;
  }
}
