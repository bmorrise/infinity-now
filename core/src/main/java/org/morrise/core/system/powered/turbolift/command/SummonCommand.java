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
package org.morrise.core.system.powered.turbolift.command;

import org.morrise.api.messages.MainFrame;
import org.morrise.api.models.character.Character;
import org.morrise.api.system.command.BaseCommand;
import org.morrise.api.system.command.Command;
import org.morrise.core.models.ship.structure.Deck;
import org.morrise.core.models.ship.structure.Room;
import org.morrise.core.models.ship.structure.TurboLift;
import org.morrise.core.system.powered.turbolift.TurboLiftSystem;

/**
 * Implementation of the SummonCommand in the TurboLiftSystem
 */
@Command(system = TurboLiftSystem.class, keywords = {"summon"})
public class SummonCommand extends BaseCommand<TurboLiftSystem> {

  public SummonCommand( TurboLiftSystem system ) {
    super( system );
  }

  @Override
  public boolean process( Character character, String... arguments ) {
    new Thread( () -> {
      doProcess( character, arguments );
    } ).start();
    return true;
  }

  public boolean doProcess( Character character, String... arguments ) {
    if ( arguments.length == 1 ) {
      Room room = (Room) character.getCharacterContainer();
      if ( room.isAdjacent( arguments[0] ) ) {
        Deck deck = (Deck) room.getParent();
        TurboLift turboLift = (TurboLift) deck.getRoomByName( arguments[0] );
        system.sendMessage( character, MainFrame.STATUS, turboLift.getName() + " " +
                "is on its way." );
        turboLift.summon( deck, () -> system.sendMessage( character, MainFrame.STATUS, turboLift.getName() + " " +
                "has arrived." ) );
        return true;
      } else {
        system.sendMessage( character, MainFrame.STATUS, arguments[0] + " not found." );
      }
    }
    return false;
  }

}
