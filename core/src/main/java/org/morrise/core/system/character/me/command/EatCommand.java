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
import org.morrise.api.models.item.FoodItem;
import org.morrise.api.models.item.Item;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.command.Command;
import org.morrise.core.system.character.me.MeSystem;

/**
 * Created by bmorrise on 10/4/17.
 */
@Command(system = MeSystem.class, keywords = {"eat"})
public class EatCommand extends BaseCommand<MeSystem> {

  private static final String EATS = "eats";

  public EatCommand( MeSystem system ) {
    super( system );
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    logger.info( "Processing eat command..." );
    if ( arguments.length == 0 ) {
      system.sendMessage( character, MainFrame.STATUS, "Nothing to eat." );
    } else {
      Item item = character.getItemByName( arguments[0] );
      if ( item != null ) {
        if ( item.getState().equals( FoodItem.FoodState.EATEN ) ) {
          system.sendMessage( character, MainFrame.STATUS, "You have already eaten the " + item.getName() );
          return false;
        }
        if ( item.getState().equals( FoodItem.FoodState.PENDING ) ) {
          system.sendMessage( character, MainFrame.STATUS, "You are already eating the " + item.getName() );
          return false;
        }
        if ( item.use( character ) ) {
          character.removeItem( item );
        }
        system.sendMessage( character, MainFrame.RESPONSE, "You start eating the " + item.getName() );
      } else {
        system.sendMessage( character, MainFrame.STATUS, "You do not have the " + arguments[0] );
      }
    }
    return true;
  }
}
