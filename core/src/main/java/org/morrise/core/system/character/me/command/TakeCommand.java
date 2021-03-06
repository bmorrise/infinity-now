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

import org.morrise.api.messages.MainFrame;
import org.morrise.api.models.character.Character;
import org.morrise.api.models.item.Item;
import org.morrise.api.models.item.Itemable;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.command.Command;
import org.morrise.core.models.ship.structure.Room;
import org.morrise.core.system.character.me.MeSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmorrise on 10/4/17.
 */
@Command(system = MeSystem.class, keywords = {"take", "grab"})
public class TakeCommand extends BaseCommand<MeSystem> {

  public TakeCommand( MeSystem system ) {
    super( system );
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    logger.info( "Processing speak command..." );
    Room room = ((Room) character.getCharacterContainer());
    Item item = room.getItemByName( arguments[0] );
    if ( item != null ) {
      character.addItem( item );
      room.removeItem( item );
      system.sendMessage( character, MainFrame.STATUS, "You took the " + item.getName() );
    } else {
      system.sendMessage( character, MainFrame.STATUS, "Nope." );
    }
    return true;
  }

//  @Override
  public List<String> getPossible( Character character ) {
    List<String> possible = new ArrayList<>();
    if ( character.getCharacterContainer() instanceof Itemable ) {
      List<Item> items = ((Itemable) character.getCharacterContainer()).getItems();
      items.forEach( item -> possible.add( item.getName() ) );
      character.getItems().forEach( item -> possible.add( item.getName() ) );
    }
    return possible;
  }
}
